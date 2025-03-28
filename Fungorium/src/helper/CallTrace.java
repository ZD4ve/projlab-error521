package helper;

import java.util.List;

@java.lang.SuppressWarnings("java:S106") // használható büntetlenül a System IO
public class CallTrace extends TraceItem {
    private String fName;
    private Object calledOn;
    private List<Object> params;

    public CallTrace(int tabulation, Object calledOn, String fName, List<Object> params) {
        super(tabulation);
        this.fName = fName;
        this.calledOn = calledOn;
        this.params = params;
    }

    public void print() {
        String paramStr = "";
        if (params != null && !params.isEmpty()) {
            paramStr = Skeleton.getObjName(params.get(0));
            for (int i = 1; i < params.size(); i++) {
                paramStr += ", " + Skeleton.getObjName(params.get(i)); // NOSONAR: nem kell StringBuilder
            }
        }
        System.out.println("  ".repeat(tabulation) + "call " + Skeleton.getObjName(calledOn) + "." + fName + "(" + paramStr + ")");
    }

}
