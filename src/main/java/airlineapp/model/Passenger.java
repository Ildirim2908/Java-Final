package airlineapp.model;

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
