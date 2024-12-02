package carlisting.infrastructure;

import carlisting.domain.CarListingEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka producers.
 * <p>
 * This class sets up the configuration for Kafka producers used within the application.
 * It defines beans for producer configurations, producer factories, and Kafka templates
 * needed for sending messages to Kafka topics.
 * </p>
 */
@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Provides the configuration properties for Kafka producers.
     * <p>
     * This method sets up various properties such as bootstrap servers and
     * serializers for the key and value, which are necessary for Kafka producer configuration.
     * </p>
     *
     * @return A Map containing Kafka producer configuration properties.
     */
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return props;
    }

    /**
     * Creates a ProducerFactory for String type key and value.
     * <p>
     * This ProducerFactory is used to create Kafka producers with String key and value.
     * </p>
     *
     * @return A ProducerFactory for Kafka producers with String key and value.
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Creates a KafkaTemplate for String type key and value.
     * <p>
     * This KafkaTemplate wraps the ProducerFactory and provides a high-level abstraction
     * for sending messages to Kafka topics.
     * </p>
     *
     * @return A KafkaTemplate for sending String key and value messages.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Creates a ProducerFactory specifically for sending CarListingEvent objects.
     * <p>
     * This ProducerFactory is configured to serialize the value as a JSON object,
     * suitable for sending CarListingEvent messages.
     * </p>
     *
     * @return A ProducerFactory for Kafka producers to send CarListingEvent messages.
     */
    @Bean
    public ProducerFactory<String, CarListingEvent> sendCarListingEventProducerFactory() {
        Map<String, Object> configProps = producerConfigs();
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Creates a KafkaTemplate for sending CarListingEvent objects.
     * <p>
     * This KafkaTemplate wraps the ProducerFactory for CarListingEvent objects,
     * providing convenient methods to send CarListingEvent messages to Kafka topics.
     * </p>
     *
     * @return A KafkaTemplate for sending CarListingEvent messages.
     */
    @Bean
    public KafkaTemplate<String, CarListingEvent> sendCarListingEventKafkaTemplate() {
        return new KafkaTemplate<>(sendCarListingEventProducerFactory());
    }
}
