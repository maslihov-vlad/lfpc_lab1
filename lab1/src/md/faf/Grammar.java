package md.faf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Grammar {

  private final List<TransitionRule> rules;

  Grammar() {
    rules = new ArrayList<>();
  }

  Grammar(List<TransitionRule> rules) {
    this.rules = rules;
  }

  public static Grammar parseFile(String inputPath) throws IOException {
    List<String> lines = Files.readAllLines(Path.of(inputPath));

    List<TransitionRule> rules = new ArrayList<>();
    for (String line : lines) {
      TransitionRule rule = TransitionRule.parse(line);
      rules.add(rule);
    }

    return new Grammar(rules);
  }

  public void addRule(TransitionRule newRule) {
    rules.add(newRule);
  }

  public List<TransitionRule> getRules() {
    return rules;
  }

  public List<String> getRulesForCharacter(char ch) {

    List<String> answer = new ArrayList<>();
    for (TransitionRule rule : rules) {
      if (rule.getFrom() == ch) {
        answer.add(rule.getTo());
      }
    }
    return answer;
  }

  public boolean hasRulesForCharacter(char ch) {
    return !getRulesForCharacter(ch).isEmpty();
  }
}
