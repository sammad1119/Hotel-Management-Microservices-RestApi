package HotelBooking.booking.Services;
import HotelBooking.booking.Exception.ResourceNotFoundException;
import HotelBooking.booking.Model.Booking;
import HotelBooking.booking.Repositry.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepository;


    @Override
    public Booking addBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(int id) {
        Booking booking =bookingRepository.findById(id).orElse(null);
        return booking;
    }

    @Override
    public List<Booking> getBookingsByRid(int rid) {
        return bookingRepository.findByrid(rid);
    }
    @Override
    public List<Booking> getBookingsByCid(int Cid) {
        return bookingRepository.findByCid(Cid);
    }


    @Override
    public List<Booking> getBookingsBetweenDates(Date checkinDate, Date checkoutDate) {
        return bookingRepository.findByCheckinBetween(checkinDate, checkoutDate);
    }


    @Override
    public boolean cancelBooking(int id) {
        bookingRepository.deleteById(id);
        return true;
    }

    public Booking updateBooking(int id, Booking newBooking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            Booking existingBooking = optionalBooking.get();
            existingBooking.setCid(newBooking.getCid());
            existingBooking.setRid(newBooking.getRid());
            existingBooking.setCheckin(newBooking.getCheckin());
            existingBooking.setCheckout(newBooking.getCheckout());
            existingBooking.setPrice(newBooking.getPrice());

            return bookingRepository.save(existingBooking);
        } else {
            throw new ResourceNotFoundException("Booking not found with ID: " + id);
        }
    }


}
