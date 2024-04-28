package Employee.Hotelemployee.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "Empoyee_ID")
    private int id;

    @Column(name = " Hotel _ID")
    private int hid;
    @Column(name = "Empoyee_Name")
    private String name;

    @Column(name = "Employee_Age")
    private int age;

    @Column(name = "Employee_City")
    private String city;

    @Column(name = "Employee_Salary")
    private int salary;

    @Column(name = "Employee_Position")
    private String Position;

    @Column(name = "Employee_Phone")
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
