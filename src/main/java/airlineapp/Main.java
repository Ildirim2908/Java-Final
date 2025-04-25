package airlineapp;

import java.io.IOException;
import java.time.LocalDateTime;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import airlineapp.console.MainMenu;
import airlineapp.model.Flight;
import airlineapp.model.Passanger;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Terminal terminal = TerminalBuilder.builder()
            .jna(true) // important for raw mode
            .system(true)
            .build();

        terminal.enterRawMode();
        
        MainMenu mainMenu = new MainMenu(terminal);
        mainMenu.display_login_and_register_screen();

        mainMenu.passangers.add(new Passanger("nigga"));
        mainMenu.flights.add(new Flight("1", "New York", LocalDateTime.of(2023, 10, 1, 10, 0), 100, 50));
        mainMenu.flights.add(new Flight("2", "Los Angeles", LocalDateTime.of(2023, 10, 2, 12, 0), 200, 100));
        mainMenu.flights.add(new Flight("3", "Chicago", LocalDateTime.of(2023, 10, 3, 14, 0), 150, 75));
        mainMenu.flights.add(new Flight("4", "Miami", LocalDateTime.of(2023, 10, 4, 16, 0), 120, 60));
        mainMenu.flights.add(new Flight("5", "Houston", LocalDateTime.of(2023, 10, 5, 18, 0), 180, 90));
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