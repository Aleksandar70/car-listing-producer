package carlisting.infrastructure;


import carlisting.domain.CarListing;
import carlisting.domain.CarListingEvent;
import carlisting.domain.EventType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarListingProducerTest {

    private static final String ID = "1";

    @InjectMocks
    private CarListingProducer carListingProducer;

    @Mock
    private KafkaTemplate<String, CarListingEvent> kafkaTemplate;

    @Test
    public void testProduceMessageCreate() {
        CarListingEvent event = new CarListingEvent(ID, EventType.CREATE, new CarListing());

        carListingProducer.produceMessage(event);

        verify(kafkaTemplate).send("car-listing-topic", event);
    }

    @Test
    public void testProduceMessageUpdate() {
        CarListingEvent event = new CarListingEvent(ID, EventType.UPDATE, new CarListing());

        carListingProducer.produceMessage(event);

        verify(kafkaTemplate).send("car-listing-topic", event);
    }

    @Test
    public void testProduceMessageDelete() {
        CarListingEvent event = new CarListingEvent(ID, EventType.DELETE, new CarListing());

        carListingProducer.produceMessage(event);

        verify(kafkaTemplate).send("car-listing-topic", event);
    }

    @Test
    public void testProduceMessageExceptionThrown() {
        CarListingEvent event = new CarListingEvent(ID, EventType.CREATE, new CarListing());
        doThrow(new KafkaException("Kafka exception")).when(kafkaTemplate).send(anyString(), any());

        assertThrows(KafkaException.class, () -> carListingProducer.produceMessage(event));
    }
}
