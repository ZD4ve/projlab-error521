package view;

import java.util.List;

public class View {
    private View() {
    }

    private static Cell selected;
    private static Map map;
    private static VPlayer selectedPlayer;
    private static List<VColony> allColonies;
    private static List<VFungus> allFungi;

    public static void redraw() {
        map.draw(); // TODO @Panni grarphics2d valahonnan
    }

    public static void create(int tecNum, int fungiNum, int colNum) {
        // TODO @Márton @Vazul
        // ref: initialize.puml
    }

    public static void notifyUser() {
        // TODO @Tamás
    }

    public static void click(int x, int y) {// NOSONAR complexity, így olvashatóbb
        Cell clicked = map.cellAt(x, y);
        if (selected == null) {
            selected = clicked;
            return;
        }
        IIcon item1 = selected.getItem();
        IIcon item2 = clicked.getItem();

        if (selectedPlayer instanceof VColony colony && item1 instanceof VInsect insect) {
            if (insect.getInsect().getColony() != colony.getColony())
                return;
            if (item2 == null)
                insect.move(clicked);
            if (item2 instanceof VSpore)
                insect.eat(clicked);
            if (item2 instanceof VMycelium)
                insect.chew(clicked);
        }

        if (selectedPlayer instanceof VFungus fungus) {
            if (item2 != null)
                return;
            if (item1 == null) {
                if (selected == clicked)
                    fungus.growMushroom(clicked);
                if (selected != clicked)
                    fungus.growMycelium(selected, clicked);
            }
            if (item1 instanceof VMushroom mushroom && mushroom.getMushroom().getSpecies() == fungus.getFungus())
                mushroom.burst(clicked);

        }

    }

    public static void endGame() {
        // TODO @Márton @Vazul @Tamás
    }

    public static Cell getSelected() {
        return selected;
    }

    public static void setSelectedPlayer(VPlayer selectedPlayer) {
        View.selectedPlayer = selectedPlayer;
    }

    public static Map getMap() {
        return map;
    }

    public static List<VColony> getAllColonies() {
        return allColonies;
    }

    public static List<VFungus> getAllFungi() {
        return allFungi;
    }
}