package airlineapp.model;

public class Passanger {
    private String name;
    private int[] booking_ids = new int[100];
    private int amount_of_bookings = 0;

    public Passanger(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

}
