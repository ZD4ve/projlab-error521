package view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.ITectonFiller;
import model.Insect;
import model.Mushroom;
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
        // TODO @Márton @Vazul
        // ref: breaking.puml
    }

    public Object getColor() {
        // TODO @Panni
        return null;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Tecton getTecton() {
        return tecton;
    }

}