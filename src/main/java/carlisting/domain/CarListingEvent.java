package carlisting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an event associated with a car listing.
 * <p>
 * This class encapsulates the details of an event occurring on a car listing,
 * such as creation, update, or deletion. It includes information about the
 * event type and the car listing involved in the event.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListingEvent {
    private String id;
    private EventType eventType;
    private CarListing carListing;
}
