package MiniJava.codeGenerator;

import java.util.ArrayList;

/**
 * Memory management class with separated query and modifier methods
 */
public class Memory {
    private ArrayList<_3AddressCode> codeBlock;
    private int lastTempIndex;
    private int lastDataAddress;
    private final int stratTempMemoryAddress = 500;
    private final int stratDataMemoryAddress = 200;
    private final int dataSize = 4;
    private final int tempSize = 4;

    public Memory() {
        codeBlock = new ArrayList<_3AddressCode>();
        lastTempIndex = stratTempMemoryAddress;
        lastDataAddress = stratDataMemoryAddress;
    }

    // Query methods (read-only, no side effects)
    public int getCurrentCodeBlockAddress() {
        return codeBlock.size();
    }

    public int getCodeBlockSize() {
        return codeBlock.size();
    }

    public _3AddressCode getCodeAt(int index) {
        if (index >= 0 && index < codeBlock.size()) {
            return codeBlock.get(index);
        }
        return null;
    }

    public String getFormattedCodeBlock() {
        StringBuilder result = new StringBuilder("Code Block\n");
        for (int i = 0; i < codeBlock.size(); i++) {
            result.append(i).append(" : ").append(codeBlock.get(i).toString()).append("\n");
        }
        return result.toString();
    }

    // Modifier methods (change state)
    public int allocateTemp() {
        int currentTemp = lastTempIndex;
        lastTempIndex += tempSize;
        return currentTemp;
    }

    public int allocateData() {
        int currentData = lastDataAddress;
        lastDataAddress += dataSize;
        return currentData;
    }

    public int reserveMemorySlot() {
        codeBlock.add(new _3AddressCode());
        return codeBlock.size() - 1;
    }

    public void addInstruction(Operation op, Address opr1, Address opr2, Address opr3) {
        codeBlock.add(new _3AddressCode(op, opr1, opr2, opr3));
    }

    public void updateInstruction(int index, Operation op, Address opr1, Address opr2, Address opr3) {
        if (index >= 0 && index < codeBlock.size()) {
            codeBlock.remove(index);
            codeBlock.add(index, new _3AddressCode(op, opr1, opr2, opr3));
        }
    }

    public void printCodeBlock() {
        System.out.print(getFormattedCodeBlock());
    }

    // Legacy methods for backward compatibility
    @Deprecated
    public int getTemp() {
        return allocateTemp();
    }

    @Deprecated
    public int getDateAddress() {
        return allocateData();
    }

    @Deprecated
    public int saveMemory() {
        return reserveMemorySlot();
    }

    @Deprecated
    public void add3AddressCode(Operation op, Address opr1, Address opr2, Address opr3) {
        addInstruction(op, opr1, opr2, opr3);
    }

    @Deprecated
    public void add3AddressCode(int i, Operation op, Address opr1, Address opr2, Address opr3) {
        updateInstruction(i, op, opr1, opr2, opr3);
    }

    @Deprecated
    public void pintCodeBlock() {
        printCodeBlock();
    }
}

class _3AddressCode {
    public Operation operation;
    public Address Operand1;
    public Address Operand2;
    public Address Operand3;

    public _3AddressCode() {

    }

    public _3AddressCode(Operation op, Address opr1, Address opr2, Address opr3) {
        operation = op;
        Operand1 = opr1;
        Operand2 = opr2;
        Operand3 = opr3;
    }

    public String toString() {
        if (operation == null) return "";
        StringBuffer res = new StringBuffer("(");
        res.append(operation.toString()).append(",");
        if (Operand1 != null) res.append(Operand1.toString());
        res.append(",");
        if (Operand2 != null) res.append(Operand2.toString());
        res.append(",");
        if (Operand3 != null) res.append(Operand3.toString());
        res.append(")");

        return res.toString();
    }
}
