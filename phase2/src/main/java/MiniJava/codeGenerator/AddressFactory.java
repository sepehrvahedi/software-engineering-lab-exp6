package MiniJava.codeGenerator;

/**
 * Factory class for creating different types of addresses
 */
public class AddressFactory {
    public static Address createAddress(int num, varType varType, TypeAddress type) {
        switch (type) {
            case Direct:
                return new DirectAddress(num, varType);
            case Indirect:
                return new IndirectAddress(num, varType);
            case Imidiate:
                return new ImmediateAddress(num, varType);
            default:
                return new DirectAddress(num, varType);
        }
    }
    
    public static Address createAddress(int num, varType varType) {
        return new DirectAddress(num, varType);
    }
}
