package view;

import controller.RandomProvider;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import model.*;

/***
 * 2 dimenziós pontot reprezentál egy rácson.
 */
class Point {
    /**
     * Létrehoz egy új példányt a megadott koordinátákkal.
     * 
     * @param x
     * @param y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int x;
    int y;

    /**
     * Kiszámolja a pont és egy másik pont távolságát.
     * 
     * @param p a másik pont
     * @return
     */
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

/**
 * Játékelemek (rovarok és gombatestek) létrehozásához használt interfész.
 */
interface GameElementCreator<T> {
    /**
     * Létrehozza a létrehozandó játékelemet a megadott cellán
     * 
     * @param cell     A cél cella
     * @param instance A létrehozandó játékelem szülője
     */
    void create(Cell cell, T instance);
}

public class View {
    /** Rejtett konstruktor */
    private View() {
    }

    // #region CONSTANTS
    /** Háttérszín */
    private static final Color BACKGROUND_COLOR = new Color(60, 120, 216, 255);
    // #endregion

    // #region ASSOCIATIONS
    /** Kiválasztott cella */
    private static Cell selected;
    /** Térkép */
    private static VMap map;
    /** Kiválasztott játékos */
    private static VPlayer selectedPlayer;
    /** Kolónia játékosok listája */
    private static List<VColony> allColonies;
    /** Gombafaj játékosok listája */
    private static List<VFungus> allFungi;
    // #endregion

    // TODO DOC @Panni
    public static void redraw(Graphics2D g) {
        // @Panni ez így, hogy van pan+zoom kell még, vagy már nincs rá szükség?
        // ha akarunk offsetet kezdésnél, akkor azt ott is be lehet állítani
        // - Dávid
        g.translate(Cell.SIZE, Cell.SIZE);
        map.draw(g);
        g.translate(-Cell.SIZE, -Cell.SIZE);
        g.dispose();
    }

    /**
     * Vezérlőpontokat generál adott méretű rácson úgy, hogy egy helyen ne lehessen 2 vezérlőpont.
     * 
     * @param count  vezérlőpontok száma
     * @param cols   oszlopok száma a rácsban
     * @param rows   sorok száma a rácsban
     * @param minDst egy cella minimális szélessége és hosszúsága
     * @return a vezérlőpontok egy Set-ben
     */
    private static Set<Point> createControlPoints(int count, int cols, int rows, int minDst) {
        int minUnitCols = cols / minDst;
        int minUnitRows = rows / minDst;
        Set<Point> controlPoints = new HashSet<>();
        for (int i = 0; i < count; i++) {
            Point p;
            do {
                p = new Point(RandomProvider.nextInt(minUnitCols) * minDst + minDst / 2,
                        RandomProvider.nextInt(minUnitRows) * minDst + minDst / 2);
            } while (controlPoints.contains(p));
            controlPoints.add(p);
        }
        return controlPoints;
    }

