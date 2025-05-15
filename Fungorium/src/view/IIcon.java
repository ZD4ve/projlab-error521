package view;

import java.awt.image.BufferedImage;

/**
 * Ikonnal rendelkező objektum, amely elhelyezhető a Cellákban.
 */
public interface IIcon {
    /**
     * Visszaadja a cellában elhelyezett objektum ikonját.
     * 
     * @return Az objektum ikonja, vagy null, ha a becsomagolt objektum megszűnt.
     */
    BufferedImage getIcon();
}