import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;


public class MyLoopProducer {
    private final static String TOPIC_NAME = "Apr08";
    private final static String FILE_NAME = "E:\\testData\\0.csv";
    private final static String KAFKA_SERVERS_IP = "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094";

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
        
        long lastFileLen = 0;
        while (true) {
            Thread.sleep(5000);//Wait 5 secs

            File file = new File(FILE_NAME);
            if (file.exists() && file.isFile()) {
                if (lastFileLen == file.length()) {
                    System.out.println("No new lines");
                    continue;
                	}
                System.out.println("New lines detected, delta size = " + (file.length() - lastFileLen));

                // Read data from csv
                List<String> csvParsed = new LinkedList<>();
                try {
                    RandomAccessFile raf = new RandomAccessFile(file, "r");
                    raf.seek(lastFileLen);

                    // ignore the first line
                    raf.readLine();

                    String line = "";
                    while ((line = raf.readLine()) != null) {
                        csvParsed.add(line);
                    	}

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
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
                //Update file size
                lastFileLen = file.length();
                	} 
            
            }else {
                System.out.println("File missing.");
                continue;

            	}
        }
    }
}