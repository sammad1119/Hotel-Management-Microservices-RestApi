package HotelPayment.payment.Repositry;

import HotelPayment.payment.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {


    List<Payment> findByPaymentMethod(String paymentMethod);



    List<Payment> getPaymentByBid(int bid);
}
