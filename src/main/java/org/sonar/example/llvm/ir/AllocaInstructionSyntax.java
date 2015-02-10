package org.sonar.example.llvm.ir;


public class AllocaInstructionSyntax extends SyntaxNode {

  private final IdentifierSyntax result;
  private final TypeSyntax type;

  public AllocaInstructionSyntax(IdentifierSyntax result, TypeSyntax type) {
    this.result = result;
    this.type = type;
  }

  public IdentifierSyntax result() {
    return result;
  }

  public TypeSyntax type() {
    return type;
  }

}
