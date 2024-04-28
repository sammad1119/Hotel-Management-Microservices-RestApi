package HotelRoom.Room.Repositry;

import HotelRoom.Room.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Integer> {


    List<Room> findByStatus(String status);


    List<Room> findByRoomType(String roomType);

    List<Room> findByHid(int hid);

}
