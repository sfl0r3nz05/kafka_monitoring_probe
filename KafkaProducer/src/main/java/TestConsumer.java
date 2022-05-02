import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.apache.kafka.clients.consumer.ConsumerRecords;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.apache.kafka.common.serialization.StringDeserializer;



import java.time.Duration;

import java.util.Arrays;

import java.util.Properties;



public class TestConsumer {

    private final static String TOPIC_NAME = "Jan25";

    private final static String CONSUMER_GROUP_NAME = "logstash";

    private final static String KAFKA_SERVERS_IP = "192.168.116.135:9092,192.168.116.135:9093,192.168.116.135:9094";



    public static void main(String[] args) {



        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVERS_IP);

        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_NAME);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MyMessageDeserializer.class.getName());



        KafkaConsumer<String, MyMessageValue> consumer = new KafkaConsumer<>(props);



        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        while (true) {



            ConsumerRecords<String, MyMessageValue> records = consumer.poll(Duration.ofMillis(1000));



            for (ConsumerRecord<String, MyMessageValue> record : records) {

                System.out.printf("Received message：partition = %d,offset = %d, key = %s, value.Inf = %s%n", record.partition(),

                        record.offset(), record.key(), record.value().Inf);

            }

        }

    }



}