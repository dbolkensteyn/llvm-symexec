package org.sonar.example.llvm.ir;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class NullLiteralSyntax extends ExpressionSyntax {

  private final SyntaxToken token;

  public NullLiteralSyntax(SyntaxToken token) {
    this.token = token;
  }

  @Override
  public List<SyntaxNode> children() {
    return ImmutableList.<SyntaxNode>of(token);
  }

}
