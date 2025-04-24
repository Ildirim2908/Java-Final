package airlineapp;

import java.io.IOException;

import airlineapp.console.MainMenu;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        try {
            MainMenu mainMenu = new MainMenu();
            mainMenu.getTerminalSize();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}