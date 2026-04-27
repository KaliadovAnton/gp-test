package anton.kaliadau.gp.test.hotels.service;

import anton.kaliadau.gp.test.hotels.model.Hotel;
import anton.kaliadau.gp.test.hotels.model.HotelCount;
import anton.kaliadau.gp.test.hotels.model.dto.HotelInfoDto;

import java.util.List;
import java.util.Set;

public interface HotelService {
    List<HotelInfoDto> findAll();

    HotelInfoDto save(Hotel hotel);

    void addAmenities(Long id, Set<String> amenities);

    List<HotelInfoDto> findByParams(String name, String brand, String city, String country, Set<String> amenities);

    Hotel findById(Long id);

    List<HotelCount> findCountByParameterName(String parameterName);
}
