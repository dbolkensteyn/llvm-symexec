package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class RegisterSyntaxTest {

  @Test
  public void test() {
    RegisterSyntax t = ParserTest.parse(IrGrammarRuleKeys.REGISTER, "%1");
    assertThat(t.name()).isEqualTo("%1");

    t = ParserTest.parse(IrGrammarRuleKeys.REGISTER, " %max ");
    assertThat(t.name()).isEqualTo("%max");
  }

}
