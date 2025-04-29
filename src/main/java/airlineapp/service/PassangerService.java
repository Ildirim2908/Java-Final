package airlineapp.service;

import java.util.List;

import airlineapp.DAO.PassengerDAO;
import airlineapp.model.Passenger;
public class PassangerService {
    private final PassengerDAO passengerDAO;
    public PassangerService(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }
    public void addPassenger(Passenger passenger) {
        passengerDAO.addPassenger(passenger);
    }
    public boolean isPasswordCorrect(String name, String password) {
        return passengerDAO.isPasswordCorrect(name, password);
    }
    public List<Passenger> getAllPassengers() {
        return passengerDAO.getAllPassengers();
    }
    public Passenger findPassenger(String name) {
        return passengerDAO.findPassenger(name);
    }
}
