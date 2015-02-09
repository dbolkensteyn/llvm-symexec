package org.sonar.example.llvm.ir;

import com.google.common.base.Charsets;
import org.sonar.example.sslr.Parser;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

public class ParserTest {

  public static <T> T parse(GrammarRuleKey ruleKey, String input) {
    return new Parser<T>(
      Charsets.UTF_8,
      LexerlessGrammarBuilder.create(),
      IrGrammar.class,
      new IrSyntaxFactory(),
      ruleKey).parse(input);
  }

}
