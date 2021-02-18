package md.faf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FiniteAutomata {

  private static final char STATE_START = 'S';
  private static final char STATE_FINISH = '$';

  private final State start;

  private FiniteAutomata(State start) {
    this.start = start;
  }

  public static FiniteAutomata buildFromGrammar(Grammar grammar) {
    Map<Character, State> states = new HashMap<>();

    grammar.getRules().stream()
        .map(TransitionRule::getFrom)
        .map(State::new)
        .forEach(state -> states.put(state.getName(), state));

    for (TransitionRule rule : grammar.getRules()) {
      State currentState = states.get(rule.getFrom());
      String transition = rule.getTo();


      char using = transition.charAt(0);

      State nextState =
          (transition.length() == 1)
            ? State.FINAL
            : states.get(transition.charAt(1));

      currentState.addTransition(using, nextState);
    }

    State startState = states.get(STATE_START);
    return new FiniteAutomata(startState);
  }

  public boolean test(String input) {
    State currentState = start;
    int idx = 0;

    while (idx < input.length()) {
      Optional<State> stateOptional =
          currentState.getNextStateFor(input.charAt(idx));
      if (stateOptional.isEmpty()) {
        break;
      }

      currentState = stateOptional.get();
      idx++;
    }

    return currentState.equals(State.FINAL) && idx == input.length();
  }

  static class State {

    private final char name;
    private final Map<Character, State> transitions;

    State(char name) {
      this.name = name;
      this.transitions = new HashMap<>();
    }

    void addTransition(char using, State next) {
      transitions.put(using, next);
    }

    static final State FINAL = new State(STATE_FINISH);

    public char getName() {
      return name;
    }

    Optional<State> getNextStateFor(char ch) {
      return Optional.ofNullable(transitions.get(ch));
    }
  }
}
