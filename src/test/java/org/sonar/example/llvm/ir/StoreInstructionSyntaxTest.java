package org.sonar.example.llvm.ir;

import org.junit.Test;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

import static org.fest.assertions.Assertions.assertThat;

public class StoreInstructionSyntaxTest {

  @Test
  public void test() {
    StoreInstructionSyntax t = ParserTest.parse(IrGrammarRuleKeys.STORE_INSTRUCTION, " store i32* %p, i32** %1, align 8 ");

    assertThat(t.toString()).isEqualTo("store i32* %p, i32** %1, align 8");
    assertThat(t.toFullString()).isEqualTo(" store i32* %p, i32** %1, align 8 ");

    assertThat(t.value().toString()).isEqualTo("%p");
    assertThat(t.pointer().toString()).isEqualTo("%1");

    t = ParserTest.parse(IrGrammarRuleKeys.STORE_INSTRUCTION, " store i32* null, i32** %1, align 8 ");
    assertThat(t.value().toString()).isEqualTo("null");
  }

}
