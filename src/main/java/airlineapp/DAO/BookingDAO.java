package airlineapp.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import airlineapp.model.Booking;
import airlineapp.model.Flight;
import airlineapp.model.Passenger;

public class BookingDAO {
    private List<Booking> bookings = new ArrayList<>();
    private final String filePath;

    public BookingDAO() {
        String userDir = System.getProperty("user.dir");
        this.filePath = userDir + File.separator + "bookings.dat";
        System.out.println("[DEBUG] Using bookings file path: " + filePath);
        
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) {
            System.out.println("[INFO] Found existing bookings file: " + file.length() + " bytes");
            loadBookingsFromFile();
        } else {
            System.out.println("[INFO] No existing bookings file, creating new one");
            this.bookings = new ArrayList<>();
            saveBookingsToFile();
        }
    }

    public Booking createBooking(Passenger passenger, Flight flight) {
        if (passenger == null) {
            System.err.println("[ERROR] Cannot create booking - passenger is null");
            return null;
        }
        if (flight == null) {
            System.err.println("[ERROR] Cannot create booking - flight is null");
            return null;
        }
        
        Booking booking = new Booking(passenger, flight);
        bookings.add(booking);
        
        boolean saved = saveBookingsToFile();
        if (!saved) {
            System.err.println("[ERROR] Failed to save booking");
        }
        return booking;
    }

    public List<Booking> getAllBookings() {
        return bookings == null ? new ArrayList<>() : new ArrayList<>(bookings);
    }

    public Booking findBooking(String id) {
        if (bookings == null) return null;
        return bookings.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Booking> getPassengerBookings(Passenger passenger) {
        if (bookings == null) return new ArrayList<>();
        if (passenger == null) {
            System.err.println("[WARN] Null passenger in getPassengerBookings");
            return new ArrayList<>();
        }
        
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getPassenger() != null && 
                b.getPassenger().getName().equals(passenger.getName())) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Booking> getFlightBookings(Flight flight) {
        if (bookings == null) return new ArrayList<>();
        return bookings.stream()
                .filter(b -> b.getFlight() != null && b.getFlight().equals(flight))
                .toList();
    }

    public void deleteBooking(Booking booking) {
        if (booking == null) {
            System.err.println("[WARN] Attempted to delete null booking");
            return;
        }
        
        boolean removed = bookings.remove(booking);
        System.out.println("[DEBUG] Booking " + booking.getId() + 
                          " removed: " + removed);
        
        saveBookingsToFile();
    }

    private void loadBookingsFromFile() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            System.out.println("[DEBUG] No bookings file or empty file found");
            this.bookings = new ArrayList<>();
            return;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        
        try {
            fis = new FileInputStream(filePath);
            System.out.println("[DEBUG] File opened for reading");
            
            ois = new ObjectInputStream(fis);
            System.out.println("[DEBUG] Object input stream created");
            
            Object obj = ois.readObject();
            System.out.println("[DEBUG] Object read successfully. Type: " + 
                             (obj != null ? obj.getClass().getName() : "null"));
            
            if (obj instanceof List<?>) {
                List<?> rawList = (List<?>) obj;
                bookings = new ArrayList<>();
                
                for (Object item : rawList) {
                    if (item instanceof Booking) {
                        Booking b = (Booking) item;
                        if (b.getFlight() == null) {
                            System.out.println("[WARN] Booking has null flight: " + b.getId());
                            continue;
                        }
                        if (b.getPassenger() == null) {
                            System.out.println("[WARN] Booking has null passenger: " + b.getId());
                            continue;
                        }
                        
                        bookings.add(b);
                    } else {
                        System.out.println("[WARN] Found non-Booking object in stored data: " + 
                            (item != null ? item.getClass().getName() : "null"));
                    }
                }
            } else {
                System.err.println("[ERROR] Stored data is not a List type");
                handleCorruptedData();
            }
        } catch (InvalidClassException e) {
            System.err.println("[ERROR] Class version mismatch: " + e.getMessage());
            handleCorruptedData();
        } catch (StreamCorruptedException e) {
            System.err.println("[ERROR] Corrupted data stream: " + e.getMessage());
            handleCorruptedData();
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] Could not find class: " + e.getMessage());
            handleCorruptedData();
        } catch (IOException e) {
            System.err.println("[ERROR] IO error loading bookings: " + e.getMessage());
            e.printStackTrace();
            handleCorruptedData();
        } catch (Exception e) {
            System.err.println("[ERROR] Unexpected error: " + e.getMessage());
            e.printStackTrace();
            handleCorruptedData();
        } finally {
            try {
                if (ois != null) ois.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                System.err.println("[ERROR] Failed to close streams: " + e.getMessage());
            }
        }
    }
    
    private void handleCorruptedData() {
        System.out.println("[WARN] Resetting bookings data due to loading error");
        this.bookings = new ArrayList<>();
        File corrupted = new File(filePath + ".corrupted");
        new File(filePath).renameTo(corrupted);
        System.out.println("[INFO] Corrupted file backed up as: " + corrupted.getName());
    }

    private boolean saveBookingsToFile() {
        if (bookings == null) {
            System.err.println("[ERROR] Cannot save - bookings list is null");
            return false;
        }
        
        
        File tempFile = new File(filePath + ".tmp");
        File mainFile = new File(filePath);
        
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean success = false;
    
        try {
            fos = new FileOutputStream(tempFile);
            oos = new ObjectOutputStream(fos);
            
            List<Booking> validBookings = new ArrayList<>();
            for (Booking b : bookings) {
                if (b == null) {
                    System.err.println("[WARN] Skipping null booking");
                    continue;
                }
                if (b.getFlight() == null) {
                    System.err.println("[WARN] Skipping booking with null flight: " + b.getId());
                    continue;
                }
                if (b.getPassenger() == null) {
                    System.err.println("[WARN] Skipping booking with null passenger: " + b.getId());
                    continue;
                }
                validBookings.add(b);
            }
            
            oos.writeObject(validBookings);
            oos.flush();
            
            if (!tempFile.exists() || tempFile.length() == 0) {
                throw new IOException("Failed to create temp file properly");
            }
            
    
            if (mainFile.exists() && !mainFile.delete()) {
                throw new IOException("Could not delete old file");
            }
            if (!tempFile.renameTo(mainFile)) {
                throw new IOException("Could not rename temp file");
            }
            success = true;
        } catch (IOException e) {
            System.err.println("[ERROR] Save failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                System.err.println("[ERROR] Failed to close streams: " + e.getMessage());
            }
            
            if (tempFile.exists() && !success) {
                tempFile.delete();
            }
        }
        
        return success;
    }
    
    public void diagnosticTest() {
        System.out.println("\n===== DIAGNOSTIC TEST =====");
        
        File file = new File(filePath);
        System.out.println("File path: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());
        System.out.println("Can read: " + file.canRead());
        System.out.println("Can write: " + file.canWrite());
        
        if (file.exists()) {
            System.out.println("File size: " + file.length() + " bytes");
        }
        
        System.out.println("Current bookings in memory: " + (bookings == null ? "null" : bookings.size()));
        
        System.out.println("\nTesting Flight serialization...");
        try {
            Flight testFlight = new Flight("TEST123", "TestDest", "TestFrom", 
                                          LocalDateTime.now(), 100, 0);
            File testFile = new File("test_flight.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(testFile));
            oos.writeObject(testFlight);
            oos.close();
            System.out.println("Flight serialization test PASSED");
            testFile.delete();
        } catch (Exception e) {
            System.out.println("Flight serialization test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nTesting Passenger serialization...");
        try {
            Passenger testPassenger = new Passenger("TestUser", "password");
            File testFile = new File("test_passenger.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(testFile));
            oos.writeObject(testPassenger);
            oos.close();
            System.out.println("Passenger serialization test PASSED");
            testFile.delete();
        } catch (Exception e) {
            System.out.println("Passenger serialization test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nTesting Booking serialization...");
        try {
            Passenger testPassenger = new Passenger("TestUser", "password");
            Flight testFlight = new Flight("TEST123", "TestDest", "TestFrom", 
                                          LocalDateTime.now(), 100, 0);
            Booking testBooking = new Booking(testPassenger, testFlight);
            
            File testFile = new File("test_booking.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(testFile));
            oos.writeObject(testBooking);
            oos.close();
            System.out.println("Booking serialization test PASSED");
            
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(testFile));
            Booking deserializedBooking = (Booking) ois.readObject();
            ois.close();
            System.out.println("Booking deserialization test PASSED");
            System.out.println("Deserialized booking ID: " + deserializedBooking.getId());
            
            testFile.delete();
        } catch (Exception e) {
            System.out.println("Booking serialization/deserialization test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nTesting full save and reload cycle...");
        if (bookings.isEmpty()) {
            System.out.println("Creating a test booking for save/reload test");
            Passenger testPassenger = new Passenger("TestUser", "password");
            Flight testFlight = new Flight("TEST123", "TestDest", "TestFrom", 
                                          LocalDateTime.now(), 100, 0);
            Booking testBooking = new Booking(testPassenger, testFlight);
            bookings.add(testBooking);
        }
        
        boolean saveSuccess = saveBookingsToFile();
        System.out.println("Save successful: " + saveSuccess);
        
        int originalCount = bookings.size();
        List<String> originalIds = new ArrayList<>();
        for (Booking b : bookings) {
            originalIds.add(b.getId());
        }
        bookings.clear();
        
        loadBookingsFromFile();
        System.out.println("Reload complete, bookings count: " + bookings.size() + 
                          " (original: " + originalCount + ")");
        
        boolean allFound = true;
        for (String id : originalIds) {
            boolean found = false;
            for (Booking b : bookings) {
                if (b.getId().equals(id)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Booking ID not found after reload: " + id);
                allFound = false;
            }
        }
        System.out.println("All original bookings found after reload: " + allFound);
        
        System.out.println("===== DIAGNOSTIC TEST COMPLETE =====\n");
    }
}