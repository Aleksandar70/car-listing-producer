package carlisting.application;

import carlisting.domain.CarListing;
import carlisting.domain.CarListingEvent;
import carlisting.infrastructure.CarListingProducer;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.KafkaException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarListingServiceTest {

    @InjectMocks
    private CarListingService carListingService;

    @Mock
    private CarListingProducer carListingProducer;

    private CarListingEvent event;

    @BeforeEach
    public void setup() {
        event = new CarListingEvent();
    }

    @Test
    public void testSendCarListingEvent() {
        event.setCarListing(new CarListing());

        CarListingEvent returnedEvent = carListingService.sendCarListingEvent(event);

        assertNotNull(returnedEvent.getId());
        assertFalse(StringUtils.isBlank(returnedEvent.getId()));
        assertNotNull(returnedEvent.getCarListing().getId());
        assertFalse(StringUtils.isBlank(returnedEvent.getCarListing().getId()));

        verify(carListingProducer).produceMessage(returnedEvent);
        verify(carListingProducer, times(1)).produceMessage(any(CarListingEvent.class));
    }

    @Test
    public void testSendCarListingEventExistingId() {
        CarListing carListing = new CarListing();
        String existingId = "existingId";

        carListing.setId(existingId);
        event.setCarListing(carListing);

        CarListingEvent returnedEvent = carListingService.sendCarListingEvent(event);

        assertEquals(existingId, returnedEvent.getCarListing().getId());
        verify(carListingProducer, times(1)).produceMessage(any(CarListingEvent.class));
    }

    @Test
    public void testSendCarListingEventExceptionInProducer() {
        doThrow(new KafkaException("Kafka exception")).when(carListingProducer).produceMessage(any(CarListingEvent.class));
        event.setCarListing(new CarListing());

        assertThrows(KafkaException.class, () -> carListingService.sendCarListingEvent(event));
    }
}
