package anton.kaliadau.gp.test.hotels.repository.impl;

import anton.kaliadau.gp.test.hotels.model.Hotel;
import anton.kaliadau.gp.test.hotels.repository.HotelRepositoryCustom;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class HotelRepositoryCustomImpl implements HotelRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Hotel> findByParams(String name, String brand, String city, String country, Set<String> amenities) {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Hotel.class);
        var hotel = query.from(Hotel.class);
        var predicates = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            predicates.add(cb.like(cb.lower(hotel.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (brand != null && !brand.isBlank()) {
            predicates.add(cb.equal(hotel.get("brand"), brand));
        }
        if (city != null && !city.isBlank()) {
            predicates.add(cb.equal(hotel.get("address").get("city"), city));
        }
        if (country != null && !country.isBlank()) {
            predicates.add(cb.equal(hotel.get("address").get("country"), country));
        }
        if (amenities != null && !amenities.isEmpty()) {
            for (String amenity : amenities) {
                predicates.add(cb.isMember(amenity, hotel.get("amenities")));
            }
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
