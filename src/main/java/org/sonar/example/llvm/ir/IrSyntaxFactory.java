package org.sonar.example.llvm.ir;

import com.google.common.base.Preconditions;

import java.util.List;

public class IrSyntaxFactory {

  public IdentifierSyntax identifier(SyntaxToken token) {
    return new IdentifierSyntax(token);
  }

  public NullLiteralSyntax nullLiteral(SyntaxToken token) {
    return new NullLiteralSyntax(token);
  }

  public BuiltinTypeSyntax builtinType(SyntaxToken token) {
    return new BuiltinTypeSyntax(token);
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

}
