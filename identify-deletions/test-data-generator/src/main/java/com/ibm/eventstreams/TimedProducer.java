package com.ibm.eventstreams;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class TimedProducer {

	private static final String TOPIC = "DEDUPE.DELETES.INPUT";
	private static final Integer PARTITION = null;
	private static final String KEY = null;
	
	public static void main(String[] args) throws IOException, ParseException, InterruptedException {
		resetTopics();
		
		KafkaProducer<String, String> producer = createProducer();
		Scanner scanner = new Scanner(System.in);
		
		Map<String, Object[][]> testdata = TestData.get();
		
		for (String day : testdata.keySet()) {
			System.err.println("press ENTER to emit events for the next day");
			scanner.nextLine();
			
			System.out.println("---------------------------");
			System.out.println(day);
			System.out.println("---------------------------");
			
			for (Object[] dataitem : testdata.get(day)) {
				String messageData = (String) dataitem[0];
				Date timestamp = (Date) dataitem[1];
				
				ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, PARTITION, timestamp.getTime(), KEY, messageData);
				producer.send(record);
				
				Thread.sleep(1000);
				System.out.println(messageData);
			}
			
			System.out.println("");
			Thread.sleep(2000);
		}
		
		System.err.println(" no more events available ");
		
		scanner.close();
		producer.close();
	}
	
	
	
	private static KafkaProducer<String, String> createProducer() throws IOException {
		Properties props = loadConfig();		
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("acks", "all");
		
		return new KafkaProducer<String, String>(props);
	}
	
	
    private static Properties loadConfig() throws IOException {
    	String configFile = "app.properties";
        if (!Files.exists(Paths.get(configFile))) {
            throw new IOException(configFile + " not found.");
        }
        final Properties cfg = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            cfg.load(inputStream);
        }
        return cfg;
    }	
    
    private static void resetTopics() throws IOException, InterruptedException {
    	System.out.println("RESETTING TOPICS");
    	
    	List<String> TOPICS = List.of("DEDUPE.DELETES.INPUT", "DEDUPE.DELETES.OUTPUT");
    	AdminClient client = AdminClient.create(loadConfig());
    	client.deleteTopics(TOPICS);
    	Thread.sleep(2000);
    	client.createTopics(TOPICS.stream().map(name -> new NewTopic(name, 1, (short) 1)).toList());
    	client.close();
    }
}
