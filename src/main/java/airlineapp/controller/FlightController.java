package airlineapp.controller;

import java.time.LocalDateTime;
import java.util.List;

import airlineapp.model.Flight;
import airlineapp.service.FlightService;

public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    public Flight getFlightById(String id) {
        return flightService.getFlightById(id);
    }

    public List<Flight> searchFlights(String destination, String from, LocalDateTime departure, int seatsNeed) {
        return flightService.searchFlights(destination, from, departure, seatsNeed);
    }

    public void increaseBookedSeats(Flight flight) {
        flightService.increaseBookedSeats(flight);
    }

    public void decreaseBookedSeats(Flight flight) {
        flightService.decreaseBookedSeats(flight);
    }
}