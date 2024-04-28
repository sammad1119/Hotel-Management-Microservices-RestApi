package HotelBooking.booking.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Booking_Details")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BooKing_ID")
    private int booking_id;

    @Column(name = "Customer_ID")
    private int cid;

    @Column(name = "Room_NUMBER")
    private int rid;

    @Column(name = "Checkin_Date")
    private Date checkin;

    @Column(name = "Checkout_Date")
    private Date checkout;

    @Column(name = "TotalPrice")
    private int price;

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        String checkinStr = checkin != null ? dateFormat.format(checkin) : "N/A";
        String checkoutStr = checkout != null ? dateFormat.format(checkout) : "N/A";

        return "Booking ID: " + booking_id +
                ", Customer ID: " + cid+
                ", Room Number: " + rid +
                ", Check-in Date: " + checkinStr +
                ", Check-out Date: " + checkoutStr +
                ", Total Price: " + price;
    }

    @Transient
private List<Payment> Payments =new ArrayList<>();


}
