package org.sonar.example.llvm.ir;

import com.sonar.sslr.api.typed.GrammarBuilder;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.internal.vm.SequenceExpression;

public class IrGrammar {

  public enum IrGrammarRuleKeys implements GrammarRuleKey {
    IDENTIFIER,
    NULL_LITERAL,
    INTEGER_LITERAL,
    EXPRESSION,

    BUILTIN_TYPE,
    CUSTOM_TYPE,
    POINTER_TYPE,
    TYPE,

    ALLOCA_INSTRUCTION,
    STORE_INSTRUCTION,
    LOAD_INSTRUCTION,
    RET_INSTRUCTION,
    GEP_INSTRUCTION,
    INSTRUCTION,

    FUNCTION_DEFINITION,

    LEXER_IDENTIFIER,
    LEXER_INTEGER,
    LEXER_CUSTOM_TYPE,
    LEXER_NULL,
    LEXER_I32,
    LEXER_I64,
    LEXER_STAR,
    LEXER_EQUAL,
    LEXER_COMMA,
    LEXER_ALLOCA,
    LEXER_ALIGN,
    LEXER_STORE,
    LEXER_LOAD,
    LEXER_RET,
    LEXER_VOID,
    LEXER_GET_ELEMENT_PTR,
    LEXER_INBOUNDS,
    LEXER_DEFINE,
    LEXER_PAREN_L,
    LEXER_PAREN_R,
    LEXER_BRACE_L,
    LEXER_BRACE_R,
    LEXER_HASH_ZERO;

    public static LexerlessGrammarBuilder createGrammarBuilder() {
      LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

      b.rule(IrGrammarRuleKeys.LEXER_IDENTIFIER).is(wrapWithOptionalSpaces(b, b.regexp("[%@][-a-zA-Z$._0-9]++")));
      b.rule(IrGrammarRuleKeys.LEXER_INTEGER).is(wrapWithOptionalSpaces(b, b.regexp("[0-9]++")));
      b.rule(IrGrammarRuleKeys.LEXER_CUSTOM_TYPE).is(wrapWithOptionalSpaces(b, b.regexp("%[-a-zA-Z$._0-9]++")));
      b.rule(IrGrammarRuleKeys.LEXER_NULL).is(wrapWithOptionalSpaces(b, "null"));
      b.rule(IrGrammarRuleKeys.LEXER_I32).is(wrapWithOptionalSpaces(b, "i32"));
      b.rule(IrGrammarRuleKeys.LEXER_I64).is(wrapWithOptionalSpaces(b, "i64"));
      b.rule(IrGrammarRuleKeys.LEXER_STAR).is(wrapWithOptionalSpaces(b, "*"));
      b.rule(IrGrammarRuleKeys.LEXER_EQUAL).is(wrapWithOptionalSpaces(b, "="));
      b.rule(IrGrammarRuleKeys.LEXER_COMMA).is(wrapWithOptionalSpaces(b, ","));
      b.rule(IrGrammarRuleKeys.LEXER_ALLOCA).is(wrapWithOptionalSpaces(b, "alloca"));
      b.rule(IrGrammarRuleKeys.LEXER_ALIGN).is(wrapWithOptionalSpaces(b, "align"));
      b.rule(IrGrammarRuleKeys.LEXER_STORE).is(wrapWithOptionalSpaces(b, "store"));
      b.rule(IrGrammarRuleKeys.LEXER_LOAD).is(wrapWithOptionalSpaces(b, "load"));
      b.rule(IrGrammarRuleKeys.LEXER_RET).is(wrapWithOptionalSpaces(b, "ret"));
      b.rule(IrGrammarRuleKeys.LEXER_VOID).is(wrapWithOptionalSpaces(b, "void"));
      b.rule(IrGrammarRuleKeys.LEXER_GET_ELEMENT_PTR).is(wrapWithOptionalSpaces(b, "getelementptr"));
      b.rule(IrGrammarRuleKeys.LEXER_INBOUNDS).is(wrapWithOptionalSpaces(b, "inbounds"));
      b.rule(IrGrammarRuleKeys.LEXER_DEFINE).is(wrapWithOptionalSpaces(b, "define"));
      b.rule(IrGrammarRuleKeys.LEXER_PAREN_L).is(wrapWithOptionalSpaces(b, "("));
      b.rule(IrGrammarRuleKeys.LEXER_PAREN_R).is(wrapWithOptionalSpaces(b, ")"));
      b.rule(IrGrammarRuleKeys.LEXER_BRACE_L).is(wrapWithOptionalSpaces(b, "{"));
      b.rule(IrGrammarRuleKeys.LEXER_BRACE_R).is(wrapWithOptionalSpaces(b, "}"));
      b.rule(IrGrammarRuleKeys.LEXER_HASH_ZERO).is(wrapWithOptionalSpaces(b, "#0"));

      return b;
    }

    private static Object wrapWithOptionalSpaces(LexerlessGrammarBuilder b, Object value) {
      return b.sequence(b.regexp("\\s*+"), value, b.regexp("\\s*+"));
    }

  }

  private final GrammarBuilder<SyntaxToken> b;
  private final IrSyntaxFactory f;

  public IrGrammar(GrammarBuilder b, IrSyntaxFactory f) {
    this.b = b;
    this.f = f;
  }

  public IdentifierSyntax IDENTIFIER() {
    return b.<IdentifierSyntax>nonterminal(IrGrammarRuleKeys.IDENTIFIER)
      .is(f.identifier(b.token(IrGrammarRuleKeys.LEXER_IDENTIFIER)));
  }

