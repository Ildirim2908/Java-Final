package airlineapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Booking implements Serializable
{
    private String id;
    private List<Flight> flights;
    private Passenger passenger;
    private List<LocalDateTime> bookingTimes;

    public Booking(Passenger passenger, List<Flight> flights)
    {
        this.flights = flights;
        this.passenger = passenger;
    }

    public String getId()
    {
        return id;
    }

    public List<Flight> getFlights()
    {
        return flights;
    }

    public Passenger getPassenger()
    {
        return passenger;
    }

    public List<LocalDateTime> getBookingTimes()
    {
        return bookingTimes;
    }

    @Override
    public String toString()
    {
        return "Booking{" +
                "id='" + id + '\'' +
                ", flight=" + flights +
                ", passengers=" + passenger +
                ", bookingTime=" + bookingTimes +
                '}';
    }
}