package hotel.hotel.Service;



import hotel.hotel.Model.Hotel;
import hotel.hotel.Repositry.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Hotel addHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        List<Hotel> hotel;
        hotel = hotelRepository.findAll();
        return hotel;

    }

    @Override
    public Hotel getHotelById(int id) {
        Hotel hotel = hotelRepository.findById(id).orElse(null);
        return hotel;
    }


    @Override
    public Hotel updateHotel(int hid, Hotel hotel) {
        Hotel existingHotel = hotelRepository.findById(hid).orElse(null);
        if (existingHotel != null) {
            hotel.setHid(existingHotel.getHid());
            return hotelRepository.save(hotel);
        }
        return null;
    }


    @Override
    public Hotel deleteHotel(int id) {
        Hotel hotelToDelete = hotelRepository.findById(id).orElse(null);
        if (hotelToDelete != null) {
            hotelRepository.delete(hotelToDelete);
            return hotelToDelete;
        }
        return null;
    }

    @Override
    public Hotel getHotelByNameOrEmailOrPhone(String name, String email, int phone) {
        return hotelRepository.findByNameOrEmailOrPhone(name, email, phone);
    }


}