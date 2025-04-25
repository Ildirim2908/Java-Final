package airlineapp.DAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import airlineapp.model.Flight;

public class FlightDAO {
    private List<Flight> flights = new ArrayList<>();
    private final String filePath = "flights.dat";

    public FlightDAO() {
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) {
            loadFlightsFromFile();
        } else {
            generateSampleFlights();
            saveFlightsToFile();
        }
    }

    public List<Flight> getAllFlights() {
        return flights == null ? new ArrayList<>() : new ArrayList<>(flights);
    }

    public Flight getFlightById(String id) {
        if (flights == null) return null;
        return flights.stream()
                .filter(f -> f.getFlightID().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void loadFlightsFromFile() {
        File file = new File(filePath);
        if (file.length() == 0) {
            generateSampleFlights();
            saveFlightsToFile();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                flights = (List<Flight>) obj;
            } else {
                throw new IOException("Invalid data format");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading flights: " + e.getMessage());
            generateSampleFlights();
            saveFlightsToFile();
        }
    }

    public void saveFlightsToFile() {
        File tempFile = new File(filePath + ".tmp");
        File mainFile = new File(filePath);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            oos.writeObject(flights);
            oos.flush();

            if (mainFile.exists() && !mainFile.delete()) {
                throw new IOException("Could not delete old file");
            }
            if (!tempFile.renameTo(mainFile)) {
                throw new IOException("Could not rename temp file");
            }
        } catch (IOException e) {
            System.err.println("Error saving flights: " + e.getMessage());
            if (tempFile.exists()) tempFile.delete();
        }
    }

    // ... (rest of your code, e.g., generateSampleFlights())
    private void generateSampleFlights() {
        String[] destinations = {"London", "Berlin", "Rome", "Paris", "Istanbul"};
        Random rand = new Random();
        String[] froms = {"Baku", "Kiev", "Moscow", "Tallin", "Florence"};

        for (int i = 1; i <= 20; i++) {
            String flightID = "F" + i;
            String destination = destinations[rand.nextInt(destinations.length)];
            String from = froms[rand.nextInt(froms.length)];
            LocalDateTime departure = LocalDateTime.now()
                    .plusHours(rand.nextInt(48))
                    .plusMinutes(rand.nextInt(60));
            int totalSeats = 100 + rand.nextInt(50);
            int bookedSeats = rand.nextInt(totalSeats / 2);

            flights.add(new Flight(flightID, destination, from, departure, totalSeats, bookedSeats));
        }
    }
}