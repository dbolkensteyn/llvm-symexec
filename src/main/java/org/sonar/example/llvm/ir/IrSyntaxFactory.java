package org.sonar.example.llvm.ir;

import com.google.common.base.Preconditions;
import org.sonar.example.sslr.SyntaxToken;

import java.util.List;

public class IrSyntaxFactory {

  public IdentifierSyntax register(SyntaxToken token) {
    return new IdentifierSyntax(token);
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

    return new AllocaInstructionSyntax(result, type);
  }

}