  public NullLiteralSyntax NULL_LITERAL() {
    return b.<NullLiteralSyntax>nonterminal(IrGrammarRuleKeys.NULL_LITERAL)
      .is(f.nullLiteral(b.token(IrGrammarRuleKeys.LEXER_NULL)));
  }

  public IntegerLiteralSyntax INTEGER_LITERAL() {
    return b.<IntegerLiteralSyntax>nonterminal(IrGrammarRuleKeys.INTEGER_LITERAL)
      .is(f.integerLiteral(b.token(IrGrammarRuleKeys.LEXER_INTEGER)));
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
            b.token(IrGrammarRuleKeys.LEXER_I32),
            b.token(IrGrammarRuleKeys.LEXER_I64))));
  }

  public CustomTypeSyntax CUSTOM_TYPE() {
    return b.<CustomTypeSyntax>nonterminal(IrGrammarRuleKeys.CUSTOM_TYPE)
      .is(f.customType(b.token(IrGrammarRuleKeys.LEXER_CUSTOM_TYPE)));
  }

  public PointerTypeSyntax POINTER_TYPE() {
    return b.<PointerTypeSyntax>nonterminal(IrGrammarRuleKeys.POINTER_TYPE)
      .is(
        f.pointerType(
          b.firstOf(
            BUILTIN_TYPE(),
            CUSTOM_TYPE()),
          b.oneOrMore(b.token(IrGrammarRuleKeys.LEXER_STAR))));
  }

  public TypeSyntax TYPE() {
    return b.<TypeSyntax>nonterminal(IrGrammarRuleKeys.TYPE)
      .is(
        b.firstOf(
          POINTER_TYPE(),
          BUILTIN_TYPE(),
          CUSTOM_TYPE()));
  }

  public AllocaInstructionSyntax ALLOCA_INSTRUCTION() {
    return b.<AllocaInstructionSyntax>nonterminal(IrGrammarRuleKeys.ALLOCA_INSTRUCTION)
      .is(f.allocaInstruction(IDENTIFIER(), b.token(IrGrammarRuleKeys.LEXER_EQUAL), b.token(IrGrammarRuleKeys.LEXER_ALLOCA), TYPE(), b.token(IrGrammarRuleKeys.LEXER_COMMA), b.token(IrGrammarRuleKeys.LEXER_ALIGN), b.token(IrGrammarRuleKeys.LEXER_INTEGER)));
  }

  public StoreInstructionSyntax STORE_INSTRUCTION() {
    return b.<StoreInstructionSyntax>nonterminal(IrGrammarRuleKeys.STORE_INSTRUCTION)
      .is(
        f.storeInstruction(
          b.token(IrGrammarRuleKeys.LEXER_STORE),
          TYPE(), EXPRESSION(),
          b.token(IrGrammarRuleKeys.LEXER_COMMA), TYPE(), IDENTIFIER(),
          b.token(IrGrammarRuleKeys.LEXER_COMMA), b.token(IrGrammarRuleKeys.LEXER_ALIGN), b.token(IrGrammarRuleKeys.LEXER_INTEGER)));
  }

  public LoadInstructionSyntax LOAD_INSTRUCTION() {
    return b.<LoadInstructionSyntax>nonterminal(IrGrammarRuleKeys.LOAD_INSTRUCTION)
      .is(
        f.loadInstruction(
          IDENTIFIER(), b.token(IrGrammarRuleKeys.LEXER_EQUAL), b.token(IrGrammarRuleKeys.LEXER_LOAD),
          TYPE(), IDENTIFIER(),
          b.token(IrGrammarRuleKeys.LEXER_COMMA), b.token(IrGrammarRuleKeys.LEXER_ALIGN), b.token(IrGrammarRuleKeys.LEXER_INTEGER)));
  }

  public RetInstructionSyntax RET_INSTRUCTION() {
    return b.<RetInstructionSyntax>nonterminal(IrGrammarRuleKeys.RET_INSTRUCTION)
      .is(f.retInstruction(b.token(IrGrammarRuleKeys.LEXER_RET), b.token(IrGrammarRuleKeys.LEXER_VOID)));
  }

  public GepInstructionSyntax GEP_INSTRUCTION() {
    return b.<GepInstructionSyntax>nonterminal(IrGrammarRuleKeys.GEP_INSTRUCTION)
      .is(
        f.gepInstruction(
          IDENTIFIER(), b.token(IrGrammarRuleKeys.LEXER_EQUAL),
          b.token(IrGrammarRuleKeys.LEXER_GET_ELEMENT_PTR), b.token(IrGrammarRuleKeys.LEXER_INBOUNDS),
          TYPE(), IDENTIFIER(),
          b.oneOrMore(f.newTuple1(b.token(IrGrammarRuleKeys.LEXER_COMMA), TYPE(), INTEGER_LITERAL()))));
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
          b.token(IrGrammarRuleKeys.LEXER_DEFINE), b.token(IrGrammarRuleKeys.LEXER_VOID), IDENTIFIER(),
          b.token(IrGrammarRuleKeys.LEXER_PAREN_L), TYPE(), IDENTIFIER(), b.token(IrGrammarRuleKeys.LEXER_PAREN_R),
          b.token(IrGrammarRuleKeys.LEXER_HASH_ZERO),
          b.token(IrGrammarRuleKeys.LEXER_BRACE_L), b.oneOrMore(INSTRUCTION()), b.token(IrGrammarRuleKeys.LEXER_BRACE_R)));
  }

}
