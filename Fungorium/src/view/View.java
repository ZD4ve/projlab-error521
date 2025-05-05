package view;

public class View {
    private Cell selected;
    private Map map;

    public View() {
        // Initialization logic
    }

    public void redraw() {
        map.draw();
        // Additional rendering logic
    }

    public void create(int tecNum, int fungiNum, int colNum) {
        // Logic to create the game state
    }

    public static void notifyUser() {
        // Notify the user with a sound effect or message
    }

    public void click(int x, int y) {
        Cell clickedCell = map.cellAt(x, y);
        if (selected == null) {
            selected = clickedCell;
        } else {
            // Handle interactions based on the selected cell and clicked cell
        }
    }

    public void endGame() {
        // Logic to end the game
    }

    public Cell getSelected() {
        return selected;
    }
}