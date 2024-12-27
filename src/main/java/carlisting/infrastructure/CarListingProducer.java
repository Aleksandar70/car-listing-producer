package carlisting.infrastructure;

import carlisting.domain.CarListingEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Producer component responsible for sending car listing events to a Kafka topic.
 * <p>
 * This component utilizes a KafkaTemplate to publish messages to a specified Kafka topic.
 * It is primarily used for producing events related to car listings, such as creation,
 * update, or deletion.
 * </p>
 */
@Component
@AllArgsConstructor
public class CarListingProducer {

    private static final Logger log = LoggerFactory.getLogger(CarListingProducer.class);

    private final KafkaTemplate<String, CarListingEvent> kafkaTemplate;

    /**
     * Sends a CarListingEvent to the Kafka topic.
     * <p>
     * This method takes a CarListingEvent object and sends it to the Kafka topic
     * named "car-listing-topic". The method logs the event's ID upon successful sending.
     * </p>
     *
     * @param carListingEvent The CarListingEvent object to be sent to Kafka.
     */
    public void produceMessage(CarListingEvent carListingEvent) {
        kafkaTemplate.send("car-listing-topic", carListingEvent);
        log.info("Event sent with id: {}", carListingEvent.getId());
    }
}
