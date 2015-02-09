package org.sonar.example.sslr;

import com.sonar.sslr.api.AstNode;
import org.sonar.sslr.grammar.GrammarRuleKey;

import java.util.List;

public interface GrammarBuilder {

  <T> NonterminalBuilder<T> nonterminal();

  <T> NonterminalBuilder<T> nonterminal(GrammarRuleKey ruleKey);

  <T> T firstOf(T... methods);

  <T> Optional<T> optional(T method);

  <T> List<T> oneOrMore(T method);

  <T> Optional<List<T>> zeroOrMore(T method);

  AstNode invokeRule(GrammarRuleKey ruleKey);

  AstNode token(String value);

}
