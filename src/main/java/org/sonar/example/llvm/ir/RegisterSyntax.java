package org.sonar.example.llvm.ir;

import org.sonar.example.sslr.SyntaxToken;

public class RegisterSyntax extends SyntaxNode {

  private final SyntaxToken token;

  public RegisterSyntax(SyntaxToken token) {
    this.token = token;
  }

  public String name() {
    return token.value();
  }

}
