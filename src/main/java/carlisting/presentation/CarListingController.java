package carlisting.presentation;

import carlisting.application.CarListingService;
import carlisting.domain.CarListingEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing car listing operations.
 * <p>
 * This controller provides REST endpoints for various operations related to car listings.
 * It serves as an interface between the client and the server, handling incoming requests
 * and delegating tasks to the service layer.
 * </p>
 */
@RestController
@RequestMapping("/api/car-listings")
public class CarListingController {

    private final CarListingService carListingService;

    /**
     * Constructs a CarListingController with the necessary service for processing car listing events.
     *
     * @param carListingService The service for handling car listing operations and events.
     */
    public CarListingController(CarListingService carListingService) {
        this.carListingService = carListingService;
    }

    /**
     * Processes and sends a car listing event.
     * <p>
     * This method takes a CarListingEvent object from the request body and processes it.
     * Typically, it involves sending the event to a message broker or an event stream for
     * further processing.
     * </p>
     *
     * @param carListingEvent The car listing event to be processed and sent.
     * @return The processed CarListingEvent object.
     */
    @PostMapping("/send")
    public CarListingEvent send(@RequestBody CarListingEvent carListingEvent) {
        return carListingService.sendCarListingEvent(carListingEvent);
    }
}
