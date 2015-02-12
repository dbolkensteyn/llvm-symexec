package org.sonar.example.llvm.ir;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import java.util.Map;

public class SymbolicExecutorTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final Map<String, State> values = Maps.newHashMap();
  private final Map<String, State> memory = Maps.newHashMap();

  @Test
  public void test() {
    thrown.expectMessage("NPE: %5 = load i32* %4, align 4");

    FunctionDefinitionSyntax f = ParserTest.parse(
      IrGrammarRuleKeys.FUNCTION_DEFINITION,
      "define void @f1(i32* %p) #0 {",
      "  %1 = alloca i32*, align 8",
      "  store i32* %p, i32** %1, align 8",
      "  %2 = load i32** %1, align 8",
      "  %3 = load i32* %2, align 4",
      "  store i32* null, i32** %1, align 8",
      "  %4 = load i32** %1, align 8",
      "  %5 = load i32* %4, align 4",
      "  ret void",
      "}");

    set(f.param(), State.UNKNOWN);

    for (InstructionSyntax i : f.instructions()) {
      if (i instanceof AllocaInstructionSyntax) {
        AllocaInstructionSyntax a = (AllocaInstructionSyntax) i;

        set(a.result(), State.NOT_NULL);
        store(a.result(), State.UNINITIALIZED);
      } else if (i instanceof StoreInstructionSyntax) {
        StoreInstructionSyntax s = (StoreInstructionSyntax) i;

        State value = get(s.value());
        Preconditions.checkState(!State.UNINITIALIZED.equals(value), "Cannot read an uninitialized value \"" + s.value().toString() + "\"! " + i.toString());

        State pointer = get(s.pointer());
        Preconditions.checkState(!State.NULL.equals(pointer), "NPE: " + i.toString());

        store(s.pointer(), value);
      } else if (i instanceof LoadInstructionSyntax) {
        LoadInstructionSyntax l = (LoadInstructionSyntax) i;

        State pointer = get(l.pointer());
        Preconditions.checkState(!State.NULL.equals(pointer), "NPE: " + i.toString());

        set(l.result(), load(l.pointer()));
      } else if (i instanceof RetInstructionSyntax) {
        // Do nothing
      } else {
        throw new UnsupportedOperationException("Unsupported instruction: " + i.toString());
      }
    }
  }

  private void set(IdentifierSyntax identifier, State value) {
    String key = identifier.name();
    Preconditions.checkState(!values.containsKey(key), "Cannot rewrite the value of: " + identifier.toString());
    values.put(key, value);
  }

  private State get(ExpressionSyntax expression) {
    if (expression instanceof IdentifierSyntax) {
      State value = values.get(((IdentifierSyntax) expression).name());
      return value != null ? value : State.UNINITIALIZED;
    } else if (expression instanceof NullLiteralSyntax) {
      return State.NULL;
    }

    throw new IllegalArgumentException("Unsupported expression: " + expression.toString());
  }

  private void store(IdentifierSyntax identifier, State state) {
    memory.put(identifier.name(), state);
  }

  private State load(IdentifierSyntax identifier) {
    State value = memory.get(identifier.name());
    return value != null ? value : State.UNINITIALIZED;
  }

  public static enum State {
    UNINITIALIZED,
    UNKNOWN,
    NULL,
    NOT_NULL
  }

}
