package carlisting.domain;

/**
 * Represents an event associated with a car listing.
 * <p>
 * This class encapsulates information about events occurring in the context of
 * car listings, such as creation, update, or deletion. It includes an identifier,
 * the type of event, and the details of the car listing involved in the event.
 * </p>
 */
public enum EventType {
    CREATE,
    UPDATE,
    DELETE
}
