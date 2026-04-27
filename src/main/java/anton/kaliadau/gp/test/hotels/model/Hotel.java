package anton.kaliadau.gp.test.hotels.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String brand;

    @NotBlank
    private String name;

    @NotNull
    private String description;

    @ElementCollection
    private Set<String> amenities;

    @Embedded
    private ArrivalTime arrivalTime;

    @Embedded
    private ContactInfo contacts;

    @Embedded
    private PhysicalAddress address;
}
