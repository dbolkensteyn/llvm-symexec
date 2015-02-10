package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class AllocaInstructionSyntaxTest {

  @Test
  public void test() {
    AllocaInstructionSyntax t = ParserTest.parse(IrGrammarRuleKeys.ALLOCA_INSTRUCTION, "%1 = alloca i32*, align 8");
    assertThat(t.result().name()).isEqualTo("%1");
    assertThat(t.type()).isInstanceOf(PointerTypeSyntax.class);
  }

}
