package org.sonar.example.llvm.ir;

import java.util.Collections;
import java.util.List;

public class IntegerLiteralSyntax extends ExpressionSyntax {

  private final SyntaxToken token;

  public IntegerLiteralSyntax(SyntaxToken token) {
    this.token = token;
  }

  @Override
  public List<SyntaxNode> children() {
    return Collections.singletonList(token);
  }

  public int value() {
    return Integer.parseInt(token.toString());
  }

}
