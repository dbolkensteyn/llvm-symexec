package org.sonar.example.llvm.ir;

import java.util.Collections;
import java.util.List;

public class NullLiteralSyntax extends ExpressionSyntax {

  private final SyntaxToken token;

  public NullLiteralSyntax(SyntaxToken token) {
    this.token = token;
  }

  @Override
  public List<SyntaxNode> children() {
    return Collections.singletonList(token);
  }

}
