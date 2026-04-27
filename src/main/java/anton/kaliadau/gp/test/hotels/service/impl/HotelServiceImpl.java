package anton.kaliadau.gp.test.hotels.service.impl;

import anton.kaliadau.gp.test.hotels.model.Hotel;
import anton.kaliadau.gp.test.hotels.model.HotelCount;
import anton.kaliadau.gp.test.hotels.model.dto.HotelInfoDto;
import anton.kaliadau.gp.test.hotels.repository.HotelRepository;
import anton.kaliadau.gp.test.hotels.service.HotelMapper;
import anton.kaliadau.gp.test.hotels.service.HotelService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final Map<String, Supplier<List<HotelCount>>> hotelCountFunctions = new HashMap<>();

    @Override
    public List<HotelInfoDto> findAll() {
        return mapHotels(hotelRepository.findAll());
    }

    @Override
    @Transactional
    public HotelInfoDto save(Hotel hotel) {
        return hotelMapper.toHotelInfoDto(hotelRepository.save(hotel));
    }

    @Override
    @Transactional
    public void addAmenities(Long id, Set<String> amenities) {
        hotelRepository.findById(id)
                .ifPresentOrElse(hotel -> hotel.getAmenities().addAll(amenities), () -> {
                    throw new NoSuchElementException("Hotel with id " + id + " does not exist");
                });
    }

    @Override
    public List<HotelInfoDto> findByParams(String name, String brand, String city, String country, Set<String> amenities) {
        return mapHotels(hotelRepository.findByParams(name, brand, city, country, amenities));
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElseThrow();
    }

    @Override
    public List<HotelCount> findCountByParameterName(String parameterName) {
        return hotelCountFunctions.get(parameterName).get();
    }

    @PostConstruct
    private void init() {
        hotelCountFunctions.put("brand", hotelRepository::getHotelCountsByBrands);
        hotelCountFunctions.put("city", hotelRepository::getHotelCountsByCities);
        hotelCountFunctions.put("amenities", hotelRepository::getHotelCountsByAmenities);
        hotelCountFunctions.put("country", hotelRepository::getHotelCountsByCountries);
    }

    private List<HotelInfoDto> mapHotels(List<Hotel> hotels) {
        return hotels.stream()
                .map(hotelMapper::toHotelInfoDto)
                .toList();
    }
}
