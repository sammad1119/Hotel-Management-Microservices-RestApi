package Employee.Hotelemployee.Services;
import Employee.Hotelemployee.Model.Employee;
import java.util.List;

public interface ServiceInterface {

    List<Employee> getAllEmployees();
    Employee getEmployeeById(int id);
    List<Employee> getEmployeesByName(String name);
    List<Employee> getEmployeesByCity(String city);
    List<Employee> findByHid(int hid);

    boolean deleteEmployee(int id);
    Employee updateEmployee(int id, Employee employee);
    Employee updateEmployeeByName(String name, Employee employee);
    Employee saveEmployee(Employee employee);

}
