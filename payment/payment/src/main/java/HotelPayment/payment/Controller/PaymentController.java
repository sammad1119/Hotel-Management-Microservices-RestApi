package HotelPayment.payment.Controller;

import HotelPayment.payment.Exception.ResourceNotFoundException;
import HotelPayment.payment.Model.Payment;
import HotelPayment.payment.Services.PaymentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentServiceInterface paymentService;

    @PostMapping("/add")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.savePayment(payment);
        return ResponseEntity.ok(savedPayment);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/getbybid/{bid}")
    public ResponseEntity<?> getPaymentsByBookingId(@PathVariable int bid) {
            List<Payment> payments = paymentService.getPaymentsByBookingId(bid);
            return ResponseEntity.ok(payments);
        }



    @PutMapping("/updatebyID/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int id, @RequestBody Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(id, payment);
        return ResponseEntity.ok(updatedPayment);
        }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable int id) {
        boolean isDeleted = paymentService.deletePayment(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Payment with ID: " + id + " deleted successfully");
        } else {
            throw new ResourceNotFoundException("Payment with ID: " + id + " not found");
        }
    }


}
