package model;

/**
 * <h3>Gombafonal rágás tiltó hatás</h3>
 * 
 * Olyan InsectEffect, amely megakadályozza, hogy a rovar tudjon gombafonalat rágni.
 */
public class AntiChewEffect extends InsectEffect {
    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * A paraméterben kapott rovar fonal elrágási képességét megszünteti.
     * 
     * Megnöveli a rovar antiChewCount-ját 1-el (Insect::getAntiChewCount, Insect::setAntiChewCount), beállítja saját
     * rovarát.
     * 
     * @param insect a rovar, ami kapja a hatást
     */
    @Override
    public void applyTo(Insect insect) {
        int antiChewCount = insect.getAntiChewCount();
        insect.setAntiChewCount(antiChewCount + 1);
        this.insect = insect;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     * Visszaadja a rovar a fonal elrágási képességét, azaz csökkenti a rovar antiChewCount-ját 1-el
     * (Insect::getAntiChewCount, Insect::setAntiChewCount).
     */
    @Override
    protected void remove() {
        int antiChewCount = insect.getAntiChewCount();
        insect.setAntiChewCount(antiChewCount - 1);
    }
}