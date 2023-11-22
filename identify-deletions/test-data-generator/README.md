## Building

```sh
mvn package
```

## Running

Create a file called `app.properties` and add the config for your Kafka cluster to it.

```sh
java -cp ./target/timed-producer-0.0.1-jar-with-dependencies.jar com.ibm.eventstreams.TimedProducer
```
