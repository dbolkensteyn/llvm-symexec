package org.sonar.example.llvm.ir;

import com.sonar.sslr.api.Rule;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.api.Trivia;
import com.sonar.sslr.api.typed.Input;
import com.sonar.sslr.api.typed.NodeBuilder;
import java.util.List;
import org.sonar.sslr.grammar.GrammarRuleKey;

public class IrNodeBuilder implements NodeBuilder {

  @Override
  public Object createNonTerminal(GrammarRuleKey ruleKey, Rule rule, List<Object> children, int startIndex, int endIndex) {
    if (children.size() == 3) {
      String leftValue = children.get(0).toString();
      String value = children.get(1).toString();
      String rightValue = children.get(2).toString();
      return new SyntaxToken(leftValue, value, rightValue);
    }

    throw new IllegalStateException();
  }

  @Override
  public Object createTerminal(Input input, int startIndex, int endIndex, List<Trivia> trivias, TokenType type) {
    String value = input.substring(startIndex, endIndex);
    return new SyntaxToken(value);
  }

}
