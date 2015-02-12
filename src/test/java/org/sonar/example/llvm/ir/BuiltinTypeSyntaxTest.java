package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class BuiltinTypeSyntaxTest {

  @Test
  public void test() {
    BuiltinTypeSyntax t = ParserTest.parse(IrGrammarRuleKeys.BUILTIN_TYPE, " i32 ");

    assertThat(t.toString()).isEqualTo("i32");
    assertThat(t.toFullString()).isEqualTo(" i32 ");

    t = ParserTest.parse(IrGrammarRuleKeys.BUILTIN_TYPE, "i64");
    assertThat(t.toString()).isEqualTo("i64");
  }

}
