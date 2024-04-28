package hotel.hotel.Repositry;



import hotel.hotel.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {


    Hotel findByNameOrEmailOrPhone(String name, String email, int phone);
}
