-- -------------------------------------------------------------------
-- identify deletions job
-- -------------------------------------------------------------------
--
-- given events like this:
--     {
--          "id": "AAAAAAAA",
--          "value": 100,
--          "timestamp": "2023-11-06 10:00:00.000"
--     }
--
-- group events in 1-day windows
--  if there is an event for a given id on any day,
--   and no event for the same id on the following day,
--   emit an event to declare it has been detected as deleted
--
-- -------------------------------------------------------------------

CREATE TABLE INPUT
(
    `id`            STRING,
    `value`         INT,
    `timestamp`     TIMESTAMP(3),
    WATERMARK FOR `timestamp` AS `timestamp` - INTERVAL '0' MINUTE
)
WITH (
    'connector' = 'kafka',
    'topic' = 'DEDUPE.DELETES.INPUT',
    'properties.bootstrap.servers' = 'event-streams-bootstrap-address:443',
    'scan.startup.mode' = 'earliest-offset',
    'format' = 'json',
    'properties.security.protocol' = 'SASL_SSL',
    'properties.sasl.mechanism' = 'SCRAM-SHA-512',
    'properties.ssl.truststore.type' = 'PEM',
    'properties.tls.pemChainIncluded' = 'false',
    'properties.ssl.endpoint.identification.algorithm' = '',
    'properties.ssl.truststore.certificates' = '-----BEGIN CERTIFICATE----- <snip> -----END CERTIFICATE-----',
    'properties.sasl.jaas.config' = 'org.apache.kafka.common.security.scram.ScramLoginModule required username="event-streams-username" password="event-streams-password";'
);


CREATE TABLE OUTPUT
(
    `deleted`    STRING NOT NULL,
    `event_time` TIMESTAMP(3) METADATA FROM 'timestamp',
    PRIMARY KEY (`deleted`) NOT ENFORCED
)
WITH (
    'connector' = 'upsert-kafka',
    'topic' = 'DEDUPE.DELETES.OUTPUT',
    'properties.bootstrap.servers' = 'event-streams-bootstrap-address:443',
    'key.format' = 'raw',
    'value.format' = 'json',
    'properties.security.protocol' = 'SASL_SSL',
    'properties.sasl.mechanism' = 'SCRAM-SHA-512',
    'properties.ssl.truststore.type' = 'PEM',
    'properties.tls.pemChainIncluded' = 'false',
    'properties.ssl.endpoint.identification.algorithm' = '',
    'properties.ssl.truststore.certificates' = '-----BEGIN CERTIFICATE----- <snip> -----END CERTIFICATE-----',
    'properties.sasl.jaas.config' = 'org.apache.kafka.common.security.scram.ScramLoginModule required username="event-streams-username" password="event-streams-password";'
);


--
-- discard unmodified state after 72 hours
--
SET table.exec.state.ttl=259200000;


--
-- group events into 1-day windows
--
CREATE TEMPORARY VIEW days_events AS
    SELECT
        *
    FROM
        TABLE(TUMBLE(TABLE INPUT, DESCRIPTOR(`timestamp`), INTERVAL '1' DAY));

--
-- what is the most recent day we have events for?
--
CREATE TEMPORARY VIEW latestevent AS
    SELECT
        MAX(window_start) as latestday
    FROM
        days_events;


-- identify events that were seen in the "day before"
--   but not in the "day after"
CREATE TEMPORARY VIEW deletedevents AS
    SELECT
        daybefore.id as deleted,
        daybefore.window_end as event_time
    FROM
        days_events daybefore
    LEFT JOIN
        days_events dayafter on
            -- matching events based on id
            daybefore.id = dayafter.id
                AND
            -- matching "day before" to "day after" based on
            --  being adjacent windows
            daybefore.window_end = dayafter.window_start
    JOIN
        -- only consider events where the latest day is the
        --   "day after"
        --  (for today's events - where today is the "day before",
        --   we can't know if those events are deleted until tomorrow)
        latestevent on
            daybefore.window_end = latestevent.latestday - INTERVAL '1' DAY
    WHERE
        -- filter on events that are not present in
        --  the "day after"
        dayafter.id IS NULL;


-- insert results into output topic
INSERT INTO `OUTPUT`
    SELECT
        deleted, event_time
    FROM
        deletedevents;