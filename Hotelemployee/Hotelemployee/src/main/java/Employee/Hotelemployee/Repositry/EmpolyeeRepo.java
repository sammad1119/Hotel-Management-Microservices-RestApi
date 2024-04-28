package Employee.Hotelemployee.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Employee.Hotelemployee.Model.Employee;

import java.util.List;

@Repository
public interface EmpolyeeRepo extends JpaRepository <Employee,Integer> {

    List<Employee> findByName(String name);
    List<Employee> findByCity(String city);

    List<Employee> findByHid(int hid);

}
