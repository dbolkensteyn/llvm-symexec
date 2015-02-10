package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class InstructionSyntaxTest {

  @Test
  public void test() {
    InstructionSyntax t = ParserTest.parse(IrGrammarRuleKeys.INSTRUCTION, "%1 = alloca i32*, align 8");
    assertThat(t).isInstanceOf(AllocaInstructionSyntax.class);

    t = ParserTest.parse(IrGrammarRuleKeys.INSTRUCTION, "store i32* %p, i32** %1, align 8");
    assertThat(t).isInstanceOf(StoreInstructionSyntax.class);

    t = ParserTest.parse(IrGrammarRuleKeys.INSTRUCTION, "%2 = load i32** %1, align 8");
    assertThat(t).isInstanceOf(LoadInstructionSyntax.class);

    t = ParserTest.parse(IrGrammarRuleKeys.INSTRUCTION, "ret void");
    assertThat(t).isInstanceOf(RetInstructionSyntax.class);
  }

}
