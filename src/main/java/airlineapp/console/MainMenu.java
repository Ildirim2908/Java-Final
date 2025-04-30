package airlineapp.console;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jline.terminal.Terminal;

import airlineapp.DAO.BookingDAO;
import airlineapp.DAO.FlightDAO;
import airlineapp.DAO.PassengerDAO;
import airlineapp.controller.BookingController;
import airlineapp.controller.FlightController;
import airlineapp.controller.PassengerController;
import airlineapp.model.Booking;
import airlineapp.model.Flight;
import airlineapp.model.Passenger;
import airlineapp.service.BookingService;
import airlineapp.service.FlightService;
import airlineapp.service.PassangerService;
public class MainMenu {
    
    char[] char_buffer = new char[100];
    int char_buffer_c_index=0;

    String currentUser = new String();

    List<ButtonInfo> buttons = new ArrayList<>();
    
    PassengerDAO passengerDAO = new PassengerDAO();
    PassangerService passengerService = new PassangerService(passengerDAO);
    PassengerController passengerController = new PassengerController(passengerService);

    FlightDAO flightDAO = new FlightDAO();
    FlightService flightService = new FlightService(flightDAO);
    FlightController flightController = new FlightController(flightService);

    BookingDAO bookingDAO = new BookingDAO();
    BookingService bookingService = new BookingService(bookingDAO);
    BookingController bookingController = new BookingController(bookingService);

    int currentButtonIndex = -1;

    private final Terminal terminal;


    boolean isLogin = false;

    int width;
    int height;
    int currentheight = 2;

    String currentMenu = "Login_or_Register"; //By default first we will be in login menu

    boolean is_login_register_buttons_added = false;
    boolean is_user_dashboard_buttons_added = false;

    public void exitProgram() throws InterruptedException, IOException{
        if(null != currentMenu)switch (currentMenu) {
            case "Login_or_Register": {
                terminal.writer().print("\u001B[" + height + ";" + width + "H");
                terminal.flush();
                System.out.println("\nQuit detected.");
                System.exit(0);
            }
            case "Login":
                display_login_and_register_screen();
                break;
            case "Register":
                display_login_and_register_screen();
                break;
            case "User_Dashboard":
                display_login_and_register_screen();
                isLogin = false;
                break;
            case "Online-Board":
                display_user_dashboard();
                break;
            case "Show Flight Info":
                display_user_dashboard();
                break;
            case "Book Flight":
                display_user_dashboard();
                break;
            case "Cancel Flight":
                display_user_dashboard();
                break;
            case "My Flights":
                display_user_dashboard();
                break;
            default:
                break;
        }
    }
    
    public MainMenu(Terminal terminal) throws IOException {
        this.terminal = terminal;
        this.width = terminal.getWidth();
        this.height = terminal.getHeight();
        height = (height > 80) ? 80 : height;
    }

