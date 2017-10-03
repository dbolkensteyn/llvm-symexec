package org.sonar.example.llvm.ir;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class SyntaxToken extends SyntaxNode {

  private final String leftValue;
  private final String value;
  private final String rightValue;

  public SyntaxToken(String value) {
    this.leftValue = "";
    this.value = value;
    this.rightValue = "";
  }

  public SyntaxToken(String leftValue, String value, String rightValue) {
    this.leftValue = leftValue;
    this.value = value;
    this.rightValue = rightValue;
  }

  @Override
  public List<SyntaxNode> children() {
    return ImmutableList.<SyntaxNode>of(this);
  }

  public String value() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public String toFullString() {
    return leftValue + value + rightValue;
  }

  public String fullLeftValue() {
    return leftValue + value;
  }

  public String fullRightValue() {
    return value + rightValue;
  }

}
