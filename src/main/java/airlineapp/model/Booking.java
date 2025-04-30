package airlineapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String id;
    private final String flightid;
    private final Passenger passenger;
    private final LocalDateTime bookingTime;

    public Booking(Passenger passenger, Flight flight) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.passenger = passenger;
        this.flightid = flight.getFlightID();
        this.bookingTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getFlightId() {
        return flightid;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", flight=" + flightid +
                ", passenger=" + passenger.getName() +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
