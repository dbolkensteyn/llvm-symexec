package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class IntegerLiteralSyntaxTest {

  @Test
  public void test() {
    IntegerLiteralSyntax t = ParserTest.parse(IrGrammarRuleKeys.INTEGER_LITERAL, " 0 ");

    assertThat(t.toString()).isEqualTo("0");
    assertThat(t.toFullString()).isEqualTo(" 0 ");
    assertThat(t.value()).isEqualTo(0);

    t = ParserTest.parse(IrGrammarRuleKeys.INTEGER_LITERAL, "42");
    assertThat(t.value()).isEqualTo(42);
  }

}
