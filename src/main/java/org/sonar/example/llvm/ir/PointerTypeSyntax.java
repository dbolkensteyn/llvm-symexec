package org.sonar.example.llvm.ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PointerTypeSyntax extends TypeSyntax {

  private final TypeSyntax type;
  private final SyntaxToken starToken;

  public PointerTypeSyntax(TypeSyntax type, SyntaxToken starToken) {
    this.type = type;
    this.starToken = starToken;
  }

  @Override
  public List<SyntaxNode> children() {
    return Collections.unmodifiableList(Arrays.asList(type, starToken));
  }

  public TypeSyntax type() {
    return type;
  }

}
