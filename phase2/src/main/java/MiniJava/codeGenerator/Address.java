package MiniJava.codeGenerator;

/**
 * Abstract base class for different address types
 */
public abstract class Address {
    protected int num;
    protected varType varType;

    public Address(int num, varType varType) {
        this.num = num;
        this.varType = varType;
    }

    public int getNum() {
        return num;
    }

    public varType getVarType() {
        return varType;
    }

    public abstract String toString();
}
