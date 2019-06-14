package kafka.intro.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;

/**
 * First of all, we need created and start the Topic
 * 
 * @author andre
 *
 */
public class ProducerCallback {

	private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ProducerCallback.class);

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		KafkaProducer<String, String> producer = 
				new KafkaProducer<String, String>(props);
		
		for (int i = 0; i < 20; i++) {
			ProducerRecord<String, String> record = 
					new ProducerRecord<String, String>("first_topic", "Message: " +i);	
			
			producer.send(record, new Callback() {
				
				public void onCompletion(RecordMetadata meta, Exception exc) {
					StringBuilder sb = new StringBuilder();
					sb.append("> Received new Data ")
					.append("Topic: ").append(meta.topic())
					.append(" Partition: ").append(meta.partition())
					.append(" Offset: ").append(meta.offset())
					.append(" Timestamp: ").append(meta.timestamp());
					logger.info(sb.toString());
				}
			});
		}
		//send data
		
		producer.flush();
		producer.close();

	}

}
