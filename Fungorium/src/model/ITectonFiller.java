package model;

/**
 * <h3>ITectonFiller</h3>
 * 
 * Interfész, amely a Tektonok törésének kezelésére szolgál. Összehangolja a VTecton
 * és a Tecton osztályokat, lehetővé téve a tektonok törését és az új tektonok feltöltését.
 */
public interface ITectonFiller {
    /**
     * Tecton törését végzi el.
     * 
     * @param dying a törésre kerülő tekton
     * @param t1 az egyik új tekton
     * @param t2 a másik új tekton
     */
    public void breaking(Tecton dying, Tecton t1, Tecton t2);

    /**
     * Megmondja, hogy a tekton törhető-e.
     */
    public boolean canBreak();
}
