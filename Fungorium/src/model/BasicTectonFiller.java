package model;

import java.util.ArrayList;

/**
 * <h3>BasicTectonFiller</h3>
 * 
 * Egy alapértelmezett implementációja az ITectonFiller interfésznek.
 */
public class BasicTectonFiller implements ITectonFiller {
    /**
     * {@inheritDoc}
     * Levezényli a tekton törési folyamatát: A tektont eltávolítja az aktív objektumok közül. A tektonhoz kapcsolódó
     * összes gombafonalat megszünteti (Mycelium::die). A tekton összes szomszédjainak listájából eltávolítja a tektont
     * (Tecton::removeNeighbor). Létrehoz 2 új tektont az eredeti tekton hatásával (Tecton::newMe). Elosztja
     * (Tecton::fillWithStuff) az új tektonok között a rajta található objektumokat, lehetőleg egyenletesen, de az első
     * tektont előnyben részesítve. (első: [0;min(darab, max(darab / 2, 1))[, második: maradék) A tektonon található
     * gombatestet a második tekton kapja. A két új tekton szomszédos lesz.
     */
    @Override
    public void breaking(Tecton dying, Tecton t1, Tecton t2) {
        var spores = dying.getSpores();
        var insects = dying.getInsects();
        var neighbors = dying.getNeighbors();
        var mushroom = dying.getMushroom();

        // weird solution to handle the case when there is only 1 element
        int sporeMid = Math.min(spores.size(), Math.max(spores.size() / 2, 1)); // NOSONAR this would throw an exception
                                                                                // if it was a clamp
        int insectMid = Math.min(insects.size(), Math.max(insects.size() / 2, 1)); // NOSONAR this would throw an
                                                                                   // exception if it was a clamp
        int neighborMid = Math.min(neighbors.size(), Math.max(neighbors.size() / 2, 1)); // NOSONAR this would throw an

        var t1Neighbors = new ArrayList<>(neighbors.subList(0, neighborMid));
        t1Neighbors.add(t2);
        t1.fillWithStuff(spores.subList(0, sporeMid), null, insects.subList(0, insectMid), t1Neighbors, new BasicTectonFiller());

        var t2Neighbors = new ArrayList<>(neighbors.subList(neighborMid, neighbors.size()));
        t2Neighbors.add(t1);
        t2.fillWithStuff(spores.subList(sporeMid, spores.size()), mushroom, insects.subList(insectMid, insects.size() ),
                t2Neighbors, new BasicTectonFiller());
    }

    /**
     * {@inheritDoc }
     * @return mindig igaz, mivel jelen esetben a tektonok mindig törhetők.
     */
    @Override
    public boolean canBreak() {
        return true;
    }
}
