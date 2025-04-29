package airlineapp.controller;

import java.util.List;

import airlineapp.model.Passenger;
import airlineapp.service.PassangerService;

public class PassangerController {
    private final PassangerService passangerService;
    public PassangerController(PassangerService passangerService) {
        this.passangerService = passangerService;
    }
    public void addPassenger(Passenger passenger) {
        passangerService.addPassenger(passenger);
    }
    public boolean isPasswordCorrect(String name, String password) {
        return passangerService.isPasswordCorrect(name, password);
    }
    public List<Passenger> getAllPassengers() {
        return passangerService.getAllPassengers();
    }
    public Passenger findPassenger(String name) {
        return passangerService.findPassenger(name);
    }
}
