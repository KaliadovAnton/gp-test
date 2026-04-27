package anton.kaliadau.gp.test.hotels.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Embeddable
public class ContactInfo {
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
}
