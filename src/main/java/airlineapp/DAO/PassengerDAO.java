package airlineapp.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import airlineapp.model.Passenger;

public class PassengerDAO {
    private final String filePath = "passengers.dat";
    private List<Passenger> passengers = new ArrayList<>();

    public PassengerDAO() {
        loadPassengers();
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        savePassengers();
    }

    public boolean isPasswordCorrect(String name, String password) {
        Passenger passenger = findPassenger(name);
        if (passenger != null && passenger.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    
    public List<Passenger> getAllPassengers() {
        return new ArrayList<>(passengers);
    }

    public Passenger findPassenger(String name) {
        return passengers.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    private void savePassengers() {
        File tmpFile = new File(filePath + ".tmp");
        File datFile = new File(filePath);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tmpFile))) {
            oos.writeObject(passengers);
            oos.flush();

            if (datFile.exists() && !datFile.delete()) {
                throw new IOException("Failed to delete old file");
            }
            if (!tmpFile.renameTo(datFile)) {
                throw new IOException("Failed to rename temp file");
            }
        } catch (IOException e) {
            e.printStackTrace();
            tmpFile.delete();
        }
    }

    private void loadPassengers() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object data = ois.readObject();
            if (data instanceof List) {
                passengers = (List<Passenger>) data;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            passengers = new ArrayList<>();
        }
    }
}