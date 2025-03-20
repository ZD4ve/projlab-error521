package helper;

import java.util.List;

import model.Fungus;
import model.Mushroom;
import model.Mycelium;
import model.SingleMyceliumTecton;
import model.Tecton;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class UseCases {
    private UseCases() {
    } // Skeleton ne legyen példányosítható

    // Térképek
    // --------------------------------------------------------------------------------

    static void burstSporeMap() {
        Skeleton.printOn = false;
        Skeleton.objNames.clear();

        Tecton t1 = new Tecton();
        Skeleton.objNames.put(t1, "t1");
        Tecton t2 = new Tecton();
        Skeleton.objNames.put(t2, "t2");
        Tecton t3 = new Tecton();
        Skeleton.objNames.put(t3, "t3");
        Tecton t4 = new Tecton();
        Skeleton.objNames.put(t4, "t4");

        t1.addNeighbor(t2);
        t2.addNeighbor(t1);
        t2.addNeighbor(t3);
        t3.addNeighbor(t2);
        t3.addNeighbor(t4);
        t4.addNeighbor(t3);

        Fungus s1 = new Fungus();
        Skeleton.objNames.put(s1, "s1");
        Mushroom m1 = new Mushroom(s1, t1);
        Skeleton.objNames.put(m1, "m1");

        Skeleton.printOn = true;
    }

    // Use case-ek
    // --------------------------------------------------------------------------------

    static void iAmAUseCase() {
        // create map
        Object t = new Tecton();
        Skeleton.objNames.put(t, "t");
        // start sequence
        Skeleton.printCall(t, List.of(1, 2, t, List.of(t, 45)));
        Skeleton.printCall(t, List.of());
        boolean a = Skeleton.ask("Should I return true?");
        Skeleton.printReturn(a);
        Skeleton.printReturn(null);
    }

    // #region BurstSpore

    static void burstSporeDist1() {
        burstSporeMap();
        try {
            Mushroom m1 = (Mushroom) Skeleton.getObjByName("m1");
            Tecton t1 = (Tecton) Skeleton.getObjByName("t1");
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
            Mushroom m1 = (Mushroom) Skeleton.getObjByName("m1");
            Tecton t2 = (Tecton) Skeleton.getObjByName("t2");
            m1.burstSpore(t2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void burstSporeDist3() {
        burstSporeMap();
        try {
            Mushroom m1 = (Mushroom) Skeleton.getObjByName("m1");
            Tecton t3 = (Tecton) Skeleton.getObjByName("t3");
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
            Skeleton.printOn = false;
            var t1 = new Tecton();
            Skeleton.addObject(t1, "t1");
            var t2 = new Tecton();
            Skeleton.addObject(t2, "t2");
            var s1 = new Fungus();
            Skeleton.addObject(s1, "s1");
            Skeleton.printOn = true;
            s1.growMycelium(t1, t2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }

    }

    static void growMyceliumNotNeighbor() {
        try {
            Skeleton.printOn = false;
            var t1 = new Tecton();
            Skeleton.addObject(t1, "t1");

            var t2 = new Tecton();
            Skeleton.addObject(t2, "t2");

            var s1 = new Fungus();
            Skeleton.addObject(s1, "s1");

            var m1 = new Mushroom(s1, t1);
            Skeleton.addObject(m1, "m1");
            
            Skeleton.printOn = true;
            s1.growMycelium(t1, t2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void growMyceliumSingleMyceliumFail() {
        try {
            Skeleton.printOn = false;
            var t1 = new Tecton();
            Skeleton.addObject(t1, "t1");

            var t2 = new SingleMyceliumTecton();
            Skeleton.addObject(t2, "t2");

            var t3 = new Tecton();
            Skeleton.addObject(t3, "t3");

            t1.addNeighbor(t2);
            t2.addNeighbor(t1);
            t2.addNeighbor(t3);
            t3.addNeighbor(t2);

            var s1 = new Fungus();
            Skeleton.addObject(s1, "s1");
            var s2 = new Fungus();
            Skeleton.addObject(s2, "s2");

            Skeleton.addObject(new Mushroom(s1, t1), "m1");
            Skeleton.addObject(new Mushroom(s2, t3), "m2");

            Skeleton.addObject(new Mycelium(s2, t3, t2), "my1");
            Skeleton.printOn = true;
            s1.growMycelium(t1, t2);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void growMyceliumSingleMyceliumSuccess() {
        try {
            Skeleton.printOn = false;
            var t1 = new Tecton();
            Skeleton.addObject(t1, "t1");

            var t2 = new SingleMyceliumTecton();
            Skeleton.addObject(t2, "t2");

            var s1 = new Fungus();
            Skeleton.addObject(s1, "s1");

            Skeleton.addObject(new Mushroom(s1, t1), "m1");
            Skeleton.printOn = true;
            s1.growMycelium(t1, t2);
            var my1 = (Mycelium) Skeleton.getObjByName("my1");
            my1.tick(1); // NOSONAR let it be thrown
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }

    static void growMyceliumSuccess() {
        try {
            Skeleton.printOn = false;
            var t1 = new Tecton();
            Skeleton.addObject(t1, "t1");

            var t2 = new Tecton();
            Skeleton.addObject(t2, "t2");

            var s1 = new Fungus();
            Skeleton.addObject(s1, "s1");

            Skeleton.addObject(new Mushroom(s1, t1), "m1");
            Skeleton.printOn = true;
            s1.growMycelium(t1, t2);
            var my1 = (Mycelium) Skeleton.getObjByName("my1");
            my1.tick(1); // NOSONAR let it be thrown
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hibásan lett beállítva a teszt!");
        }
    }
    // #endregion
}
