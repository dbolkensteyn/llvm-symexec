package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class NullLiteralSyntaxTest {

  @Test
  public void test() {
    NullLiteralSyntax t = ParserTest.parse(IrGrammarRuleKeys.NULL_LITERAL, " null ");

    assertThat(t.toString()).isEqualTo("null");
    assertThat(t.toFullString()).isEqualTo(" null ");
  }

}
