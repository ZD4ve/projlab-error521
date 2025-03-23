package helper;

import static helper.Skeleton.*;
import model.*;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class UseCases {
    private UseCases() {
    } // UseCases ne legyen példányosítható

    public static void init() {
        addUseCase(UseCases::absorbMyceliumSuccess, "AbsorbMycelium-Success");

        // BurstSpore
        addUseCase(UseCases::burstSporeDist1, "BurstSpore-Dist1");
        addUseCase(UseCases::burstSporeDist2, "BurstSpore-Dist2");
        addUseCase(UseCases::burstSporeDist3, "BurstSpore-Dist3");

        // GrowMycelium
        addUseCase(UseCases::growMyceliumSuccess, "GrowMycelium-Success");
        addUseCase(UseCases::growMyceliumSingleMyceliumSuccess, "GrowMycelium-SingleSuccess");
        addUseCase(UseCases::growMyceliumSingleMyceliumFail, "GrowMycelium-SingleFail");
        addUseCase(UseCases::growMyceliumNotNeighbor, "GrowMycelium-NotNeighbor");
        addUseCase(UseCases::growMyceliumNoSourceFail, "GrowMycelium-NoSource");

        // InsectChewMycelium
        addUseCase(UseCases::insectChewMyceliumSuccess, "InsectChewMycelium-Success");
        addUseCase(UseCases::insectChewMyceliumParalysed, "InsectChewMycelium-Paralysed");
        addUseCase(UseCases::insectChewMyceliumToothless, "InsectChewMycelium-Toothless");

        // InsectMove
        addUseCase(UseCases::insectMoveSuccess, "InsectMove-Success");
        addUseCase(UseCases::insectMoveParalysed, "InsectMove-Paralysed");
        addUseCase(UseCases::insectMoveNoMycelium, "InsectMove-NoMycelium");

        // MyceliumTearing
        addUseCase(UseCases::myceliumTearingTear, "MyceliumTearing-Tear");

        // EatSpore
        addUseCase(UseCases::eatSporeSuccess, "EatSpore-Success");
        addUseCase(UseCases::eatSporeNoSpore, "EatSpore-NoSpore");
        addUseCase(UseCases::eatSporeParalysed, "EatSpore-Paralysed");

        // GrowMushroom
        addUseCase(UseCases::growMushroomAlreadyOnTarget, "GrowMushroom-AlreadyOnTarget");
        addUseCase(UseCases::growMushroomNoMycelia, "GrowMushroom-NoMycelia");
        addUseCase(UseCases::growMushroomNoMushroom, "GrowMushroom-NoMushroom");
        addUseCase(UseCases::growMushroomNotEnoughSpore, "GrowMushroom-NotEnoughSpore");
        addUseCase(UseCases::growMushroomSuccess, "GrowMushroom-Success");

        // TectonBreak
        addUseCase(UseCases::tectonBreak, "Tecton-Break");
    }

    // #region AbsorbMycelium

    static void absorbMyceliumSuccess() {
        printOn = false;
        objNames.clear();

        var t1 = new Tecton();
        addObject(t1, "t1");
        var t2 = new MyceliumAbsorbingTecton();
        addObject(t2, "t2");

        t1.addNeighbor(t2);
        t2.addNeighbor(t1);

        var s1 = new Fungus();
        addObject(s1, "s1");

        var m1 = new Mushroom(s1, t1);
        addObject(m1, "m1");

        s1.addMushroom(m1);

        var m2 = new Mycelium(s1, t1, t2);
        addObject(m2, "m2");

        printOn = true;

        t2.tick(0.1);

        printTrace();
    }

    // #endregion

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

    /**
     * Gombafonalat felszívó tekton felszívja a rajta található gombafonalat.
     */
    static void burstSporeDist1() {
        burstSporeMap();
        Mushroom m1 = (Mushroom) getObjByName("m1");
        Tecton t2 = (Tecton) getObjByName("t2");
        m1.burstSpore(t2);
        Skeleton.printTrace();
    }

    /**
     * Egy gombatest egy tőle 2 távolságra lévő tektonra kíván spórát szórni.
     */
    static void burstSporeDist2() {
        burstSporeMap();
        Mushroom m1 = (Mushroom) getObjByName("m1");
        Tecton t3 = (Tecton) getObjByName("t3");
        m1.burstSpore(t3);
        Skeleton.printTrace();
    }

    /**
     * Egy gombatest egy tőle 3 távolságra lévő tektonra kíván spórát szórni.
     */
    static void burstSporeDist3() {
        burstSporeMap();
        Mushroom m1 = (Mushroom) getObjByName("m1");
        Tecton t4 = (Tecton) getObjByName("t4");
        m1.burstSpore(t4);
        Skeleton.printTrace();
    }

    // #endregion

    // #region GrowMycelium
    /**
     * Egy normális tektonról egy másikra próbálunk gombafonalat növeszteni, de a
     * forrás nem tartalmaz alkalmas gombatestet vagy gombafonalat (konkrétan
     * semmilyet).
     */

    static void growMyceliumNoSourceFail() {
        printOn = false;
        objNames.clear();
        var t1 = new Tecton();
        addObject(t1, "t1");
        var t2 = new Tecton();
        addObject(t2, "t2");
        var s1 = new Fungus();
        addObject(s1, "s1");
        printOn = true;
        s1.growMycelium(t1, t2);
        Skeleton.printTrace();
    }

    /**
     * Egy normális tektonról egy másikra próbálunk gombafonalat növeszteni, de az
     * nem szomszédos a forrással.
     */
    static void growMyceliumNotNeighbor() {
        printOn = false;
        objNames.clear();
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
        Skeleton.printTrace();
    }

    /**
     * Egy normális tektonról egy SingleMyceliumTecton-ra próbálunk gombafonalat
     * növeszteni, de azon egy másik fajnak már van fonala.
     */
    static void growMyceliumSingleMyceliumFail() {
        printOn = false;
        objNames.clear();
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
        Skeleton.printTrace();
    }

    /**
     * Egy normális tektonról egy SingleMyceliumTecton-ra növesztünk gombafonalat,
     * optimális körülmények között.
     */
    static void growMyceliumSingleMyceliumSuccess() {
        printOn = false;
        objNames.clear();
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
        my1.tick(1);
        Skeleton.printTrace();
    }

    /**
     * Egy normális tektonról egy másikra növesztünk gombafonalat, optimális
     * körülmények között.
     */
    static void growMyceliumSuccess() {
        printOn = false;
        objNames.clear();
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
        var my1 = (Mycelium) getObjByName("myc");
        my1.tick(1);

        Skeleton.printTrace();
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

    /**
     * A rovar átmozdul egy gombafonal mentén.
     */
    static void insectMoveSuccess() {
        insectMoveMap();
        Insect i1 = (Insect) getObjByName("i1");
        Tecton t2 = (Tecton) getObjByName("t2");
        i1.moveTo(t2);

        Skeleton.printTrace();
    }

    /**
     * Mozgás sikertelen, mivel a rovar béna.
     */
    static void insectMoveParalysed() {
        insectMoveMap();
        Insect i2 = (Insect) getObjByName("i2");
        Tecton t2 = (Tecton) getObjByName("t2");
        i2.moveTo(t2);

        Skeleton.printTrace();
    }

    /**
     * A rovar megpróbál atmozdulni egy tektonra, de mivel nincs fonál oda, így ez
     * nem történik meg.
     */
    static void insectMoveNoMycelium() {
        insectMoveMap();
        Insect i1 = (Insect) getObjByName("i2");
        Tecton t3 = (Tecton) getObjByName("t3");
        i1.moveTo(t3);

        Skeleton.printTrace();
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

    /**
     * A rovar sikeresen elrág egy fonalat.
     */
    static void insectChewMyceliumSuccess() {
        insectChewMyceliumMap();
        Insect i1 = (Insect) getObjByName("i1");
        Mycelium my1 = (Mycelium) getObjByName("my1");
        i1.chewMycelium(my1);

        Skeleton.printTrace();
    }

    /**
     * Elrágás sikertelen, mivel a rovar béna.
     */
    static void insectChewMyceliumParalysed() {
        insectChewMyceliumMap();
        Insect i2 = (Insect) getObjByName("i2");
        Mycelium my1 = (Mycelium) getObjByName("my1");
        i2.chewMycelium(my1);

        Skeleton.printTrace();
    }

    /**
     * Elrágás sikertelen, mivel a rovar rágásképtelen.
     */
    static void insectChewMyceliumToothless() {
        insectChewMyceliumMap();
        Insect i3 = (Insect) getObjByName("i3");
        Mycelium my1 = (Mycelium) getObjByName("my1");
        i3.chewMycelium(my1);

        Skeleton.printTrace();
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

    /**
     * Elvágódik egy gombafonál a gombatestjétől, így megszűnik.
     */
    static void myceliumTearingTear() {
        myceliumTearingMap();
        Mycelium my1 = (Mycelium) getObjByName("my1");
        my1.die();

        Skeleton.printTrace();
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

    /**
     * Rovar spóra evést követően rágást képtelen lesz.
     */
    static void eatSporeParalysed() {
        eatSporeMapParalysed();
        Insect i1 = (Insect) getObjByName("i1");
        i1.eatSpore();

        Skeleton.printTrace();
    }

    /**
     * Rovar megpróbál spórát enni, de a tektonon nincs spóra.
     */
    static void eatSporeNoSpore() {
        eatSporeMapNoSpore();
        Insect i1 = (Insect) getObjByName("i1");
        i1.eatSpore();

        Skeleton.printTrace();
    }

    /**
     * Rovar sikeresen elfogyaszt egy spórát.
     */
    static void eatSporeSuccess() {
        eatSporeMapSuccess();
        Insect i1 = (Insect) getObjByName("i1");
        i1.eatSpore();
        if (ask("A rovarnak lejárt a várakozási ideje?")) {
            InsectEffect eff = (InsectEffect) getObjByName("eff");

            eff.tick(1);
        }

        Skeleton.printTrace();
    }

    // #endregion

    // #region Tecton-break

    static void tectonBreakMap() {
        printOn = false;
        objNames.clear();

        Tecton tecton = new Tecton();
        objNames.put(tecton, "tecton");

        Tecton neighbor1 = new Tecton();
        objNames.put(neighbor1, "neighbor1");

        Tecton neighbor2 = new Tecton();
        objNames.put(neighbor2, "neighbor2");

        tecton.addNeighbor(neighbor1);
        tecton.addNeighbor(neighbor2);
        neighbor1.addNeighbor(tecton);
        neighbor2.addNeighbor(tecton);

        Insect i1 = new Insect(tecton);
        objNames.put(i1, "i1");

        Insect i2 = new Insect(tecton);
        objNames.put(i2, "i2");

        Fungus s1 = new Fungus();
        objNames.put(s1, "s1");

        Mycelium mycelia1 = new Mycelium(s1, tecton, neighbor1);
        objNames.put(mycelia1, "mycelia1");

        Mycelium mycelia2 = new Mycelium(s1, tecton, neighbor2);
        objNames.put(mycelia2, "mycelia2");

        s1.addMycelium(mycelia1);
        s1.addMycelium(mycelia2);

        Mushroom m1 = new Mushroom(s1, tecton);
        objNames.put(m1, "m1");

        s1.addMushroom(m1);

        tecton.setMushroom(m1); // TODO: KOMM DIAGRAM

        Spore s2 = new Spore(s1);
        objNames.put(s2, "s2");

        tecton.addSpore(s2);

        printOn = true;
    }

    static void tectonBreak() {
        tectonBreakMap();
        Tecton tecton = (Tecton) getObjByName("tecton");
        tecton.tectonBreak();
        Skeleton.printTrace();
    }

    // #endregion

    // #region Grow-mushroom

    static void mapAlreadyOnTarget() {
        printOn = false;
        objNames.clear();

        Tecton target = new Tecton();
        objNames.put(target, "target");

        Tecton neighbor1 = new Tecton();
        objNames.put(neighbor1, "neighbor1");

        Tecton neighbor2 = new Tecton();
        objNames.put(neighbor2, "neighbor2");

        target.addNeighbor(neighbor2);
        target.addNeighbor(neighbor1);
        neighbor1.addNeighbor(target);
        neighbor1.addNeighbor(neighbor2);
        neighbor2.addNeighbor(target);
        neighbor2.addNeighbor(neighbor1);

        Fungus fu1 = new Fungus();
        objNames.put(fu1, "fu1");

        Fungus fu2 = new Fungus();
        objNames.put(fu2, "fu2");

        Mycelium my1 = new Mycelium(fu1, target, neighbor1);
        objNames.put(my1, "my1");

        Mycelium my2 = new Mycelium(fu2, target, neighbor2);
        objNames.put(my2, "my2");

        fu2.addMycelium(my1);
        fu1.addMycelium(my2);

        Spore sp1 = new Spore(fu1);
        objNames.put(sp1, "sp1");

        Spore sp2 = new Spore(fu1);
        objNames.put(sp2, "sp2");

        Spore sp3 = new Spore(fu1);
        objNames.put(sp3, "sp3");

        target.addSpore(sp1);
        target.addSpore(sp2);
        target.addSpore(sp3);

        Mushroom mu1 = new Mushroom(fu1, target);
        objNames.put(mu1, "mu1");

        fu1.addMushroom(mu1);

        target.setMushroom(mu1);

        printOn = true;
    }

    static void growMushroomAlreadyOnTarget() {
        mapAlreadyOnTarget();
        Fungus fu1 = (Fungus) getObjByName("fu1");
        Tecton target = (Tecton) getObjByName("target");
        fu1.growMushroom(target);

        Skeleton.printTrace();
    }

    static void mapNoMushroom() {
        printOn = false;
        objNames.clear();

        Tecton target = new Tecton();
        objNames.put(target, "target");

        Tecton neighbor1 = new Tecton();
        objNames.put(neighbor1, "neighbor1");

        Tecton neighbor2 = new Tecton();
        objNames.put(neighbor2, "neighbor2");

        target.addNeighbor(neighbor2);
        target.addNeighbor(neighbor1);
        neighbor1.addNeighbor(target);
        neighbor1.addNeighbor(neighbor2);
        neighbor2.addNeighbor(target);
        neighbor2.addNeighbor(neighbor1);

        Fungus fu1 = new Fungus();
        objNames.put(fu1, "fu1");

        Fungus fu2 = new Fungus();
        objNames.put(fu2, "fu2");

        Mycelium my1 = new Mycelium(fu1, target, neighbor1);
        objNames.put(my1, "my1");

        Mycelium my2 = new Mycelium(fu2, target, neighbor2);
        objNames.put(my2, "my2");

        fu2.addMycelium(my1);
        fu1.addMycelium(my2);

        Spore sp1 = new Spore(fu1);
        objNames.put(sp1, "sp1");

        Spore sp2 = new Spore(fu1);
        objNames.put(sp2, "sp2");

        Spore sp3 = new Spore(fu1);
        objNames.put(sp3, "sp3");

        target.addSpore(sp1);
        target.addSpore(sp2);
        target.addSpore(sp3);

        Mushroom mu1 = new Mushroom(fu1, neighbor1);
        objNames.put(mu1, "mu1");

        fu1.addMushroom(mu1);

        neighbor1.setMushroom(mu1);
        printOn = true;
    }

    static void growMushroomNoMushroom() {
        mapNoMushroom();
        Fungus fu1 = (Fungus) getObjByName("fu1");
        Tecton target = (Tecton) getObjByName("target");
        fu1.growMushroom(target);

        Skeleton.printTrace();
    }

    static void mapNoMycelia() {
        printOn = false;
        objNames.clear();

        Tecton target = new Tecton();
        objNames.put(target, "target");

        Tecton neighbor1 = new Tecton();
        objNames.put(neighbor1, "neighbor1");

        Tecton neighbor2 = new Tecton();
        objNames.put(neighbor2, "neighbor2");

        target.addNeighbor(neighbor2);
        target.addNeighbor(neighbor1);
        neighbor1.addNeighbor(target);
        neighbor1.addNeighbor(neighbor2);
        neighbor2.addNeighbor(target);
        neighbor2.addNeighbor(neighbor1);

        Fungus fu1 = new Fungus();
        objNames.put(fu1, "fu1");

        Fungus fu2 = new Fungus();
        objNames.put(fu2, "fu2");

        Mycelium my1 = new Mycelium(fu1, target, neighbor1);
        objNames.put(my1, "my1");

        Mycelium my2 = new Mycelium(fu2, target, neighbor2);
        objNames.put(my2, "my2");

        fu2.addMycelium(my1);
        fu2.addMycelium(my2);

        Spore sp1 = new Spore(fu1);
        objNames.put(sp1, "sp1");

        Spore sp2 = new Spore(fu1);
        objNames.put(sp2, "sp2");

        Spore sp3 = new Spore(fu1);
        objNames.put(sp3, "sp3");

        target.addSpore(sp1);
        target.addSpore(sp2);
        target.addSpore(sp3);

        Mushroom mu1 = new Mushroom(fu1, neighbor1);
        objNames.put(mu1, "mu1");

        fu1.addMushroom(mu1);

        neighbor1.setMushroom(mu1);

        printOn = true;
    }

    static void growMushroomNoMycelia() {
        mapNoMycelia();
        Fungus fu1 = (Fungus) getObjByName("fu1");
        Tecton target = (Tecton) getObjByName("target");
        fu1.growMushroom(target);

        Skeleton.printTrace();
    }

    static void mapNotEnoughSpore() {
        printOn = false;
        objNames.clear();

        Tecton target = new Tecton();
        objNames.put(target, "target");

        Tecton neighbor1 = new Tecton();
        objNames.put(neighbor1, "neighbor1");

        Tecton neighbor2 = new Tecton();
        objNames.put(neighbor2, "neighbor2");

        target.addNeighbor(neighbor2);
        target.addNeighbor(neighbor1);
        neighbor1.addNeighbor(target);
        neighbor1.addNeighbor(neighbor2);
        neighbor2.addNeighbor(target);
        neighbor2.addNeighbor(neighbor1);

        Fungus fu1 = new Fungus();
        objNames.put(fu1, "fu1");

        Fungus fu2 = new Fungus();
        objNames.put(fu2, "fu2");

        Spore sp1 = new Spore(fu1);
        objNames.put(sp1, "sp1");

        Spore sp2 = new Spore(fu1);
        objNames.put(sp2, "sp2");

        Spore sp3 = new Spore(fu2);
        objNames.put(sp3, "sp3");

        target.addSpore(sp1);
        target.addSpore(sp2);
        target.addSpore(sp3);

        Mushroom mu1 = new Mushroom(fu1, neighbor1);
        objNames.put(mu1, "mu1");

        fu1.addMushroom(mu1);

        neighbor1.setMushroom(mu1);

        printOn = true;
    }

    static void growMushroomNotEnoughSpore() {
        mapNotEnoughSpore();
        Fungus fu1 = (Fungus) getObjByName("fu1");
        Tecton target = (Tecton) getObjByName("target");
        fu1.growMushroom(target);

        Skeleton.printTrace();
    }

    static void mapSuccess() {
        printOn = false;
        objNames.clear();

        Tecton target = new Tecton();
        objNames.put(target, "target");

        Tecton neighbor1 = new Tecton();
        objNames.put(neighbor1, "neighbor1");

        Tecton neighbor2 = new Tecton();
        objNames.put(neighbor2, "neighbor2");

        target.addNeighbor(neighbor2);
        target.addNeighbor(neighbor1);
        neighbor1.addNeighbor(target);
        neighbor1.addNeighbor(neighbor2);
        neighbor2.addNeighbor(target);
        neighbor2.addNeighbor(neighbor1);

        Fungus fu1 = new Fungus();
        objNames.put(fu1, "fu1");

        Fungus fu2 = new Fungus();
        objNames.put(fu2, "fu2");

        Mycelium my1 = new Mycelium(fu1, target, neighbor1);
        objNames.put(my1, "my1");

        Mycelium my2 = new Mycelium(fu2, target, neighbor2);
        objNames.put(my2, "my2");

        fu2.addMycelium(my1);
        fu1.addMycelium(my2);

        Spore sp1 = new Spore(fu1);
        objNames.put(sp1, "sp1");

        Spore sp2 = new Spore(fu1);
        objNames.put(sp2, "sp2");

        Spore sp3 = new Spore(fu1);
        objNames.put(sp3, "sp3");

        target.addSpore(sp1);
        target.addSpore(sp2);
        target.addSpore(sp3);

        Mushroom mu1 = new Mushroom(fu1, neighbor1);
        objNames.put(mu1, "mu1");

        fu1.addMushroom(mu1);

        neighbor1.setMushroom(mu1);

        printOn = true;
    }

    static void growMushroomSuccess() {
        mapSuccess();
        Fungus fu1 = (Fungus) getObjByName("fu1");
        Tecton target = (Tecton) getObjByName("target");
        fu1.growMushroom(target);

        Skeleton.printTrace();
    }

    // #endregion

}
