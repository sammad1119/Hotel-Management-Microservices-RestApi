package HotelBooking.booking.Services;

import HotelBooking.booking.Model.Booking;

import java.util.Date;
import java.util.List;

public interface BookingService {
    Booking addBooking(Booking booking);
    List<Booking> getAllBookings();
    Booking getBookingById(int id);
    List<Booking> getBookingsByRid(int rid);
    List<Booking> getBookingsByCid(int Cid);

    List<Booking> getBookingsBetweenDates(Date checkinDate, Date checkoutDate);
    boolean cancelBooking(int id);

    Booking updateBooking(int id, Booking booking);
}
