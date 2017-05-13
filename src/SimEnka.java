//
// Created by ozrenkosi on 23.03.2017..
//

import java.util.*;

public class SimEnka {

    private static List<String> inputStrings = new ArrayList<>();
    private static String initialState;
    private static Map<List<String>, List<String>> transitionFunction = new HashMap<>();

    private static List<String> currentInputString = new ArrayList<>();
    private static Set<String> currentStates = new TreeSet<>();

    public static void main(String[] args) {

        dataInput();

        for (int i = 0; i < inputStrings.size(); i++) {
            currentInputString.addAll(Arrays.asList(inputStrings.get(i).split(",")));
            runEnkaSimulation();
            currentInputString.clear();
        }

    }

    private static void dataInput() {
        String[] temporaryInput;

        Scanner reader = new Scanner(System.in);

        temporaryInput = reader.nextLine().split("\\|");
        inputStrings.addAll(Arrays.asList(temporaryInput));

        reader.nextLine();
        reader.nextLine();
        reader.nextLine();

        initialState = reader.nextLine();


        while (reader.hasNextLine()) {
            temporaryInput = reader.nextLine().split("->");

            if (temporaryInput[0].equals("")) {
                break;
            }

            List<String> key = new ArrayList<>();
            List<String> value = new ArrayList<>();

            key.addAll(Arrays.asList(temporaryInput[0].split(",")));
            value.addAll(Arrays.asList(temporaryInput[1].split(",")));

            transitionFunction.put(key, value);
        }
    }

    private static void runEnkaSimulation() {
        List<String> key = new ArrayList<>();
        Set<String> pastStates = new TreeSet<>();

        currentStates.add(initialState);

        checkForEpsilonTransitions();

        printStep("");

        pastStates.addAll(currentStates);

        for (int i = 0; i < currentInputString.size(); i++) {
            currentStates.clear();

            for (String state : pastStates) {
                key.addAll(Arrays.asList(state, currentInputString.get(i)));

                if (transitionExists(key)) {
                    currentStates.addAll(transitionFunction.get(key));
                }

                key.clear();
            }

            checkForEpsilonTransitions();

            printStep("|");

            pastStates.clear();
            pastStates.addAll(currentStates);
        }

        currentStates.clear();
        System.out.println();

    }

    private static void checkForEpsilonTransitions() {
        List<String> epsilonKey = new ArrayList<>();
        Set<String> currentEpsilonTransitions = new TreeSet<>();
        boolean transitionHappened = true;

        while (transitionHappened) {
            transitionHappened = false;

            for (String state: currentStates) {
                epsilonKey.addAll(Arrays.asList(state, "$"));

                if (transitionExists(epsilonKey) && newStateExists(currentEpsilonTransitions, epsilonKey)) {
                    currentEpsilonTransitions.addAll(transitionFunction.get(epsilonKey));
                    transitionHappened = true;
                }

                epsilonKey.clear();
            }

            currentStates.addAll(currentEpsilonTransitions);
        }


    }

    private static boolean transitionExists(List<String> key) {
        return transitionFunction.containsKey(key) && !transitionFunction.get(key).get(0).equals("#");
    }

    private static boolean newStateExists(Set<String> set, List<String> key) {
        for(String state : transitionFunction.get(key)) {
            if (!set.contains(state)) {
                return true;
            }
        }
        return false;
    }

    private static void printStep(String separator) {
        if (currentStates.isEmpty()) {
            System.out.print(separator + "#");
            return;
        }

        String formattedOutput = currentStates.toString()
                .replace(" ", "")
                .replace("[", "")
                .replace("]", "")
                .trim();

        System.out.print(separator + formattedOutput);
    }

}
