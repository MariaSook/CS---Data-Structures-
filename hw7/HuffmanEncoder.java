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
        char prev = inputSymbols[0];
        char curr;
        int count = 0;
        int i = 0;

        while (i != inputSymbols.length) {
            curr = inputSymbols[i];
            if (prev == curr) {
                count += 1;
            } else {
                table.put(prev, count);
                prev = curr;
                count = 1;
            }
            i += 1;
        }
        return table;
    }

    //The main method should open the file given as the 0th command
    // line argument (args[0]), and write a new file with the name
    // args[0] + ".huf" that contains a huffman encoded version of
    // the original file.
    public static void main(String[] args) {
        char[] inputSymbols = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        BinaryTrie binaryTrie = new BinaryTrie(frequencyTable);
        ObjectWriter objectwriter = new ObjectWriter(args[0] + ".huf");
        objectwriter.writeObject(binaryTrie);
        objectwriter.writeObject(inputSymbols.length);
        Map<Character, BitSequence> lookupTable = binaryTrie.buildLookupTable();
        List<BitSequence> bitSeq = new ArrayList<>();
        for (char c : inputSymbols) {
            BitSequence b = lookupTable.get(c);
            bitSeq.add(b);
        }
        BitSequence encodeHuf = BitSequence.assemble(bitSeq);
        objectwriter.writeObject(encodeHuf);
    }
}
