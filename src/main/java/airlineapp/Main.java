package airlineapp;

import java.io.IOException;

import airlineapp.console.MainMenu;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Terminal terminal = TerminalBuilder.builder()
            .jna(true) // important for raw mode
            .system(true)
            .build();

        terminal.enterRawMode();
        
        MainMenu mainMenu = new MainMenu(terminal);
        mainMenu.init_menu();
        mainMenu.display_login_screen();

        while (true) {
            int key = terminal.reader().read(); // reads one key at a time

            if (key == 9) { // ASCII code for Tab is 9
                mainMenu.onTabKeyPressed();
            } else if (key == 'q') {
                System.out.println("\nQuit detected.");
                break;
            } else {
                System.out.println("Key pressed: " + (char) key + " (" + key + ")");
            }
        }
        terminal.close();
    }
}