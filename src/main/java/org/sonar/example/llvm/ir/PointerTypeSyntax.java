package org.sonar.example.llvm.ir;

import org.sonar.example.sslr.SyntaxToken;

public class PointerTypeSyntax extends TypeSyntax {

  private final TypeSyntax type;

  public PointerTypeSyntax(TypeSyntax type, SyntaxToken starToken) {
    this.type = type;
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
