package DAO;
import model.Flight;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlightDAO {
    private List<Flight> flights = new ArrayList<>();
    private final String filePath = "flights.dat"; // Binary file

    public FlightDAO() {
        File file = new File(filePath);
        if (file.exists()) {
            loadFlightsFromFile();
        } else {
            generateSampleFlights();
            saveFlightsToFile();
        }
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
    }

    public Flight getFlightById(String id) {
        return flights.stream()
                .filter(f -> f.getFlightID().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void loadFlightsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            flights = (List<Flight>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading flights: " + e.getMessage());
        }
    }

    public void saveFlightsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(flights);
        } catch (IOException e) {
            System.out.println("Error saving flights: " + e.getMessage());
        }
    }

    private void generateSampleFlights() {
        String[] destinations = {"London", "Berlin", "Rome", "Paris", "Istanbul"};
        Random rand = new Random();
        String[] froms = {"Baku", "Kiev", "Moscow", "Tallin", "Florence"};

        for (int i = 1; i <= 20; i++) {
            String flightID = "F" + i;
            String destination = destinations[rand.nextInt(destinations.length)];
            String from = froms[rand.nextInt(destinations.length)];
            LocalDateTime departure = LocalDateTime.now().plusHours(rand.nextInt(48));
            int totalSeats = 100 + rand.nextInt(50);
            int bookedSeats = rand.nextInt(totalSeats / 2);

            flights.add(new Flight(flightID, destination, from, departure, totalSeats, bookedSeats));
        }
    }
}