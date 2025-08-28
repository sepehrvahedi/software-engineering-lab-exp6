package MiniJava.semantic.symbol;

/**
 * Symbol class with self-encapsulated fields
 */
public class Symbol {
    private SymbolType type;
    private int address;

    public Symbol(SymbolType type, int address) {
        setType(type);
        setAddress(address);
    }

    // Self-encapsulated field accessors
    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        if (type == null) {
            throw new IllegalArgumentException("Symbol type cannot be null");
        }
        this.type = type;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        if (address < 0) {
            throw new IllegalArgumentException("Address cannot be negative");
        }
        this.address = address;
    }

    public boolean isNumericType() {
        return getType() == SymbolType.Int;
    }

    public boolean isBooleanType() {
        return getType() == SymbolType.Bool;
    }
}
