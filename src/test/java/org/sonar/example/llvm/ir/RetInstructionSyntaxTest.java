package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class RetInstructionSyntaxTest {

  @Test
  public void test() {
    RetInstructionSyntax t = ParserTest.parse(IrGrammarRuleKeys.RET_INSTRUCTION, " ret void ");

    assertThat(t.toString()).isEqualTo("ret void");
    assertThat(t.toFullString()).isEqualTo(" ret void ");
  }

}
