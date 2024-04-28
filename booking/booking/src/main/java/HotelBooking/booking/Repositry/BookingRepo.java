package HotelBooking.booking.Repositry;

import HotelBooking.booking.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Integer> {
    List<Booking> findByrid(int rid);
    List<Booking> findByCid(int Cid);
    List<Booking> findByCheckinBetween(Date checkinDate, Date checkoutDate);

}
