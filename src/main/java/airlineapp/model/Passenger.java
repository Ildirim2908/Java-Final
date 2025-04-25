package airlineapp.model;

public class Passenger {
    private String name;
    private int[] booking_ids = new int[900];
    private int amount_of_bookings = 0;

    public Passenger(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

}
