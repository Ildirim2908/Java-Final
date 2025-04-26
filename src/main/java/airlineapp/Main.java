package airlineapp;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import airlineapp.DAO.FlightDAO;
import airlineapp.console.MainMenu;
import airlineapp.model.Passenger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Passenger {
    private final String name;
    private final List<Integer> bookingIds;

    public Passenger(String name) {
        this.name = name;
        this.bookingIds = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addBooking(int bookingId) {
        bookingIds.add(bookingId);
    }

    public List<Integer> getBookingIds() {
        return new ArrayList<>(bookingIds);
    }

    public int getBookingCount() {
        return bookingIds.size();
    }
}


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
public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Terminal terminal = TerminalBuilder.builder()
            .jna(true) // important for raw mode
            .system(true)
            .build();

        terminal.enterRawMode();
        
        MainMenu mainMenu = new MainMenu(terminal);
        mainMenu.display_login_and_register_screen();

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = dateTime.format(formatter); 

        mainMenu.passengers.add(new Passenger("kenan"));
        mainMenu.passengers.add(new Passenger("nezrin"));
        mainMenu.passengers.add(new Passenger("ildirim"));
        mainMenu.passengers.add(new Passenger("fuad"));
        FlightDAO flightDAO = new FlightDAO();


        while (true) {
            int key = terminal.reader().read();
            switch (key) {
                case 9 -> // ASCII code for Tab is 9¬
                    mainMenu.onTabKeyPressed();
                case 27 -> {
                    mainMenu.exitProgram();
                }
                case 13 ->
                    mainMenu.onEnterKeyPressed();
                default -> {
                }
            }
        }
    }
}
