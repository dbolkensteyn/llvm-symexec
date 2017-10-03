package org.sonar.example.llvm.ir;

import java.util.Arrays;
import java.util.List;

public class RetInstructionSyntax extends InstructionSyntax {

  private final List<SyntaxNode> children;

  public RetInstructionSyntax(SyntaxToken retToken, SyntaxToken voidToken) {
    this.children = Arrays.asList(retToken, voidToken);
  }

  @Override
  public List<SyntaxNode> children() {
    return children;
  }

}
