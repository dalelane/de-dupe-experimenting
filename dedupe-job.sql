-- -------------------------------------------------------------------
-- de-duplication job
-- -------------------------------------------------------------------
--
-- given events like this:
--     {
--         "entity": "CCCCCCCC",
--         "value": 40
--     }
--
-- keep track of the last value for each entity, and only output an
--  event if it has a new or different value
--
-- NOTE: Kafka cluster config needs to be updated to run this
--         (certificates, username/password, bootstrap address)
--
-- -------------------------------------------------------------------


SET 'pipeline.name' = 'unique-values-deduplication';


--
-- Define the DEDUPE.INPUT topic to use for input
--
--  Events on this topic will contain two fields: entity and value
--

CREATE TABLE INPUT
(
    `entity`     STRING NOT NULL,
    `value`      INT NOT NULL,
    `event_time` TIMESTAMP(3) METADATA FROM 'timestamp',
    WATERMARK FOR `event_time` AS `event_time`
)
WITH (
    'connector' = 'kafka',
    'topic' = 'DEDUPE.INPUT',
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


--
-- Define the DEDUPE.OUTPUT topic to use for output
--
--  Events on this topic will have identical payloads and timestamps to input
--

CREATE TABLE OUTPUT
(
    `entity`     STRING NOT NULL,
    `value`      INT NOT NULL,
    `event_time` TIMESTAMP(3) METADATA FROM 'timestamp',
    PRIMARY KEY (`entity`) NOT ENFORCED
)
WITH (
    'connector' = 'upsert-kafka',
    'topic' = 'DEDUPE.OUTPUT',
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


-- aggregate events by entity, using LAG to keep the previous value
--  for each entity for comparison

CREATE TEMPORARY VIEW DELTAS AS
    WITH change_tracker AS (
        SELECT
            `entity`,
            `value`,
            `event_time`,
            LAG(`value`, 1) OVER (PARTITION BY `entity` ORDER BY `event_time`) previous_val
        FROM
            INPUT
    )
    SELECT
        `event_time`, `entity`, `value`
    FROM
        change_tracker
    WHERE
        `previous_val` IS NULL OR `previous_val` <> `value`;
    -- notice that "different" here just involves comparing a single value
    --  but with more complex events, this could be an expression that
    --  looked for differences in multiple attributes

-- run the temporary view as a long-running job,
--  producing the results into the output topic

INSERT INTO `OUTPUT`
    SELECT
        `entity`, `value`, `event_time`
    FROM
        `DELTAS`;
