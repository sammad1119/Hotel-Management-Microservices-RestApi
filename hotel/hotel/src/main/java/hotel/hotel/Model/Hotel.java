package hotel.hotel.Model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Hotel_ID")
    private int hid;


    @Column(name = "Hotel_Name")
    private String name;


    @Column(name = "Hotel_Address")
    private String  address;

    @Column(name = "Hotel_Phone")
    private int phone;

    @Column(name = "Hotel_Email")
    private String  email;

    @Override
    public String toString() {
        return "Hotel{" +
                "hid=" + hid + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                '}';
    }


@Transient
private List<Employee>  employees =new ArrayList<>();

    @Transient
    private List<Room>  rooms =new ArrayList<>();


}
