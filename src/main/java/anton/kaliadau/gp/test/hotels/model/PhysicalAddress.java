package anton.kaliadau.gp.test.hotels.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PhysicalAddress {
    private Long houseNumber;
    private String street;
    private String city;
    private String country;
    private String postalCode;
}
