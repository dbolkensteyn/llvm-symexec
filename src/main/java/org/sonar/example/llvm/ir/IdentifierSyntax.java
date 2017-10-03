package org.sonar.example.llvm.ir;

import java.util.Collections;
import java.util.List;

public class IdentifierSyntax extends ExpressionSyntax {

  private final SyntaxToken token;

  public IdentifierSyntax(SyntaxToken token) {
    this.token = token;
  }

  @Override
  public List<SyntaxNode> children() {
    return Collections.singletonList(token);
  }

  public String name() {
    return token.toString();
  }

}
