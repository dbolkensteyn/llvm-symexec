package org.sonar.example.sslr;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class OptionalTest {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  private final Optional<String> present = Optional.of("foo");
  private final Optional<String> absent = Optional.absent();

  @Test
  public void present() {
    assertThat(present.isPresent()).isTrue();

    assertThat(present.orNull()).isSameAs("foo");

    assertThat(present.or("bar")).isSameAs("foo");

    assertThat(present.get()).isSameAs("foo");

    assertThat(present.toString()).isEqualTo("Optional.of(foo)");

    assertThat(present.equals(present)).isTrue();
    assertThat(present.equals(Optional.of("foo"))).isTrue();
    assertThat(present.equals(Optional.of("bar"))).isFalse();
    assertThat(present.equals(absent)).isFalse();

    assertThat(present.hashCode()).isEqualTo(0x598df91c + "foo".hashCode());
  }

  @Test
  public void absent() {
    assertThat(absent.isPresent()).isFalse();

    assertThat(absent.orNull()).isNull();

    assertThat(absent.or("bar")).isSameAs("bar");

    assertThat(absent.toString()).isEqualTo("Optional.absent()");

    assertThat(absent.equals(present)).isFalse();
    assertThat(absent.equals(absent)).isTrue();

    assertThat(absent.hashCode()).isEqualTo(0x598df91c);

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("value is absent");
    absent.get();
  }

  @Test
  public void present_or_null() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("use orNull() instead of or(null)");
    present.or(null);
  }

  @Test
  public void absent_or_null() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("use orNull() instead of or(null)");
    absent.or(null);
  }

}
