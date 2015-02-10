package org.sonar.example.llvm.ir;

import com.google.common.collect.ImmutableList;

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

    ImmutableList.Builder<SyntaxNode> childrenBuilder = ImmutableList.builder();

    childrenBuilder.add(
      defineToken, voidToken, identifier,
      openParenToken, paramType, param, closeParenToken,
      unnamedAddressToken);
    childrenBuilder.add(openBraceToken);
    childrenBuilder.addAll(instructions);
    childrenBuilder.add(closeBraceToken);
    this.children = childrenBuilder.build();

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
