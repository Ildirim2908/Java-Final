package airlineapp.controller;

import java.util.List;
import airlineapp.model.Booking;
import airlineapp.model.Flight;
import airlineapp.model.Passenger;
import airlineapp.service.BookingService;

public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public Booking createBooking(Passenger passenger, Flight flight) {
        return bookingService.createBooking(passenger, flight);
    }

    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    public Booking findBooking(String id) {
        return bookingService.findBooking(id);
    }

    public List<Booking> getPassengerBookings(Passenger passenger) {
        return bookingService.getPassengerBookings(passenger);
    }

    public List<Booking> getFlightBookings(Flight flight) {
        return bookingService.getFlightBookings(flight);
    }

    public void deleteBooking(Booking booking) {
        bookingService.deleteBooking(booking);
    }
}
