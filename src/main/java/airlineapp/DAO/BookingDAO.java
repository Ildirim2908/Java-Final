package airlineapp.DAO;

import airlineapp.model.Booking;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingDAO
{
    private final String filePath = "bookings.dat";
    private List<Booking> bookings = new ArrayList<>();

    public BookingDAO()
    {
        loadBookings();
    }

    public Booking createBooking(Passenger passenger, List<Flight> flights)
    {
        Booking booking = new Booking(passenger, flights);
        booking.setId(UUID.randomUUID().toString());
        booking.getBookingTimes().add(LocalDateTime.now());
        bookings.add(booking);
        saveBookings();
        return booking;
    }

    public List<Booking> getAllBookings()
    {
        return new ArrayList<>(bookings);
    }

    public Booking findBooking(String id)
    {
        return bookings.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Booking> getPassengerBookings(Passenger passenger)
    {
        return bookings.stream()
                .filter(b -> b.getPassenger().equals(passenger))
                .toList();
    }

    private void saveBookings()
    {
        File tmpFile = new File(filePath + ".tmp");
        File datFile = new File(filePath);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tmpFile)))
        {
            oos.writeObject(bookings);
            oos.flush();

            if (datFile.exists() && !datFile.delete())
            {
                throw new IOException("Failed to delete old bookings file");
            }
            if (!tmpFile.renameTo(datFile))
            {
                throw new IOException("Failed to rename temp bookings file");
            }
        }
        catch (IOException e)
        {
            System.err.println("Error saving bookings: " + e.getMessage());
            tmpFile.delete();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadBookings()
    {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
        {
            Object data = ois.readObject();
            if (data instanceof List)
            {
                bookings = (List<Booking>) data;
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.err.println("Error loading bookings: " + e.getMessage());
            bookings = new ArrayList<>();
        }
    }
}
