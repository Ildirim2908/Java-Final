package airlineapp.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import airlineapp.model.Booking;
import airlineapp.model.Flight;
import airlineapp.model.Passenger;

public class BookingDAO {
    private static final String FILE_PATH = "bookings.dat";
    private final List<Booking> bookings;
    
    public BookingDAO() {
        this.bookings = loadBookings();
    }

    public Booking createBooking(Passenger passenger, Flight flight) {
        Booking booking = new Booking(passenger, flight);
        bookings.add(booking);
        saveBookings();
        return booking;
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    public Booking findBooking(String id) {
        return bookings.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Booking> getPassengerBookings(Passenger passenger) {
        return bookings.stream()
                .filter(b -> b.getPassenger().equals(passenger))
                .toList();
    }
    
    public List<Booking> getFlightBookings(Flight flight) {
        return bookings.stream()
                .filter(b -> b.getFlight().equals(flight))
                .toList();
    }

    public void deleteBooking(Booking booking) {
        bookings.remove(booking);
        saveBookings();
    }

    private void saveBookings() {
        File tmpFile = new File(FILE_PATH + ".tmp");
        File datFile = new File(FILE_PATH);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tmpFile))) {
            oos.writeObject(bookings);
            
            if (datFile.exists() && !datFile.delete()) {
                throw new IOException("Failed to delete old file");
            }
            if (!tmpFile.renameTo(datFile)) {
                throw new IOException("Failed to rename temp file");
            }
        } catch (IOException e) {
            System.err.println("Error saving bookings: " + e.getMessage());
            tmpFile.delete();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Booking> loadBookings() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object data = ois.readObject();
            if (data instanceof List) {
                return (List<Booking>) data;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading bookings: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
