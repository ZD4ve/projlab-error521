package helper;

abstract class TraceItem {
    protected int tabulation;

    TraceItem(int tabulation) {
        this.tabulation = tabulation;
    }

    abstract void print();
}
