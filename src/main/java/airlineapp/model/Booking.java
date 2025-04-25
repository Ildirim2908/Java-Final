package airlineapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Booking implements Serializable
{
    private String id;
    private Flight flight;
    private List<Passenger> passengers;
    private LocalDateTime bookingTime;

    public Booking(Flight flight, List<Passenger> passengers)
    {
        this.id=UUID.randomUUID().toString();
        this.flight = flight;
        this.passengers = passengers;
        this.bookingTime = LocalDateTime.now();
    }

    public String getId()
    {
        return id;
    }

    public Flight getFlight()
    {
        return flight;
    }

    public List<Passenger> getPassengers()
    {
        return passengers;
    }

    public LocalDateTime getBookingTime()
    {
        return bookingTime;
    }

    @Override
    public String toString()
    {
        return "Booking{" +
                "id='" + id + '\'' +
                ", flight=" + flight +
                ", passengers=" + passengers +
                ", bookingTime=" + bookingTime +
                '}';
    }
}