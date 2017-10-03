package org.sonar.example.llvm.ir;

import java.util.Collections;
import java.util.List;

public class CustomTypeSyntax extends TypeSyntax {

  private final SyntaxToken token;

  public CustomTypeSyntax(SyntaxToken token) {
    this.token = token;
  }

  @Override
  public List<SyntaxNode> children() {
    return Collections.singletonList(token);
  }

}
