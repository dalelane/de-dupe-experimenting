## Overview

Example of using Flink SQL to use the contents of an event to identify duplicates and emit a new stream of events that only contains new events

Flink SQL can be found in [`dedupe-job.sql`](./dedupe-job.sql).

## Requirements

For this example, events look like this:

```json
{
    "entity": "BBBBBBBB",
    "value":  1234
}
```

Each event is compared with the most recent event with the same `entity` value.

If this is the first event with that entity string, or it has a different `value` to the last event with the same entity string, it is produced to the output topic.

## Demonstrating

[`send-from-csv.sh`](./send-from-csv.sh) can be used to demonstrate, as a helper script for creating events using data from [a CSV file](./data.csv).

Start by sending new events.

```sh
$ ./send-from-csv.sh 1 4
generating events using rows 1 to 4
{"entity":"AAAAAAAA","value":1000}
{"entity":"BBBBBBBB","value":100}
{"entity":"CCCCCCCC","value":10}
{"entity":"DDDDDDDD","value":1}
```

Four input events.

Notice that the output topic receives all four events, as they are all new entities.

Send more events, starting with the same four events used previously, followed by new values.

```sh
$ ./send-from-csv.sh 1 8
generating events using rows 1 to 8
{"entity":"AAAAAAAA","value":1000}
{"entity":"BBBBBBBB","value":100}
{"entity":"CCCCCCCC","value":10}
{"entity":"DDDDDDDD","value":1}
{"entity":"AAAAAAAA","value":2000}
{"entity":"BBBBBBBB","value":200}
{"entity":"CCCCCCCC","value":20}
{"entity":"DDDDDDDD","value":2}
```

Eight input events.

Notice that the output topic only receives four new events (the four new values, one for each entity).

Repeat

```sh
$ ./send-from-csv.sh 5 12
generating events using rows 5 to 12
{"entity":"AAAAAAAA","value":2000}
{"entity":"BBBBBBBB","value":200}
{"entity":"CCCCCCCC","value":20}
{"entity":"DDDDDDDD","value":2}
{"entity":"AAAAAAAA","value":3000}
{"entity":"BBBBBBBB","value":300}
{"entity":"CCCCCCCC","value":30}
{"entity":"DDDDDDDD","value":3}
```

Eight input events.

Notice that, again, only four new events are sent to the output topic, as the first four are recognised as duplicates.

