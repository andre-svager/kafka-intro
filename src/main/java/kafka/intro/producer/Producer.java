package kafka.intro.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * First of all, we need created and start the Topic
 * 
 * @author andre
 *
 */
public class Producer {

	private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		KafkaProducer<String, String> producer = 
				new KafkaProducer<String, String>(props);
		
		for (int i = 0; i < 20; i++) {
			ProducerRecord<String, String> record = 
					new ProducerRecord<String, String>("first_topic","id_"+i,  "Message: " +i);	
			
			producer.send(record);
		}
		//send data
		
		producer.flush();
		producer.close();

	}

}
