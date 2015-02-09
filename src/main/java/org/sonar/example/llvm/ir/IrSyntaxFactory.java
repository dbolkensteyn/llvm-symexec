package org.sonar.example.llvm.ir;

import org.sonar.example.sslr.SyntaxToken;

public class IrSyntaxFactory {

  public RegisterSyntax register(SyntaxToken token) {
    return new RegisterSyntax(token);
  }

}
