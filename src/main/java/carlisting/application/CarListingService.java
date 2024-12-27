package carlisting.application;

import carlisting.domain.CarListing;
import carlisting.domain.CarListingEvent;
import carlisting.infrastructure.CarListingProducer;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for handling car listing events.
 * <p>
 * This service is responsible for business logic associated with car listing events.
 * It primarily handles the sending of these events through a Kafka producer.
 * </p>
 */
@Service
@Slf4j
public class CarListingService {

    private final CarListingProducer carListingProducer;

    /**
     * Constructs a CarListingService with the necessary CarListingProducer.
     *
     * @param carListingProducer The producer used for sending car listing events to Kafka.
     */
    public CarListingService(CarListingProducer carListingProducer) {
        this.carListingProducer = carListingProducer;
    }

    /**
     * Sends a car listing event to the Kafka topic.
     * <p>
     * This method generates a unique identifier for the car listing event and, if necessary,
     * for the car listing itself. It then sends the event to the Kafka topic using the CarListingProducer.
     * </p>
     *
     * @param carListingEvent The CarListingEvent to be sent.
     * @return The CarListingEvent with generated IDs.
     */
    public CarListingEvent sendCarListingEvent(CarListingEvent carListingEvent) {
        carListingEvent.setId(UUID.randomUUID().toString());
        CarListing carListing = carListingEvent.getCarListing();
        if (StringUtils.isBlank(carListing.getId())) {
            carListing.setId(UUID.randomUUID().toString());
        }

        carListingProducer.produceMessage(carListingEvent);

        return carListingEvent;
    }
}
