package org.sonar.example.llvm.ir;

import com.google.common.collect.ImmutableList;

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
    return ImmutableList.of(type, starToken);
  }

  public TypeSyntax type() {
    return type;
  }

  @Override
  public int size() {
    // FIXME? Should be more clever?
    // See http://stackoverflow.com/questions/1473935/can-the-size-of-pointers-vary-depending-on-whats-pointed-to
    return 4;
  }

}
