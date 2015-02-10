package org.sonar.example.llvm.ir;

import org.sonar.example.sslr.GrammarBuilder;
import org.sonar.sslr.grammar.GrammarRuleKey;

public class IrGrammar {

  public enum IrGrammarRuleKeys implements GrammarRuleKey {
    IDENTIFIER,
    NULL_LITERAL,
    EXPRESSION,
    TYPE,
    BUILTIN_TYPE,
    POINTER_TYPE,
    ALLOCA_INSTRUCTION,
    STORE_INSTRUCTION,
    LOAD_INSTRUCTION;
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

  public ExpressionSyntax EXPRESSION() {
    return b.<ExpressionSyntax>nonterminal(IrGrammarRuleKeys.EXPRESSION)
      .is(
        b.firstOf(
          NULL_LITERAL(),
          IDENTIFIER()));
  }

  public TypeSyntax TYPE() {
    return b.<TypeSyntax>nonterminal(IrGrammarRuleKeys.TYPE)
      .is(
        b.firstOf(
          POINTER_TYPE(),
          BUILTIN_TYPE()));
  }

  public BuiltinTypeSyntax BUILTIN_TYPE() {
    return b.<BuiltinTypeSyntax>nonterminal(IrGrammarRuleKeys.BUILTIN_TYPE)
      .is(f.builtinType(b.token("i32")));
  }

  public PointerTypeSyntax POINTER_TYPE() {
    return b.<PointerTypeSyntax>nonterminal(IrGrammarRuleKeys.POINTER_TYPE)
      .is(f.pointerType(BUILTIN_TYPE(), b.oneOrMore(b.token("*"))));
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

}
