package org.sonar.example.llvm.ir;

import org.sonar.example.sslr.GrammarBuilder;
import org.sonar.sslr.grammar.GrammarRuleKey;

public class IrGrammar {

  public enum IrGrammarRuleKeys implements GrammarRuleKey {
    REGISTER,
    TYPE,
    BUILTIN_TYPE;
  }

  private final GrammarBuilder b;
  private final IrSyntaxFactory f;

  public IrGrammar(GrammarBuilder b, IrSyntaxFactory f) {
    this.b = b;
    this.f = f;
  }

  public RegisterSyntax REGISTER() {
    return b.<RegisterSyntax>nonterminal(IrGrammarRuleKeys.REGISTER)
      .is(f.register(b.pattern("%[0-9a-zA-Z.]++")));
  }

  public TypeSyntax TYPE() {
    return b.<TypeSyntax>nonterminal(IrGrammarRuleKeys.TYPE)
      .is(
        b.firstOf(
          BUILTIN_TYPE()));
  }

  public BuiltinTypeSyntax BUILTIN_TYPE() {
    return b.<BuiltinTypeSyntax>nonterminal(IrGrammarRuleKeys.BUILTIN_TYPE)
      .is(f.builtinType(b.token("i32")));
  }

}
