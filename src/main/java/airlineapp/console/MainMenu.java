package airlineapp.console;


import java.io.IOException;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class MainMenu {
    Terminal terminal;
    int width;
    int height;
    boolean isWindows = isWindows();

    public MainMenu() throws IOException {
        this.terminal = TerminalBuilder.terminal();
        this.width = terminal.getWidth();
        this.height = terminal.getHeight();
    }
    public boolean isWindows(){
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }
    public void enableAnsii() {
        if (!isWindows) {
            return; // ANSI is supported natively on non-Windows systems
        }
    
        try {
            // Load the kernel32 library
            Kernel32 kernel32 = Native.load("kernel32", Kernel32.class);
    
            // Get the console handle
            long consoleHandle = kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
    
            // Enable virtual terminal processing
            int mode = kernel32.GetConsoleMode(consoleHandle);
            kernel32.SetConsoleMode(consoleHandle, mode | Kernel32.ENABLE_VIRTUAL_TERMINAL_PROCESSING);
        } catch (Exception e) {
            System.err.println("Error enabling ANSI support: " + e.getMessage());
        }
    }
    
    // Define the Kernel32 interface for JNA
    public interface Kernel32 extends Library {
        int STD_OUTPUT_HANDLE = -11;
        int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 0x0004;
    
        long GetStdHandle(int nStdHandle);
        int GetConsoleMode(long hConsoleHandle);
        boolean SetConsoleMode(long hConsoleHandle, int dwMode);
    }

    public void init_menu() {
        if (isWindows) {
            enableAnsii();
        }
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
    }

    public void display_menu() throws InterruptedException {
        System.out.print("\u001B[H\u001B[1B\u001B[1C");
        String message = "Welcome to the Airline Reservation System";
        int startPosition = (width - message.length()) / 2;
        System.out.print("\u001B[" + (2) + ";" + startPosition + "H");
        System.out.print(message);

        System.out.print("\u001b[" + height + ";" + width + "f");
        Thread.sleep(10000000000L);
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
}