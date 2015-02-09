package org.sonar.example.sslr;


public class SyntaxToken {

  private final String value;
  private final int startOffset;
  private final int endOffset;
  private final String fullValue;
  private final int fullStartOffset;
  private final int fullEndOffset;

  public SyntaxToken(String value, int startOffset, int endOffset, String fullValue, int fullStartOffset, int fullEndOffset) {
    this.value = value;
    this.startOffset = startOffset;
    this.endOffset = endOffset;
    this.fullValue = fullValue;
    this.fullStartOffset = fullStartOffset;
    this.fullEndOffset = fullEndOffset;
  }

  public String value() {
    return value;
  }

  public int startOffset() {
    return startOffset;
  }

  public int endOffset() {
    return endOffset;
  }

  public String fullValue() {
    return fullValue;
  }

  public int fullStartOffset() {
    return fullStartOffset;
  }

  public int fullEndOffset() {
    return fullEndOffset;
  }

}
