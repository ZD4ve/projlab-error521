package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

import controller.RandomProvider;
import model.*;

class Point {
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int x;
    int y;

    public double distanceTo(Point p) {
        return Math.sqrt(Math.pow((x - p.x), 2) + Math.pow((y - p.y), 2));
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point other) {
            return x == other.x && y == other.y;
        }
        return false;
    }
}

interface GameElementCreator<T> {
    void create(Cell cell, T instance);
}

public class View {
    private View() {
    }

    private static Cell selected;
    private static Map map;
    private static VPlayer selectedPlayer;
    private static List<VColony> allColonies;
    private static List<VFungus> allFungi;
    private static BufferedImage canvas = new BufferedImage(1500, 1000, BufferedImage.TYPE_INT_ARGB); // TODO @Panni not
                                                                                                      // hardcoded w/h
    private static Color backgroundColor = new Color(60, 120, 216, 255);

    public static void redraw() {
        Graphics2D g = canvas.createGraphics();
        g.setColor(View.getBackgroundColor());
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int size = Cell.getSize();
        g.translate(size, size);
        map.draw(g);
        g.translate(-size, -size);
        g.dispose();
    }

    private static Set<Point> createControlPoints(int tecNum, int minUnitCols, int minUnitRows, int minDst) {
        Set<Point> controlPoints = new HashSet<>();
        for (int i = 0; i < tecNum; i++) {
            Point p;
            do {
                p = new Point(RandomProvider.nextInt(minUnitCols) * minDst + 2,
                        RandomProvider.nextInt(minUnitRows) * minDst + 2);
            } while (controlPoints.contains(p));
            controlPoints.add(p);
        }
        return controlPoints;
    }

    private static HashMap<Point, List<Cell>> calculateTectonCells(final int rows, final int cols,
            Set<Point> controlPoints) {
        HashMap<Point, List<Cell>> tectonsCells = new HashMap<>();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Point curr = new Point(i, j);
                Point closest = controlPoints.stream()
                        .sorted((a, b) -> Double.compare(a.distanceTo(curr), b.distanceTo(curr))).findFirst()
                        .orElse(null);
                tectonsCells.putIfAbsent(closest, new ArrayList<>());
                tectonsCells.get(closest).add(map.getCellByIndex(i, j));
            }
        }
        return tectonsCells;
    }

    private static Tecton createRandomTypeTecton() {
        Tecton tecton;
        double rand = RandomProvider.nextRand();
        if (rand < 0.8) {
            tecton = new Tecton();
        } else if (rand < 0.85) {
            tecton = new MyceliumAbsorbingTecton();
        } else if (rand < 0.9) {
            tecton = new SingleMyceliumTecton();
        } else if (rand < 0.95) {
            tecton = new NoMushroomTecton();
        } else {
            tecton = new MyceliumKeepingTecton();
        }
        return tecton;
    }

    private static <T> void putStuffOnEmptyTectons(List<VTecton> emptyVTectons, List<T> all,
            GameElementCreator<T> creator) {
        for (T f : all) {
            VTecton targetTecton;
            targetTecton = emptyVTectons.remove(RandomProvider.nextInt(emptyVTectons.size()));
            Cell targetCell = targetTecton.getCells().get(RandomProvider.nextInt(targetTecton.getCells().size()));
            creator.create(targetCell, f);
        }
    }

    // TODO tecNum must be greater than fungiNum + colNum @Tamasz
    public static void create(int tecNum, int fungiNum, int colNum) {
        List<Color> colors = VPlayer.generateColors(fungiNum + colNum);
        allFungi = IntStream.range(0, fungiNum).mapToObj(x -> new VFungus(new Fungus(), colors.get(x))).toList();
        allColonies = IntStream.range(0, colNum).mapToObj(x -> new VColony(new Colony(), colors.get(fungiNum + x)))
                .toList();

        final int minDst = 3;
        final int rows = (int) Math.ceil(4 * Math.sqrt((tecNum * minDst * minDst)));
        final int cols = 3 * rows / 2;

        final int minUnitRows = rows / minDst;
        final int minUnitCols = cols / minDst;

        Set<Point> controlPoints = createControlPoints(tecNum, minUnitCols, minUnitRows, minDst);

        map = new Map(cols, rows, Math.min(canvas.getWidth() / cols, canvas.getHeight() / rows));

        HashMap<Point, List<Cell>> tectonsCells = calculateTectonCells(rows, cols, controlPoints);

        List<VTecton> vtectons = new ArrayList<>();
        for (var entry : tectonsCells.entrySet()) {
            vtectons.add(new VTecton(entry.getValue(), createRandomTypeTecton()));
        }

        putStuffOnEmptyTectons(vtectons, allFungi,
                (cell, vf) -> new VMushroom(cell, new Mushroom(vf.getFungus(), cell.getTecton().getTecton())));

        putStuffOnEmptyTectons(vtectons, allColonies,
                (cell, vf) -> new VInsect(cell, new Insect(cell.getTecton().getTecton(), vf.getColony())));

        // for testing:
        /*
         * map = new Map(10, 10, 50); Fungus f = new Fungus(); Colony c = new Colony(); VColony vColony = new
         * VColony(c); VFungus vFungus = new VFungus(f); allColonies = new ArrayList<>(); allColonies.add(vColony);
         * allFungi = new ArrayList<>(); allFungi.add(vFungus); Tecton t1 = new Tecton(); List<Cell> cells1 = new
         * ArrayList<>(); for (int i = 0; i < 5; i++) { for (int j = 0; j < 10; j++) { cells1.add(map.cellAt(i *
         * Cell.getSize(), j * Cell.getSize())); } } new VTecton(cells1, t1); Tecton t2 = new MyceliumAbsorbingTecton();
         * List<Cell> cells2 = new ArrayList<>(); for (int i = 5; i < 10; i++) { for (int j = 0; j < 10; j++) {
         * cells2.add(map.cellAt(i * Cell.getSize(), j * Cell.getSize())); } } // model is probably inconsistent here
         * new VTecton(cells2, t2); new VInsect(map.cellAt(0, 0), new Insect(t1, c)); Insect i = new Insect(t2, c); new
         * VInsect(map.cellAt(9 * Cell.getSize(), 0), i); i.addEffect(new SpeedEffect()); i.addEffect(new
         * ParalysingEffect()); i.addEffect(new AntiChewEffect()); new VMushroom(map.cellAt(0, Cell.getSize()), new
         * Mushroom(f, t1)); Mushroom mush = new Mushroom(f, t1); mush.setIsGrown(true); new
         * VMushroom(map.cellAt(Cell.getSize(), Cell.getSize()), mush); Spore spore = new Spore(f); t1.addSpore(spore);
         * new VSpore(map.cellAt(0, 2 * Cell.getSize()), spore); spore = new Spore(f); t2.addSpore(spore); new
         * VSpore(map.cellAt(7 * Cell.getSize(), 2 * Cell.getSize()), spore); Mycelium m = new Mycelium(f, t1, t2); new
         * VMycelium(map.cellAt(4 * Cell.getSize(), 0), m, map.cellAt(5 * Cell.getSize(), 0)); new
         * VMycelium(map.cellAt(5 * Cell.getSize(), 0), m, map.cellAt(4 * Cell.getSize(), 0));
         */

    }

    public static void notifyUser() {
        // TODO @Tamás
    }

    public static void click(int x, int y) {// NOSONAR complexity, így olvashatóbb
        Cell clicked = map.cellAt(x - Cell.getSize(), y - Cell.getSize()); // compensate for the offset
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
        selected = null;
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