package helper;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class Skeleton {
    private Skeleton() {
    } // Skeleton ne legyen példányosítható

    static int tabulation = 0;
    static boolean printOn = true;
    static List<TraceItem> trace = new ArrayList<>();

    static Map<Object, String> objNames = new HashMap<>();

    static List<Runnable> useCases = new ArrayList<>();
    static Map<Runnable, String> useCaseNames = new HashMap<>();

    // Segédfüggvények
    // --------------------------------------------------------------------------------

    static void addUseCase(Runnable f, String name) {
        useCases.add(f);
        useCaseNames.put(f, name);
    }

    public static void addObject(Object obj, String name) {
        objNames.put(obj, name);
    }

    static String listToString(List<?> l) {
        if (l == null || l.isEmpty()) {
            return "[]";
        }
        String s = "[" + getObjName(l.get(0));
        for (int i = 1; i < l.size(); i++) {
            s += ", " + getObjName(l.get(i)); // NOSONAR: nem kell StringBuilder
        }
        s += "]";
        return s;
    }

    static String getObjName(Object obj) {
        if (obj == null) {
            return "null";
        } else if (objNames.containsKey(obj)) {
            return objNames.get(obj);
        } else if (obj instanceof List<?> list) {
            return listToString(list);
        } else if (obj instanceof Object[] arr) {
            return listToString(Arrays.asList(arr));
        } else if (obj instanceof Class<?> cls) {
            return cls.getSimpleName();
        } else {
            return obj.toString();
        }
    }

    static Object getObjByName(String name) throws IllegalArgumentException {
        for (Map.Entry<Object, String> entry : objNames.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("No object with name: " + name);
    }

    // Be és kimeneti függvények
    // --------------------------------------------------------------------------------

    public static void printCall(Object obj, List<Object> params) {
        if (printOn) {
            var frame = StackWalker.getInstance().walk(s -> s.skip(1).findFirst());
            String fName = frame.isPresent() ? frame.get().getMethodName() : "func";
            if (fName.equals("printCall")) { // fujj
                var frame2 = StackWalker.getInstance().walk(s -> s.skip(2).findFirst());
                fName = frame2.isPresent() ? frame2.get().getMethodName() : "func";
            }
            if (fName.equals("<init>")) {
                fName = "new";
            }
            trace.add(new CallTrace(tabulation, obj, fName, params));
            tabulation++;
        }
    }

    public static void printCall(Object obj) {
        printCall(obj, null);
    }

    public static void printReturn(Object returnValue) {
        if (printOn) {
            tabulation--;
            trace.add(new ReturnTrace(tabulation, returnValue));
        }
    }

    public static void printReturn() {
        printReturn(" ");
    }

    public static boolean ask(String question) {
        printTrace();
        System.out.println(question + " (Y/n)");
        System.out.print(">");
        String answer = System.console().readLine();
        return !answer.equals("n");
    }

    public static void printTrace() {
        for (TraceItem item : trace) {
            item.print();
        }
        trace = new ArrayList<>();
    }

    static int useCaseChooser() {
        System.out.println();
        for (int i = 0; i < useCases.size(); i++) {
            System.out.println(i + 1 + ": " + useCaseNames.get(useCases.get(i)));
        }
        while (true) {
            System.out.print(">");
            try {
                String inp = System.console().readLine();
                System.out.println();
                if (inp.equals("exit")) {
                    return -1;
                }
                int choice = Integer.parseInt(inp) - 1;
                if (choice >= 0 && choice < useCases.size()) {
                    return choice;
                }
            } catch (NumberFormatException e) { // NOSONAR: szándékosan üres a kezelés, a ciklus újraindul
            }
        }
    }

    public static void main(String[] args) {
        UseCases.init();
        while (true) {
            int choice = useCaseChooser();
            if (choice == -1)
                break;
            try {
                useCases.get(choice).run();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}