    public void OutputButtons (String message1, String message2, int space_between_buttons) throws IOException {

        int total_width;

        total_width = message1.length() + message2.length() + 8 + space_between_buttons;
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_width) / 2)  + "H");

        terminal.writer().print("\u2554");
        for (int i=0; i<message1.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557" + " ".repeat(space_between_buttons));
        terminal.writer().print("\u2554");
        for (int i=0; i<message2.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_width) / 2)  + "H");
        terminal.writer().print("\u2551");
        terminal.writer().print(" " + message1 + " ");
        terminal.writer().print("\u2551" + " ".repeat(space_between_buttons));
        terminal.writer().print("\u2551");
        terminal.writer().print(" " + message2 + " ");
        terminal.writer().print("\u2551");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_width) / 2)  + "H");
        terminal.writer().print("\u255A");
        for (int i=0; i<message1.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u255D" + " ".repeat(space_between_buttons));
        terminal.writer().print("\u255A");
        for (int i=0; i<message2.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u255D");
        terminal.flush();
    }
    
    public void OutputButtons (String message1) throws IOException {

        int total_width;

        total_width = message1.length() + 4;
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_width) / 2)  + "H");

        terminal.writer().print("\u2554");
        for (int i=0; i<message1.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_width) / 2)  + "H");
        terminal.writer().print("\u2551");
        terminal.writer().print(" " + message1 + " ");
        terminal.writer().print("\u2551");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_width) / 2)  + "H");
        terminal.writer().print("\u255A");
        for (int i=0; i<message1.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u255D");
        terminal.flush();
    }
    
    public void OutputFlight (String id, String From, String To, String DepartureTime, int AvailableSeats, int currentwidth) throws IOException {
        String line = String.format("%-5s %-15s %-15s %-20s %-3s",id + ":", From, To, DepartureTime, AvailableSeats);
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (3 + currentwidth) + "H");
        terminal.writer().print(line);
        terminal.flush();
    }
    //basic border for the menu
    public void init_menu(String esc, String tab, String enter) throws IOException, InterruptedException {
        currentheight = 2;
        terminal.writer().print("\u001B[2J\u001B[H\u001B[?25l\u001B[0;37;40m\u2554"); // Clear the screen

        for (int i=1;i<width-1;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557\n");
        for (int i=0;i<height-2;i++){
            terminal.writer().print("\u2551");
            for (int j=0;j<width-2;j++){
                terminal.writer().print(" ");
            }
            terminal.writer().print("\u2551\n");
        }
        terminal.writer().print("\u255A");
        for (int i=1;i<width-1;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u255D");

        terminal.writer().print("\u001B[H\u001B[1B\u001B[1C");
        String message1 = "Welcome to the Airline Reservation System";
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print(message1);

        terminal.writer().print("\u001B[" + (height-3) + ";" + 3 + "H");
        if(esc!= ""){
            terminal.writer().print(esc);
        }
        if(enter!= ""){
            terminal.writer().print("\u001B[" + (height-4) + ";" + 3 + "H");
            terminal.writer().print(enter);
        }
        if(tab!= ""){
            terminal.writer().print("\u001B[" + (height-5) + ";" + 3 + "H");
            terminal.writer().print(tab);
        }

        terminal.flush();
    }

    public void onTabKeyPressed(){
        // Check if the current menu is "Login"
        if(currentButtonIndex == -1) currentButtonIndex++;
        if (currentMenu.equals("Login_or_Register") || currentMenu.equals("User_Dashboard")) {
            int menu_button_group = 0;
            int amount_of_buttons = 2;
            if (currentMenu.equals("User_Dashboard")){
                menu_button_group = 2;
                amount_of_buttons = 5;
            }
            // Move to the next button
            this.currentButtonIndex = (currentButtonIndex) % amount_of_buttons;
            ButtonInfo currentButton = buttons.get(currentButtonIndex + menu_button_group);
            this.currentheight = currentButton.getHeight();

            // Clear the previous button
            terminal.writer().print("\u001B[" + (currentheight++)+ ";" + currentButton.getWidth() + "H");
            terminal.writer().print("\u001B[0;37;40m\u2554");
            for (int i=0; i<currentButton.getName().length() + 2;i++){
                terminal.writer().print("\u2550");
            }
            terminal.writer().print("\u2557");

            terminal.writer().print("\u001B[" + (currentheight++)+ ";" + currentButton.getWidth() + "H");
            terminal.writer().print("\u2551");
            terminal.writer().print(" " + currentButton.getName() + " ");
            terminal.writer().print("\u2551");

            terminal.writer().print("\u001B[" + (currentheight++)+ ";" + currentButton.getWidth() + "H");
            terminal.writer().print("\u255A");
            for (int i=0; i<currentButton.getName().length() + 2;i++){
                terminal.writer().print("\u2550");
            }
            terminal.writer().print("\u255D");


            this.currentButtonIndex = (currentButtonIndex+1) % amount_of_buttons;
            currentButton = buttons.get(currentButtonIndex + menu_button_group);
            this.currentheight = currentButton.getHeight();

            terminal.writer().print("\u001B[" + (currentheight++)+ ";" + currentButton.getWidth() + "H");
            terminal.writer().print("\u001B[1;30;47m\u2554");
            for (int i=0; i<currentButton.getName().length() + 2;i++){
                terminal.writer().print("\u2550");
            }
            terminal.writer().print("\u2557");

            terminal.writer().print("\u001B[" + (currentheight++)+ ";" + currentButton.getWidth() + "H");
            terminal.writer().print("\u2551");
            terminal.writer().print(" " + currentButton.getName() + " ");
            terminal.writer().print("\u2551");

            terminal.writer().print("\u001B[" + (currentheight++)+ ";" + currentButton.getWidth() + "H");
            terminal.writer().print("\u255A");
            for (int i=0; i<currentButton.getName().length() + 2;i++){
                terminal.writer().print("\u2550");
            }
            terminal.writer().print("\u255D\u001B[0;37;40m" + "\u001b[" + height + ";" + width + "f");

            terminal.flush();
        }
    }

    public void onEnterKeyPressed() throws IOException, InterruptedException {
        // Check if the current menu is "Login"
        if (currentMenu.equals("Login_or_Register") || currentMenu.equals("User_Dashboard")) {
            // Perform action based on the selected button
            if (currentButtonIndex == -1) return;
            int menu_button_group = 0;
            if (currentMenu.equals("User_Dashboard")){
                menu_button_group = 2;
            }
            ButtonInfo currentButton = buttons.get(currentButtonIndex + menu_button_group);
            String buttonName = currentButton.getName();
            if (buttonName.equals("Login")) {
                // Handle login action
                display_login_screen();

            } else if (buttonName.equals("Register")) {
                // Handle register action
                display_register_screen();
            }

            if(isLogin && (currentMenu.equals("Login") || currentMenu.equals("Register") || currentMenu.equals("Login_or_Register"))){
                display_user_dashboard();
            }

            else if (buttonName.equals("Online-Board")) {
                display_online_board();
            } else if (buttonName.equals("Show Flight Info")) {
                show_flight_info();
            } else if (buttonName.equals("Book Flight")) {
                show_book_a_flight();
            } else if (buttonName.equals("Cancel Flight")) {
                show_cancel_booking();
            } else if (buttonName.equals("My Flights")) {
                show_my_flights();
            }
        }
    }
    //Show Login Screen buttons and save their positions in buttons list
    public void display_login_and_register_screen() throws InterruptedException, IOException {
        init_menu("Press \"Esc\" to quit.", "Press \"Tab\" to select between buttons.", "Press \"Enter\" to continue.");

        currentButtonIndex = -1;
        currentMenu = "Login_or_Register";
        int total_str_len;
        terminal.writer().print("\u001B[H\u001B[1B\u001B[1C");
        String message1;
        String message2;
        //login and register button
        currentheight++;
        message1 = "Login";
        message2 = "Register";
        total_str_len = message1.length() + message2.length() + 8 + 3;

        if(!is_login_register_buttons_added){
            is_login_register_buttons_added = true;
            buttons.add(new ButtonInfo("Login",(width - total_str_len)/2, currentheight));
            buttons.add(new ButtonInfo("Register",((width - total_str_len)/2 + 7 + message1.length()), currentheight));
        }

        OutputButtons(message1, message2, 3);
    }

    public void register_name() throws IOException, InterruptedException{
        char_buffer_c_index=0;
        OUTER:
        while (true) {
            int key = terminal.reader().read();
            switch (key) {
                case 27 -> {
                    exitProgram();
                    break OUTER;
                }
                case 9 ->{
                    
                }
                case 13 -> {
                    if(char_buffer_c_index!=0){
                        String str = new String(char_buffer, 0, char_buffer_c_index);
                        if(passengerController.findPassenger(str)!= null){
                            char_buffer_c_index=5;
                            terminal.writer().print("\u001B[" + (6) + ";" + (((width - 15) / 2) + 1) + "H");
                            terminal.writer().print("ERROR       " + "\u001B[" + (6) + ";" + (((width - 15) / 2) + 6) + "H");
                            terminal.flush();
                        }
                        else{
                            passengerController.addPassenger(new Passenger(str, ""));
                            currentUser = str;
                            isLogin=true;
                            break OUTER;
                        }
                    }
                }
                case 127 -> {
                    if(char_buffer_c_index>0){
                        char_buffer[char_buffer_c_index--] = ' ';
                        terminal.writer().print("\u001B[1D \u001B[1D");
                        terminal.flush();
                    }
                }
                default -> {
                    if(char_buffer_c_index<13){
                        char_buffer[char_buffer_c_index++] = (char) key;
                        terminal.writer().print((char) key);
                        terminal.flush();
                    }

                }
            }
        }
        if(isLogin){
            terminal.writer().print("nigga");
        }
    }

    public void login_name() throws IOException, InterruptedException{
        char_buffer_c_index=0;
        OUTER:
        while (true) {
            int key = terminal.reader().read();
            switch (key) {
                case 27 -> {
                    exitProgram();
                    break OUTER;
                }
                case 9 ->{
                    
                }
                case 13 -> {
                    if(char_buffer_c_index!=0){
                        String str = new String(char_buffer, 0, char_buffer_c_index);

                        if(passengerController.findPassenger(str)!= null){
                            currentUser = str;
                            isLogin=true;
                            break OUTER;
                        }
                        else{
                        char_buffer_c_index=5;
                        terminal.writer().print("\u001B[" + (6) + ";" + (((width - 15) / 2) + 1) + "H");
                        terminal.writer().print("ERROR       " + "\u001B[" + (6) + ";" + (((width - 15) / 2) + 6) + "H");
                        terminal.flush();
                        }
                        
                    }
                }
                case 127 -> {
                    if(char_buffer_c_index>0){
                        char_buffer[char_buffer_c_index--] = ' ';
                        terminal.writer().print("\u001B[1D \u001B[1D");
                        terminal.flush();
                    }
                }
                default -> {
                    if(char_buffer_c_index<13){
                        char_buffer[char_buffer_c_index++] = (char) key;
                        terminal.writer().print((char) key);
                        terminal.flush();
                    }

                }
            }
        }
        if(isLogin){
            display_user_dashboard();
        }
    }

    public void enter_flight_id() throws IOException, InterruptedException{
        char_buffer_c_index=0;
        OUTER:
        while (true) {
            int key = terminal.reader().read();
            switch (key) {
                case 27 -> {
                    exitProgram();
                    break OUTER;
                }
                case 9 ->{
                    
                }
                case 13 -> {
                    if(char_buffer_c_index!=0){
                        String str = new String(char_buffer, 0, char_buffer_c_index);
                        Flight flight = flightController.getFlightById(str);
                        if (flight != null) {
                            String DepartureTime = flight.getDeparturetime().toString();
                            DepartureTime = DepartureTime.substring(0, DepartureTime.indexOf('T')) + " " + DepartureTime.substring(DepartureTime.indexOf('T') + 1, DepartureTime.indexOf('.') - 3);
                            currentheight++;
                            OutputFlight(flight.getFlightID(), flight.getFrom(), flight.getDestination(), DepartureTime, flight.getAvailableSeats(), 0);
                            terminal.writer().print("\u001B[" + (11) + ";" + ((((width - 15) / 2) + 1) + char_buffer_c_index) + "H");
                            currentheight-=2;
                            terminal.flush();
                        }
                        else{
                            char_buffer_c_index=5;
                            terminal.writer().print("\u001B[" + (11) + ";" + (((width - 15) / 2) + 1) + "H");
                            terminal.writer().print("ERROR       " + "\u001B[" + (11) + ";" + (((width - 15) / 2) + 6) + "H");
                            terminal.flush();
                        }
                    }

                }
                case 127 -> {
                    if(char_buffer_c_index>0){
                        char_buffer[char_buffer_c_index--] = ' ';
                        terminal.writer().print("\u001B[1D \u001B[1D");
                        terminal.flush();
                    }
                }
                default -> {
                    if(char_buffer_c_index<13){
                        char_buffer[char_buffer_c_index++] = (char) key;
                        terminal.writer().print((char) key);
                        terminal.flush();
                    }

                }
            }
        }
    }

    public void book_flight_by_id() throws IOException, InterruptedException{
        char_buffer_c_index=0;
        OUTER:
        while (true) {
            int key = terminal.reader().read();
            switch (key) {
                case 27 -> {
                    // clean button
                    for (int i=-1; i<3;i++){
                        terminal.writer().print("\u001B[" + (currentheight + i - 3) + ";" + ((width - 30) / 2)  + "H");
                        for (int j = 0; j < 30; j++) {
                            terminal.writer().print(" ");
                        }
                    }
                    terminal.writer().print("\u001B[" + (9) + ";" + ((width - 56) / 2)  + "H");
                    String emptystr = " ".repeat(65);
                    terminal.writer().print(emptystr + "\u001B[" + (9) + ";" + (((width - 56) / 2)) + "H");
                    terminal.flush();

                    currentheight = 11;
                    break OUTER;
                }
                case 9 ->{
                    
                }
                case 13 -> {
                    if(char_buffer_c_index!=0){
                        String str = new String(char_buffer, 0, char_buffer_c_index);
                        Flight flight = flightController.getFlightById(str);
                        if (flight != null) {
                            char_buffer_c_index=8;
                            flightController.increaseBookedSeats(flight);
                            bookingController.createBooking(passengerController.findPassenger(currentUser), flight);
                            terminal.writer().print("\u001B[" + (currentheight - 2) + ";" + (((width - 12) / 2)) + "H");
                            terminal.writer().print("Success.");
                            terminal.writer().print("\u001B[" + (currentheight - 2) + ";" + (((width - 12) / 2) + 8) + "H");
                            terminal.flush();
                        }
                        else{
                            char_buffer_c_index=5;
                            terminal.writer().print("\u001B[" + (currentheight-2) + ";" + (((width - 14) / 2) + 1) + "H");
                            terminal.writer().print("ERROR        " + "\u001B[" + (currentheight-2) + ";" + (((width - 14) / 2) + 6) + "H");
                            terminal.flush();
                        }
                    }

                }
                case 127 -> {
                    if(char_buffer_c_index>0){
                        char_buffer[char_buffer_c_index--] = ' ';
                        terminal.writer().print("\u001B[1D \u001B[1D");
                        terminal.flush();
                    }
                }
                default -> {
                    if(char_buffer_c_index<13){
                        char_buffer[char_buffer_c_index++] = (char) key;
                        terminal.writer().print((char) key);
                        terminal.flush();
                    }

                }
            }
        }
    }

    public void search_for_a_flight() throws InterruptedException, IOException {
        char_buffer_c_index=0;
        OUTER:
        while (true) {
            int key = terminal.reader().read();
            switch (key) {
                case 27 -> {
                    exitProgram();
                    break OUTER;
                }
                case 9 ->{
                    
                }
                case 13 -> {
                    if(char_buffer_c_index!=0){

                        String str = new String(char_buffer, 0, char_buffer_c_index);
                        
                        if (str.split(";", -1).length - 1 != 3) {
                            char_buffer_c_index=5;
                            String emptystr = " ".repeat(60);
                            terminal.writer().print("\u001B[" + (9) + ";" + (((width - 58) / 2) + 1) + "H");
                            terminal.writer().print("ERROR" + emptystr + "\u001B[" + (9) + ";" + (((width - 58) / 2) + 6) + "H");
                            terminal.flush();
                            continue;
                        }

                        String[] parts = str.split(";", -1);
                        String from = parts[0].trim().isEmpty() ? null : parts[0].trim();
                        String to = parts[1].trim().isEmpty() ? null : parts[1].trim();
                        String departureTimeStr = parts[2].trim().isEmpty() ? null : parts[2].trim();
                        String seatsNeededStr = parts[3].trim().isEmpty() ? null : parts[3].trim();

                        int seatsNeeded = (seatsNeededStr != null) ? Integer.parseInt(seatsNeededStr) : -1;
                        
                        LocalDateTime departureTime = null;

                        if (departureTimeStr != null) {
                            departureTimeStr = departureTimeStr.replace(" ", "T");
                            try{
                                departureTime = LocalDateTime.parse(departureTimeStr);
                            }
                            catch (Exception e){
                                char_buffer_c_index=5;
                                String emptystr = " ".repeat(60);
                                terminal.writer().print("\u001B[" + (9) + ";" + (((width - 58) / 2) + 1) + "H");
                                terminal.writer().print("ERROR" + emptystr + "\u001B[" + (9) + ";" + (((width - 58) / 2) + 6) + "H");
                                terminal.flush();
                                continue;
                            }
                        }
                        List<Flight> searchResults = flightController.searchFlights(to, from, departureTime, seatsNeeded);
                        if (searchResults.isEmpty()) {
                            String emptystr = " ".repeat(48);
                            char_buffer_c_index = 17;
                            terminal.writer().print("\u001B[" + (9) + ";" + (((width - 58) / 2) + 1) + "H");
                            terminal.writer().print("No flights found." + emptystr + "\u001B[" + (9) + ";" + (((width - 58) / 2) + 18) + "H");
                            terminal.flush();
                        } 
                        else {
                            terminal.writer().print("\u001B[" + (12) + ";" + (((width - 58) / 2) + 1) + "H");
                            for (Flight flight : searchResults) {
                                String DepartureTime = flight.getDeparturetime().toString();
                                DepartureTime = DepartureTime.substring(0, DepartureTime.indexOf('T')) + " " + DepartureTime.substring(DepartureTime.indexOf('T') + 1, DepartureTime.indexOf('.') - 3);
                                currentheight++;
                                OutputFlight(flight.getFlightID(), flight.getFrom(), flight.getDestination(), DepartureTime, flight.getAvailableSeats(), 0);
                            }

                            for (int i = currentheight; i < height - 4; i++) {
                                terminal.writer().print("\u001B[" + (i) + ";" + 2 + "H");
                                for (int j = 0; j < 80; j++) {
                                    terminal.writer().print(" ");
                                }
                            }

                            currentheight++;
                            book_flight_button();
                        }
                    }

                }
                case 127 -> {
                    if(char_buffer_c_index>0){
                        char_buffer[char_buffer_c_index--] = ' ';
                        terminal.writer().print("\u001B[1D \u001B[1D");
                        terminal.flush();
                    }
                }
                default -> {
                    if(char_buffer_c_index<65){
                        char_buffer[char_buffer_c_index++] = (char) key;
                        terminal.writer().print((char) key);
                        terminal.flush();
                    }

                }
            }
        }
    }

    public void cancel_booking_by_id() throws InterruptedException, IOException {
        char_buffer_c_index=0;
        OUTER:
        while (true) {
            int key = terminal.reader().read();
            switch (key) {
                case 27 -> {
                    exitProgram();
                    break OUTER;
                }
                case 9 ->{
                    
                }
                case 13 -> {
                    if(char_buffer_c_index!=0){
                        String str = new String(char_buffer, 0, char_buffer_c_index);
                        Booking booking = bookingController.findBooking(str);
                        if (booking != null && booking.getPassenger().getName().equals(currentUser)) {
                            flightController.decreaseBookedSeats(booking.getFlight());
                            bookingController.deleteBooking(booking);
                            terminal.writer().print("\u001B[" + (currentheight - 2) + ";" + (((width - 12) / 2)) + "H");
                            terminal.writer().print("Success.");
                            terminal.writer().print("\u001B[" + (currentheight - 2) + ";" + (((width - 12) / 2) + 8) + "H");
                            terminal.flush();
                        }
                        else{
                            char_buffer_c_index=5;
                            terminal.writer().print("\u001B[" + (currentheight-2) + ";" + (((width - 14) / 2) + 1) + "H");
                            terminal.writer().print("ERROR        " + "\u001B[" + (currentheight-2) + ";" + (((width - 14) / 2) + 6) + "H");
                            terminal.flush();
                        }
                    }

                }
                case 127 -> {
                    if(char_buffer_c_index>0){
                        char_buffer[char_buffer_c_index--] = ' ';
                        terminal.writer().print("\u001B[1D \u001B[1D");
                        terminal.flush();
                    }
                }
                default -> {
                    if(char_buffer_c_index<13){
                        char_buffer[char_buffer_c_index++] = (char) key;
                        terminal.writer().print((char) key);
                        terminal.flush();
                    }

                }
            }
        }
    }

    public void book_flight_button() throws IOException, InterruptedException{
        currentheight++;
        String message1 = "Book Flight";
        int total_str_len = message1.length() + 4;
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_str_len + 4) / 2)  + "H");
        terminal.writer().print(message1);
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_str_len) / 2)  + "H");
        terminal.writer().print("\u2554");
        for (int i=0; i<message1.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_str_len) / 2)  + "H");
        terminal.writer().print("\u2551");
        terminal.writer().print(String.valueOf(' ').repeat(total_str_len - 2));
        terminal.writer().print("\u2551");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_str_len) / 2)  + "H");
        terminal.writer().print("\u255A");
        for (int i=0; i<message1.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u255D");
        terminal.writer().print("\u001B[" + (currentheight - 2) + ";" + ((width - 12) / 2)  + "H");

        terminal.flush();
        book_flight_by_id();
    }

    public void display_register_screen() throws InterruptedException, IOException {
        init_menu("Press \"Esc\" to go back.", "", "Press \"Enter\" to continue.");
        currentMenu = "Register";
        
        currentheight++;
        String message1 = "Enter your name";
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print(message1);

        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");

        terminal.writer().print("\u2554");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557");

        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");

        terminal.writer().print("\u2551");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print(" ");
        }   
        terminal.writer().print("\u2551");

        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");

        terminal.writer().print("\u255A");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print("\u2550");
        } 
        terminal.writer().print("\u255D");


        terminal.writer().print("\u001B[" + (6) + ";" + (((width - message1.length()) / 2) + 1) + "H");

        terminal.flush();

        register_name();
    }

    public void display_login_screen() throws InterruptedException, IOException {
        init_menu("Press \"Esc\" to go back.", "", "Press \"Enter\" to continue.");
        currentMenu = "Login";
        
        currentheight++;
        String message1 = "Enter your name";
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print(message1);

        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");

        terminal.writer().print("\u2554");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557");

        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");

        terminal.writer().print("\u2551");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print(" ");
        }   
        terminal.writer().print("\u2551");

        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");

        terminal.writer().print("\u255A");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print("\u2550");
        } 
        terminal.writer().print("\u255D");


        terminal.writer().print("\u001B[" + (6) + ";" + (((width - message1.length()) / 2) + 1) + "H");

        terminal.flush();

        login_name();
    }

    public void display_user_dashboard() throws IOException, InterruptedException{
        init_menu("Press \"Esc\" to go back.", "Press \"Tab\" to select between buttons.", "Press \"Enter\" to continue.");
        currentButtonIndex = -1;
        currentMenu = "User_Dashboard";
        int total_str_len;
        currentheight ++;
        String message1 = "Welcome " + currentUser;
        String message2;

        terminal.writer().print("\u001B[" + (currentheight++) + ";" + 3 + "H");
        terminal.writer().print(message1);

        message1 = "Online-Board";
        message2 = "Show Flight Info";
        total_str_len = message1.length() + message2.length() + 8 + 8;
        if(!is_user_dashboard_buttons_added){
            buttons.add(new ButtonInfo("Online-Board",(width - total_str_len)/2, currentheight));
            buttons.add(new ButtonInfo("Show Flight Info",((width - total_str_len)/2 + 12 + message1.length()), currentheight));
        }
        OutputButtons(message1, message2, 8);

        currentheight+=2;
        message1 = "Book Flight";
        total_str_len = message1.length() + 4;
        if(!is_user_dashboard_buttons_added){
            buttons.add(new ButtonInfo("Book Flight",(width - total_str_len)/2, currentheight));
        }
        OutputButtons(message1);

        currentheight+=2;
        message1 = "Cancel Flight";
        message2 = "My Flights";
        total_str_len = message1.length() + message2.length() + 8 + 8;
        if(!is_user_dashboard_buttons_added){
            is_user_dashboard_buttons_added = true;
            buttons.add(new ButtonInfo("Cancel Flight",(width - total_str_len)/2, currentheight));
            buttons.add(new ButtonInfo("My Flights",((width - total_str_len)/2 + 12 + message1.length()), currentheight));
        }
        OutputButtons(message1, message2, 8);
    }

    public void display_online_board() throws IOException, InterruptedException{
        init_menu("Press \"Esc\" to go back.", "", "");
        currentMenu = "Online-Board";
        currentheight++;
        String message1 = "Online Board";
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        OutputButtons(message1);
        currentheight++;
        String line = String.format("%-5s %-15s %-15s %-20s %-3s","ID", "From", "To", "DepartureTime", "AvailableSeats");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + 3 + "H");
        terminal.writer().print(line);
        terminal.flush();
        List<Flight> flights = flightController.getAllFlights();
        for (Flight k : flights){
            String DepartureTime = k.getDeparturetime().toString();
            DepartureTime = DepartureTime.substring(0, DepartureTime.indexOf('T')) + " " + DepartureTime.substring(DepartureTime.indexOf('T') + 1, DepartureTime.indexOf('.') - 3);
            OutputFlight(k.getFlightID(), k.getFrom(), k.getDestination(), DepartureTime, k.getAvailableSeats(), 0);
        }
        terminal.flush();
    }

    public void show_flight_info() throws IOException, InterruptedException{
        init_menu("Press \"Esc\" to go back.", "Press \"Tab\" to select between buttons.", "Press \"Enter\" to continue.");
        currentMenu = "Show Flight Info";
        String message1 = "Flight Info";
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        OutputButtons(message1);
        currentheight+=2;

        message1 = "Enter Flight ID";
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print(message1);
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print("\u2554");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print("\u2551");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print(" ");
        }
        terminal.writer().print("\u2551");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print("\u255A");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u255D");

        terminal.writer().print("\u001B[" + (11) + ";" + (((width - message1.length()) / 2) + 1) + "H");

        terminal.flush();

        enter_flight_id();
    }

    public void show_book_a_flight() throws IOException, InterruptedException{
        init_menu("Press \"Esc\" to go back.", "", "Press \"Enter\" to continue.");
        currentMenu = "Book Flight";
        String message1 = "Book a Flight";
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        OutputButtons(message1);
        String message2 = "Format to search for a flight: From; To; DepartureTime; SeatsNeeded";
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message2.length() + 8) / 2 + "H");
        terminal.writer().print(message2);
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - 58) / 2 + "H");
        terminal.writer().print("\u2554");
        for (int i=0; i<message2.length()-2; i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - 58) / 2 + "H");
        terminal.writer().print("\u2551");
        for (int i=0; i<message2.length()-2; i++){
            terminal.writer().print(" ");
        }
        terminal.writer().print("\u2551");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - 58) / 2 + "H");
        terminal.writer().print("\u255A");
        for (int i=0; i<message2.length()-2; i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u255D");
        terminal.writer().print("\u001B[" + (9) + ";" + (((width - 58) / 2) + 1) + "H");
        terminal.flush();
        char_buffer_c_index=0;

        search_for_a_flight();
    }

    public void show_cancel_booking() throws IOException, InterruptedException{
        init_menu("Press \"Esc\" to go back.", "", "Press \"Enter\" to continue.");
        currentMenu = "Cancel Flight";
        String message1 = "Cancel a Flight";
        currentheight++;
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print(message1);
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print("\u2554");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print("\u2551");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print(" ");
        }
        terminal.writer().print("\u2551");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print("\u255A");
        for (int i=0; i<message1.length()-2; i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u255D");
        terminal.writer().print("\u001B[" + (6) + ";" + (((width - message1.length()) / 2) + 1) + "H");
        terminal.flush();

        cancel_booking_by_id();
    }

    public void show_my_flights() throws IOException, InterruptedException{

        Passenger passenger = passengerController.findPassenger(currentUser);
        init_menu("Press \"Esc\" to go back.", "", "Press \"Enter\" to continue.");
        List<Booking> passengerBookings = bookingController.getPassengerBookings(passenger);

        currentMenu = "My Flights";
        String message1 = "My Flights";
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        OutputButtons(message1);
        currentheight++;
        String line = String.format("%-9s %-5s %-15s %-15s %-20s %-3s","BID" ,"ID", "From", "To", "DepartureTime", "AvailableSeats");
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + 3 + "H");
        terminal.writer().print(line);
        terminal.flush();
        for (Booking k : passengerBookings){
            Flight flight = flightController.getFlightById(k.getFlight().getFlightID());
            String DepartureTime = flight.getDeparturetime().toString();
            DepartureTime = DepartureTime.substring(0, DepartureTime.indexOf('T')) + " " + DepartureTime.substring(DepartureTime.indexOf('T') + 1, DepartureTime.indexOf('.') - 3);
            String line2 = String.format("\u001B[" + (currentheight) + ";" + 3 + "H" + "%-8s ", k.getId());
            terminal.writer().print(line2);
            OutputFlight(flight.getFlightID(), flight.getFrom(), flight.getDestination(), DepartureTime, flight.getAvailableSeats(), 10);
        }
        terminal.flush();
    }
}