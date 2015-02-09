package org.sonar.example.llvm.ir;

import com.google.common.base.Preconditions;
import org.sonar.example.sslr.SyntaxToken;

public class BuiltinTypeSyntax extends TypeSyntax {

  private final SyntaxToken token;

  public BuiltinTypeSyntax(SyntaxToken token) {
    Preconditions.checkArgument("i32".equals(token.value()), "Only built-in type i32 is supported, not: " + token.value());
    this.token = token;
  }

  public String name() {
    return token.value();
  }

  @Override
  public int size() {
    return 4;
  }

}
