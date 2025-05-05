package view;

public class Cell {
    // #region ASSOCIATIONS
    private IIcon item;
    private VTecton tecton;
    // #endregion

    // #region ATTRIBUTES
    private int x;
    private int y;
    private static int size = -1;
    // #endregion

    // #region CONSTRUCTOR
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // #endregion

    // #region FUNCTIONS
    public void draw() {
        // TODO @Panni
        // ref: rendering.puml
    }
    // #endregion

    // #region GETTERS-SETTERS
    public static int getSize() {
        return size;
    }

    public static void setSize(int cellSize) {
        if (size != -1)
            throw new IllegalStateException("Cell size is already set.");
        size = cellSize;
    }

    public IIcon getItem() {
        return item;
    }

    public void setItem(IIcon item) {
        this.item = item;
    }

    public VTecton getTecton() {
        return tecton;
    }

    public void setTecton(VTecton tecton) {
        this.tecton = tecton;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    // #endregion
}