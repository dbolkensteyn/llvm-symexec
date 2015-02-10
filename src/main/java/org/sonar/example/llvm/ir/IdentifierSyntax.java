package org.sonar.example.llvm.ir;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class IdentifierSyntax extends SyntaxNode {

  private final SyntaxToken token;

  public IdentifierSyntax(SyntaxToken token) {
    this.token = token;
  }

  @Override
  public List<SyntaxNode> children() {
    return ImmutableList.<SyntaxNode>of(token);
  }

  public String name() {
    return token.value();
  }

}
