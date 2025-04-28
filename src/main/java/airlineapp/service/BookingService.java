package airlineapp.service;

import airlineapp.DAO.BookingDAO;
import airlineapp.model.Booking;
import airlineapp.model.Flight;
import airlineapp.model.Passenger;
import java.util.List;

public class BookingService {
    private final BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public Booking createBooking(Passenger passenger, Flight flight) {
        return bookingDAO.createBooking(passenger, flight);
    }

    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    public Booking findBooking(String id) {
        return bookingDAO.findBooking(id);
    }

    public List<Booking> getPassengerBookings(Passenger passenger) {
        return bookingDAO.getPassengerBookings(passenger);
    }

    public List<Booking> getFlightBookings(Flight flight) {
        return bookingDAO.getFlightBookings(flight);
    }

    public void deleteBooking(Booking booking) {
        bookingDAO.deleteBooking(booking);
    }
}
