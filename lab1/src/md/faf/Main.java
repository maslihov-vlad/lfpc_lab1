package md.faf;

import java.io.IOException;

public class Main {

    private static final String inputPath = "assets/input.txt";

    public static void main(String[] args) throws IOException {
        Grammar grammar = Grammar.parseFile(inputPath);
        FiniteAutomata automata = FiniteAutomata.buildFromGrammar(grammar);

        final String inputString = "baaccacb";
        boolean isValid = automata.test(inputString);

        System.out.println("Is valid = " + isValid);
    }
}
