package org.sonar.example.llvm.ir;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import java.util.List;

public abstract class SyntaxNode {

  public abstract List<SyntaxNode> children();

  public String value() {
    StringBuilder sb = new StringBuilder();
    List<SyntaxToken> tokens = tokens();

    int i = 0;
    for (SyntaxToken token : tokens) {
      String value;
      if (tokens.size() == 1) {
        value = token.value();
      } else if (i == 0) {
        value = token.fullRightValue();
      } else if (i == tokens.size() - 1) {
        value = token.fullLeftValue();
      } else {
        value = token.fullValue();
      }

      sb.append(value);
      i++;
    }

    return sb.toString();
  }

  public String fullValue() {
    StringBuilder sb = new StringBuilder();
    for (SyntaxNode child : children()) {
      sb.append(child.fullValue());
    }

    return sb.toString();
  }

  public List<SyntaxToken> tokens() {
    ImmutableList.Builder<SyntaxToken> builder = ImmutableList.builder();
    tokens(builder, this);
    return builder.build();
  }

  private static void tokens(Builder<SyntaxToken> builder, SyntaxNode node) {
    for (SyntaxNode child : node.children()) {
      if (child instanceof SyntaxToken) {
        builder.add((SyntaxToken) child);
      } else {
        tokens(builder, child);
      }
    }
  }

}
