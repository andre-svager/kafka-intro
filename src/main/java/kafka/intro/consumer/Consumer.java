package kafka.intro.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.LoggerFactory;

/**
 * First of all, we need created and start the Topic
 * 
 * @author andre
 *
 */
public class Consumer {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(Consumer.class);
	private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";
	private static String groupId = "first_group";
	private static String topic = "first_topic";

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); 
		
		
		KafkaConsumer<String, String> consumer = 
				new KafkaConsumer<String, String>(props);
		
		consumer.subscribe(Arrays.asList(topic));
		
		while(true) {
			ConsumerRecords<String, String> consumers = 
					consumer.poll(Duration.ofMillis(100));
			
			for (ConsumerRecord<String, String> consumerRecord : consumers) {
				logger.info("Key: "+ consumerRecord.key() +"Value: "+ consumerRecord.value());
				logger.info("Partition: "+consumerRecord.partition());
				logger.info("Offset: "+consumerRecord.offset());
			}
		}
	}

}

