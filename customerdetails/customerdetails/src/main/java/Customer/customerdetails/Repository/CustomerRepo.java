package Customer.customerdetails.Repository;

import Customer.customerdetails.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {


}
