package org.sonar.example.llvm.ir;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import java.util.List;

public class IrSyntaxFactory {

  public IdentifierSyntax identifier(SyntaxToken token) {
    return new IdentifierSyntax(token);
  }

  public NullLiteralSyntax nullLiteral(SyntaxToken token) {
    return new NullLiteralSyntax(token);
  }

  public IntegerLiteralSyntax integerLiteral(SyntaxToken token) {
    return new IntegerLiteralSyntax(token);
  }

  public BuiltinTypeSyntax builtinType(SyntaxToken token) {
    return new BuiltinTypeSyntax(token);
  }

  public CustomTypeSyntax customType(SyntaxToken token) {
    return new CustomTypeSyntax(token);
  }

  public PointerTypeSyntax pointerType(TypeSyntax type, List<SyntaxToken> starTokens) {
    Preconditions.checkArgument(starTokens.size() >= 1);

    PointerTypeSyntax result = null;
    for (SyntaxToken starToken : starTokens) {
      result = new PointerTypeSyntax(result != null ? result : type, starToken);
    }
    return result;
  }

  public AllocaInstructionSyntax allocaInstruction(
    IdentifierSyntax result, SyntaxToken equalToken, SyntaxToken allocaToken, TypeSyntax type,
    SyntaxToken commaToken, SyntaxToken alignToken, SyntaxToken alignment) {

    return new AllocaInstructionSyntax(result, equalToken, allocaToken, type, commaToken, alignToken, alignment);
  }

  public StoreInstructionSyntax storeInstruction(
    SyntaxToken storeToken,
    TypeSyntax valueType, ExpressionSyntax value,
    SyntaxToken commaToken1, TypeSyntax pointerType, IdentifierSyntax pointer,
    SyntaxToken commaToken2, SyntaxToken alignToken, SyntaxToken alignment) {

    return new StoreInstructionSyntax(storeToken, valueType, value, commaToken1, pointerType, pointer, commaToken2, alignToken, alignment);
  }

  public LoadInstructionSyntax loadInstruction(
    IdentifierSyntax result, SyntaxToken equalToken, SyntaxToken loadToken,
    TypeSyntax pointerType, IdentifierSyntax pointer,
    SyntaxToken commaToken, SyntaxToken alignToken, SyntaxToken alignment) {

    return new LoadInstructionSyntax(
      result, equalToken, loadToken,
      pointerType, pointer,
      commaToken, alignToken, alignment);
  }

  public RetInstructionSyntax retInstruction(SyntaxToken retToken, SyntaxToken voidToken) {
    return new RetInstructionSyntax(retToken, voidToken);
  }

  public GepInstructionSyntax gepInstruction(
    IdentifierSyntax result, SyntaxToken equalToken,
    SyntaxToken gepToken, SyntaxToken inboundsToken,
    TypeSyntax pointerType, IdentifierSyntax pointer,
    List<Tuple<SyntaxToken, TypeSyntax, IntegerLiteralSyntax>> indexes) {

    return new GepInstructionSyntax(result, equalToken, gepToken, inboundsToken, pointerType, pointer, indexes);
  }

  public FunctionDefinitionSyntax functionDefinition(
    SyntaxToken defineToken, SyntaxToken voidToken, IdentifierSyntax identifier,
    SyntaxToken openParenToken, TypeSyntax paramType, IdentifierSyntax param, SyntaxToken closeParenToken,
    SyntaxToken unnamedAddressToken,
    SyntaxToken openBraceToken, List<InstructionSyntax> instructions, SyntaxToken closeBraceToken) {

    return new FunctionDefinitionSyntax(
      defineToken, voidToken, identifier,
      openParenToken, paramType, param, closeParenToken,
      unnamedAddressToken,
      openBraceToken, instructions, closeBraceToken);
  }

  public static class Tuple<T, U, V> {

    private final T first;
    private final U second;
    private final V third;

    public Tuple(T first, U second, @Nullable V third) {
      this.first = first;
      this.second = second;
      this.third = third;
    }

    public T first() {
      return first;
    }

    public U second() {
      return second;
    }

    public V third() {
      Preconditions.checkNotNull(third);
      return third;
    }

  }

  public <T, U, V> Tuple<T, U, V> newTuple1(T first, U second, V third) {
    return newTuple(first, second, third);
  }

  private <T, U, V> Tuple<T, U, V> newTuple(T first, U second, V third) {
    return new Tuple<T, U, V>(first, second, third);
  }

}
