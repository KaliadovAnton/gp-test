package anton.kaliadau.gp.test.hotels.repository;

import anton.kaliadau.gp.test.hotels.model.Hotel;

import java.util.List;
import java.util.Set;

public interface HotelRepositoryCustom {
    List<Hotel> findByParams(String name, String brand, String city, String country, Set<String> amenities);
}
