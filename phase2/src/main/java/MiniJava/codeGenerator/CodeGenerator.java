package MiniJava.codeGenerator;

import MiniJava.Log.Log;
import MiniJava.errorHandler.ErrorHandler;
import MiniJava.scanner.token.Token;
import MiniJava.semantic.symbol.Symbol;
import MiniJava.semantic.symbol.SymbolTable;
import MiniJava.semantic.symbol.SymbolType;

import java.util.Stack;

/**
 * Code generator with extracted methods for better readability
 */
public class CodeGenerator {
    private Memory memory = new Memory();
    private Stack<Address> ss = new Stack<Address>();
    private Stack<String> symbolStack = new Stack<>();
    private Stack<String> callStack = new Stack<>();
    private SymbolTable symbolTable;

    public CodeGenerator() {
        symbolTable = new SymbolTable(memory);
    }

    public void printMemory() {
        memory.printCodeBlock();
    }

    public void semanticFunction(int func, Token next) {
        Log.print("codegenerator : " + func);
        switch (func) {
            case 0: return;
            case 1: checkID(); break;
            case 2: pid(next); break;
            case 3: fpid(); break;
            case 4: kpid(next); break;
            case 5: intpid(next); break;
            case 6: startCall(); break;
            case 7: call(); break;
            case 8: arg(); break;
            case 9: assign(); break;
            case 10: add(); break;
            case 11: sub(); break;
            case 12: mult(); break;
            case 13: label(); break;
            case 14: save(); break;
            case 15: _while(); break;
            case 16: jpf_save(); break;
            case 17: jpHere(); break;
            case 18: print(); break;
            case 19: equal(); break;
            case 20: less_than(); break;
            case 21: and(); break;
            case 22: not(); break;
            case 23: defClass(); break;
            case 24: defMethod(); break;
            case 25: popClass(); break;
            case 26: extend(); break;
            case 27: defField(); break;
            case 28: defVar(); break;
            case 29: methodReturn(); break;
            case 30: defParam(); break;
            case 31: lastTypeBool(); break;
            case 32: lastTypeInt(); break;
            case 33: defMain(); break;
        }
    }

    // Extracted method for type conversion
    private varType convertSymbolTypeToVarType(SymbolType symbolType) {
        switch (symbolType) {
            case Bool: return varType.Bool;
            case Int: return varType.Int;
            default: return varType.Int;
        }
    }

    // Extracted method for type validation
    private void validateOperandTypes(Address operand1, Address operand2, varType expectedType, String operationName) {
        if (operand1.getVarType() != expectedType || operand2.getVarType() != expectedType) {
            ErrorHandler.printError("In " + operationName + " two operands must be " + expectedType);
        }
    }

    // Extracted method for arithmetic operations
    private void performArithmeticOperation(Operation operation, String operationName) {
        Address temp = AddressFactory.createAddress(memory.allocateTemp(), varType.Int);
        Address s2 = ss.pop();
        Address s1 = ss.pop();

        validateOperandTypes(s1, s2, varType.Int, operationName);
        memory.addInstruction(operation, s1, s2, temp);
        ss.push(temp);
    }

    // Extracted method for symbol lookup with error handling
    private Symbol getSymbolSafely(String className, String methodName, String symbolName) {
        try {
            return symbolTable.get(className, methodName, symbolName);
        } catch (Exception e) {
            return null;
        }
    }

    // Refactored methods using extracted helper methods
    public void add() {
        performArithmeticOperation(Operation.ADD, "add");
    }

    public void sub() {
        performArithmeticOperation(Operation.SUB, "sub");
    }

    public void mult() {
        performArithmeticOperation(Operation.MULT, "mult");
    }

    public void pid(Token next) {
        if (symbolStack.size() > 1) {
            String methodName = symbolStack.pop();
            String className = symbolStack.pop();

            Symbol symbol = getSymbolSafely(className, methodName, next.value);

            if (symbol != null) {
                varType type = convertSymbolTypeToVarType(symbol.getType());
                ss.push(AddressFactory.createAddress(symbol.getAddress(), type));
            } else {
                ss.push(AddressFactory.createAddress(0, varType.Non));
            }

            symbolStack.push(className);
            symbolStack.push(methodName);
        } else {
            ss.push(AddressFactory.createAddress(0, varType.Non));
        }
        symbolStack.push(next.value);
    }

