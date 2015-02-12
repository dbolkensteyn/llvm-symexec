package org.sonar.example.llvm.ir;

import com.google.common.collect.ImmutableList;
import org.sonar.example.llvm.ir.IrSyntaxFactory.Tuple;

import java.util.List;

public class GepInstructionSyntax extends InstructionSyntax {

  private final List<SyntaxNode> children;
  private final IdentifierSyntax result;
  private final IdentifierSyntax pointer;
  private final List<IntegerLiteralSyntax> indexValues;

  public GepInstructionSyntax(
    IdentifierSyntax result, SyntaxToken equalToken,
    SyntaxToken gepToken, SyntaxToken inboundsToken,
    TypeSyntax pointerType, IdentifierSyntax pointer,
    List<Tuple<SyntaxToken, TypeSyntax, IntegerLiteralSyntax>> indexes) {

    ImmutableList.Builder<SyntaxNode> childrenBuilder = ImmutableList.builder();
    ImmutableList.Builder<IntegerLiteralSyntax> indexValuesBuilder = ImmutableList.builder();
    childrenBuilder.add(
      result, equalToken,
      gepToken, inboundsToken,
      pointerType, pointer);
    for (Tuple<SyntaxToken, TypeSyntax, IntegerLiteralSyntax> index : indexes) {
      childrenBuilder.add(index.first());
      childrenBuilder.add(index.second());
      childrenBuilder.add(index.third());

      indexValuesBuilder.add(index.third());
    }

    this.children = childrenBuilder.build();
    this.indexValues = indexValuesBuilder.build();

    this.result = result;
    this.pointer = pointer;
  }

  @Override
  public List<SyntaxNode> children() {
    return children;
  }

  public IdentifierSyntax result() {
    return result;
  }

  public IdentifierSyntax pointer() {
    return pointer;
  }

  public List<IntegerLiteralSyntax> indexValues() {
    return indexValues;
  }

}
