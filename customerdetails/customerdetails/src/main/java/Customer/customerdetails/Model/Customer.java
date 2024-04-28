package Customer.customerdetails.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customer_Details")
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "Customer_ID")
    private int cid;

    @Column(name = "Customer_Name")
    private String customerName;

    @Column(name = "Customer_Address")
    private String customerAddress;

    @Column(name = "Customer_Phone")
    private int customerPhone;

    @Column(name = "Customer_Email")
    private String customerEmail;

    @Override
    public String toString() {
        return "Customer{" +
                "cid=" + cid +
                ", c_name='" + customerName + '\'' +
                ", customer_add='" + customerAddress + '\'' +
                ", c_phone=" + customerPhone +
                ", c_Email='" + customerEmail + '\'' ;
    }

    @Transient
    private List<Booking> bookings =new ArrayList<>();

}

