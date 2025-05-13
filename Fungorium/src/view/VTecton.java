package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.ITectonFiller;
import model.Insect;
import model.Mushroom;
import model.MyceliumAbsorbingTecton;
import model.MyceliumKeepingTecton;
import model.NoMushroomTecton;
import model.SingleMyceliumTecton;
import model.Spore;
import model.Tecton;

public class VTecton implements ITectonFiller {
    private Tecton tecton;
    private List<Cell> cells;

    public VTecton(List<Cell> cells, Tecton tecton) {
        this.tecton = tecton;
        this.cells = cells;

        List<Spore> spores = new ArrayList<>();
        Mushroom mushroom = null;
        List<Insect> insects = new ArrayList<>();
        Set<Tecton> neighbors = new HashSet<>();

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
            if (item instanceof VTecton vneighbor)
                neighbors.add(vneighbor.getTecton());
        }
        tecton.fillWithStuff(spores, mushroom, insects, new ArrayList<>(neighbors), this);
    }

    @Override
    public void breaking(Tecton dying, Tecton t1, Tecton t2) {
        double centerX = 0;
        double centerY = 0;

        for (Cell cell : cells) {
            centerX += cell.getX();
            centerY += cell.getY();
        }
        centerX /= cells.size();
        centerY /= cells.size();

        double sxx = 0;
        double syy = 0;
        double sxy = 0;

        for (Cell cell : cells) {
            double dx = cell.getX() - centerX;
            double dy = cell.getY() - centerY;
            sxx += dx * dx;
            syy += dy * dy;
            sxy += dx * dy;
        }

        double theta = 0.5 * Math.atan2(2 * sxy, sxx - syy);
        double ux = Math.cos(theta);
        double uy = Math.sin(theta);

        List<Cell> t1Cells = new ArrayList<>();
        List<Cell> t2Cells = new ArrayList<>();

        for (Cell cell : cells) {
            double dx = cell.getX() - centerX;
            double dy = cell.getY() - centerY;
            if (ux * dy + uy * dx > 0) {
                t1Cells.add(cell);
            } else {
                t2Cells.add(cell);
            }
        }

        new VTecton(t1Cells, t1);
        new VTecton(t2Cells, t2);
    }

    public Color getColor() {
        if (tecton.getClass() == Tecton.class)
            return new Color(239, 239, 239, 255);
        else if (tecton.getClass() == MyceliumAbsorbingTecton.class)
            return new Color(217, 234, 211, 255);
        else if (tecton.getClass() == MyceliumKeepingTecton.class)
            return new Color(201, 218, 248, 255);
        else if (tecton.getClass() == NoMushroomTecton.class)
            return new Color(244, 204, 204, 255);
        else if (tecton.getClass() == SingleMyceliumTecton.class)
            return new Color(255, 242, 204, 255);
        else
            throw new IllegalArgumentException("Unknown class: " + tecton.getClass().getSimpleName());
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Tecton getTecton() {
        return tecton;
    }

}