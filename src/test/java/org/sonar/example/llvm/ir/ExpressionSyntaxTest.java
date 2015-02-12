package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class ExpressionSyntaxTest {

  @Test
  public void test() {
    ExpressionSyntax t = ParserTest.parse(IrGrammarRuleKeys.EXPRESSION, "null");
    assertThat(t).isInstanceOf(NullLiteralSyntax.class);

    t = ParserTest.parse(IrGrammarRuleKeys.EXPRESSION, "%foo");
    assertThat(t).isInstanceOf(IdentifierSyntax.class);

    t = ParserTest.parse(IrGrammarRuleKeys.EXPRESSION, "42");
    assertThat(t).isInstanceOf(IntegerLiteralSyntax.class);
  }

}
