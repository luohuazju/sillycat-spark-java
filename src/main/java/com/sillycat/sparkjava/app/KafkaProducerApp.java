package com.sillycat.sparkjava.app;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProducerApp {

	private final static String TOPIC = "sillycat-topic";

	private final static String BOOTSTRAP_SERVERS = "fr-stage-api:9092,fr-stage-consumer:9092,fr-perf1:9092";

	private Producer<Long, String> createProducer() {
		Properties props = new Properties();
		// server list
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		// client ID
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducerApp");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return new KafkaProducer<>(props);
	}

	public void runProducer(final int sendMessageCount) throws Exception {
		final Producer<Long, String> producer = createProducer();
		long time = System.currentTimeMillis();
		try {
			for (long index = time; index < time + sendMessageCount; index++) {
				final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, index,
						"Hello Sillycat " + index);
				RecordMetadata metadata = producer.send(record).get();
				long elapsedTime = System.currentTimeMillis() - time;
				System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n",
						record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
			}
		} finally {
			producer.flush();
			producer.close();
		}
	}

	public static void main(String[] args) {
		KafkaProducerApp app = new KafkaProducerApp();
		try {
			app.runProducer(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
