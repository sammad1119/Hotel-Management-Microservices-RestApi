package HotelPayment.payment.Services;

import HotelPayment.payment.Exception.ResourceNotFoundException;
import HotelPayment.payment.Model.Payment;
import HotelPayment.payment.Repositry.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentServiceInterface {

    @Autowired
    private PaymentRepo paymentRepository;

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(int id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    @Override
    public List<Payment> getPaymentsByBookingId(int bookingId) {
        List<Payment> payments = paymentRepository.getPaymentByBid(bookingId);
        if (payments.isEmpty()) {
            throw new ResourceNotFoundException("Payments not found for booking ID: " + bookingId);
        }
        return payments;
    }


    @Override
    public List<Payment> getPaymentsByPaymentMethod(String paymentMethod) {
        return paymentRepository.findByPaymentMethod(paymentMethod);
    }

    @Override
    public Payment updatePayment(int id, Payment payment) {
        Payment existingPayment = getPaymentById(id);
        existingPayment.setBid(payment.getBid());
        existingPayment.setRent(payment.getRent());
        existingPayment.setPaymentDate(payment.getPaymentDate());
        existingPayment.setPaymentMethod(payment.getPaymentMethod());
        return paymentRepository.save(existingPayment);
    }

    @Override
    public boolean deletePayment(int id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        throw new ResourceNotFoundException("Payment with ID: " + id + " not found");
    }


}