package gui;

import javax.swing.*;

/**
 * A grafikus alkalmazás főosztálya.
 */
public class Main {

    /**
     * Fő metódus, amely megnyitja a grafikus alkalmazás kezdőablakát.
     */
    public static void main(String[] args) {
        JFrame mf = new StartFrame();
        mf.pack();
    }
}