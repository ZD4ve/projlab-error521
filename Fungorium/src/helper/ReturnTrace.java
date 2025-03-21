package helper;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class ReturnTrace extends TraceItem {
    private Object returned;

    public ReturnTrace(int tabulation, Object returned) {
        super(tabulation);
        this.returned = returned;
    }

    public void print() {
        System.out.println("  ".repeat(tabulation) + "return " + Skeleton.getObjName(returned));
    }
}
