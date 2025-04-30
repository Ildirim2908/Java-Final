package airlineapp.service;

import java.time.LocalDateTime;
import java.util.List;

import airlineapp.DAO.FlightDAO;
import airlineapp.model.Flight;

public class FlightService {
    private final FlightDAO flightDAO;

    public FlightService(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    public List<Flight> getAllFlights() {
        return flightDAO.getAllFlights();
    }

    public Flight getFlightById(String id) {
        return flightDAO.getFlightById(id);
    }

    public List<Flight> searchFlights(String destination, String from, LocalDateTime departure, int seatsNeed) {
        return flightDAO.searchFlights(destination, from, departure, seatsNeed);
    }

    public void increaseBookedSeats(Flight flight) {
        flightDAO.increaseBookedSeats(flight);
    }
    public void decreaseBookedSeats(Flight flight) {
        flightDAO.decreaseBookedSeats(flight);
    }
}