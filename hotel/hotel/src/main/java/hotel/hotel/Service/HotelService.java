package hotel.hotel.Service;




import hotel.hotel.Model.Hotel;

import java.util.List;

public interface HotelService {
    Hotel addHotel(Hotel hotel);

    List<Hotel> getAllHotels();

    Hotel getHotelById(int hid);

    Hotel updateHotel(int hid, Hotel hotel);

    Hotel deleteHotel(int hid);

    Hotel getHotelByNameOrEmailOrPhone(String name, String email, int phone);

}
