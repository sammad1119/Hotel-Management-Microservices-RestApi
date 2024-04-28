package Employee.Hotelemployee.Services;
import Employee.Hotelemployee.Exception.ResourceNotFoundException;
import Employee.Hotelemployee.Model.Employee;
import Employee.Hotelemployee.Repositry.EmpolyeeRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class ServiceImpl implements ServiceInterface {

    @Autowired
    public EmpolyeeRepo employeeRepository;

    @Override
    public boolean deleteEmployee(int id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(int id) {

        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public List<Employee> getEmployeesByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public List<Employee> getEmployeesByCity(String city) {
        return employeeRepository.findByCity(city);
    }

    @Override
    public List<Employee> findByHid(int hid) {
        return employeeRepository.findByHid(hid);
    }


    public Employee updateEmployee(int id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        updateEmployeeFields(existingEmployee, employee);

        return employeeRepository.save(existingEmployee);
    }

    public Employee updateEmployeeByName(String name, Employee employee) {
        Employee existingEmployee = employeeRepository.findByName(name).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with Name: " + name));

        updateEmployeeFields(existingEmployee, employee);

        return employeeRepository.save(existingEmployee);
    }

    private void updateEmployeeFields(Employee existingEmployee, Employee newEmployee) {
        existingEmployee.setName(newEmployee.getName());
        existingEmployee.setAge(newEmployee.getAge());
        existingEmployee.setCity(newEmployee.getCity());
        existingEmployee.setSalary(newEmployee.getSalary());
        existingEmployee.setPhone(newEmployee.getPhone());
        existingEmployee.setPosition(newEmployee.getPosition());
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }


}
