package org.sonar.example.llvm.ir;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class BuiltinTypeSyntax extends TypeSyntax {

  private static final Map<String, Integer> TYPE_SIZES = ImmutableMap.of(
    "i32", 4,
    "i64", 8);
  private final SyntaxToken token;

  public BuiltinTypeSyntax(SyntaxToken token) {
    this.token = token;
    Preconditions.checkArgument(TYPE_SIZES.containsKey(name()), "Unsupported built-in type: " + name());
  }

  @Override
  public List<SyntaxNode> children() {
    return ImmutableList.<SyntaxNode>of(token);
  }

  public String name() {
    return token.toString();
  }

  @Override
  public int size() {
    return TYPE_SIZES.get(name());
  }

}
