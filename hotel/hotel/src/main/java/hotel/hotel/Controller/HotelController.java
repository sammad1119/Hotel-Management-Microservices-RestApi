package hotel.hotel.Controller;
import hotel.hotel.Exception.ResourceNotFoundException;
import hotel.hotel.Model.Employee;
import hotel.hotel.Model.Hotel;
import hotel.hotel.Model.Room;
import hotel.hotel.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/add")
    public ResponseEntity<Object> addHotel(@RequestBody Hotel hotel) {
        Hotel existingHotel = hotelService.getHotelByNameOrEmailOrPhone(hotel.getName(), hotel.getEmail(), hotel.getPhone());
        if (existingHotel != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hotel with the same name, email, or phone already exists");
        }
        Hotel addedHotel = hotelService.addHotel(hotel);
        if (addedHotel != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addedHotel);
        } else {
            throw new ResourceNotFoundException("Failed to add hotel");
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        if (!hotels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(hotels);
        }
        throw new ResourceNotFoundException("No hotels found");
    }


    @GetMapping("/getbyid/{hid}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable int  hid) {
        Hotel hotel = hotelService.getHotelById(hid);
        if (hotel != null) {
            return ResponseEntity.status(HttpStatus.OK).body(hotel);
        }
        throw new ResourceNotFoundException("Hotel with ID " + hid + " not found");
    }

    @GetMapping("/getAllHotelsWithEmployees")
    public ResponseEntity<List<Hotel>> getAllHotelsWithEmployees() {
        List<Hotel> hotels = hotelService.getAllHotels();
        for (Hotel hotel : hotels) {
            // Fetch employees for each hotel
            String url = "http://localhost:8082/Employees/byhid/" + hotel.getHid();
            ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
            List employees = responseEntity.getBody();
            hotel.setEmployees(employees);
        }
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @GetMapping("/getEmployeeswithidfromhotel/{hid}")
    public ResponseEntity<Hotel> getHotelByIdWithEmployees(@PathVariable int hid) {
        Hotel hotel = hotelService.getHotelById(hid);
        if (hotel != null) {
            // Fetch employees for this hotel
            String url = "http://${EMPLOYEE_HOST}:${EMPLOYEE_PORT}/Employees/byhid/" + hid;
            ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
            List employees = responseEntity.getBody();
            hotel.setEmployees(employees);
            return ResponseEntity.status(HttpStatus.OK).body(hotel);
        }
        throw new ResourceNotFoundException("Hotel with ID " + hid + " not found");
    }

    @GetMapping("/getEmployeeByName/{employeeName}")
    public ResponseEntity getEmployeeByNameFromHotel(@PathVariable String employeeName) {
        // Make a REST call to the Employee microservice to get employees by name
        String url = "http://localhost:8082/Employees/getbyname/" + employeeName;
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
        List employees = responseEntity.getBody();
        if (!employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployeeToHotel(@RequestBody Employee employee) {
        // Make a REST call to the Employee microservice to add the employee
        String url = "http://localhost:8082/Employees/add";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, employee, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add employee");
        }
    }




    @PutMapping("/updatebyid/{hid}")
    public ResponseEntity<Object> updateHotel(@PathVariable int hid, @RequestBody Hotel hotel) {
        Hotel updatedHotel = hotelService.updateHotel(hid, hotel);
        if (updatedHotel != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedHotel);
        }
        throw new ResourceNotFoundException("Hotel with ID " + hid + " not found");
    }

    @PutMapping("/updateEmployee/{employeeId}")
    public ResponseEntity<String> updateEmployeeInHotel(@PathVariable int employeeId, @RequestBody Employee employee) {
        // Make a REST call to the Employee microservice to update the employee
        String url = "http://localhost:8082/Employees/update/" + employeeId;
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(employee), String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Employee with ID " + employeeId + " updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update employee");
        }
    }

    @DeleteMapping("/deletebyid/{hid}")
    public ResponseEntity<Object> deleteHotel(@PathVariable int hid) {
        Hotel deletedHotel = hotelService.deleteHotel(hid);
        if (deletedHotel != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Hotel with ID " + hid + " deleted successfully:\n" );
        } else {
            throw new ResourceNotFoundException("Hotel with ID " + hid + " not found");
        }
    }

    @DeleteMapping("/deleteEmployee/{employeeId}")
    public ResponseEntity<String> deleteEmployeeFromHotel(@PathVariable int employeeId) {
        // Make a REST call to the Employee microservice to delete the employee
        String url = "http://localhost:8082/Employees/delete/" + employeeId;
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Employee with ID " + employeeId + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete employee");
        }
    }

    @PostMapping("/addRoom/{hotelId}")
    public ResponseEntity<String> addRoomToHotel(@PathVariable int hotelId, @RequestBody Room room) {
        // Make a REST call to the Room microservice to add the room to the hotel
        String url = "http://localhost:8083/rooms/add";
        room.setHid(hotelId);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, room, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Room added to hotel successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add room to hotel");
        }
    }

    @GetMapping("/getAllRooms/{hotelId}")
    public ResponseEntity<List<Room>> getAllRoomsOfHotel(@PathVariable int hotelId) {
        // Make a REST call to the Room microservice to get all rooms of the hotel
        String url = "http://localhost:8083/rooms/byhid/" + hotelId;
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
        List<Room> rooms = responseEntity.getBody();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/getRoomById/{roomId}")
    public ResponseEntity<Room> getRoomByIdInHotel( @PathVariable int roomId) {
        // Make a REST call to the Room microservice to get the room by ID
        String url = "http://localhost:8083/rooms/getby/" + roomId;
        ResponseEntity<Room> responseEntity = restTemplate.getForEntity(url, Room.class);
        Room room = responseEntity.getBody();
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/deleteRoom/{roomId}")
    public ResponseEntity<String> deleteRoomFromHotel( @PathVariable int roomId) {
        // Make a REST call to the Room microservice to delete the room from the hotel
        String url = "http://localhost:8083/rooms/deleteby/" + roomId;
        restTemplate.delete(url);
        return ResponseEntity.ok("Room deleted from hotel successfully");
    }

    @GetMapping("/getAvailableRooms/{Available}")
    public ResponseEntity getAvailableRoomsInHotel(@PathVariable String Available) {
        String url = "http://localhost:8083/rooms/getavailable/Available";
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
        List rooms = responseEntity.getBody();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/getRoomsByType/{roomType}")
    public ResponseEntity<List<Room>> getRoomsByTypeInHotel( @PathVariable String roomType) {
        String url = "http://localhost:8083/rooms/getByRoomType/" + roomType;
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
        List<Room> rooms = responseEntity.getBody();
        return ResponseEntity.ok(rooms);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