    public void defMain() {
        memory.updateInstruction(ss.pop().getNum(), Operation.JP,
                AddressFactory.createAddress(memory.getCurrentCodeBlockAddress(), varType.Address), null, null);

        String methodName = "main";
        String className = symbolStack.pop();
        symbolTable.addMethod(className, methodName, memory.getCurrentCodeBlockAddress());

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    public void checkID() {
        symbolStack.pop();
        if (ss.peek().getVarType() == varType.Non) {
            //TODO : error
        }
    }

    public void fpid() {
        ss.pop();
        ss.pop();

        Symbol s = symbolTable.get(symbolStack.pop(), symbolStack.pop());
        varType t = convertSymbolTypeToVarType(s.getType());
        ss.push(AddressFactory.createAddress(s.getAddress(), t));
    }

    public void kpid(Token next) {
        ss.push(symbolTable.get(next.value));
    }

    public void intpid(Token next) {
        ss.push(AddressFactory.createAddress(Integer.parseInt(next.value), varType.Int, TypeAddress.Imidiate));
    }

    public void startCall() {
        //TODO: method ok
        ss.pop();
        ss.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();
        symbolTable.startCall(className, methodName);
        callStack.push(className);
        callStack.push(methodName);

        //symbolStack.push(methodName);
    }

    public void call() {
        //TODO: method ok
        String methodName = callStack.pop();
        String className = callStack.pop();
        try {
            symbolTable.getNextParam(className, methodName);
            ErrorHandler.printError("The few argument pass for method");
        } catch (IndexOutOfBoundsException e) {
        }
        varType t = convertSymbolTypeToVarType(symbolTable.getMethodReturnType(className, methodName));

        Address temp = AddressFactory.createAddress(memory.allocateTemp(), t);
        ss.push(temp);
        memory.addInstruction(Operation.ASSIGN,
                AddressFactory.createAddress(temp.getNum(), varType.Address, TypeAddress.Imidiate),
                AddressFactory.createAddress(symbolTable.getMethodReturnAddress(className, methodName), varType.Address),
                null);
        memory.addInstruction(Operation.ASSIGN,
                AddressFactory.createAddress(memory.getCurrentCodeBlockAddress() + 2, varType.Address, TypeAddress.Imidiate),
                AddressFactory.createAddress(symbolTable.getMethodCallerAddress(className, methodName), varType.Address),
                null);
        memory.addInstruction(Operation.JP,
                AddressFactory.createAddress(symbolTable.getMethodAddress(className, methodName), varType.Address),
                null, null);

        //symbolStack.pop();
    }

    public void arg() {
        //TODO: method ok
        String methodName = callStack.pop();
        try {
            Symbol s = symbolTable.getNextParam(callStack.peek(), methodName);
            varType t = convertSymbolTypeToVarType(s.getType());

            Address param = ss.pop();
            if (param.getVarType() != t) {
                ErrorHandler.printError("The argument type isn't match");
            }
            memory.addInstruction(Operation.ASSIGN, param,
                    AddressFactory.createAddress(s.getAddress(), t), null);

        } catch (IndexOutOfBoundsException e) {
            ErrorHandler.printError("Too many arguments pass for method");
        }
        callStack.push(methodName);
    }

    public void assign() {
        Address s1 = ss.pop();
        Address s2 = ss.pop();
        if (s1.getVarType() != s2.getVarType()) {
            ErrorHandler.printError("The type of operands in assign is different ");
        }
        memory.addInstruction(Operation.ASSIGN, s1, s2, null);
    }

    public void label() {
        ss.push(AddressFactory.createAddress(memory.getCurrentCodeBlockAddress(), varType.Address));
    }

    public void save() {
        ss.push(AddressFactory.createAddress(memory.reserveMemorySlot(), varType.Address));
    }

    public void _while() {
        memory.updateInstruction(ss.pop().getNum(), Operation.JPF, ss.pop(),
                AddressFactory.createAddress(memory.getCurrentCodeBlockAddress() + 1, varType.Address), null);
        memory.addInstruction(Operation.JP, ss.pop(), null, null);
    }

    public void jpf_save() {
        Address save = AddressFactory.createAddress(memory.reserveMemorySlot(), varType.Address);
        memory.updateInstruction(ss.pop().getNum(), Operation.JPF, ss.pop(),
                AddressFactory.createAddress(memory.getCurrentCodeBlockAddress(), varType.Address), null);
        ss.push(save);
    }

    public void jpHere() {
        memory.updateInstruction(ss.pop().getNum(), Operation.JP,
                AddressFactory.createAddress(memory.getCurrentCodeBlockAddress(), varType.Address), null, null);
    }

    public void print() {
        memory.addInstruction(Operation.PRINT, ss.pop(), null, null);
    }

    public void equal() {
        Address temp = AddressFactory.createAddress(memory.allocateTemp(), varType.Bool);
        Address s2 = ss.pop();
        Address s1 = ss.pop();
        if (s1.getVarType() != s2.getVarType()) {
            ErrorHandler.printError("The type of operands in equal operator is different");
        }
        memory.addInstruction(Operation.EQ, s1, s2, temp);
        ss.push(temp);
    }

    public void less_than() {
        Address temp = AddressFactory.createAddress(memory.allocateTemp(), varType.Bool);
        Address s2 = ss.pop();
        Address s1 = ss.pop();
        if (s1.getVarType() != varType.Int || s2.getVarType() != varType.Int) {
            ErrorHandler.printError("The type of operands in less than operator is different");
        }
        memory.addInstruction(Operation.LT, s1, s2, temp);
        ss.push(temp);
    }

    public void and() {
        Address temp = AddressFactory.createAddress(memory.allocateTemp(), varType.Bool);
        Address s2 = ss.pop();
        Address s1 = ss.pop();
        if (s1.getVarType() != varType.Bool || s2.getVarType() != varType.Bool) {
            ErrorHandler.printError("In and operator the operands must be boolean");
        }
        memory.addInstruction(Operation.AND, s1, s2, temp);
        ss.push(temp);
    }

    public void not() {
        Address temp = AddressFactory.createAddress(memory.allocateTemp(), varType.Bool);
        Address s1 = ss.pop();
        if (s1.getVarType() != varType.Bool) {
            ErrorHandler.printError("In not operator the operand must be boolean");
        }
        memory.addInstruction(Operation.NOT, s1, null, temp);
        ss.push(temp);
    }

    public void defClass() {
        ss.pop();
        symbolTable.addClass(symbolStack.peek());
    }

    public void defMethod() {
        ss.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();

        symbolTable.addMethod(className, methodName, memory.getCurrentCodeBlockAddress());

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    public void popClass() {
        symbolStack.pop();
    }

    public void extend() {
        ss.pop();
        symbolTable.setSuperClass(symbolStack.pop(), symbolStack.peek());
    }

    public void defField() {
        ss.pop();
        symbolTable.addField(symbolStack.pop(), symbolStack.peek());
    }

    public void defVar() {
        ss.pop();

        String var = symbolStack.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();

        symbolTable.addMethodLocalVariable(className, methodName, var);

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    public void methodReturn() {
        //TODO : call ok
        String methodName = symbolStack.pop();
        Address s = ss.pop();
        SymbolType t = symbolTable.getMethodReturnType(symbolStack.peek(), methodName);
        varType temp = convertSymbolTypeToVarType(t);

        if (s.getVarType() != temp) {
            ErrorHandler.printError("The type of method and return address was not match");
        }
        memory.addInstruction(Operation.ASSIGN, s,
                AddressFactory.createAddress(symbolTable.getMethodReturnAddress(symbolStack.peek(), methodName),
                        varType.Address, TypeAddress.Indirect), null);
        memory.addInstruction(Operation.JP,
                AddressFactory.createAddress(symbolTable.getMethodCallerAddress(symbolStack.peek(), methodName),
                        varType.Address), null, null);

        //symbolStack.pop();
    }

    public void defParam() {
        //TODO : call Ok
        ss.pop();
        String param = symbolStack.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();

        symbolTable.addMethodParameter(className, methodName, param);

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    public void lastTypeBool() {
        symbolTable.setLastType(SymbolType.Bool);
    }

    public void lastTypeInt() {
        symbolTable.setLastType(SymbolType.Int);
    }

    public void main() {

    }
}
