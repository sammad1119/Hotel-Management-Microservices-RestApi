package Customer.customerdetails.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Booking {

    private int booking_id;


    private int cid;


    private int rid;


    private Date checkin;

    private Date checkout;

    private int price;

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        String checkinStr = checkin != null ? dateFormat.format(checkin) : "N/A";
        String checkoutStr = checkout != null ? dateFormat.format(checkout) : "N/A";

        return "Booking ID: " + booking_id +
                ", Customer ID: " + cid +
                ", Room Number: " + rid +
                ", Check-in Date: " + checkinStr +
                ", Check-out Date: " + checkoutStr +
                ", Total Price: " + price;
    }
}