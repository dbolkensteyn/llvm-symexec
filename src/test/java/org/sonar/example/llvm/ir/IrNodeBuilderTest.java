package org.sonar.example.llvm.ir;

import com.sonar.sslr.api.Rule;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.api.typed.Input;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.sonar.example.llvm.ir.IrGrammar.IrGrammarRuleKeys;
import org.sonar.sslr.grammar.GrammarRuleKey;

import static org.fest.assertions.Assertions.assertThat;

public class IrNodeBuilderTest {

  IrNodeBuilder builder = new IrNodeBuilder();

  @Test
  public void createNonTerminal() {
    SyntaxToken left = new SyntaxToken(" ");
    SyntaxToken value = new SyntaxToken("foo");
    SyntaxToken right = new SyntaxToken("   ");

    SyntaxToken result = (SyntaxToken)builder.createNonTerminal(Mockito.mock(GrammarRuleKey.class), Mockito.mock(Rule.class), Arrays.asList(left, value, right), 0, 0);
    assertThat(result.toString()).isEqualTo("foo");
    assertThat(result.toFullString()).isEqualTo(" foo   ");
  }

  @Test
  public void createTerminal() {
    Input input = Mockito.mock(Input.class);
    Mockito.when(input.substring(40, 2)).thenReturn("foo");

    SyntaxToken result = (SyntaxToken)builder.createTerminal(input, 40, 2, Collections.emptyList(), Mockito.mock(TokenType.class));
    assertThat(result.toString()).isEqualTo("foo");
  }

}
