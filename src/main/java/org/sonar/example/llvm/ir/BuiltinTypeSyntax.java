package org.sonar.example.llvm.ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuiltinTypeSyntax extends TypeSyntax {

  private static final Set<String> SUPPORTED_TYPES = new HashSet<>(Arrays.asList("i32", "i64"));
  private final SyntaxToken token;

  public BuiltinTypeSyntax(SyntaxToken token) {
    this.token = token;
    if (!SUPPORTED_TYPES.contains(token.toString())) {
      throw new IllegalArgumentException("Unsupported built-in type: " + token.toString());
    }
  }

  @Override
  public List<SyntaxNode> children() {
    return Collections.singletonList(token);
  }

}
