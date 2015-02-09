package org.sonar.example.sslr;

import com.google.common.base.Preconditions;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

/**
 * Loosely modeled after {@link com.google.common.base.Optional}.
 */
public abstract class Optional<T> {

  @SuppressWarnings("unchecked")
  public static <T> Optional<T> absent() {
    return (Optional<T>) Absent.INSTANCE;
  }

  public static <T> Optional<T> of(T reference) {
    return new Present<T>(Preconditions.checkNotNull(reference));
  }

  public abstract boolean isPresent();

  public abstract T get();

  public abstract T or(T defaultValue);

  @CheckForNull
  public abstract T orNull();

  private static class Present<T> extends Optional<T> {
    private final T reference;

    public Present(T reference) {
      this.reference = reference;
    }

    @Override
    public boolean isPresent() {
      return true;
    }

    @Override
    public T get() {
      return reference;
    }

    @Override
    public T or(Object defaultValue) {
      Preconditions.checkNotNull(defaultValue, "use orNull() instead of or(null)");
      return reference;
    }

    @CheckForNull
    @Override
    public T orNull() {
      return reference;
    }

    @Override
    public boolean equals(@Nullable Object object) {
      if (object instanceof Present) {
        Present other = (Present) object;
        return reference.equals(other.reference);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return 0x598df91c + reference.hashCode();
    }

    @Override
    public String toString() {
      return "Optional.of(" + reference + ")";
    }
  }

  private static class Absent extends Optional<Object> {
    private static final Absent INSTANCE = new Absent();

    @Override
    public boolean isPresent() {
      return false;
    }

    @Override
    public Object get() {
      throw new IllegalStateException("value is absent");
    }

    @Override
    public Object or(Object defaultValue) {
      return Preconditions.checkNotNull(defaultValue, "use orNull() instead of or(null)");
    }

    @CheckForNull
    @Override
    public Object orNull() {
      return null;
    }

    @Override
    public boolean equals(@Nullable Object object) {
      return object == this;
    }

    @Override
    public int hashCode() {
      return 0x598df91c;
    }

    @Override
    public String toString() {
      return "Optional.absent()";
    }
  }

}
