package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class IdentifierSyntaxTest {

  @Test
  public void test() {
    IdentifierSyntax t = ParserTest.parse(IrGrammarRuleKeys.IDENTIFIER, " %1 ");

    assertThat(t.toString()).isEqualTo("%1");
    assertThat(t.toFullString()).isEqualTo(" %1 ");

    assertThat(t.name()).isEqualTo("%1");

    t = ParserTest.parse(IrGrammarRuleKeys.IDENTIFIER, " %max ");
    assertThat(t.name()).isEqualTo("%max");

    t = ParserTest.parse(IrGrammarRuleKeys.IDENTIFIER, "%a.really.long.identifier");
    assertThat(t.name()).isEqualTo("%a.really.long.identifier");

    t = ParserTest.parse(IrGrammarRuleKeys.IDENTIFIER, "@DivisionByZero");
    assertThat(t.name()).isEqualTo("@DivisionByZero");
  }

}
