package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class CustomTypeSyntaxTest {

  @Test
  public void test() {
    CustomTypeSyntax t = ParserTest.parse(IrGrammarRuleKeys.CUSTOM_TYPE, " %struct.my_struct_t ");

    assertThat(t.toString()).isEqualTo("%struct.my_struct_t");
    assertThat(t.toFullString()).isEqualTo(" %struct.my_struct_t ");
  }

}
