package anton.kaliadau.gp.test.hotels.repository;

import anton.kaliadau.gp.test.hotels.model.Hotel;
import anton.kaliadau.gp.test.hotels.model.HotelCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository  extends JpaRepository<Hotel, Long>, HotelRepositoryCustom {
    @Query("SELECT h.address.city as key, COUNT(h) as count FROM hotel h GROUP BY h.address.city")
    List<HotelCount> getHotelCountsByCities();

    @Query("SELECT h.brand as key, COUNT(h) as count FROM hotel h GROUP BY h.brand")
    List<HotelCount> getHotelCountsByBrands();

    @Query("SELECT h.address.country as key, COUNT(h) as count FROM hotel h GROUP BY h.address.country")
    List<HotelCount> getHotelCountsByCountries();

    @Query("SELECT a as key, COUNT(h) as count FROM hotel h JOIN h.amenities a GROUP BY a")
    List<HotelCount> getHotelCountsByAmenities();
}
