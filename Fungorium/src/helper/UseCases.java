package helper;

import static helper.Skeleton.*;

import java.util.List;

import model.Fungus;
import model.Insect;
import model.Mushroom;
import model.Mycelium;
import model.ParalysingEffect;
import model.SingleMyceliumTecton;
import model.Tecton;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class UseCases {
    private UseCases() {
    } // Skeleton ne legyen példányosítható

    public static void init() {
        // TODO: Doksiban szereplő sorrendben legyenek!
        // (@Tamás ezt majd te csináld meg a végén)

        // TODO: Itt szerintem a doksiban szereplő use-case neveket használjuk! (David)
        addUseCase(UseCases::iAmAUseCase, "demoUseCase");
        addUseCase(UseCases::burstSporeDist1, "Spóraszórás 1 távolságra");
        addUseCase(UseCases::burstSporeDist2, "Spóraszórás 2 távolságra");
        addUseCase(UseCases::burstSporeDist3, "Spóraszórás 3 távolságra");

        addUseCase(UseCases::growMyceliumNoSourceFail, "Gombafonal növesztés gombatest és gombafonal nélkül");
        addUseCase(UseCases::growMyceliumNotNeighbor, "Gombafonal növesztés nem szomszédos tektonok között");
        addUseCase(UseCases::growMyceliumSingleMyceliumFail,
                "Gombafonal növesztés SingleMyceliumTecton-ra, ami már foglalt");
        addUseCase(UseCases::growMyceliumSingleMyceliumSuccess, "Gombafonal növesztés SingleMyceliumTecton-ra");
        addUseCase(UseCases::growMyceliumSuccess, "Gombafonal növesztés optimális körülmények között");

        addUseCase(UseCases::insectMoveSuccess, "InsectMove-Success");
        addUseCase(UseCases::insectMoveParalysed, "InsectMove-Paralysed");
        addUseCase(UseCases::insectMoveNoMycelium, "InsectMove-NoMycelium");
    }

    // Térképek
    // --------------------------------------------------------------------------------

    static void burstSporeMap() {
        printOn = false;
        objNames.clear();

        Tecton t1 = new Tecton();
        objNames.put(t1, "t1");
        Tecton t2 = new Tecton();
        objNames.put(t2, "t2");
        Tecton t3 = new Tecton();
        objNames.put(t3, "t3");
        Tecton t4 = new Tecton();
        objNames.put(t4, "t4");

        t1.addNeighbor(t2);
        t2.addNeighbor(t1);
        t2.addNeighbor(t3);
        t3.addNeighbor(t2);
        t3.addNeighbor(t4);
        t4.addNeighbor(t3);

        Fungus s1 = new Fungus();
        objNames.put(s1, "s1");
        Mushroom m1 = new Mushroom(s1, t1);
        objNames.put(m1, "m1");

        printOn = true;
    }

    // Use case-ek
    // --------------------------------------------------------------------------------

    static void iAmAUseCase() {
        // create map
        Object t = new Tecton();
        objNames.put(t, "t");
        // start sequence
        printCall(t, List.of(1, 2, t, List.of(t, 45)));
        printCall(t, List.of());
        boolean a = ask("Should I return true?");
        printReturn(a);
        printReturn(null);
    }

    // #region BurstSpore

    static void burstSporeDist1() {
        burstSporeMap();
        try {
            Mushroom m1 = (Mushroom) getObjByName("m1");
            Tecton t1 = (Tecton) getObjByName("t1");
            m1.burstSpore(t1);
            // TODO: Kérdés: itt hogy akarjuk? A visszakasztolás elég csúnya, de más ötletem
            // nincs mivel a hashmap mindenképp egy Object-et tárol

            // Vélemény: Ez elég gányolásnak néz ki
            // Szerintem possible solution:
            // Minden Map egy class, és az ahhoz tartozó use-case-ek
            // a classban függények, így elérik a privát változókat, amik a cuccok a mapon.
            // Vagy minden static, és van egy init map, vagy ctor-ban építünk fel mindent.

            // Re:Vélemény: Háát igen, az, de egyrészt a skeletonnak szerintem annyira nem
            // kell szépenk lennie, másrészt pedig a skeletonnak kell tudnia az összes nevet
            // hogy ki tudja szépen printelni őket - tehát egy map-be akkor is be kell majd
            // pakolni mindet. Tehát gyakokorlatilag 100% lenne az overhead ezért a
            // megoldásért. Nyilván én lennék a legboldogabb ha a szépérzékem így
            // kielégülhetne, de eredetileg nem ezt beszéltük meg, ezért nem kezdtem el
            // szépészkedni. Úgyhogy ez további megbeszélés tárgya kéne h legyen (vagy itt a
            // kommentben indítunk egy szavazást :P)
            // Szavazz a szebb jővőért és kódért:
            // Pro: Panni,
            // Contra: Márton, David
            // comment: David: Legyen a köztes megoldás amit a Discord-on írtam
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void burstSporeDist2() {
        burstSporeMap();
        try {
            Mushroom m1 = (Mushroom) getObjByName("m1");
            Tecton t2 = (Tecton) getObjByName("t2");
            m1.burstSpore(t2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void burstSporeDist3() {
        burstSporeMap();
        try {
            Mushroom m1 = (Mushroom) getObjByName("m1");
            Tecton t3 = (Tecton) getObjByName("t3");
            m1.burstSpore(t3);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    // #endregion

    // #region GrowMycelium
    static void growMyceliumNoSourceFail() {

        try {
            printOn = false;
            var t1 = new Tecton();
            addObject(t1, "t1");
            var t2 = new Tecton();
            addObject(t2, "t2");
            var s1 = new Fungus();
            addObject(s1, "s1");
            printOn = true;
            s1.growMycelium(t1, t2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }

    }

    static void growMyceliumNotNeighbor() {
        try {
            printOn = false;
            var t1 = new Tecton();
            addObject(t1, "t1");

            var t2 = new Tecton();
            addObject(t2, "t2");

            var s1 = new Fungus();
            addObject(s1, "s1");

            addObject(new Mushroom(s1, t1), "m1");

            printOn = true;
            s1.growMycelium(t1, t2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void growMyceliumSingleMyceliumFail() {
        try {
            printOn = false;
            var t1 = new Tecton();
            addObject(t1, "t1");

            var t2 = new SingleMyceliumTecton();
            addObject(t2, "t2");

            var t3 = new Tecton();
            addObject(t3, "t3");

            t1.addNeighbor(t2);
            t2.addNeighbor(t1);
            t2.addNeighbor(t3);
            t3.addNeighbor(t2);

            var s1 = new Fungus();
            addObject(s1, "s1");
            var s2 = new Fungus();
            addObject(s2, "s2");

            addObject(new Mushroom(s1, t1), "m1");
            addObject(new Mushroom(s2, t3), "m2");

            addObject(new Mycelium(s2, t3, t2), "my1");
            printOn = true;
            s1.growMycelium(t1, t2);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void growMyceliumSingleMyceliumSuccess() {
        try {
            printOn = false;
            var t1 = new Tecton();
            addObject(t1, "t1");

            var t2 = new SingleMyceliumTecton();
            addObject(t2, "t2");

            var s1 = new Fungus();
            addObject(s1, "s1");

            addObject(new Mushroom(s1, t1), "m1");
            printOn = true;
            s1.growMycelium(t1, t2);
            var my1 = (Mycelium) getObjByName("my1");
            my1.tick(1); // NOSONAR let it be thrown
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void growMyceliumSuccess() {
        try {
            printOn = false;
            var t1 = new Tecton();
            addObject(t1, "t1");

            var t2 = new Tecton();
            addObject(t2, "t2");

            var s1 = new Fungus();
            addObject(s1, "s1");

            addObject(new Mushroom(s1, t1), "m1");
            printOn = true;
            s1.growMycelium(t1, t2);
            var my1 = (Mycelium) getObjByName("my1");
            my1.tick(1); // NOSONAR let it be thrown
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }
    // #endregion

    // #region InsectMove
    static void insectMoveMap() {
        printOn = false;
        objNames.clear();

        Tecton t1 = new Tecton();
        objNames.put(t1, "t1");
        Tecton t2 = new Tecton();
        objNames.put(t2, "t2");
        Tecton t3 = new Tecton();
        objNames.put(t3, "t3");
        t1.addNeighbor(t2);
        t2.addNeighbor(t1);
        t1.addNeighbor(t3);
        t3.addNeighbor(t1);

        Insect i1 = new Insect(t1);
        objNames.put(i1, "i1");
        Insect i2 = new Insect(t1);
        objNames.put(i2, "i2");
        ParalysingEffect p = new ParalysingEffect();
        objNames.put(p, "p");
        i2.addEffect(p);
        p.applyTo(i2);

        Fungus fu1 = new Fungus();
        objNames.put(fu1, "fu1");
        Mycelium my1 = new Mycelium(fu1, t1, t2);
        objNames.put(my1, "my1");
        fu1.addMycelium(my1);

        Mushroom mu1 = new Mushroom(fu1, t1);
        objNames.put(mu1, "mu1");
        fu1.addMushroom(mu1);
        t1.setMushroom(mu1);

        printOn = true;
    }

    static void insectMoveSuccess() {
        insectMoveMap();
        try {
            Insect i1 = (Insect) getObjByName("i1");
            Tecton t2 = (Tecton) getObjByName("t2");
            i1.moveTo(t2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void insectMoveParalysed() {
        insectMoveMap();
        try {
            Insect i2 = (Insect) getObjByName("i2");
            Tecton t2 = (Tecton) getObjByName("t2");
            i2.moveTo(t2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void insectMoveNoMycelium() {
        insectMoveMap();
        try {
            Insect i1 = (Insect) getObjByName("i2");
            Tecton t3 = (Tecton) getObjByName("t3");
            i1.moveTo(t3);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    // #endregion
}
