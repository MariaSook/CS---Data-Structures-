import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HuffmanEncoder {

    //The buildFrequencyTable method should map characters to their counts. For example,
    // suppose we have the character array [‘a’, ‘b’, ‘b’, ‘c’, ‘c’ , ‘c’, ‘c’, d’, ‘d’,
    // ‘d’, ‘d’, ‘d’, e’, ‘e’, ‘e’, ‘e’, ‘e’, ‘e’], then this method should return a map
    // from ‘a’ to 1, ‘b’ to 2, and so forth.
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> table = new HashMap<>();
        int count = 0;
        char past = inputSymbols[0];
        char charcurr;


        for (char chars : inputSymbols) {
            charcurr = chars;
            if (charcurr != past) {
                table.put(past, count);
                past = charcurr;
                count = 1;
            } else {
                count += 1;
            }
        }
        return table;
    }

    //The main method should open the file given as the 0th command
    // line argument (args[0]), and write a new file with the name
    // args[0] + ".huf" that contains a huffman encoded version of
    // the original file.
    public static void main(String[] args) {
        List<BitSequence> bitSeq = new ArrayList<>();
        char[] inputSymbols = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        BinaryTrie binaryTrie = new BinaryTrie(frequencyTable);
        Map<Character, BitSequence> table = binaryTrie.buildLookupTable();
        ObjectWriter objectwriter = new ObjectWriter(args[0] + ".huf");
        objectwriter.writeObject(binaryTrie);
        objectwriter.writeObject(inputSymbols.length);
        for (int i = 9; i < inputSymbols.length; i++) {
            BitSequence b = table.get(inputSymbols[i]);
            bitSeq.add(b);
        }
        BitSequence encodeHuf = BitSequence.assemble(bitSeq);
        objectwriter.writeObject(encodeHuf);
    }
}
