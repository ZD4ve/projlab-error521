package controller;

/** Egyszerű 2 paraméteres (Object, boolean) delegált. */
public interface ObjectEventHandler {
    /** Esemény bekövetkezésekor hívandó. */
    public void handle(Object o, boolean added);
}
