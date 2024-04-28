
    package HotelBooking.booking.Controller;

import HotelBooking.booking.Exception.ResourceNotFoundException;
import HotelBooking.booking.Model.Booking;
import HotelBooking.booking.Model.Payment;
import HotelBooking.booking.Services.BookingService;
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
    @RequestMapping("/bookings")
    public class BookingController {

        @Autowired
        private BookingService bookingService;

        @Autowired
        private RestTemplate restTemplate;


        @PostMapping("/add")
        public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
            if (booking.getCheckin() == null || booking.getCheckout() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Check-in date and check-out date are required.");
            }

            if (booking.getCheckout().before(booking.getCheckin())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Check-out date must be after check-in date.");
            }

            List<Booking> conflictingBookings = bookingService.getBookingsBetweenDates(booking.getCheckin(), booking.getCheckout());
            for (Booking existingBooking : conflictingBookings) {
                if (existingBooking.getRid() == booking.getRid()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("The room is already booked for the given period.");
                }
            }

            Booking createdBooking = bookingService.addBooking(booking);
            return ResponseEntity.status(HttpStatus.CREATED).body("Booking created with ID: " + createdBooking.getBooking_id());
        }

        @PostMapping("/addPaymentforbooking/{bookingId}")
        public ResponseEntity<String> addPaymentForBooking(@PathVariable int bookingId, @RequestBody Payment payment) {
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                // If booking does not exist, return an error message
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found with ID: " + bookingId);
            }

            // Set the booking ID for the payment
            payment.setBid(bookingId);

            // Make a POST request to the Payment microservice to add the payment
            String paymentAddUrl = "http://localhost:8086/payments/add";
            ResponseEntity<Payment> paymentResponseEntity;
            try {
                paymentResponseEntity = restTemplate.postForEntity(paymentAddUrl, payment, Payment.class);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add payment for booking ID: " + bookingId);
            }

            if (paymentResponseEntity.getStatusCode() == HttpStatus.CREATED) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Payment added successfully for booking ID: " + bookingId);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("successfully  added payment for booking ID: " + bookingId);
            }
        }



        @GetMapping("/getby/{id}")
        public ResponseEntity<Booking> getBookingById(@PathVariable int id) {
            Booking booking = bookingService.getBookingById(id);
            if (booking != null) {
                return ResponseEntity.ok(booking);
            } else {
                throw new ResourceNotFoundException("Booking not found with ID: " + id);
            }
        }

        @GetMapping("/getByIdWithPayments/{bookingId}")
        public ResponseEntity<Booking> getBookingByIdWithPayments(@PathVariable int bookingId) {
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                throw new ResourceNotFoundException("Booking not found with ID: " + bookingId);
            }
            // Fetch payments associated with this booking from payment microservice
            ResponseEntity<Payment[]> responseEntity = restTemplate.getForEntity("http://localhost:8086/payments/getbybid/" + bookingId, Payment[].class);
            Payment[] payments = responseEntity.getBody();
            booking.setPayments(Arrays.asList(payments));

            return ResponseEntity.ok(booking);
        }


        @GetMapping("/getall")
        public List<Booking> getAllBookings() {
            return bookingService.getAllBookings();
        }


        @GetMapping("/getAllWithPayments")
        public List<Booking> getAllBookingsWithPayments() {
            List<Booking> bookings = bookingService.getAllBookings();
            try{
            for (Booking booking : bookings) {
                // Fetch payments associated with each booking from payment microservice
                ResponseEntity<Payment[]> responseEntity = restTemplate.getForEntity("http://localhost:8086/payments/getbybid/" + booking.getBooking_id(), Payment[].class);
                Payment[] payments = responseEntity.getBody();
                booking.setPayments(Arrays.asList(payments));
            }
            }catch (ResourceNotFoundException e) {
                    return (List<Booking>) ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payments not found for booking ID: ");
                }
            return bookings;

        }

        @GetMapping("/byCid/{Cid}")
        public List<Booking> getBookingsByCid(@PathVariable int Cid) {
            List<Booking> bookings = bookingService.getBookingsByCid(Cid);
            if (!bookings.isEmpty()) {
                return bookings;
            }
            return bookings;
        }
           // } else {
             //   throw new ResourceNotFoundException("Bookings not found for Cid: " + Cid);
           // }
       // }


        @GetMapping("/byRid/{rid}")
        public List<Booking> getBookingsByRid(@PathVariable int rid) {
            List<Booking> bookings = bookingService.getBookingsByRid(rid);
            if (!bookings.isEmpty()) {
                return bookings;
            } else {
                throw new ResourceNotFoundException("Bookings not found for rid: " + rid);
            }
        }




        @PutMapping("/update/{id}")
        public ResponseEntity<String> updateBooking(@PathVariable int id, @RequestBody Booking booking) {
            Booking updatedBooking = bookingService.updateBooking(id, booking);
            if (updatedBooking != null) {
                return ResponseEntity.ok("Booking with ID " + id + " updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PutMapping("/updatePaymentForBooking/{bookingId}")
        public ResponseEntity<String> updatePaymentForBooking(@PathVariable int bookingId, @RequestBody Payment payment) {
            // Ensure the Payment object has the correct ID set
            payment.setId(bookingId);

            // Make a REST call to the Payment microservice to update the payment
            String url = "http://localhost:8086/payments/updatebyID/" + bookingId;
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(payment), String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Payment for booking ID " + bookingId + " updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update payment for booking ID " + bookingId);
            }
        }



        @DeleteMapping("/cancelbookings/{id}")
        public ResponseEntity<String> cancelBooking(@PathVariable int id) {
            boolean canceled = bookingService.cancelBooking(id);
            if (canceled) {
                return ResponseEntity.ok("Booking canceled successfully");
            } else {
                throw new ResourceNotFoundException("Booking not found with ID: " + id);
            }
        }

        @DeleteMapping("/deletePaymentForBooking/{bookingId}")
        public ResponseEntity<String> deletePaymentForBooking(@PathVariable int bookingId) {
            String paymentDeleteUrl = "http://localhost:8086/payments/delete/" + bookingId;

            ResponseEntity<String> responseEntity = restTemplate.exchange(paymentDeleteUrl, HttpMethod.DELETE, null, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Payment for booking ID " + bookingId + " deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Payment for booking ID " + bookingId + " deleted successfully");
            }
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


