package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class TypeSyntaxTest {

  @Test
  public void test() {
    TypeSyntax t = ParserTest.parse(IrGrammarRuleKeys.TYPE, "i32");
    assertThat(t).isInstanceOf(BuiltinTypeSyntax.class);

    t = ParserTest.parse(IrGrammarRuleKeys.TYPE, "i32*");
    assertThat(t).isInstanceOf(PointerTypeSyntax.class);

    t = ParserTest.parse(IrGrammarRuleKeys.TYPE, "%struct.my_struct_t");
    assertThat(t).isInstanceOf(CustomTypeSyntax.class);
  }

}
