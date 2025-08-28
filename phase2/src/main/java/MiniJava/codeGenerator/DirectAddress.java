package MiniJava.codeGenerator;

/**
 * Direct address implementation
 */
public class DirectAddress extends Address {
    public DirectAddress(int num, varType varType) {
        super(num, varType);
    }
    
    @Override
    public String toString() {
        return String.valueOf(num);
    }
}