    /**
     * Minden vezérlőponthoz megkeresi azokat a cellákat, melyek az érintett vezérlőponthoz vannak legközeleb.
     * 
     * @param rows          a rács sorai
     * @param cols          a rács oszlopai
     * @param controlPoints vezérlőpontok
     * @return
     */
    private static HashMap<Point, List<Cell>> groupCellsByControlPoint(final int cols, final int rows,
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

    /**
     * Létrehoz egy véletlen típusú tektont.
     * 
     * @return
     */
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

    /**
     * Véletlenszerű tektonokat keres, majd ezeket a megtölti.
     * 
     * @param <T>           A létrehozandó játékelem típusa
     * @param emptyVTectons Az üres tektonok
     * @param all           A létrehozandó játékelemek szülői
     * @param creator       A létrehozást vezérlő objektum
     */
    private static <T> void putStuffOnEmptyTectons(List<VTecton> emptyVTectons, List<T> all,
            GameElementCreator<T> creator) {
        for (T f : all) {
            VTecton targetTecton;
            targetTecton = emptyVTectons.remove(RandomProvider.nextInt(emptyVTectons.size()));
            Cell targetCell = targetTecton.getCells().get(RandomProvider.nextInt(targetTecton.getCells().size()));
            creator.create(targetCell, f);
        }
    }

    /**
     * Inicializál egy új játékot, annak térképét, valamint a résztvevő játékosokat és játékelemeket.
     * 
     * @param tecNum   létrehozandó tektonok száma
     * @param fungiNum létrehozandó gombafajok száma
     * @param colNum   létrehozandó rovar kolóniák száma
     */
    public static void create(int tecNum, int fungiNum, int colNum) {
        List<Color> colors = generateColors(fungiNum + colNum);
        allFungi = IntStream.range(0, fungiNum).mapToObj(x -> new VFungus(new Fungus(), colors.get(x))).toList();
        allColonies = IntStream.range(0, colNum).mapToObj(x -> new VColony(new Colony(), colors.get(fungiNum + x)))
                .toList();

        final int minDst = 3;
        final int rows = (int) Math.ceil(4 * Math.sqrt((tecNum * minDst * minDst)));
        final int cols = 3 * rows / 2;

        Set<Point> controlPoints = createControlPoints(tecNum, cols, rows, minDst);

        map = new VMap(cols, rows);

        HashMap<Point, List<Cell>> tectonsCells = groupCellsByControlPoint(cols, rows, controlPoints);

        List<VTecton> vtectons = new ArrayList<>();
        for (var entry : tectonsCells.entrySet()) {
            vtectons.add(new VTecton(entry.getValue(), createRandomTypeTecton()));
        }

        putStuffOnEmptyTectons(vtectons, allFungi,
                (cell, vf) -> new VMushroom(cell, new Mushroom(vf.getFungus(), cell.getTecton().getTecton())));

        putStuffOnEmptyTectons(vtectons, allColonies,
                (cell, vf) -> new VInsect(cell, new Insect(cell.getTecton().getTecton(), vf.getColony())));

    }

    /**
     * A játékosok színeit generálja. A színek egyenlő távolságra vannak egymástól a színkörön, telítettségük és
     * fényességük állandó.
     * 
     * @param playerCount a játékosok száma
     * @return a játékosok színei
     */
    private static List<Color> generateColors(int playerCount) {
        List<Color> colors = new ArrayList<>();
        final float saturation = 0.9f;
        final float brightness = 0.6f;
        for (int i = 0; i < playerCount; i++) {
            float hue = (float) i / playerCount;
            colors.add(Color.getHSBColor(hue, saturation, brightness));
        }
        return colors;
    }

    public static void notifyUser() {
        try {
            FileInputStream fis = new FileInputStream("resources/error.wav");
            BufferedInputStream bis = new BufferedInputStream(fis);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bis);
            Clip clip = AudioSystem.getClip();

            clip.open(audioStream);
            clip.start();
            
            audioStream.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Kattintás eseménykezelő.
     * 
     * Ha nincs kiválasztva cella, akkor a kattintott cellát választja ki. Ha van kiválasztva cella, akkor végrehajtja a
     * megfelelő műveletet. Ezt a kiválasztott játékos és a két cellán szereplő elem típusa határozza meg. Ha nincs a
     * típusoknak megfelelő művelet, akkor értesíti a felhasználót.
     * 
     * @param x egér x koordinátája
     * @param y egér y koordinátája
     */
    public static void click(int x, int y) {// NOSONAR complexity, így olvashatóbb
        Cell clicked = map.cellAt(x - Cell.SIZE, y - Cell.SIZE); // compensate for the offset
        if (clicked == null)
            return;
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

    // TODO DOC aki megcsinálja
    public static void endGame() {
        // TODO @Márton @Vazul @Tamás
        
    }

    // #region GETTERS-SETTERS
    public static Cell getSelected() {
        return selected;
    }

    public static VPlayer getSelectedPlayer() {
        return selectedPlayer;
    }

    public static void setSelectedPlayer(VPlayer selectedPlayer) {
        View.selectedPlayer = selectedPlayer;
    }

    public static VMap getMap() {
        return map;
    }

    public static List<VColony> getAllColonies() {
        return allColonies;
    }

    public static List<VFungus> getAllFungi() {
        return allFungi;
    }

    public static Color getBackgroundColor() {
        return BACKGROUND_COLOR;
    }
    // #endregion
}