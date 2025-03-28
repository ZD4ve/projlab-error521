package proto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import model.Colony;
import model.Fungus;
import model.Insect;
import model.Mushroom;
import model.Mycelium;
import model.MyceliumAbsorbingTecton;
import model.MyceliumKeepingTecton;
import model.NoMushroomTecton;
import model.SingleMyceliumTecton;
import model.Spore;
import model.Tecton;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class Proto {
    static HashMap<String, Object> gameObjects = new HashMap<>();
    static Map<Class, String> names = Map.of(Fungus.class, "fu", Tecton.class, "te", Mushroom.class, "mu");

    private static int teId = 1;
    private static int fuId = 1;
    private static int coId = 1;
    private static int inId = 1;
    private static int muId = 1;
    private static int myId = 1;

    private static void addTectonObject(String tecton) {
        String name = String.format(names.get(Tecton.class) + "%02d", teId);

        switch (tecton) {
            case "tect":
                gameObjects.put(name, new Tecton());
                break;
            case "nomu":
                gameObjects.put(name, new NoMushroomTecton());
                break;
            case "simy":
                gameObjects.put(name, new SingleMyceliumTecton());
                break;
            case "myab":
                gameObjects.put(name, new MyceliumAbsorbingTecton());
                break;
            case "myke":
                gameObjects.put(name, new MyceliumKeepingTecton());
                break;
            default:
                System.out.println("Error: invalid tecton type");
                return;
        }

        teId++;
    }

    private static void tectonsMenu(ArrayList<String> params) {
        String input;

        do {
            for (var param : params) {
                if (param.equals("exit"))
                    return;
                
                addTectonObject(param);
            }
            
            input = System.console().readLine();
            params = new ArrayList<>(Arrays.asList(input.split(" ")));
        } while (!params.isEmpty());
    }

    private static void addNeighbor(String name1, String name2) {
        var t1 = (Tecton) gameObjects.get(name1);
        if (t1 == null) {
            System.out.println("Error: invalid left tecton name");
            return;
        }

        var t2 = (Tecton) gameObjects.get(name2);
        if (t2 == null) {
            System.out.println("Error: invalid right tecton name");
            return;
        }

        t1.addNeighbor(t2);
        t2.addNeighbor(t1);
    }

    private static void neighborsMenu(ArrayList<String> params) {
        String input;

        do {
            for (int i = 0; i < params.size() - 2; i += 3) {
                String t1 = params.get(i);
                String check = params.get(i + 1);
                String t2 = params.get(i + 2);

                if (t1.equals("exit") || check.equals("exit") || t2.equals("exit"))
                    return;
                if (!t2.equals("--")) {
                    System.out.println("Error: invalid neighbor definition");
                    continue;
                }
                
                addNeighbor(t1, t2);
            }

            input = System.console().readLine();
            params = new ArrayList<>(Arrays.asList(input.split(" ")));
        } while (!params.isEmpty());
    }

    private static void mushroomsMenu(ArrayList<String> params) {
        String input;

        do {
            for (int i = 0; i < params.size() - 1; i += 2) {
                String f = params.get(i);
                String t = params.get(i + 1);

                if (f.equals("exit") || t.equals("exit"))
                    return;

                var fungus = (Fungus)gameObjects.get(f);
                if (fungus == null) {
                    System.out.println("Error: invalid fungus name");
                    continue;
                }

                var tecton = (Tecton)gameObjects.get(t);
                if (tecton == null) {
                    System.out.println("Error: invalid tecton name");
                    continue;
                }

                gameObjects.put(String.format(names.get(Tecton.class) + "%02d", muId++), new Mushroom(fungus, tecton));
            }

            input = System.console().readLine();
            params = new ArrayList<>(Arrays.asList(input.split(" ")));
        } while (!params.isEmpty());
    }

    private static void myceliaMenu(ArrayList<String> params) {
        String input;

        do {
            for (int i = 0; i < params.size() - 4; i += 5) {
                String t1 = params.get(i);
                String check1 = params.get(i + 1);
                String t2 = params.get(i + 2);
                String check2 = params.get(i + 3);
                String f = params.get(i + 4);

                if (t1.equals("exit") || check1.equals("exit") || t2.equals("exit") || check2.equals("exit") || f.equals("exit"))
                    return;
                if (!check1.equals("--") || !check2.equals("--")) {
                    System.out.println("Error: invalid mycelium definition");
                    continue;
                }

                Tecton left = (Tecton)gameObjects.get(t1);
                if (left == null) {
                    System.out.println("Error: invalid left tecton name");
                    continue;
                }

                Tecton right = (Tecton)gameObjects.get(t1);
                if (right == null) {
                    System.out.println("Error: invalid right tecton name");
                    continue;
                }

                var fungus = (Fungus)gameObjects.get(t1);
                if (fungus == null) {
                    System.out.println("Error: invalid fungus name");
                    continue;
                }

                gameObjects.put(String.format(names.get(Tecton.class) + "%02d", myId++), new Mycelium(fungus, left, right));
            }

            input = System.console().readLine();
            params = new ArrayList<>(Arrays.asList(input.split(" ")));
        } while (!params.isEmpty());
    }

    private static void sporesMenu(ArrayList<String> params) {
        String input;

        do {
            for (int i = 0; i < params.size() - 1; i += 2) {
                String f = params.get(i);
                String t = params.get(i + 1);

                if (f.equals("exit") || t.equals("exit"))
                    return; 
                
                var fungus = (Fungus)gameObjects.get(f);
                if (fungus == null) {
                    System.out.println("Error: invalid fungus name");
                    continue;
                }

                var tecton = (Tecton)gameObjects.get(t);
                if (tecton == null) {
                    System.out.println("Error: invalid tecton name");
                    continue;
                }

                var spore = new Spore(fungus);

                tecton.addSpore(spore);
                gameObjects.put(String.format(names.get(Tecton.class) + "%02d", myId++), spore);
            }

            input = System.console().readLine();
            params = new ArrayList<>(Arrays.asList(input.split(" ")));
        } while (!params.isEmpty());
    }

    private static void insectsMenu(ArrayList<String> params) {
        String input;

        do {
            for (int i = 0; i < params.size() - 1; i += 2) {
                String c = params.get(i);
                String t = params.get(i + 1);
                String s = "1.0f";

                if (params.size() > i + 2) {
                    s = params.get(i + 2);
                    i++;
                }

                if (c.equals("exit") || t.equals("exit"))
                    return;
                
                var colony = (Colony)gameObjects.get(c);
                if (colony == null) {
                    System.out.println("Error: invalid colony name");
                    continue;
                }
                
                var tecton = (Tecton)gameObjects.get(t);
                if (tecton == null) {
                    System.out.println("Error: invalid tecton name");
                    continue;
                }

                var insect = new Insect(tecton);

                // Colony implementation missing here, like addinsect, or we can just set it at the constructor

                float speed;
                try {
                    speed = Float.parseFloat(s);
                } catch (NumberFormatException e) {
                    speed = 1.0f;
                }

                insect.setSpeed(teId);
                gameObjects.put(String.format(names.get(Tecton.class) + "%02d", inId++), new Insect(tecton));

                if (s.equals("exit"))
                    return;
            }

            input = System.console().readLine();
            params = new ArrayList<>(Arrays.asList(input.split(" ")));
        } while (!params.isEmpty());
    }

    // Main menu
    private static void buildPhase() {
        String input;
        
        do {
            input = System.console().readLine();
            if (input.isEmpty())
                continue;

            var tokens = new ArrayList<>(Arrays.asList(input.split(" ")));
            var command = tokens.get(0).toLowerCase();
            var params = new ArrayList<>(tokens.subList(1, tokens.size())); // NotOkay

            switch (command) {
                case "tectons":
                    tectonsMenu(params);
                    break;
                case "neighbors":
                    neighborsMenu(params);
                    break;
                case "fungi":
                    // Gombafajok hozzáadása
                    
                    break;
                case "colonies":
                    // kolóniák hozzáadása

                    break;
                case "mushrooms":
                    mushroomsMenu(params);
                    break;
                case "mycelia":
                    myceliaMenu(params);
                    break;
                case "insects":
                    // rovarok hozzáadása
                    insectsMenu(params);
                    break;
                case "spores":
                    sporesMenu(params);
                    break;
                default:
                    System.out.println("Error: invalid command");
                }
        } while (!input.toLowerCase().endsWith("end"));
    }

    // there is no static class in java
    /**
     * private Proto() { }
     */
}