package org.sonar.example.llvm.ir;

import com.sonar.sslr.api.typed.ActionParser;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.sonar.sslr.grammar.GrammarRuleKey;

public class ParserTest {

  public static <T> T parse(GrammarRuleKey ruleKey, String... lines) {
    return parse(ruleKey, String.join("\n", Arrays.asList(lines)));
  }

  public static <T> T parse(GrammarRuleKey ruleKey, String input) {
    return new ActionParser<T>(
      StandardCharsets.UTF_8,
      IrGrammar.IrGrammarRuleKeys.createGrammarBuilder(),
      IrGrammar.class,
      new IrSyntaxFactory(),
      new IrNodeBuilder(),
      ruleKey).parse(input);
  }

}
