package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class PointerTypeSyntaxTest {

  @Test
  public void test() {
    PointerTypeSyntax t = ParserTest.parse(IrGrammarRuleKeys.POINTER_TYPE, " i32* ");
    assertThat(t.value()).isEqualTo("i32*");
    assertThat(t.fullValue()).isEqualTo(" i32* ");
    assertThat(t.type()).isInstanceOf(BuiltinTypeSyntax.class);
    assertThat(((BuiltinTypeSyntax) t.type()).name()).isEqualTo("i32");
    assertThat(t.size()).isEqualTo(4);

    t = ParserTest.parse(IrGrammarRuleKeys.POINTER_TYPE, " i32** ");
    assertThat(t.value()).isEqualTo("i32**");
    assertThat(t.fullValue()).isEqualTo(" i32** ");
    assertThat(t.type()).isInstanceOf(PointerTypeSyntax.class);
    assertThat(((PointerTypeSyntax) t.type()).type()).isInstanceOf(BuiltinTypeSyntax.class);
  }

}
