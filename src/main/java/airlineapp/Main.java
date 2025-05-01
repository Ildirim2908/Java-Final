package airlineapp;

import java.io.IOException;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import airlineapp.console.MainMenu;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Terminal terminal = TerminalBuilder.builder()
            .jna(true) // important for raw mode
            .system(true)
            .build();

        terminal.enterRawMode();
        
        MainMenu mainMenu = new MainMenu(terminal);
        mainMenu.display_login_and_register_screen();
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
