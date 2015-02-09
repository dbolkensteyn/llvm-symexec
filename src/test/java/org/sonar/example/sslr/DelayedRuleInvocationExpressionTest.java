package org.sonar.example.sslr;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.sonar.example.sslr.Parser.GrammarBuilderInterceptor;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.internal.grammar.MutableParsingRule;
import org.sonar.sslr.internal.vm.CompilationHandler;
import org.sonar.sslr.internal.vm.ParsingExpression;

import java.lang.reflect.Method;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
public class DelayedRuleInvocationExpressionTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void should_compile_rule_keys() {
    LexerlessGrammarBuilder b = spy(LexerlessGrammarBuilder.create());
    GrammarRuleKey ruleKey = mock(GrammarRuleKey.class);

    DelayedRuleInvocationExpression expression = new DelayedRuleInvocationExpression(b, ruleKey);

    CompilationHandler compiler = mock(CompilationHandler.class);
    expression.compile(compiler);

    verify(b).rule(ruleKey);

    ArgumentCaptor<ParsingExpression> ruleExpression = ArgumentCaptor.forClass(ParsingExpression.class);
    verify(compiler).compile(ruleExpression.capture());
    assertThat(ruleExpression.getAllValues()).hasSize(1);
    assertThat(((MutableParsingRule) ruleExpression.getValue()).getRuleKey()).isSameAs(ruleKey);
  }

  @Test
  public void should_compile_methods() throws Exception {
    LexerlessGrammarBuilder b = spy(LexerlessGrammarBuilder.create());
    GrammarRuleKey ruleKey = mock(GrammarRuleKey.class);
    Method method = DelayedRuleInvocationExpressionTest.class.getDeclaredMethod("FOO");
    GrammarBuilderInterceptor grammarBuilderInterceptor = mock(GrammarBuilderInterceptor.class);
    when(grammarBuilderInterceptor.ruleKeyForMethod(method)).thenReturn(ruleKey);

    DelayedRuleInvocationExpression expression = new DelayedRuleInvocationExpression(b, grammarBuilderInterceptor, method);

    CompilationHandler compiler = mock(CompilationHandler.class);
    expression.compile(compiler);

    verify(b).rule(ruleKey);

    ArgumentCaptor<ParsingExpression> ruleExpression = ArgumentCaptor.forClass(ParsingExpression.class);
    verify(compiler).compile(ruleExpression.capture());
    assertThat(ruleExpression.getAllValues()).hasSize(1);
    assertThat(((MutableParsingRule) ruleExpression.getValue()).getRuleKey()).isSameAs(ruleKey);
  }

  @Test
  public void should_fail_when_method_is_not_mapped() throws Exception {
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Cannot find the rule key corresponding to the invoked method: FOO()");

    Method method = DelayedRuleInvocationExpressionTest.class.getDeclaredMethod("FOO");
    new DelayedRuleInvocationExpression(LexerlessGrammarBuilder.create(), mock(GrammarBuilderInterceptor.class), method).compile(mock(CompilationHandler.class));
  }

  @Test
  public void test_toString() throws Exception {
    GrammarRuleKey ruleKey = mock(GrammarRuleKey.class);
    when(ruleKey.toString()).thenReturn("foo");
    assertThat(new DelayedRuleInvocationExpression(mock(LexerlessGrammarBuilder.class), ruleKey).toString()).isEqualTo("foo");

    Method method = DelayedRuleInvocationExpressionTest.class.getDeclaredMethod("FOO");
    assertThat(new DelayedRuleInvocationExpression(mock(LexerlessGrammarBuilder.class), mock(GrammarBuilderInterceptor.class), method).toString()).isEqualTo("FOO()");
  }

  // Called by reflection
  public void FOO() {
  }

}
