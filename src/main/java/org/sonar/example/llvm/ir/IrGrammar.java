package org.sonar.example.llvm.ir;

import org.sonar.example.sslr.GrammarBuilder;
import org.sonar.sslr.grammar.GrammarRuleKey;

public class IrGrammar {

  public enum IrGrammarRuleKeys implements GrammarRuleKey {
    IDENTIFIER,
    NULL_LITERAL,
    INTEGER_LITERAL,
    EXPRESSION,

    BUILTIN_TYPE,
    POINTER_TYPE,
    TYPE,

    ALLOCA_INSTRUCTION,
    STORE_INSTRUCTION,
    LOAD_INSTRUCTION,
    RET_INSTRUCTION,
    GEP_INSTRUCTION,
    INSTRUCTION,

    FUNCTION_DEFINITION;
  }

  private final GrammarBuilder b;
  private final IrSyntaxFactory f;

  public IrGrammar(GrammarBuilder b, IrSyntaxFactory f) {
    this.b = b;
    this.f = f;
  }

  public IdentifierSyntax IDENTIFIER() {
    return b.<IdentifierSyntax>nonterminal(IrGrammarRuleKeys.IDENTIFIER)
      .is(f.identifier(b.pattern("[%@][-a-zA-Z$._0-9]++")));
  }

  public NullLiteralSyntax NULL_LITERAL() {
    return b.<NullLiteralSyntax>nonterminal(IrGrammarRuleKeys.NULL_LITERAL)
      .is(f.nullLiteral(b.token("null")));
  }

  public IntegerLiteralSyntax INTEGER_LITERAL() {
    return b.<IntegerLiteralSyntax>nonterminal(IrGrammarRuleKeys.INTEGER_LITERAL)
      .is(f.integerLiteral(b.pattern("[0-9]++")));
  }

  public ExpressionSyntax EXPRESSION() {
    return b.<ExpressionSyntax>nonterminal(IrGrammarRuleKeys.EXPRESSION)
      .is(
        b.firstOf(
          NULL_LITERAL(),
          IDENTIFIER(),
          INTEGER_LITERAL()));
  }

  public BuiltinTypeSyntax BUILTIN_TYPE() {
    return b.<BuiltinTypeSyntax>nonterminal(IrGrammarRuleKeys.BUILTIN_TYPE)
      .is(
        f.builtinType(
          b.firstOf(
            b.token("i32"),
            b.token("i64"))));
  }

  public PointerTypeSyntax POINTER_TYPE() {
    return b.<PointerTypeSyntax>nonterminal(IrGrammarRuleKeys.POINTER_TYPE)
      .is(f.pointerType(BUILTIN_TYPE(), b.oneOrMore(b.token("*"))));
  }

  public TypeSyntax TYPE() {
    return b.<TypeSyntax>nonterminal(IrGrammarRuleKeys.TYPE)
      .is(
        b.firstOf(
          POINTER_TYPE(),
          BUILTIN_TYPE()));
  }

  public AllocaInstructionSyntax ALLOCA_INSTRUCTION() {
    return b.<AllocaInstructionSyntax>nonterminal(IrGrammarRuleKeys.ALLOCA_INSTRUCTION)
      .is(f.allocaInstruction(IDENTIFIER(), b.token("="), b.token("alloca"), TYPE(), b.token(","), b.token("align"), b.pattern("[0-9]++")));
  }

  public StoreInstructionSyntax STORE_INSTRUCTION() {
    return b.<StoreInstructionSyntax>nonterminal(IrGrammarRuleKeys.STORE_INSTRUCTION)
      .is(
        f.storeInstruction(
          b.token("store"),
          TYPE(), EXPRESSION(),
          b.token(","), TYPE(), IDENTIFIER(),
          b.token(","), b.token("align"), b.pattern("[0-9]++")));
  }

  public LoadInstructionSyntax LOAD_INSTRUCTION() {
    return b.<LoadInstructionSyntax>nonterminal(IrGrammarRuleKeys.LOAD_INSTRUCTION)
      .is(
        f.loadInstruction(
          IDENTIFIER(), b.token("="), b.token("load"),
          TYPE(), IDENTIFIER(),
          b.token(","), b.token("align"), b.pattern("[0-9]++")));
  }

  public RetInstructionSyntax RET_INSTRUCTION() {
    return b.<RetInstructionSyntax>nonterminal(IrGrammarRuleKeys.RET_INSTRUCTION)
      .is(f.retInstruction(b.token("ret"), b.token("void")));
  }

  public GepInstructionSyntax GEP_INSTRUCTION() {
    return b.<GepInstructionSyntax>nonterminal(IrGrammarRuleKeys.GEP_INSTRUCTION)
      .is(
        f.gepInstruction(
          IDENTIFIER(), b.token("="),
          b.token("getelementptr"), b.token("inbounds"),
          TYPE(), IDENTIFIER(),
          b.oneOrMore(f.newTuple1(b.token(","), TYPE(), INTEGER_LITERAL()))));
  }

  public InstructionSyntax INSTRUCTION() {
    return b.<InstructionSyntax>nonterminal(IrGrammarRuleKeys.INSTRUCTION)
      .is(
        b.firstOf(
          ALLOCA_INSTRUCTION(),
          STORE_INSTRUCTION(),
          LOAD_INSTRUCTION(),
          RET_INSTRUCTION(),
          GEP_INSTRUCTION()));
  }

  public FunctionDefinitionSyntax FUNCTION_DEFINITION() {
    return b.<FunctionDefinitionSyntax>nonterminal(IrGrammarRuleKeys.FUNCTION_DEFINITION)
      .is(
        f.functionDefinition(
          b.token("define"), b.token("void"), IDENTIFIER(),
          b.token("("), TYPE(), IDENTIFIER(), b.token(")"),
          b.token("#0"),
          b.token("{"), b.oneOrMore(INSTRUCTION()), b.token("}")));
  }

}
