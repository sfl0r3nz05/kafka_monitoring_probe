import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class MyAckProducer {
    private final static String TOPIC_NAME = "Apr08";
    private final static String FILE_NAME = "/home/mudong1/Desktop/0408.csv";
    private final static String KAFKA_SERVERS_IP = "192.168.116.135:9092,192.168.116.135:9093,192.168.116.135:9094";

    public static void main(String[] args) throws ExecutionException,
            InterruptedException {

        // Initialization
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVERS_IP);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        Producer<String, String> producer = new KafkaProducer<>(props);

        List<String> csvParsed = new LinkedList<>();
        // Read data from csv
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            // ignore the first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                csvParsed.add(line);
            	}

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error in sending record");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in sending record");
            return;
        	}

        // Send messages
        for (Integer i = 0; i < csvParsed.size(); ++i) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, FILE_NAME, csvParsed.get(i));
            try {
            	RecordMetadata metadata = producer.send(producerRecord).get();
               System.out.println("Message sent to：" + "topic-" + metadata.topic() + "|partition-" + metadata.partition() + "|offset-" + metadata.offset());
            }catch(Exception e) {
            	e.printStackTrace();
            }
           
        }
        	producer.close();

    }
}