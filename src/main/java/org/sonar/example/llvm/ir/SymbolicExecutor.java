package org.sonar.example.llvm.ir;

import java.util.HashMap;
import java.util.Map;

public class SymbolicExecutor {

  private final Map<String, BaseValue> values = new HashMap<>();
  private final Map<BaseValue, BaseValue> memory = new HashMap<>();

  public void evaluate(FunctionDefinitionSyntax f) {
    newLeftValue(f.param(), new SymbolicValue());

    for (InstructionSyntax i : f.instructions()) {
      if (i instanceof AllocaInstructionSyntax) {
        AllocaInstructionSyntax a = (AllocaInstructionSyntax) i;

        newLeftValue(a.result(), UninitializedValue.INSTANCE);
      } else if (i instanceof StoreInstructionSyntax) {
        StoreInstructionSyntax s = (StoreInstructionSyntax) i;

        BaseValue value = get(s.value());
        if (value.isUninitialized()) {
          throw new IllegalStateException("Cannot read an uninitialized value \"" + s.value().toString() + "\"! " + i.toString());
        }

        BaseValue pointer = get(s.pointer());
        if (pointer.isNull()) {
          throw new IllegalStateException("NPE: " + i.toString());
        }

        store(pointer, value);
      } else if (i instanceof LoadInstructionSyntax) {
        LoadInstructionSyntax l = (LoadInstructionSyntax) i;

        BaseValue pointer = get(l.pointer());
        if (pointer.isNull()) {
          throw new IllegalStateException("NPE: " + i.toString());
        }

        BaseValue value = load(pointer);
        if (value.isUninitialized()) {
          throw new IllegalStateException("Cannot load an uninitialized value from memory! " + i.toString());
        }

        set(l.result(), value);
      } else if (i instanceof GepInstructionSyntax) {
        GepInstructionSyntax g = (GepInstructionSyntax) i;

        BaseValue address = get(g.pointer());
        for (IntegerLiteralSyntax index : g.indexValues()) {
          address = address.addressOf(index.value());
          if (load(address).isUninitialized()) {
            // FIXME: Do not assume all fields are initialized?
            store(address, new SymbolicValue());
          }
        }

        set(g.result(), address);
      } else if (i instanceof RetInstructionSyntax) {
        break;
      } else {
        throw new UnsupportedOperationException("Unsupported instruction: " + i.toString());
      }
    }
  }

  private void newLeftValue(IdentifierSyntax identifier, BaseValue value) {
    SymbolicValue address = new SymbolicValue();
    set(identifier, address);
    store(address, value);
  }

  private void set(IdentifierSyntax identifier, BaseValue value) {
    String key = identifier.name();
    if (values.containsKey(key)) {
      throw new IllegalStateException("Cannot rewrite the value of: " + identifier.toString());
    }
    values.put(key, value);
  }

  private BaseValue get(ExpressionSyntax expression) {
    if (expression instanceof IdentifierSyntax) {
      BaseValue value = values.get(((IdentifierSyntax) expression).name());
      return value != null ? value : UninitializedValue.INSTANCE;
    } else if (expression instanceof NullLiteralSyntax) {
      return ConcreteIntValue.NULL;
    } else if (expression instanceof IntegerLiteralSyntax) {
      return new ConcreteIntValue(((IntegerLiteralSyntax) expression).value());
    }

    throw new IllegalArgumentException("Unsupported expression: " + expression.toString());
  }

  private void store(BaseValue address, BaseValue value) {
    memory.put(address, value);
  }

  private BaseValue load(BaseValue address) {
    BaseValue value = memory.get(address);
    return value != null ? value : UninitializedValue.INSTANCE;
  }

  public abstract static class BaseValue {

    public boolean isNull() {
      return false;
    }

    public boolean isUninitialized() {
      return false;
    }

    public BaseValue addressOf(int index) {
      return ConcreteIntValue.NULL;
    }

  }

  public static class SymbolicValue extends BaseValue {

    private final Map<Integer, SymbolicValue> indexAddresses = new HashMap<>();

    @Override
    public BaseValue addressOf(int index) {
      SymbolicValue value = indexAddresses.get(indexAddresses);
      if (value == null) {
        value = new SymbolicValue();
        indexAddresses.put(index, value);
      }
      return value;
    }

  }

  public static class UninitializedValue extends BaseValue {

    public final static UninitializedValue INSTANCE = new UninitializedValue();

    private UninitializedValue() {
    }

    @Override
    public boolean isUninitialized() {
      return true;
    }

  }

  public static class ConcreteIntValue extends BaseValue {

    public static final ConcreteIntValue NULL = new ConcreteIntValue(0);
    private final int value;

    public ConcreteIntValue(int value) {
      this.value = value;
    }

    public int value() {
      return value;
    }

    @Override
    public boolean isNull() {
      return value() == 0;
    }

  }

}
