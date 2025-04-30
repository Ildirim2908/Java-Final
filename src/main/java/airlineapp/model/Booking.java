package airlineapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String id;
    private final Flight flight;
    private final Passenger passenger;
    private final LocalDateTime bookingTime;

    public Booking(Passenger passenger, Flight flight) {
        this.id = UUID.randomUUID().toString().substring(0, 5);
        this.passenger = passenger;
        this.flight = flight;
        this.bookingTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
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
                ", flight=" + flight.getFlightID() +
                ", passenger=" + passenger.getName() +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
