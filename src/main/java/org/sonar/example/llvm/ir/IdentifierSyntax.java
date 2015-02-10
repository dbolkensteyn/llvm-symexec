package org.sonar.example.llvm.ir;

import org.sonar.example.sslr.SyntaxToken;

public class IdentifierSyntax extends SyntaxNode {

  private final SyntaxToken token;

  public IdentifierSyntax(SyntaxToken token) {
    this.token = token;
  }

  public String name() {
    return token.value();
  }

}
