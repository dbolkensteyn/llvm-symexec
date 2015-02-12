package org.sonar.example.llvm.ir;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;

public class SymbolicExecutorTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void should_not_fail_on_basic_function() {
    FunctionDefinitionSyntax f = ParserTest.parse(
      IrGrammarRuleKeys.FUNCTION_DEFINITION,
      "define void @f1(i32* %p) #0 {",
      "  ret void",
      "}");

    new SymbolicExecutor().evaluate(f);
  }

  @Test
  public void should_detect_loading_from_null() {
    thrown.expectMessage("NPE: %5 = load i32* %4, align 4");

    FunctionDefinitionSyntax f = ParserTest.parse(
      IrGrammarRuleKeys.FUNCTION_DEFINITION,
      "define void @f1(i32* %p) #0 {",
      "  %1 = alloca i32*, align 8",
      "  store i32* %p, i32** %1, align 8",
      "  %2 = load i32** %1, align 8",
      "  %3 = load i32* %2, align 4",
      "  store i32* null, i32** %1, align 8",
      "  %4 = load i32** %1, align 8",
      "  %5 = load i32* %4, align 4",
      "  ret void",
      "}");

    new SymbolicExecutor().evaluate(f);
  }

  @Test
  public void should_detect_storing_to_null() {
    thrown.expectMessage("NPE: store i32* null, i32** %2, align 8");

    FunctionDefinitionSyntax f = ParserTest.parse(
      IrGrammarRuleKeys.FUNCTION_DEFINITION,
      "define void @f1(i32* %p) #0 {",
      "  %1 = alloca i32*, align 8",
      "  store i32* null, i32** %1, align 8",
      "  %2 = load i32** %1, align 8",
      "  store i32* null, i32** %2, align 8",
      "  ret void",
      "}");

    new SymbolicExecutor().evaluate(f);
  }

  @Test
  public void should_detect_stores_of_uninitialized_values() {
    thrown.expectMessage("Cannot read an uninitialized value \"%2\"! store i32* %2, i32** %1, align 8");

    FunctionDefinitionSyntax f = ParserTest.parse(
      IrGrammarRuleKeys.FUNCTION_DEFINITION,
      "define void @f1(i32* %p) #0 {",
      "  %1 = alloca i32*, align 8",
      "  store i32* %2, i32** %1, align 8",
      "  ret void",
      "}");

    new SymbolicExecutor().evaluate(f);
  }

}
