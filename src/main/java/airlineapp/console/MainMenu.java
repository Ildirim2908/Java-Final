package airlineapp.console;

import airlineapp.console.ButtonInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class MainMenu {
    List<ButtonInfo> buttons = new ArrayList<>();
    int currentButtonIndex = 0;

    private Terminal terminal;


    int width;
    int height;

    boolean isWindows = isWindows();
    int currentheight = 2;

    String currentMenu = "Login"; //By default first we will be in login menu

    public MainMenu(Terminal terminal) {
        this.terminal = terminal;
    }

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
    //only for getting windows cmd border position which works poorly
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
    
    //basic border for the menu
    public void init_menu() throws IOException, InterruptedException {
        if (isWindows) {
            enableAnsii();
            getRealWindowsTerminalSize();
        }
        else{
            this.width = terminal.getWidth();
            this.height = terminal.getHeight();
        }

        height = (height > 30) ? 30 : height;

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
    }

    public void onTabKeyPressed(){
        // Check if the current menu is "Login"
        if (currentMenu.equals("Login")) {
            // Move to the next button
            this.currentButtonIndex = (currentButtonIndex) % buttons.size();
            ButtonInfo currentButton = buttons.get(currentButtonIndex);
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


            this.currentButtonIndex = (currentButtonIndex+1) % buttons.size();
            currentButton = buttons.get(currentButtonIndex);
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

    //Show Login Screen buttons and save their positions in buttons list
    public void display_login_screen() throws InterruptedException {
        int total_str_len;
        terminal.writer().print("\u001B[H\u001B[1B\u001B[1C");
        String message1 = "Welcome to the Airline Reservation System";
        String message2;
        terminal.writer().print("\u001B[" + (currentheight++) + ";" + (width - message1.length()) / 2 + "H");
        terminal.writer().print(message1);

        //login and register button
        currentheight++;
        message1 = "Login";
        message2 = "Register";
        total_str_len = message1.length() + message2.length() + 8 + 3;

        buttons.add(new ButtonInfo("Login",(width - total_str_len)/2, currentheight));
        buttons.add(new ButtonInfo("Register",((width - total_str_len)/2 + 7 + message1.length()), currentheight));

        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_str_len) / 2)  + "H");
        terminal.writer().print("\u2554");
        for (int i=0; i<message1.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557" + "   ");

        terminal.writer().print("\u2554");
        for (int i=0; i<message2.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u2557");

        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_str_len) / 2)  + "H");
        terminal.writer().print("\u2551");
        terminal.writer().print(" " + message1 + " ");
        terminal.writer().print("\u2551" + "   ");

        terminal.writer().print("\u2551");
        terminal.writer().print(" " + message2 + " ");
        terminal.writer().print("\u2551");

        terminal.writer().print("\u001B[" + (currentheight++) + ";" + ((width - total_str_len) / 2)  + "H");

        terminal.writer().print("\u255A");
        for (int i=0; i<message1.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u255D" + "   ");

        terminal.writer().print("\u255A");
        for (int i=0; i<message2.length() + 2;i++){
            terminal.writer().print("\u2550");
        }
        terminal.writer().print("\u255D");

        terminal.writer().print("\u001b[" + height + ";" + width + "f");
        terminal.flush();
    }


}