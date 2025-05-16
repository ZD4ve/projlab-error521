package view;

import model.*;
import java.util.*;
import java.awt.Color;

/**
 * Becsomagol egy tektont, nyilvántartja celláit. Elérhetővé teszi a színét a típusának megfelelően. Kezeli a
 * tekton törését, az új tektonok feltöltését a megfelelő elemekkel.
 */
public class VTecton implements ITectonFiller {
    // #region ASSOCIATIONS
    /** Becsomagolt tekton */
    private Tecton tecton;
    /** A tektonon található cellák */
    private List<Cell> cells;
    // #endregion

    // #region CONSTANTS
    private static final int MIN_CELLS = 15;
    private static final java.util.Map<Class<? extends Tecton>, Color> TECTON_COLORS = Map.of(Tecton.class,
            new Color(239, 239, 239, 255), MyceliumAbsorbingTecton.class, new Color(217, 234, 211, 255),
            MyceliumKeepingTecton.class, new Color(201, 218, 248, 255), NoMushroomTecton.class,
            new Color(244, 204, 204, 255), SingleMyceliumTecton.class, new Color(255, 242, 204, 255),
            SingleMyceliumTecton.class, new Color(255, 242, 204, 255), SingleMyceliumTecton.class,
            new Color(255, 242, 204, 255));
    // #endregion

    // TODO DOC @Vazul
    private static class Vec2 {
        double x;
        double y;

        Vec2(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // TODO DOC @Vazul
    private static class Line {
        Vec2 point;
        Vec2 direction;

        Line(Vec2 point, Vec2 direction) {
            this.point = point;
            this.direction = direction;
        }
    }

    // #region CONSTRUCTORS
    /**
     * Létrehoz egy új VTecton példányt, beállítja a tekton referenciát és a cellákat. A becsomagolt tektont feltölti a
     * cellákban található elemekkel.
     *
     * @param cells  cellák, amik a tektonhoz tartoznak
     * @param tecton tekton
     */
    public VTecton(List<Cell> cells, Tecton tecton) {
        this.tecton = tecton;
        this.cells = cells;

        List<Spore> spores = new ArrayList<>();
        Mushroom mushroom = null;
        List<Insect> insects = new ArrayList<>();
        Set<Tecton> neighbors = new HashSet<>();
        VMap map = View.getMap();

        for (Cell cell : cells) {
            cell.setTecton(this);
            IIcon item = cell.getItem();
            if (item == null)
                continue;
            if (item instanceof VSpore vspore)
                spores.add(vspore.getSpore());
            if (item instanceof VMushroom vmushroom)
                mushroom = vmushroom.getMushroom();
            if (item instanceof VInsect vinsect)
                insects.add(vinsect.getInsect());
            map.getNeighbors(cell).forEach(n -> neighbors.add(n.getTecton().getTecton()));
        }
        tecton.fillWithStuff(spores, mushroom, insects, new ArrayList<>(neighbors), this);
    }
    // #endregion

    // TODO DOC @Vazul
    @Override
    public void breaking(Tecton dying, Tecton t1, Tecton t2) {
        Line mainAxis = getMainAxis();
        List<Cell> t1Cells = new ArrayList<>();
        List<Cell> t2Cells = new ArrayList<>();

        for (Cell cell : cells) {
            double dx = cell.getX() - mainAxis.point.x;
            double dy = cell.getY() - mainAxis.point.y;

            if (mainAxis.direction.x * dx + mainAxis.direction.y * dy > 0)
                t1Cells.add(cell);
            else
                t2Cells.add(cell);
        }

        new VTecton(t1Cells, t1);
        new VTecton(t2Cells, t2);
    }

    /**
     * Visszaadja, hogy a tekton képes-e törni.
     * 
     * @return true, ha a tekton nagyobb, mint a minimális méret, false egyébként
     */
    @Override
    public boolean canBreak() {
        return cells.size() >= MIN_CELLS;
    }

    /**
     * Visszaadja a tekton főtengelyét.
     * 
     * @return a tekton főtengelye
     */
    private Line getMainAxis() {
        Vec2 center = getCenterPoint();
        double sxx = 0;
        double syy = 0;
        double sxy = 0;

        for (Cell cell : cells) {
            double dx = cell.getX() - center.x;
            double dy = cell.getY() - center.y;

            sxx += dx * dx;
            syy += dy * dy;
            sxy += dx * dy;
        }

        double theta = 0.5 * Math.atan2(2 * sxy, sxx - syy);
        return new Line(center, new Vec2(Math.cos(theta), Math.sin(theta)));
    }

    /**
     * Visszaadja a tekton középpontját.
     * 
     * @return a tekton középpontja
     */
    private Vec2 getCenterPoint() {
        double centerX = 0;
        double centerY = 0;

        for (Cell cell : cells) {
            centerX += cell.getX();
            centerY += cell.getY();
        }
        centerX /= cells.size();
        centerY /= cells.size();

        return new Vec2(centerX, centerY);
    }

    /**
     * Visszaadja a tekton színét a tekton típusának megfelelően.
     * 
     * @return a tekton színe
     */
    public Color getColor() {
        Color color = TECTON_COLORS.get(tecton.getClass());
        if (color != null) {
            return color;
        }
        throw new IllegalArgumentException("Unknown class: " + tecton.getClass().getSimpleName());
    }

    // #region GETTERS-SETTERS
    public List<Cell> getCells() {
        return cells;
    }

    public Tecton getTecton() {
        return tecton;
    }
    // #endregion
}