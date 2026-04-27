package anton.kaliadau.gp.test.hotels.service.impl;

import anton.kaliadau.gp.test.hotels.model.Hotel;
import anton.kaliadau.gp.test.hotels.model.HotelCount;
import anton.kaliadau.gp.test.hotels.model.dto.HotelInfoDto;
import anton.kaliadau.gp.test.hotels.repository.HotelRepository;
import anton.kaliadau.gp.test.hotels.service.HotelMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {
    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Hotel testHotel;
    private HotelInfoDto testHotelInfoDto;
    private List<Hotel> testHotels;

    @BeforeEach
    void setUp() {
        testHotel = new Hotel();
        testHotel.setId(1L);
        testHotel.setBrand("Test Brand");
        testHotel.setName("Test Hotel");
        testHotel.setDescription("Test Description");
        testHotel.setAmenities(new HashSet<>(Set.of("WiFi", "Pool")));

        testHotelInfoDto = new HotelInfoDto(1L, "Test Hotel", "Test Address", "Test Description", "Test phone");

        testHotels = Arrays.asList(testHotel);
    }

    @Test
    void findAll_ShouldReturnListOfHotelInfoDto_WhenHotelsExist() {
        // given
        when(hotelRepository.findAll()).thenReturn(testHotels);
        when(hotelMapper.toHotelInfoDto(testHotel)).thenReturn(testHotelInfoDto);

        // when
        List<HotelInfoDto> result = hotelService.findAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst()).isEqualTo(testHotelInfoDto);
        verify(hotelRepository).findAll();
        verify(hotelMapper).toHotelInfoDto(testHotel);
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoHotelsExist() {
        // given
        when(hotelRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<HotelInfoDto> result = hotelService.findAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);
        verify(hotelRepository).findAll();
        verify(hotelMapper, new NoInteractions()).toHotelInfoDto(any());
    }

    @Test
    void save_ShouldReturnSavedHotelInfoDto() {
        // given
        when(hotelRepository.save(testHotel)).thenReturn(testHotel);
        when(hotelMapper.toHotelInfoDto(testHotel)).thenReturn(testHotelInfoDto);

        // when
        HotelInfoDto result = hotelService.save(testHotel);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testHotelInfoDto);
        verify(hotelRepository).save(testHotel);
        verify(hotelMapper).toHotelInfoDto(testHotel);
    }

    @Test
    void addAmenities_ShouldThrowException_WhenHotelDoesNotExist() {
        // given
        Set<String> newAmenities = new HashSet<>(Set.of("Gym"));
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> hotelService.addAmenities(99L, newAmenities))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Hotel with id 99 does not exist");

        verify(hotelRepository).findById(99L);
    }

    @Test
    void findByParams_ShouldReturnFilteredHotels() {
        // given
        String name = "Test";
        String brand = "Brand";
        String city = "City";
        String country = "Country";
        Set<String> amenities = Set.of("WiFi");

        when(hotelRepository.findByParams(name, brand, city, country, amenities)).thenReturn(testHotels);
        when(hotelMapper.toHotelInfoDto(testHotel)).thenReturn(testHotelInfoDto);

        // when
        List<HotelInfoDto> result = hotelService.findByParams(name, brand, city, country, amenities);

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst()).isEqualTo(testHotelInfoDto);
        verify(hotelRepository).findByParams(name, brand, city, country, amenities);
    }

    @Test
    void findByParams_ShouldHandleNullParameters() {
        // given
        when(hotelRepository.findByParams(null, null, null, null, null)).thenReturn(testHotels);
        when(hotelMapper.toHotelInfoDto(testHotel)).thenReturn(testHotelInfoDto);

        // when
        List<HotelInfoDto> result = hotelService.findByParams(null, null, null, null, null);

        // then
        assertThat(result.size()).isEqualTo(1);
        verify(hotelRepository).findByParams(null, null, null, null, null);
    }

    @Test
    void findById_ShouldReturnHotel_WhenExists() {
        // given
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(testHotel));

        // when
        Hotel result = hotelService.findById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getBrand()).isEqualTo("Test Brand");
        verify(hotelRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenNotExists() {
        // given
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> hotelService.findById(99L))
                .isInstanceOf(NoSuchElementException.class);

        verify(hotelRepository).findById(99L);
    }

    @Test
    void addAmenities_ShouldAddMultipleAmenities() {
        // given
        Set<String> newAmenities = new HashSet<>(Set.of("Restaurant", "Bar", "Gym"));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(testHotel));
        int initialSize = testHotel.getAmenities().size();

        // when
        hotelService.addAmenities(1L, newAmenities);

        // then
        assertThat(testHotel.getAmenities().size()).isEqualTo(initialSize + newAmenities.size());
        assertThat(testHotel.getAmenities().containsAll(newAmenities));
        verify(hotelRepository).findById(1L);
    }
}