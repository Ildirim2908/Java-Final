package airlineapp.console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jline.terminal.Terminal;

import airlineapp.model.Passanger;


public class MainMenu {
    
    char[] char_buffer = new char[100];
    int char_buffer_c_index=0;

    String currentUser = new String();

    List<ButtonInfo> buttons = new ArrayList<>();
    public List<Passanger> passangers = new ArrayList<>();

    int currentButtonIndex = -1;

    private final Terminal terminal;


    boolean isLogin = false;

    int width;
    int height;
    int currentheight = 2;

    String currentMenu = "Login_or_Register"; //By default first we will be in login menu

    boolean is_login_register_buttons_added = false;

    public void exitProgram() throws InterruptedException, IOException{
        if("Login_or_Register".equals(currentMenu)){
            terminal.writer().print("\u001B[" + height + ";" + width + "H");
            terminal.flush();
            System.out.println("\nQuit detected.");
            System.exit(0);
        }
        else if("Login".equals(currentMenu) || "Register".equals(currentMenu)){
            display_login_and_register_screen();
        }
        else if("User_Dashboard".equals(currentMenu)){
            display_login_and_register_screen();
            isLogin = false;
        }
    }
    
    public MainMenu(Terminal terminal) throws IOException {
        this.terminal = terminal;
        this.width = terminal.getWidth();
        this.height = terminal.getHeight();
        height = (height > 30) ? 30 : height;
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
                // Handle online board action
                System.out.println("Online-Board button pressed");
            } else if (buttonName.equals("Show Flight Info")) {
                // Handle show flight info action
                System.out.println("Show Flight Info button pressed");
            } else if (buttonName.equals("Book Flight")) {
                // Handle book flight action
                System.out.println("Book Flight button pressed");
            } else if (buttonName.equals("Cancel Flight")) {
                // Handle cancel flight action
                System.out.println("Cancel Flight button pressed");
            } else if (buttonName.equals("My Flights")) {
                // Handle my flights action
                System.out.println("My Flights button pressed");
            }
        }
    }
    //Show Login Screen buttons and save their positions in buttons list
    public void display_login_and_register_screen() throws InterruptedException, IOException {
        init_menu();

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
                    break OUTER;
                }
                case 9 ->{
                    
                }
                case 13 -> {
                    if(char_buffer_c_index!=0){
                        String str = new String(char_buffer, 0, char_buffer_c_index);

                        for (Passanger k : passangers){
                            if (k.getName().equals(str)){
                                isLogin=true;
                                currentUser = str;
                                break OUTER;
                            }
                        }
                        char_buffer_c_index=5;
                        terminal.writer().print("\u001B[" + (6) + ";" + (((width - 15) / 2) + 1) + "H");
                        terminal.writer().print(char_buffer_c_index + "\u001B[" + (6) + ";" + (((width - 15) / 2) + 6) + "H");
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
            display_user_dashboard();
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
        buttons.add(new ButtonInfo("Online-Board",(width - total_str_len)/2, currentheight));
        buttons.add(new ButtonInfo("Show Flight Info",((width - total_str_len)/2 + 12 + message1.length()), currentheight));
        OutputButtons(message1, message2, 8);

        currentheight+=2;
        message1 = "Book Flight";
        total_str_len = message1.length() + 4;
        buttons.add(new ButtonInfo("Book Flight",(width - total_str_len)/2, currentheight));
        OutputButtons(message1);

        currentheight+=2;
        message1 = "Cancel Flight";
        message2 = "My Flights";
        total_str_len = message1.length() + message2.length() + 8 + 8;
        buttons.add(new ButtonInfo("Cancel Flight",(width - total_str_len)/2, currentheight));
        buttons.add(new ButtonInfo("My Flights",((width - total_str_len)/2 + 12 + message1.length()), currentheight));
        OutputButtons(message1, message2, 8);
    }

}