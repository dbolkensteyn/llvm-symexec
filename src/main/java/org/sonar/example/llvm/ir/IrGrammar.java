package org.sonar.example.llvm.ir;

import org.sonar.example.sslr.GrammarBuilder;
import org.sonar.sslr.grammar.GrammarRuleKey;

public class IrGrammar {

  public enum IrGrammarRuleKeys implements GrammarRuleKey {
    IDENTIFIER,
    TYPE,
    BUILTIN_TYPE,
    POINTER_TYPE,
    ALLOCA_INSTRUCTION;
  }

  private final GrammarBuilder b;
  private final IrSyntaxFactory f;

  public IrGrammar(GrammarBuilder b, IrSyntaxFactory f) {
    this.b = b;
    this.f = f;
  }

  public IdentifierSyntax IDENTIFIER() {
    return b.<IdentifierSyntax>nonterminal(IrGrammarRuleKeys.IDENTIFIER)
      .is(f.register(b.pattern("[%@][-a-zA-Z$._0-9]++")));
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

}
