package HotelPayment.payment.Services;

import HotelPayment.payment.Model.Payment;

import java.util.Date;
import java.util.List;

public interface PaymentServiceInterface {
    Payment savePayment(Payment payment);

    List<Payment> getAllPayments();

    Payment getPaymentById(int id);

    List<Payment> getPaymentsByBookingId(int bid);

    List<Payment> getPaymentsByPaymentMethod(String paymentMethod);

    Payment updatePayment(int id, Payment payment);

    boolean deletePayment(int id);

}
