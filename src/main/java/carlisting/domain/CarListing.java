package carlisting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a car listing in the application.
 * <p>
 * This class models the essential details of a car listing, capturing information
 * such as its make, model, year, price range, and color. It is used throughout the
 * application wherever car listing information is required.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarListing {
    private String id;
    private String make;
    private String model;
    private Integer year;
    private Double minPrice;
    private Double maxPrice;
    private String color;
}
