import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Boggle {
    private static List<Character> boggleBoard;
    private static Trie trie;

    private static int width = 4;
    private static int height = 4;

    // File path of dictionary file
    static String dictPath = "words.txt";
    // File path of board to be set
    static String boardPath;

    /**
     * Solves a Boggle puzzle.
     *
     * @param k             The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     * The Strings are sorted in descending order of length.
     * If multiple words have the same length,
     * have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        if (k < 1) {
            throw new IllegalArgumentException();
        }

        trie = readDictionary(dictPath);
        boggleBoard = readBoard(boardFilePath);

        List<String> solved = new ArrayList<>();

        for (int i = 0; i < boggleBoard.size(); i++) {
            ArrayList<Integer> v = new ArrayList<>();
            v.add(i);
            char character = boggleBoard.get(i);
            solveBoard(Character.toString(character), i, solved, v);
        }

        solved.sort((a, b) -> Integer.compare(b.length(), a.length()));

        ArrayList<String> returned = new ArrayList();

        for (int i = 0; i < k; i++) {
            try {
                returned.add(solved.get(i));
            } catch (RuntimeException e) {
                System.out.println(e);
            }
        }

        return returned;
    }

    private static Trie readDictionary(String dictionaryFile) {
        Trie dictionary = new Trie();

        try {
            List<String> words = Files.readAllLines(Paths.get(dictionaryFile));

            for (String word : words) {
                dictionary.put(word);
            }

        } catch (IOException e) {
            System.out.println("Invalid file name!");
        }

        return dictionary;
    }

    private static ArrayList<Character> readBoard(String boardName) {
        ArrayList<Character> board = new ArrayList<>();

        In path = new In(boardName);

        while (path.hasNextLine()) {
            String letters = path.readLine();

            for (int i = 0; i < letters.length(); i++) {
                board.add(letters.charAt(i));
            }
        }

        return board;
    }

    private static void solveBoard(String word, int index,
                                   List<String> result, List<Integer> marked) {

        if (trie.keysWithPrefix(word).size() == 0) {
            return;
        }

        if (trie.keysThatMatch(word)) {
            if (!result.contains(word)) {
                result.add(word);
            }
        }

        HashMap<Integer, Character> adjacents = adjacentChars(index);

        for (int i : adjacents.keySet()) {
            List<Integer> temp = new ArrayList<>(marked);
            if (temp.contains(i)) {
                continue;
            } else {
                temp.add(i);
                char c = adjacents.get(i);
                solveBoard(word + c, i, result, temp);
            }
        }
    }

    private static HashMap<Integer, Character> adjacentChars(int index) {
        int[] adjIndices = adjIndices(index);
        HashMap<Integer, Character> adjacentChars = new HashMap<>();

        for (int i : adjIndices) {
            if (i < 0 || i >= width * height) {
                continue;
            }

            try {
                adjacentChars.put(i, boggleBoard.get(i));
            } catch (RuntimeException e) {
                System.out.println(e);
            }
        }

        return adjacentChars;
    }

    private static int[] adjIndices(int index) {
        int[] adjIndices = new int[8];

        //possible adjacent indices
        adjIndices[0] = index - 1;
        adjIndices[1] = index + 1;
        adjIndices[2] = index - width;
        adjIndices[3] = index + width;
        adjIndices[4] = index - width - 1;
        adjIndices[5] = index - width + 1;
        adjIndices[6] = index + width - 1;
        adjIndices[7] = index + width + 1;

        if (index % width == 0) {
            adjIndices[0] = -1;
            adjIndices[4] = -1;
            adjIndices[6] = -1;
        }

        if (index % width == width - 1) {
            adjIndices[1] = -1;
            adjIndices[5] = -1;
            adjIndices[7] = -1;
        }

        return adjIndices;
    }

    public static void main(String[] args) {
        //args[0] e= boardPath;
        //Boggle boggle = new Boggle();
        long startTime = System.nanoTime();

        System.out.println(Boggle.solve(7, args[0]));

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime / 1000000);

    }

}
