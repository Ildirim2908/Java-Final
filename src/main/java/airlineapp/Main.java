package airlineapp;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import airlineapp.DAO.FlightDAO;
import airlineapp.console.MainMenu;
import airlineapp.model.Passenger;

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
