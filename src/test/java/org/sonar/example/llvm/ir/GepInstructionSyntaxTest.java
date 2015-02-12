package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class GepInstructionSyntaxTest {

  @Test
  public void test() {
    GepInstructionSyntax t = ParserTest.parse(IrGrammarRuleKeys.GEP_INSTRUCTION, " %3 = getelementptr inbounds i32* %2, i64 0 ");

    assertThat(t.toString()).isEqualTo("%3 = getelementptr inbounds i32* %2, i64 0");
    assertThat(t.toFullString()).isEqualTo(" %3 = getelementptr inbounds i32* %2, i64 0 ");

    assertThat(t.result().toString()).isEqualTo("%3");
    assertThat(t.pointer().toString()).isEqualTo("%2");
    assertThat(t.indexValues()).hasSize(1);
    assertThat(t.indexValues().get(0).toString()).isEqualTo("0");

    t = ParserTest.parse(IrGrammarRuleKeys.GEP_INSTRUCTION, "%2 = getelementptr inbounds i32* %1, i32 1, i32 3");
    assertThat(t.result().toString()).isEqualTo("%2");
    assertThat(t.pointer().toString()).isEqualTo("%1");
    assertThat(t.indexValues()).hasSize(2);
    assertThat(t.indexValues().get(0).toString()).isEqualTo("1");
    assertThat(t.indexValues().get(1).toString()).isEqualTo("3");
  }

}
