package anton.kaliadau.gp.test.hotels.controller;

import anton.kaliadau.gp.test.hotels.model.HotelCount;
import anton.kaliadau.gp.test.hotels.model.dto.HotelInfoDto;
import anton.kaliadau.gp.test.hotels.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/property-view")
public class HotelFilterController {
    private final HotelService hotelService;

    @GetMapping("/search")
    public List<HotelInfoDto> search(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String brand,
                                     @RequestParam(required = false) String city,
                                     @RequestParam(required = false) String country,
                                     @RequestParam(required = false) Set<String> amenities) {
        return hotelService.findByParams(name, brand, city, country, amenities);
    }

    @GetMapping("/histogram/{param}")
    public List<HotelCount> histogram(@PathVariable("param") String paramName) {
        return hotelService.findCountByParameterName(paramName);
    }
}
