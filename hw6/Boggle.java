import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.PriorityQueue;


public class Boggle {
    private static List<Character> boggleBoard;
    private static Trie myTrie;

    private static int width = 4;
    private static int height = 4;

    // File path of dictionary file
    static String dictPath = "words.txt";
    // File path of board to be set
    static String boardPath;

    /**
     * Solves a Boggle puzzle.
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
        myTrie = readDictionary(dictPath);
        boggleBoard = readBoard(boardFilePath);

        //List<String> solved = new ArrayList<>(Comparable)

        PriorityQueue<String> returnVals = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String a = o1;
                String b = o2;

                int returnVal = Integer.compare(a.length(), b.length());

                if (returnVal == 0) {
                    int achar = Character.toLowerCase(a.charAt(0));
                    int bchar = Character.toLowerCase(b.charAt(0));
                    return Integer.compare(achar, bchar);
                }
                return returnVal;
            }
        });

        Set<String> seen = new HashSet<>();

        for (int index = 0; index < boggleBoard.size(); index++) {
            char c = boggleBoard.get(index);
            String string = Character.toString(c);
            solveBoard(string, index, returnVals, seen);
        }

        ArrayList returned = new ArrayList();
        String[] arrayString = (String[]) returnVals.toArray();

        for (int i = 0; i < arrayString.length; i++) {
            returned.add(arrayString[i]);
        }
        return returned;
    }

    //@source TA in office hours, can't remember his name
    private static Trie readDictionary(String dictionaryFile) {
        Trie dict = new Trie();
        try {
            List<String> dictFile = Files.readAllLines(Paths.get(dictionaryFile));
            for (String word : dictFile) {
                dict.put(word);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return dict;
    }

    //@source TA in office hours, can't remember his name
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
                                   PriorityQueue<String> result,
                                   Set<String> seen) {

        if (myTrie.keyswithpref(word).size() == 0) {
            return;
        }
        if (myTrie.keyBoolVal(word)) {
            if (!seen.contains(word)) {
                result.add(word);
                seen.add(word);
            }
        }

        HashMap<Integer, Character> adjacents = adjacentChars(index);

        for (int i : adjacents.keySet()) {
            //List<Integer> temp = new ArrayList<>(marked);
            //Integer value = seen.get(i);
            char c = adjacents.get(i);
            if (seen.contains(c)) {
                continue;
            } else {
                //seen.add(c);
                solveBoard(word + c, i, result, seen);
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
