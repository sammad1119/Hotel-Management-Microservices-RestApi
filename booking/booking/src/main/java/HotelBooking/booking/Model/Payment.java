package HotelBooking.booking.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Payment {

        private int id;

        private int bid;

        private int rent;

        @CreationTimestamp
        private Date paymentDate;

        private String paymentMethod;

        @Override
        public String toString() {
            return "Payment{" +
                    "id=" + id +
                    ", bid=" + bid +
                    ", rent=" + rent +
                    ", paymentDate=" + paymentDate +
                    ", paymentMethod='" + paymentMethod + '\'' +
                    '}';
        }
    }

