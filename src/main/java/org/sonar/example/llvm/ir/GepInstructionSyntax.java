package org.sonar.example.llvm.ir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    ArrayList<SyntaxNode> children = new ArrayList<>();
    ArrayList<IntegerLiteralSyntax> indexValues = new ArrayList<>();
    children.addAll(Arrays.asList(
      result, equalToken,
      gepToken, inboundsToken,
      pointerType, pointer));
    for (Tuple<SyntaxToken, TypeSyntax, IntegerLiteralSyntax> index : indexes) {
      children.add(index.first());
      children.add(index.second());
      children.add(index.third());

      indexValues.add(index.third());
    }

    this.children = Collections.unmodifiableList(children);
    this.indexValues = Collections.unmodifiableList(indexValues);

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
