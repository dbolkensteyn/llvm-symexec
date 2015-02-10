package org.sonar.example.llvm.ir;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class BuiltinTypeSyntax extends TypeSyntax {

  private final SyntaxToken token;

  public BuiltinTypeSyntax(SyntaxToken token) {
    Preconditions.checkArgument("i32".equals(token.toString()), "Only built-in type i32 is supported, not: " + token.toString());
    this.token = token;
  }

  @Override
  public List<SyntaxNode> children() {
    return ImmutableList.<SyntaxNode>of(token);
  }

  public String name() {
    return token.toString();
  }

  @Override
  public int size() {
    return 4;
  }

}
