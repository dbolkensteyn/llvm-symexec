package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class FunctionDefinitionSyntaxTest {

  @Test
  public void test() {
    FunctionDefinitionSyntax t = ParserTest.parse(
      IrGrammarRuleKeys.FUNCTION_DEFINITION,
      " define void @f1(i32* %p) #0 {",
      "  %1 = alloca i32*, align 8",
      "  ret void",
      "} ");

    assertThat(t.toString()).startsWith("define");
    assertThat(t.toString()).endsWith("}");
    assertThat(t.toFullString()).startsWith(" define");
    assertThat(t.toFullString()).endsWith("} ");

    assertThat(t.identifier().toString()).isEqualTo("@f1");
    assertThat(t.param().toString()).isEqualTo("%p");

    assertThat(t.instructions()).hasSize(2);
    assertThat(t.instructions().get(0).toString()).isEqualTo("%1 = alloca i32*, align 8");
    assertThat(t.instructions().get(1).toString()).isEqualTo("ret void");
  }

}
