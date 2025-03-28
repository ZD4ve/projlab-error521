package model;

import helper.Skeleton;
import static helper.Skeleton.printCall;
import static helper.Skeleton.printReturn;
import java.util.Arrays;

/**
 * <h3>Gombafonal</h3>
 * 
 * A gombafonalak bizonyos feltételeket teljesítő tektonokon nőhetnek,
 * felelőssége ezen feltételek ellenőrzése, valamint a létrejövés utáni
 * vastagodás elvégzése, ennek sikerességéről a gombafaj tájékoztatása.
 * Felelőssége továbbá a bármilyen okból történő megsemmisülésének kezelése,
 * erről fajának értesítése, továbbá saját gombafajánal nyilvántartása.
 */
public class Mycelium implements IActive {
    private final Fungus species;
    private final Tecton[] ends;

    /**
     * Konstruktor, létrehozza a paraméterként kapott gombafajhoz tartozó
     * gombafonalat, a két paraméterként kapott tekton közé.
     */
    public Mycelium(Fungus fungus, Tecton end1, Tecton end2) {
        Skeleton.printCall(this.getClass(), Arrays.asList(fungus, end1, end2));
        this.species = fungus;
        this.ends = new Tecton[] { end1, end2 };

        end1.addMycelium(this);
        end2.addMycelium(this);
        Skeleton.printReturn(this);
    }

    /**
     * Megszünteti az egyedet, erről értesíti a gombafaját és végpontjait.
     */
    public void die() {
        printCall(this);
        for (Tecton tecton : ends) {
            tecton.removeMycelium(this);
        }
        species.removeMycelium(this);
        printReturn();
    }

    /**
     * Visszaadja a gombafonal két végpontját.
     */
    public Tecton[] getEnds() {
        Skeleton.printCall(this);
        Skeleton.printReturn(ends);
        return ends;
    }

    /**
     * Visszaadja a gombafajt, amelyhez a gombafonal tartozik.
     */
    public Fungus getSpecies() {
        Skeleton.printCall(this);
        Skeleton.printReturn(species);
        return species;
    }

    @Override
    public void tick(double dT) {
        printCall(this, Arrays.asList(dT));
        boolean thickened = Skeleton.ask("Megvastagodott a gombafonal?");
        if (thickened)
            species.myceliumGrowthComplete();
        printReturn();
    }
}
