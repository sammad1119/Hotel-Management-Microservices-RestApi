package HotelRoom.Room.Controller;

import HotelRoom.Room.Exception.ResourceNotFoundException;
import HotelRoom.Room.Model.Bookings;
import HotelRoom.Room.Model.Room;
import HotelRoom.Room.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired

    private RestTemplate restTemplate;

    @PostMapping("/add")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        try {
            Room savedRoom = roomService.save(room);
            return ResponseEntity.ok(savedRoom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create room: " + e.getMessage());
        }
    }

    @GetMapping("/getby/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable int id) {
        try {
            Room room = roomService.findById(id);
            if (room != null) {
                return ResponseEntity.ok(room);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get room: " + e.getMessage());
        }
    }

    @GetMapping("/byhid/{hid}")
    public ResponseEntity<List<Room>> getroomsByHotelId(@PathVariable int hid) {
        List<Room> employees = roomService.findByHid(hid);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("Employees with HID " + hid + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }


    @GetMapping("/getall")
    public ResponseEntity<?> getAllRooms() {
        try {
            List<Room> rooms = roomService.findAll();
            return ResponseEntity.ok(rooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get all rooms: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable int id) {
        try {
            Room room = roomService.findById(id);
            if (room != null) {
                roomService.delete(room);
                return ResponseEntity.ok("Room deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete room: " + e.getMessage());
        }
    }

        @PostMapping("/book/add")
        public ResponseEntity<String> bookRoom( @RequestBody Bookings booking) {
            // Make a REST call to the Booking microservice to create a booking
            String url = "http://localhost:8084/bookings/add";
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, booking, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
                return ResponseEntity.ok("Booking created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to book room");
            }
        }

        @GetMapping("/bookings/{roomId}")
        public ResponseEntity getBookingsForRoom(@PathVariable int roomId) {
            // Make a REST call to the Booking microservice to get bookings for the room
            String url = "http://localhost:8084/bookings/byRid/" + roomId;
            ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
            List bookings = responseEntity.getBody();
            return ResponseEntity.ok(bookings);
        }

        @GetMapping("/bookingsbybid/{bookingId}")
        public ResponseEntity<Bookings> getBookingByIdForRoom( @PathVariable int bookingId) {
            // Make a REST call to the Booking microservice to get the booking by ID
            String url = "http://localhost:8084/bookings/getby/" + bookingId;
            ResponseEntity<Bookings> responseEntity = restTemplate.getForEntity(url, Bookings.class);
            Bookings booking = responseEntity.getBody();
            return ResponseEntity.ok(booking);
        }

        @DeleteMapping("/cancelbookings/{bookingId}/")
        public ResponseEntity<String> cancelBookingForRoom( @PathVariable int bookingId) {
            // Make a REST call to the Booking microservice to cancel the booking
            String url = "http://localhost:8084/cancelbookings/" + bookingId;
            restTemplate.delete(url);
            return ResponseEntity.ok("Booking canceled successfully");
        }


    @GetMapping("/getbystatus/{status}")
    public ResponseEntity<?> getRoomsByStatus(@PathVariable String status) {
        try {
            List<Room> rooms = roomService.getByStatus(status);
            if (rooms.isEmpty()) {
                throw new ResourceNotFoundException("No rooms found with status: " + status);
            }
            return ResponseEntity.ok(rooms);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get rooms by status: " + e.getMessage());
        }
    }

    @GetMapping("/getavailable/{Available}")
    public ResponseEntity<?> getAvailableRooms() {
        try {
            List<Room> rooms = roomService.findAvailableRooms();
            if (rooms.isEmpty()) {
                throw new ResourceNotFoundException("No available rooms found");
            }
            return ResponseEntity.ok(rooms);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get available rooms: " + e.getMessage());
        }
    }

    @GetMapping("/getByRoomType/{roomType}")
    public ResponseEntity<?> getByRoomType(@PathVariable String roomType) {
        try {
            List<Room> rooms = roomService.getByRoomType(roomType);
            if (rooms.isEmpty()) {
                throw new ResourceNotFoundException("No rooms found with room type: " + roomType);
            }
            return ResponseEntity.ok(rooms);
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get type of  rooms: " + e.getMessage());
        }
    }


}