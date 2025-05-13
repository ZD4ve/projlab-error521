package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.ITectonFiller;
import model.Insect;
import model.Mushroom;
import model.Spore;
import model.Tecton;
import model.MyceliumAbsorbingTecton;
import model.MyceliumKeepingTecton;
import model.NoMushroomTecton;
import model.SingleMyceliumTecton;

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
        Map map = View.getMap();

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

    @Override
    public void breaking(Tecton dying, Tecton t1, Tecton t2) {
        // TODO @Márton @Vazul
        // ref: breaking.puml
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