package airlineapp.console;

import java.io.IOException;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class MainMenu {
    public void getTerminalSize() throws IOException, InterruptedException {
        // Linux/Mac command to set terminal size
        // Using jline or jansi libraries
        Terminal terminal = TerminalBuilder.terminal();
        int width = terminal.getWidth();
        int height = terminal.getHeight();
        System.out.print("\u001B[2J\u001B[H\u001B[?25l\u2554"); // Clear the screen

        for (int i=1;i<width-1;i++){
            System.out.print("\u2550");
        }
        System.out.print("\u2557\n");
        for (int i=0;i<height-2;i++){
            System.out.print("\u2551");
            for (int j=0;j<width-2;j++){
                System.out.print(" ");
            }
            System.out.print("\u2551\n");
        }
        System.out.print("\u255A");
        for (int i=1;i<width-1;i++){
            System.out.print("\u2550");
        }
        System.out.print("\u255D");
        Thread.sleep(1000);
    }
}