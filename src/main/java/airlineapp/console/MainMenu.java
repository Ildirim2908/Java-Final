package airlineapp.console;

import airlineapp.model.Passanger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.jline.terminal.Terminal;


public class MainMenu {
    
    char[] char_buffer = new char[100];
    int char_buffer_c_index=0;

    String currentUser = new String();

    List<ButtonInfo> buttons = new ArrayList<>();
    List<Passanger> passangers = new ArrayList<>();

    int currentButtonIndex = -1;

    private Terminal terminal;


    boolean isLogin = false;

    int width;
    int height;
    int currentheight = 2;

    String currentMenu = "Login_or_Register"; //By default first we will be in login menu

    public void exitProgram(){
        terminal.writer().print("\u001B[" + height + ";" + width + "H");
        terminal.flush();
        System.out.println("\nQuit detected.");
        System.exit(0);
    }
    
    public MainMenu(Terminal terminal) throws IOException {
        this.terminal = terminal;
        this.width = terminal.getWidth();
        this.height = terminal.getHeight();
        height = (height > 30) ? 30 : height;
    }
    //basic border for the menu
    public void init_menu() throws IOException, InterruptedException {
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
        terminal.flush();
    }

    public void onTabKeyPressed(){
        // Check if the current menu is "Login"
        if(currentButtonIndex == -1) currentButtonIndex++;
        if (currentMenu.equals("Login_or_Register")) {
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

    public void onEnterKeyPressed() throws IOException, InterruptedException {
        // Check if the current menu is "Login"
        if (currentMenu.equals("Login_or_Register")) {
            // Perform action based on the selected button
            if (currentButtonIndex == -1) return;
            ButtonInfo currentButton = buttons.get(currentButtonIndex);
            String buttonName = currentButton.getName();
            if (buttonName.equals("Login")) {
                // Handle login action
                display_login_screen();

            } else if (buttonName.equals("Register")) {
                // Handle register action
                display_register_screen();
            }

            if(isLogin){
                display_user_dashboard();
            }
        }
    }
    //Show Login Screen buttons and save their positions in buttons list
    public void display_login_and_register_screen() throws InterruptedException, IOException {
        init_menu();

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

    public void register_name() throws IOException, InterruptedException{
        char_buffer_c_index=0;
        OUTER:
        while (true) {
            int key = terminal.reader().read();
            switch (key) {
                case 27 -> {
                    exitProgram();
                }
                case 9 ->{
                    
                }
                case 13 -> {
                    if(char_buffer_c_index!=0){
                        String str = new String(char_buffer);
                        boolean registered = true;
                        for (Passanger k : passangers){
                            if (k.getName().equals(str)){
                                char_buffer_c_index=5;
                                terminal.writer().print("\u001B[" + (6) + ";" + (((width - 15) / 2) + 1) + "H");
                                terminal.writer().print("ERROR        " + "\u001B[" + (6) + ";" + (((width - 15) / 2) + 6) + "H");
                                terminal.flush();
                                registered = false;
                            }
                        }
                        if(registered){
                            passangers.add(new Passanger(str));
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
                }
                case 9 ->{
                    
                }
                case 13 -> {
                    if(char_buffer_c_index!=0){
                        String str = new String(char_buffer);

                        for (Passanger k : passangers){
                            if (k.getName().equals(str)){
                                isLogin=true;
                                currentUser = str;
                                break OUTER;
                            }
                        }
                        char_buffer_c_index=5;
                        terminal.writer().print("\u001B[" + (6) + ";" + (((width - 15) / 2) + 1) + "H");
                        terminal.writer().print("ERROR        " + "\u001B[" + (6) + ";" + (((width - 15) / 2) + 6) + "H");
                        terminal.flush();
                        
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

    public void display_register_screen() throws InterruptedException, IOException {
        init_menu();
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
        init_menu();
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
        init_menu();
        

    }

}