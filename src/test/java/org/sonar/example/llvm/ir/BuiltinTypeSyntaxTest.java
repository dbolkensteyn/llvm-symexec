package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class BuiltinTypeSyntaxTest {

  @Test
  public void test() {
    BuiltinTypeSyntax t = ParserTest.parse(IrGrammarRuleKeys.BUILTIN_TYPE, " i32 ");

    assertThat(t.value()).isEqualTo("i32");
    assertThat(t.fullValue()).isEqualTo(" i32 ");

    assertThat(t.name()).isEqualTo("i32");
    assertThat(t.size()).isEqualTo(4);
  }

}
