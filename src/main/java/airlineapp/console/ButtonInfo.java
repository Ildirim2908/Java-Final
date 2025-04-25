package airlineapp.console;

public class ButtonInfo {
    private final String name;
    private final int width;
    private final int height;

    public ButtonInfo(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
