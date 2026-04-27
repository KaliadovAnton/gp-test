package anton.kaliadau.gp.test.hotels.service;

import anton.kaliadau.gp.test.hotels.model .Hotel;
import anton.kaliadau.gp.test.hotels.model.PhysicalAddress;
import anton.kaliadau.gp.test.hotels.model.dto.HotelInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelInfoDto toHotelInfoDto(Hotel hotel);

    default String mapAddressToString(PhysicalAddress address) {
        if (address == null) {
            return null;
        }
        return String.format("%s %s, %s, %s, %s",
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountry());
    }
}
