package hotel.hotel.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private int id;
    private int hid;
    private String name;

    private int age;

    private String city;

    private int salary;

    private String Position;
    private int phone;

    @Override
        public String toString() {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", city='" + city + '\'' +
                    ", salary=" + salary +
                    ", Position='" + Position + '\'' +
                    ", phone=" + phone +
                    '}';
        }
}
