package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import model.MyceliumAbsorbingTecton;
import model.Tecton;

public class View {
    private View() {
    }

    private static Cell selected;
    private static Map map;
    private static VPlayer selectedPlayer;
    private static List<VColony> allColonies;
    private static List<VFungus> allFungi;
    private static BufferedImage canvas = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB); // TODO @Panni not
                                                                                                    // hardcoded w/h
    private static Color backgroundColor = new Color(60, 120, 216, 255);

    public static void redraw() {
        if (map != null)
            map.draw(canvas);
        else
            System.out.println("Map is null");
    }

    public static void create(int tecNum, int fungiNum, int colNum) {
        // TODO @Márton @Vazul
        // ref: initialize.puml

        // for testing:
        map = new Map(10, 10, 50);
        allColonies = new ArrayList<>();
        allFungi = new ArrayList<>();
        Tecton t1 = new Tecton();
        List<Cell> cells1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                cells1.add(map.cellAt(i * Cell.getSize(), j * Cell.getSize()));
            }
        }
        new VTecton(cells1, t1);
        Tecton t2 = new MyceliumAbsorbingTecton();
        List<Cell> cells2 = new ArrayList<>();
        for (int i = 5; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells2.add(map.cellAt(i * Cell.getSize(), j * Cell.getSize()));
            }
        }
        new VTecton(cells2, t2);

    }

    public static void notifyUser() {
        // TODO @Tamás
    }

    public static void click(int x, int y) {// NOSONAR complexity, így olvashatóbb
        Cell clicked = map.cellAt(x, y);
        if (selected == null) {
            selected = clicked;
            return;
        }
        IIcon item1 = selected.getItem();
        IIcon item2 = clicked.getItem();

        if (selectedPlayer instanceof VColony colony && item1 instanceof VInsect insect) {
            if (insect.getInsect().getColony() != colony.getColony())
                return;
            if (item2 == null)
                insect.move(clicked);
            if (item2 instanceof VSpore)
                insect.eat(clicked);
            if (item2 instanceof VMycelium)
                insect.chew(clicked);
        }

        if (selectedPlayer instanceof VFungus fungus) {
            if (item2 != null)
                return;
            if (item1 == null) {
                if (selected == clicked)
                    fungus.growMushroom(clicked);
                if (selected != clicked)
                    fungus.growMycelium(selected, clicked);
            }
            if (item1 instanceof VMushroom mushroom && mushroom.getMushroom().getSpecies() == fungus.getFungus())
                mushroom.burst(clicked);

        }

    }

    public static void endGame() {
        // TODO @Márton @Vazul @Tamás
    }

    public static Cell getSelected() {
        return selected;
    }

    public static void setSelectedPlayer(VPlayer selectedPlayer) {
        View.selectedPlayer = selectedPlayer;
    }

    public static Map getMap() {
        return map;
    }

    public static List<VColony> getAllColonies() {
        return allColonies;
    }

    public static List<VFungus> getAllFungi() {
        return allFungi;
    }

    public static BufferedImage getCanvas() {
        return canvas;
    }

    public static Color getBackgroundColor() {
        return backgroundColor;
    }
}