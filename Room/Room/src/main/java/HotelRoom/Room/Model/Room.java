package HotelRoom.Room.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Room_Details")
public class Room {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "Room_ID")
        private int Rid;

        @Column(name = "Hotel_ID")
        private int hid;

        @Column(name = "Room_Type")
        private String roomType;

        @Column(name = "Status")
        private String status;


@Transient
private List<Bookings> bookings =new ArrayList<>();


}
