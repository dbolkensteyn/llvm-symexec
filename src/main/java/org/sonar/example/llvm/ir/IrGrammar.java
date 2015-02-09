package org.sonar.example.llvm.ir;

import org.sonar.example.sslr.GrammarBuilder;
import org.sonar.sslr.grammar.GrammarRuleKey;

public class IrGrammar {

  public enum IrGrammarRuleKeys implements GrammarRuleKey {
    REGISTER;
  }

  private final GrammarBuilder b;
  private final IrSyntaxFactory f;

  public IrGrammar(GrammarBuilder b, IrSyntaxFactory f) {
    this.b = b;
    this.f = f;
  }

  public RegisterSyntax VARIABLE() {
    return b.<RegisterSyntax>nonterminal(IrGrammarRuleKeys.REGISTER)
      .is(f.register(b.pattern("%[0-9a-zA-Z.]++")));
  }

}
