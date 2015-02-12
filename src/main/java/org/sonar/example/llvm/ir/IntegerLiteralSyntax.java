package org.sonar.example.llvm.ir;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class IntegerLiteralSyntax extends ExpressionSyntax {

  private final SyntaxToken token;

  public IntegerLiteralSyntax(SyntaxToken token) {
    this.token = token;
  }

  @Override
  public List<SyntaxNode> children() {
    return ImmutableList.<SyntaxNode>of(token);
  }

  public int value() {
    return Integer.parseInt(token.toString());
  }

}
