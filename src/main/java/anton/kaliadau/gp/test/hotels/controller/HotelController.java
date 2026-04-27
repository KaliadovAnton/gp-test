package anton.kaliadau.gp.test.hotels.controller;

import anton.kaliadau.gp.test.hotels.model.Hotel;
import anton.kaliadau.gp.test.hotels.model.dto.HotelInfoDto;
import anton.kaliadau.gp.test.hotels.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/property-view/hotels")
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public List<HotelInfoDto> findAll() {
        return hotelService.findAll();
    }

    @GetMapping("/{id}")
    public Hotel findOne(@PathVariable Long id) {
        return hotelService.findById(id);
    }


    @PostMapping
    public HotelInfoDto save(@RequestBody Hotel hotel) {
        return hotelService.save(hotel);
    }

    @PostMapping("/{id}/amenities")
    public void addAmenities(@PathVariable Long id, @RequestBody Set<String> amenities) {
        hotelService.addAmenities(id, amenities);
    }
}
