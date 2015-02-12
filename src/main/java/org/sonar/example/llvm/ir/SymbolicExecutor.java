package org.sonar.example.llvm.ir;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;

public class SymbolicExecutor {

  private final Map<String, SymbolicValue> values = Maps.newHashMap();
  private final Map<SymbolicValue, SymbolicValue> memory = Maps.newHashMap();

  public void evaluate(FunctionDefinitionSyntax f) {
    newLeftValue(f.param(), new SomeValue());

    for (InstructionSyntax i : f.instructions()) {
      if (i instanceof AllocaInstructionSyntax) {
        AllocaInstructionSyntax a = (AllocaInstructionSyntax) i;

        newLeftValue(a.result(), UninitializedValue.INSTANCE);
      } else if (i instanceof StoreInstructionSyntax) {
        StoreInstructionSyntax s = (StoreInstructionSyntax) i;

        SymbolicValue value = get(s.value());
        Preconditions.checkState(!value.isUninitialized(), "Cannot read an uninitialized value \"" + s.value().toString() + "\"! " + i.toString());

        SymbolicValue pointer = get(s.pointer());
        Preconditions.checkState(!pointer.isNull(), "NPE: " + i.toString());

        store(pointer, value);
      } else if (i instanceof LoadInstructionSyntax) {
        LoadInstructionSyntax l = (LoadInstructionSyntax) i;

        SymbolicValue pointer = get(l.pointer());
        Preconditions.checkState(!pointer.isNull(), "NPE: " + i.toString());

        SymbolicValue value = load(pointer);
        Preconditions.checkState(!value.isUninitialized(), "Cannot load an uninitialized value from memory! " + i.toString());

        set(l.result(), value);
      } else if (i instanceof GepInstructionSyntax) {
        GepInstructionSyntax g = (GepInstructionSyntax) i;

        System.out.println(g);
      } else if (i instanceof RetInstructionSyntax) {
        // Do nothing
      } else {
        throw new UnsupportedOperationException("Unsupported instruction: " + i.toString());
      }
    }
  }

  private void newLeftValue(IdentifierSyntax identifier, SymbolicValue value) {
    SomeValue address = new SomeValue();
    set(identifier, address);
    store(address, value);
  }

  private void set(IdentifierSyntax identifier, SymbolicValue value) {
    String key = identifier.name();
    Preconditions.checkState(!values.containsKey(key), "Cannot rewrite the value of: " + identifier.toString());
    values.put(key, value);
  }

  private SymbolicValue get(ExpressionSyntax expression) {
    if (expression instanceof IdentifierSyntax) {
      SymbolicValue value = values.get(((IdentifierSyntax) expression).name());
      return value != null ? value : UninitializedValue.INSTANCE;
    } else if (expression instanceof NullLiteralSyntax) {
      return ConcreteIntValue.NULL;
    } else if (expression instanceof IntegerLiteralSyntax) {
      return new ConcreteIntValue(((IntegerLiteralSyntax) expression).value());
    }

    throw new IllegalArgumentException("Unsupported expression: " + expression.toString());
  }

  private void store(SymbolicValue address, SymbolicValue value) {
    memory.put(address, value);
  }

  private SymbolicValue load(SymbolicValue address) {
    SymbolicValue value = memory.get(address);
    return value != null ? value : UninitializedValue.INSTANCE;
  }

  public abstract static class SymbolicValue {

    public boolean isNull() {
      return false;
    }

    public boolean isUninitialized() {
      return false;
    }

  }

  public static class SomeValue extends SymbolicValue {
  }

  public static class UninitializedValue extends SymbolicValue {

    public final static UninitializedValue INSTANCE = new UninitializedValue();

    private UninitializedValue() {
    }

    @Override
    public boolean isUninitialized() {
      return true;
    }

  }

  public static class ConcreteIntValue extends SymbolicValue {

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
