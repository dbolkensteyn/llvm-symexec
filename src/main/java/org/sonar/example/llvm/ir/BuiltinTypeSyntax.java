package org.sonar.example.llvm.ir;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.List;
import java.util.Set;

public class BuiltinTypeSyntax extends TypeSyntax {

  private static final Set<String> SUPPORTED_TYPES = ImmutableSet.of("i32", "i64");
  private final SyntaxToken token;

  public BuiltinTypeSyntax(SyntaxToken token) {
    this.token = token;
    Preconditions.checkArgument(SUPPORTED_TYPES.contains(token.toString()), "Unsupported built-in type: " + token.toString());
  }

  @Override
  public List<SyntaxNode> children() {
    return ImmutableList.<SyntaxNode>of(token);
  }

}
