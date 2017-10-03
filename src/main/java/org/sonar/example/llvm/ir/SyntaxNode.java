package org.sonar.example.llvm.ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SyntaxNode {

  public abstract List<SyntaxNode> children();

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    List<SyntaxToken> tokens = tokens();

    int i = 0;
    for (SyntaxToken token : tokens) {
      String value;
      if (tokens.size() == 1) {
        value = token.toString();
      } else if (i == 0) {
        value = token.fullRightValue();
      } else if (i == tokens.size() - 1) {
        value = token.fullLeftValue();
      } else {
        value = token.toFullString();
      }

      sb.append(value);
      i++;
    }

    return sb.toString();
  }

  public String toFullString() {
    StringBuilder sb = new StringBuilder();
    for (SyntaxNode child : children()) {
      sb.append(child.toFullString());
    }

    return sb.toString();
  }

  public List<SyntaxToken> tokens() {
    ArrayList<SyntaxToken> builder = new ArrayList<>();
    tokens(builder, this);
    return Collections.unmodifiableList(builder);
  }

  private static void tokens(ArrayList<SyntaxToken> builder, SyntaxNode node) {
    for (SyntaxNode child : node.children()) {
      if (child instanceof SyntaxToken) {
        builder.add((SyntaxToken) child);
      } else {
        tokens(builder, child);
      }
    }
  }

}
