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
 * erről fajának értesítése, továbbá saját gombafajának nyilvántartása.
 */
public class Mycelium implements IActive {
    private final Fungus species;
    private final Tecton[] ends;

    /**
     * Konstruktor, létrehozza a paraméterként kapott gombafajhoz tartozó
     * gombafonalat, a két paraméterként kapott tekton közé.
     */
    public Mycelium(Fungus fungus, Tecton end1, Tecton end2) {
        this.species = fungus;
        this.ends = new Tecton[] { end1, end2 };

        end1.addMycelium(this);
        end2.addMycelium(this);
    }

    /**
     * Megszünteti az egyedet, erről értesíti a gombafaját és végpontjait.
     */
    public void die() {
        for (Tecton tecton : ends) {
            tecton.removeMycelium(this);
        }
        species.removeMycelium(this);
    }

    /**
     * Visszaadja a gombafonal két végpontját.
     */
    public Tecton[] getEnds() {
        return ends;
    }

    /**
     * Visszaadja a gombafajt, amelyhez a gombafonal tartozik.
     */
    public Fungus getSpecies() {
        return species;
    }

    @Override
    public void tick(double dT) {
        boolean thickened = Skeleton.ask("Megvastagodott a gombafonal?");
        if (thickened)
            species.myceliumGrowthComplete();
    }
}
