package airlineapp.console;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class MainMenu {
    Terminal terminal;
    int width;
    int height;
    boolean isWindows = isWindows();

    public boolean isWindows(){
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }
    public void enableAnsii() {
        try {
            new ProcessBuilder("cmd", "/c", "echo \u001B[?25h")
                .inheritIO()
                .start()
                .waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to enable ANSI for this session.");
        }
    }
    public void getRealWindowsTerminalSize() throws IOException {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{
                "powershell", "-command",
                "$host.UI.RawUI.WindowSize.Width; $host.UI.RawUI.WindowSize.Height"
            });

            if (!p.waitFor(2, TimeUnit.SECONDS)) {
            p.destroy();
            throw new IOException("PowerShell command timed out");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String widthLine = reader.readLine();
            String heightLine = reader.readLine();

            if (widthLine != null && heightLine != null) {
                this.width = Integer.parseInt(widthLine.trim());
                this.height = Integer.parseInt(heightLine.trim());
            } else {
                throw new IOException("Failed to read terminal dimensions from PowerShell");
            }
            }
        } catch (InterruptedException | NumberFormatException e) {
            System.err.println("Warning: Falling back to JLine terminal dimensions");
            this.width = terminal.getWidth();
            this.height = terminal.getHeight();
        }
    }
    

    public void init_menu() throws IOException, InterruptedException {
        if (isWindows) {
            enableAnsii();
            getRealWindowsTerminalSize();
        }
        else{
            terminal = TerminalBuilder.terminal();
            this.width = terminal.getWidth();
            this.height = terminal.getHeight();
        }

        height = (height > 30) ? 30 : height;

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
        String message = "Welcome to the Airline Reservation System" + width + " " +height;
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