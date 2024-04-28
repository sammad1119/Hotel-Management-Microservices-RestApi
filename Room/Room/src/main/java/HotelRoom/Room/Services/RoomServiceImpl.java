package HotelRoom.Room.Services;

import HotelRoom.Room.Model.Room;
import HotelRoom.Room.Repositry.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class RoomServiceImpl implements RoomService {
        @Autowired
        private  RoomRepo roomRepository;

        @Override
        public Room save(Room room) {
            return roomRepository.save(room);
        }

        @Override
        public Room findById(int id) {
            return roomRepository.findById(id).orElse(null);
        }

        @Override
        public List<Room> findByHid(int hid) {
            return roomRepository.findByHid(hid);
        }

        @Override
        public List<Room> findAll() {
            return roomRepository.findAll();
        }

        @Override
        public void delete(Room room) {
            roomRepository.delete(room);
        }



        @Override
        public List<Room> getByStatus(String status) {
            return roomRepository.findByStatus(status);
        }

        @Override
        public List<Room> findAvailableRooms() {
            return roomRepository.findByStatus("Available");
        }

        public List<Room> getByRoomType(String roomType) {
            return roomRepository.findByRoomType(roomType);
        }
    }
