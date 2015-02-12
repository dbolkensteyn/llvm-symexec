package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class PointerTypeSyntaxTest {

  @Test
  public void test() {
    PointerTypeSyntax t = ParserTest.parse(IrGrammarRuleKeys.POINTER_TYPE, " i32* ");
    assertThat(t.toString()).isEqualTo("i32*");
    assertThat(t.toFullString()).isEqualTo(" i32* ");
    assertThat(t.type()).isInstanceOf(BuiltinTypeSyntax.class);
    assertThat(t.type().toString()).isEqualTo("i32");

    t = ParserTest.parse(IrGrammarRuleKeys.POINTER_TYPE, " i32** ");
    assertThat(t.toString()).isEqualTo("i32**");
    assertThat(t.toFullString()).isEqualTo(" i32** ");
    assertThat(t.type()).isInstanceOf(PointerTypeSyntax.class);
    assertThat(t.type().toString()).isEqualTo("i32*");

    t = ParserTest.parse(IrGrammarRuleKeys.POINTER_TYPE, "%struct.my_struct_t*");
    assertThat(t.toString()).isEqualTo("%struct.my_struct_t*");
  }

}
