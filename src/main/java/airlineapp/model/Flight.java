package airlineapp.model;
import java.time.LocalDateTime;
import java.util.Objects;



public class Flight {
    private String From
    private String flightID;
    private String destination;
    private LocalDateTime departuretime;
    private int totalSeats;
    private int bookedSeats;
    public Flight(String flightID, String destination, String From, LocalDateTime departuretime, int totalSeats, int bookedSeats){
        this.flightID = flightID;
        this.destination = destination;
        this.departuretime = departuretime;
        this.totalSeats = totalSeats;
        this.bookedSeats = bookedSeats;
        this.From = From;

    }

    public String getFlightID(){
        return flightID;
    }
    public String getFrom() {return From;}
    public String getDestination(){return destination; }
    public LocalDateTime getDeparturetime(){
        return departuretime;
    }
    public int getTotalSeats(){
        return totalSeats;
    }
    public int getBookedSeats(){
        return bookedSeats;
    }
    public int getAvailableSeats(){
        return totalSeats - bookedSeats;
    }


    @Override
    public String toString() {
        return "Flight{" +
                "flightID='" + flightID + '\'' +
                ", destination='" + destination + '\'' +
                ", departuretime=" + departuretime +
                ", totalSeats=" + totalSeats +
                ", bookedSeats=" + bookedSeats +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
