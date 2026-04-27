package anton.kaliadau.gp.test.hotels.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Amenity {
    private String name;
}
