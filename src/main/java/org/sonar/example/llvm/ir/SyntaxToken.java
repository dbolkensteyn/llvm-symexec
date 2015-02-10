package org.sonar.example.llvm.ir;

import com.google.common.collect.ImmutableList;
import org.sonar.example.sslr.Input;

import java.util.List;

public class SyntaxToken extends SyntaxNode {

  private final Input input;
  private final int startOffset;
  private final int endOffset;
  private final int fullStartOffset;
  private final int fullEndOffset;

  public SyntaxToken(Input input, int startOffset, int endOffset, int fullStartOffset, int fullEndOffset) {
    this.input = input;

    this.startOffset = startOffset;
    this.endOffset = endOffset;
    this.fullStartOffset = fullStartOffset;
    this.fullEndOffset = fullEndOffset;
  }

  @Override
  public List<SyntaxNode> children() {
    return ImmutableList.<SyntaxNode>of(this);
  }

  public Input input() {
    return input;
  }

  @Override
  public String value() {
    return input.substring(startOffset, endOffset);
  }

  public int startOffset() {
    return startOffset;
  }

  public int endOffset() {
    return endOffset;
  }

  @Override
  public String fullValue() {
    return input.substring(fullStartOffset, fullEndOffset);
  }

  public String fullLeftValue() {
    return input.substring(fullStartOffset, endOffset);
  }

  public String fullRightValue() {
    return input.substring(startOffset, fullEndOffset);
  }

  public int fullStartOffset() {
    return fullStartOffset;
  }

  public int fullEndOffset() {
    return fullEndOffset;
  }

}
