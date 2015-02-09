package org.sonar.example.sslr;

import org.junit.Test;

import java.io.File;
import java.net.URI;

import static org.fest.assertions.Assertions.assertThat;

public class InputTest {

  @Test
  public void input() {
    char[] input = new char[0];
    assertThat(new Input(input).input()).isSameAs(input);
  }

  @Test
  public void uri() {
    URI uri = new File("tests://something").toURI();
    assertThat(new Input("".toCharArray(), uri).uri()).isSameAs(uri);
  }

  @Test
  public void substring() {
    Input input = new Input("abc".toCharArray());
    assertThat(input.substring(0, 3)).isEqualTo("abc");
    assertThat(input.substring(0, 2)).isEqualTo("ab");
    assertThat(input.substring(0, 1)).isEqualTo("a");
    assertThat(input.substring(0, 0)).isEqualTo("");
    assertThat(input.substring(1, 3)).isEqualTo("bc");
    assertThat(input.substring(2, 3)).isEqualTo("c");
    assertThat(input.substring(3, 3)).isEqualTo("");
  }

  @Test
  public void lineAndColumnAt() {
    assertLineAndColumn(
      "", 0,
      1, 1);

    assertLineAndColumn(
      "abc", 0,
      1, 1);

    assertLineAndColumn(
      "abc", 1,
      1, 2);

    assertLineAndColumn(
      "abc", 2,
      1, 3);

    assertLineAndColumn(
      "\n_", 1,
      2, 1);

    assertLineAndColumn(
      "\r_", 1,
      2, 1);

    assertLineAndColumn(
      "\r\n_", 2,
      2, 1);

    assertLineAndColumn(
      "\r", 1,
      2, 1);
  }

  private static void assertLineAndColumn(String string, int index, int expectedLine, int expectedColumn) {
    int[] location = new Input(string.toCharArray()).lineAndColumnAt(index);
    assertThat(location[0]).isEqualTo(expectedLine);
    assertThat(location[1]).isEqualTo(expectedColumn);
  }

}
