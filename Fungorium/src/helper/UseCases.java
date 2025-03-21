package helper;

import static helper.Skeleton.*;

import java.util.List;

import model.*;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class UseCases {
    private UseCases() {
    } // UseCases ne legyen példányosítható

    public static void init() {
        // TODO: Doksiban szereplő sorrendben legyenek!
        // (@Tamás ezt majd te csináld meg a végén)

        // TODO: Itt szerintem a doksiban szereplő use-case neveket használjuk! (David)
        // BurstSpore
        addUseCase(UseCases::burstSporeDist1, "Spóraszórás 1 távolságra");
        addUseCase(UseCases::burstSporeDist2, "Spóraszórás 2 távolságra");
        addUseCase(UseCases::burstSporeDist3, "Spóraszórás 3 távolságra");

        // GrowMycelium
        addUseCase(UseCases::growMyceliumNoSourceFail, "Gombafonal növesztés gombatest és gombafonal nélkül");
        addUseCase(UseCases::growMyceliumNotNeighbor, "Gombafonal növesztés nem szomszédos tektonok között");
        addUseCase(UseCases::growMyceliumSingleMyceliumFail, "Gombafonal növesztés SingleMyceliumTecton-ra, ami már foglalt");
        addUseCase(UseCases::growMyceliumSingleMyceliumSuccess, "Gombafonal növesztés SingleMyceliumTecton-ra");
        addUseCase(UseCases::growMyceliumSuccess, "Gombafonal növesztés optimális körülmények között");

        // InsectMove
        addUseCase(UseCases::insectMoveSuccess, "InsectMove-Success");
        addUseCase(UseCases::insectMoveParalysed, "InsectMove-Paralysed");
        addUseCase(UseCases::insectMoveNoMycelium, "InsectMove-NoMycelium");

        // InsectChewMycelium
        addUseCase(UseCases::insectChewMyceliumSuccess, "InsectChewMycelium-Success");
        addUseCase(UseCases::insectChewMyceliumParalysed, "InsectChewMycelium-Paralysed");
        addUseCase(UseCases::insectChewMyceliumToothless, "InsectChewMycelium-Toothless");

        // MyceliumTearing
        addUseCase(UseCases::myceliumTearingTear, "MyceliumTearing-Tear");

        // EatSpore
        addUseCase(UseCases::eatSporeSuccess, "EatSpore-Success");
        addUseCase(UseCases::eatSporeNoSpore, "EatSpore-NoSpore");
        addUseCase(UseCases::eatSporeParalysed, "EatSpore-Paralysed");

    }

    // #region BurstSpore

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

    static void burstSporeDist1() {
        burstSporeMap();
        Mushroom m1 = (Mushroom) getObjByName("m1");
        Tecton t1 = (Tecton) getObjByName("t1");
        m1.burstSpore(t1);
    }

    static void burstSporeDist2() {
        burstSporeMap();
        Mushroom m1 = (Mushroom) getObjByName("m1");
        Tecton t2 = (Tecton) getObjByName("t2");
        m1.burstSpore(t2);
    }

    static void burstSporeDist3() {
        burstSporeMap();
        Mushroom m1 = (Mushroom) getObjByName("m1");
        Tecton t3 = (Tecton) getObjByName("t3");
        m1.burstSpore(t3);
    }

    // #endregion

    // #region GrowMycelium
    static void growMyceliumNoSourceFail() {
        printOn = false;
        var t1 = new Tecton();
        addObject(t1, "t1");
        var t2 = new Tecton();
        addObject(t2, "t2");
        var s1 = new Fungus();
        addObject(s1, "s1");
        printOn = true;
        s1.growMycelium(t1, t2);
    }

    static void growMyceliumNotNeighbor() {
        printOn = false;
        var t1 = new Tecton();
        addObject(t1, "t1");

        var t2 = new Tecton();
        addObject(t2, "t2");

        var s1 = new Fungus();
        addObject(s1, "s1");

        var m1 = new Mushroom(s1, t1);
        Skeleton.addObject(m1, "m1");
        s1.addMushroom(m1);

        Skeleton.printOn = true;
        s1.growMycelium(t1, t2);
    }

    static void growMyceliumSingleMyceliumFail() {
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

        var m1 = new Mushroom(s1, t1);
        addObject(m1, "m1");
        s1.addMushroom(m1);
        var m2 = new Mushroom(s2, t3);
        addObject(m2, "m2");
        s2.addMushroom(m2);

        addObject(new Mycelium(s2, t3, t2), "my1");
        printOn = true;
        s1.growMycelium(t1, t2);
    }

    static void growMyceliumSingleMyceliumSuccess() {
        printOn = false;
        var t1 = new Tecton();
        addObject(t1, "t1");

        var t2 = new SingleMyceliumTecton();
        addObject(t2, "t2");

        t1.addNeighbor(t2);
        t2.addNeighbor(t1);

        var s1 = new Fungus();
        addObject(s1, "s1");

        var m1 = new Mushroom(s1, t1);
        addObject(m1, "m1");
        s1.addMushroom(m1);

        printOn = true;
        s1.growMycelium(t1, t2);
        var my1 = (Mycelium) getObjByName("my1");
        my1.tick(1); // NOSONAR let it be thrown
    }

    static void growMyceliumSuccess() {
        printOn = false;
        var t1 = new Tecton();
        addObject(t1, "t1");

        var t2 = new Tecton();
        addObject(t2, "t2");

        t1.addNeighbor(t2);
        t2.addNeighbor(t1);

        var s1 = new Fungus();
        addObject(s1, "s1");

        var m1 = new Mushroom(s1, t1);
        addObject(m1, "m1");
        s1.addMushroom(m1);
        printOn = true;
        s1.growMycelium(t1, t2);
        var my1 = (Mycelium) getObjByName("my1");
        my1.tick(1); // NOSONAR let it be thrown
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
        Insect i1 = (Insect) getObjByName("i1");
        Tecton t2 = (Tecton) getObjByName("t2");
        i1.moveTo(t2);
    }

    static void insectMoveParalysed() {
        insectMoveMap();
        Insect i2 = (Insect) getObjByName("i2");
        Tecton t2 = (Tecton) getObjByName("t2");
        i2.moveTo(t2);
    }

    static void insectMoveNoMycelium() {
        insectMoveMap();
        Insect i1 = (Insect) getObjByName("i2");
        Tecton t3 = (Tecton) getObjByName("t3");
        i1.moveTo(t3);
    }

    // #endregion

    // #region InsectChewMycelium
    static void insectChewMyceliumMap() {
        printOn = false;
        objNames.clear();

        Tecton t1 = new Tecton();
        objNames.put(t1, "t1");
        Tecton t2 = new Tecton();
        objNames.put(t2, "t2");
        t1.addNeighbor(t2);
        t2.addNeighbor(t1);

        Insect i1 = new Insect(t1);
        objNames.put(i1, "i1");
        Insect i2 = new Insect(t1);
        objNames.put(i2, "i2");
        Insect i3 = new Insect(t1);
        objNames.put(i3, "i3");

        ParalysingEffect p = new ParalysingEffect();
        objNames.put(p, "p");
        i2.addEffect(p);
        p.applyTo(i2);

        AntiChewEffect c = new AntiChewEffect();
        objNames.put(c, "c");
        i3.addEffect(c);
        c.applyTo(i3);

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

    static void insectChewMyceliumSuccess() {
        insectChewMyceliumMap();
        Insect i1 = (Insect) getObjByName("i1");
        Mycelium my1 = (Mycelium) getObjByName("my1");
        i1.chewMycelium(my1);

    }

    static void insectChewMyceliumParalysed() {
        insectChewMyceliumMap();
        Insect i2 = (Insect) getObjByName("i2");
        Mycelium my1 = (Mycelium) getObjByName("my1");
        i2.chewMycelium(my1);
    }

    static void insectChewMyceliumToothless() {
        insectChewMyceliumMap();
        Insect i3 = (Insect) getObjByName("i3");
        Mycelium my1 = (Mycelium) getObjByName("my1");
        i3.chewMycelium(my1);
    }
    // #endregion

    // #region MyceliumTearing
    static void myceliumTearingMap() {
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
        t2.addNeighbor(t3);
        t3.addNeighbor(t2);

        Fungus fu1 = new Fungus();
        objNames.put(fu1, "fu1");

        Mushroom mu1 = new Mushroom(fu1, t1);
        objNames.put(mu1, "mu1");
        fu1.addMushroom(mu1);
        t1.setMushroom(mu1);

        Mycelium my1 = new Mycelium(fu1, t1, t2);
        objNames.put(my1, "my1");
        fu1.addMycelium(my1);

        Mycelium my2 = new Mycelium(fu1, t2, t3);
        objNames.put(my2, "my2");
        fu1.addMycelium(my2);

        printOn = true;
    }

    static void myceliumTearingTear() {
        myceliumTearingMap();
        Mycelium my1 = (Mycelium) getObjByName("my1");
        my1.die();
    }
    // #endregion

    // #region EatSpore

    static void eatSporeMapParalysed() {
        printOn = false;
        objNames.clear();

        Tecton t1 = new Tecton();
        objNames.put(t1, "t1");

        Insect i1 = new Insect(t1);
        objNames.put(i1, "i1");

        Fungus fu1 = new Fungus();
        objNames.put(fu1, "fu1");

        ParalysingEffect p1 = new ParalysingEffect();
        objNames.put(p1, "p1");

        i1.addEffect(p1);
        p1.applyTo(i1);

        printOn = true;
    }

    static void eatSporeMapNoSpore() {
        printOn = false;
        objNames.clear();

        Tecton t1 = new Tecton();
        objNames.put(t1, "t1");

        Insect i1 = new Insect(t1);
        objNames.put(i1, "i1");

        Fungus fu1 = new Fungus();
        objNames.put(fu1, "fu1");

        printOn = true;
    }

    static void eatSporeMapSuccess() {
        printOn = false;
        objNames.clear();

        Fungus fu1 = new Fungus();
        objNames.put(fu1, "fu1");

        Tecton t1 = new Tecton();
        objNames.put(t1, "t1");

        Spore sp1 = new Spore(fu1);
        objNames.put(sp1, "sp1");

        t1.addSpore(sp1);

        Insect i1 = new Insect(t1);
        objNames.put(i1, "i1");

        printOn = true;
    }

    static void eatSporeParalysed() {
        eatSporeMapParalysed();
        Insect i1 = (Insect) getObjByName("i1");
        i1.eatSpore();
    }

    static void eatSporeNoSpore() {
        eatSporeMapNoSpore();
        Insect i1 = (Insect) getObjByName("i1");
        i1.eatSpore();
    }

    static void eatSporeSuccess() {
        eatSporeMapSuccess();
        Insect i1 = (Insect) getObjByName("i1");
        i1.eatSpore();
        if (ask("A rovarnak lejárt a várakozási ideje?")) {
            InsectEffect eff = (InsectEffect) getObjByName("eff");

            eff.tick(1);
        }
    }

}
