package Employee.Hotelemployee.Controller;

import Employee.Hotelemployee.Model.Employee;
import Employee.Hotelemployee.Exception.ResourceNotFoundException;
import Employee.Hotelemployee.Services.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/Employees")
public class EmployeeController {

    @Autowired
        private ServiceInterface Service;

        @PostMapping("/add")
        public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
            List<Employee> existingEmployees = Service.getEmployeesByName(employee.getName());
            if (!existingEmployees.isEmpty()) {
                return new ResponseEntity<>("Employee with the same name already exists", HttpStatus.CONFLICT);
            }

            Employee savedEmployee;
            savedEmployee = Service.saveEmployee(employee);
            return new ResponseEntity<>("Employee created", HttpStatus.CREATED);
        }


        @GetMapping("/getall")
        public ResponseEntity<List<Employee>> getAllEmployees() {
            List<Employee> employees = Service.getAllEmployees();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }

        @GetMapping("/getbyid/{id}")
        public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
            Employee employee = Service.getEmployeeById(id);
            if (employee != null) {
                return new ResponseEntity<>(employee, HttpStatus.OK);
            }
            throw new ResourceNotFoundException("Employee not found with ID: " + id);
        }

    @GetMapping("/byhid/{hid}")
    public ResponseEntity<List<Employee>> getEmployeesByHotelId(@PathVariable int hid) {
        List<Employee> employees = Service.findByHid(hid);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("Employees with HID " + hid + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }


        @GetMapping("/getbyname/{name}")
        public ResponseEntity<List<Employee>> getEmployeesByName(@PathVariable String name) {
            List<Employee> employees = Service.getEmployeesByName(name);
            if (!employees.isEmpty()) {
                return new ResponseEntity<>(employees, HttpStatus.OK);
            }
            throw new ResourceNotFoundException("Employee not found with name: " + name);
        }

        @GetMapping("/getbycity/{city}")
        public ResponseEntity<List<Employee>> getEmployeesByCity(@PathVariable String city) {
            List<Employee> employees = Service.getEmployeesByCity(city);
            if (!employees.isEmpty()) {
                return new ResponseEntity<>(employees, HttpStatus.OK);
            }
            throw new ResourceNotFoundException("Employee not found in city: " + city);
        }



    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee updatedEmployee = Service.updateEmployee(id, employee);
        return ResponseEntity.ok("Employee with ID " + id + " updated successfully");
    }

    @PutMapping("/updatebyname/{name}")
    public ResponseEntity<String> updateEmployeeByName(@PathVariable String name, @RequestBody Employee employee) {
        Employee updatedEmployee = Service.updateEmployeeByName(name, employee);
        return ResponseEntity.ok("Employee with Name " + name + " updated successfully");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        if (Service.deleteEmployee(id)) {
            return ResponseEntity.ok("Employee with ID: " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with ID: " + id + " not found");
        }
    }



}
