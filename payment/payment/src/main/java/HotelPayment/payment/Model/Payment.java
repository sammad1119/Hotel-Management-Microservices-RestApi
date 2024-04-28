package HotelPayment.payment.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Payment_Details")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Payment_ID")
    private int id;

    @Column(name = "Booking_ID")
    private int bid;

    @Column(name = "Rent")
    private int rent;

    @CreationTimestamp
    @Column(name ="Payment_Date" ,nullable = false,updatable = false)
    private Date paymentDate;

    @Column(name = "Payment_Method" )
    private String paymentMethod;

}
