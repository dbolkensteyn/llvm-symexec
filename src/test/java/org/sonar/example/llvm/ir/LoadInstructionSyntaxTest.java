package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class LoadInstructionSyntaxTest {

  @Test
  public void test() {
    LoadInstructionSyntax t = ParserTest.parse(IrGrammarRuleKeys.LOAD_INSTRUCTION, " %2 = load i32** %1, align 8 ");

    assertThat(t.toString()).isEqualTo("%2 = load i32** %1, align 8");
    assertThat(t.toFullString()).isEqualTo(" %2 = load i32** %1, align 8 ");

    assertThat(t.result().toString()).isEqualTo("%2");
    assertThat(t.pointer().toString()).isEqualTo("%1");
  }

}
