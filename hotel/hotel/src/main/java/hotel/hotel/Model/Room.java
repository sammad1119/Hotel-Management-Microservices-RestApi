package hotel.hotel.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Room {

        private int Rid;

        private int hid;

        private String roomType;

        private String status;

        @Override
        public String toString() {
            return "Room{" +
                    "Rid=" + Rid +
                    ", hid=" + hid +
                    ", roomType='" + roomType + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

