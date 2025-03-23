package model;

import helper.Skeleton;
import static helper.Skeleton.printCall;
import static helper.Skeleton.printReturn;
import java.util.Arrays;

/**
 * <h3>Gombatest</h3>
 * 
 * Felelőssége a saját fejlődésének kezelése, illetve a saját halálának feltételeinek beteljesülésének ellenőrzése.
 * Halálakor értesíti a megfelelő objektumokat, hogy ők is le tudják kezelni a halálának következményeit.
 * Felelőssége továbbá a spóraszórás kezdeményezése, illetve az ehhez szükséges ellenőrzések elvégzése.
 */
public class Mushroom implements IActive {
    /**
     * Mekkor a gomba hatótávja.
     */
    private int range;

    /**
     * Mennyi időnek kell még legalább eltelnie, hogy ismét szórhasson spórát.
     */
    private double cooldown;

    /**
     * Gombatesthez tartozó faj.
     */
    private Fungus species;

    /**
     * Az a tekton amelyen a gombatest található.
     */
    private Tecton location;

    /**
     * Létrehoz egy fungus fajú gombatestet a location tektonon
     * 
     * @param fungus a gombatesthez tartozó gombafaj.
     * @param location a tekton, amin található
     */
    public Mushroom(Fungus fungus, Tecton location) {
        printCall(this.getClass(), Arrays.asList(fungus, location));
        this.species = fungus;
        this.location = location;
        location.setMushroom(this);
        printReturn(this);
    }

    /**
     * spóraszórást kezdeményező metódus.
     * Hatására a paraméterben kapott tektonra spórát szór a gomba, amennyiben éppen spóraszórásra alkalmas állapotban van, és a céltekton a gombatest hatókörében található.
     * A spóraszórás hatására a gombatest meg is halhat, ha már kiszórta minden spóráját.
     * A visszatérési érték a spóraszórás sikeressége.
     * 
     * @param target a céltekton, ahova a spóra kerül
     */
    public void burstSpore(Tecton target) {
        Skeleton.printCall(this, Arrays.asList(target));
        boolean isReady = Skeleton.ask("Készen áll m1 a spóra szórására?");
        int tmpRange = 1; // attribútum helyett lokális változó (teszthez)

        if (isReady) {
            int distance = location.distanceTo(target);
            if (distance == 2) {
                boolean isGrown = Skeleton.ask("Fejlett-e a gomba?");
                if (isGrown)
                    tmpRange = 2;
            }
            if (distance <= tmpRange) {
                Spore spo = new Spore(species);
                Skeleton.addObject(spo, "spo");
                target.addSpore(spo);
                boolean alive = Skeleton.ask("Tud még spórát szórni a gomba?");
                if (!alive) {
                    location.removeMushroom();
                    species.removeMushroom(this);
                }
            }
        }
        Skeleton.printReturn();
    }

    @Override
    public void tick(double dT) {

    }

    /**
     * Visszaadja a spóra faját.
     * 
     * @return a spóra faja
     */
    public Fungus getSpecies() {
        Skeleton.printCall(this);
        Skeleton.printReturn(species);
        return species;
    }

    /**
     * Visszaadja azt a tektont, amin tartózkodik.
     * 
     * @return a tartózkodási tektonja
     */
    public Tecton getLocation() {
        Skeleton.printCall(this);
        Skeleton.printReturn(location);
        return location;
    }
}
