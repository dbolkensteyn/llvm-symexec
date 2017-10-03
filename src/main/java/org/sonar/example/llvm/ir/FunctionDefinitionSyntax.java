package org.sonar.example.llvm.ir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FunctionDefinitionSyntax extends SyntaxNode {

  private final List<SyntaxNode> children;
  private final IdentifierSyntax identifier;
  private final IdentifierSyntax param;
  private final List<InstructionSyntax> instructions;

  public FunctionDefinitionSyntax(
    SyntaxToken defineToken, SyntaxToken voidToken, IdentifierSyntax identifier,
    SyntaxToken openParenToken, TypeSyntax paramType, IdentifierSyntax param, SyntaxToken closeParenToken,
    SyntaxToken unnamedAddressToken,
    SyntaxToken openBraceToken, List<InstructionSyntax> instructions, SyntaxToken closeBraceToken) {

    ArrayList<SyntaxNode> children = new ArrayList<>();
    children.addAll(Arrays.asList(
      defineToken, voidToken, identifier,
      openParenToken, paramType, param, closeParenToken,
      unnamedAddressToken));
    children.add(openBraceToken);
    children.addAll(instructions);
    children.add(closeBraceToken);
    this.children = Collections.unmodifiableList(children);

    this.identifier = identifier;
    this.param = param;
    this.instructions = instructions;
  }

  @Override
  public List<SyntaxNode> children() {
    return children;
  }

  public IdentifierSyntax identifier() {
    return identifier;
  }

  public IdentifierSyntax param() {
    return param;
  }

  public List<InstructionSyntax> instructions() {
    return instructions;
  }

}
