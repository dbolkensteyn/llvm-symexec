package org.sonar.example.llvm.ir;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class RetInstructionSyntax extends InstructionSyntax {

  private final List<SyntaxNode> children;

  public RetInstructionSyntax(SyntaxToken retToken, SyntaxToken voidToken) {
    this.children = ImmutableList.<SyntaxNode>of(retToken, voidToken);
  }

  @Override
  public List<SyntaxNode> children() {
    return children;
  }

}
