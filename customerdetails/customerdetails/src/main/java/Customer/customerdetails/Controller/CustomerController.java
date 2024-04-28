package Customer.customerdetails.Controller;

import Customer.customerdetails.Exception.ResourceNotFoundException;
import Customer.customerdetails.Model.Booking;
import Customer.customerdetails.Model.Customer;
import Customer.customerdetails.Model.CustomerBookingRequest;
import Customer.customerdetails.Service.CustomerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceInterface customerService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getAll")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/getById/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int customerId) {
        Customer customer = customerService.getCustomerById(customerId);




        return ResponseEntity.ok(customer);
    }

    // Method to get all customers with their bookings
    @GetMapping("/getAllWithBookings")
    public List<Customer> getAllCustomersWithBookings() {
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            // Fetch bookings associated with each customer from booking microservice
            ResponseEntity<Booking[]> responseEntity = restTemplate.getForEntity("http://localhost:8084/bookings/byCid/" + customer.getCid(), Booking[].class);
            Booking[] bookings = responseEntity.getBody();
            customer.setBookings(Arrays.asList(bookings));
        }
        return customers;
    }

    // Method to get customer by ID with associated bookings
    @GetMapping("/getByIdWithBookings/{customerId}")
    public ResponseEntity<Customer> getCustomerByIdWithBookings(@PathVariable int customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
        }
        // Fetch bookings associated with this customer from booking microservice
        ResponseEntity<Booking[]> responseEntity = restTemplate.getForEntity("http://localhost:8084/bookings/byCid/" + customerId, Booking[].class);
        Booking[] bookings = responseEntity.getBody();
        customer.setBookings(Arrays.asList(bookings));

        return ResponseEntity.ok(customer);
    }


    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.savecustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @PostMapping("/addWithBooking")
    public ResponseEntity<String> addCustomerWithBooking(@RequestBody CustomerBookingRequest request) {
        // Extract customer and booking from the request
        Customer customer = request.getCustomer();
        Booking booking = request.getBooking();

        // Create the booking in the Booking microservice
        ResponseEntity<String> bookingResponse = restTemplate.postForEntity("http://localhost:8084/bookings/add", booking, String.class);

        // Check if booking was successful
        if (bookingResponse.getStatusCode() == HttpStatus.CREATED) {
            // String bookingId = bookingResponse.getBody();

            // Add the customer
            Customer savedCustomer = customerService.savecustomer(customer);

            // Set the customer ID for the booking
            booking.setCid(savedCustomer.getCid());

            return ResponseEntity.status(HttpStatus.CREATED).body("Customer and Booking created successfully");
        } else {
            // Booking failed, return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create booking. Customer not created.");
        }
    }




    @PutMapping("/update/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int customerId, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updatebyid(customerId, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @PutMapping("/updateBookingforcutomer/{bookingId}")
    public ResponseEntity<String> updateBooking(@PathVariable int bookingId, @RequestBody Booking booking) {
        // Make a REST call to the Booking microservice to update the booking
        String url = "http://localhost:8084/bookings/update/" + bookingId;
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(booking), String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Booking with ID " + bookingId + " updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update booking");
        }
    }


    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {
        boolean deleted = customerService.deleteCustomerById(customerId);
        if (deleted) {
            return ResponseEntity.ok("Customer with ID " + customerId + " has been deleted successfully.");
        } else {
            throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
        }
    }
    @DeleteMapping("/deleteCustomerWithBooking/{customerId}")
    public ResponseEntity<String> deleteCustomerWithBooking(@PathVariable int customerId) {
        String bookingCancelUrl = "http://localhost:8084/bookings/cancelbookings/" + customerId;
        ResponseEntity<String> bookingResponseEntity = restTemplate.exchange(bookingCancelUrl, HttpMethod.DELETE, null, String.class
        );

        if (bookingResponseEntity.getStatusCode() == HttpStatus.OK) {
            String customerDeleteUrl = "http://localhost:8085/customers/delete/" + customerId;
            ResponseEntity<String> customerResponseEntity = restTemplate.exchange(customerDeleteUrl, HttpMethod.DELETE, null, String.class
            );

            if (customerResponseEntity.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Customer with ID " + customerId + " and associated booking deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete customer with ID " + customerId);
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to cancel booking associated with customer ID " + customerId);
        }
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
