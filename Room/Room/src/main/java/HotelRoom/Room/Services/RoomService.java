package HotelRoom.Room.Services;
import java.util.List;
import HotelRoom.Room.Model.Room;


    public interface RoomService
    {


        Room save(Room room);

        Room findById(int id);

        List<Room> findAll();

        void delete(Room room);

        List<Room> findByHid(int hid);
        List<Room> getByStatus(String status);

        List<Room> findAvailableRooms();
        List<Room> getByRoomType(String roomType);


    }
