import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import edu.princeton.cs.algs4.MinPQ;

public class BinaryTrie implements Serializable {
    private Node myTrie;
    private Map<Character, Integer> frequencyTable;
    private MinPQ<Node> minpq = new MinPQ<>();
    private Map<Character, BitSequence> lookUpTable = new HashMap<>();

    // @source Princeton implementation given in the spec
    private class Node implements Comparable<Node> {
        private final char c;
        private final int freq;
        private final Node left, right;

        private Node(char c, int freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return (left == null) && (right == null);
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    //Given a frequency table which maps symbols of type V to their relative
    // frequencies, the constructor should build a Huffman decoding
    // trie according to the procedure discussed in class
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        this.frequencyTable = frequencyTable;
        this.myTrie = build();
    }

    //Match API
//    public class Match {
//        public Match(BitSequence sequence, char symbol)
//        public char getSymbol()
//        public BitSequence getSequence()
//    }

    //The longestPrefixMatch method finds the longest prefix that matches the
    //given querySequence and returns a Match object for that Match
    public Match longestPrefixMatch(BitSequence querySequence) {
        Node copy = myTrie;
        int length = querySequence.length();

        for (int i = 0; i < length; i++) {
            if (copy.isLeaf()) {
                Match match = new Match(querySequence.firstNBits(i), copy.c);
                return match;
            } else {
                if (querySequence.bitAt(i) != 0) {
                    copy = copy.right;
                } else {
                    copy = copy.left;
                }
            }
        }
        return new Match(querySequence, copy.c);
    }

    //@source help from the princeton implementation
    private void helperLookUp(Node n, String s) {
        if (!n.isLeaf()) {
            helperLookUp(n.left, s + '0');
            helperLookUp(n.right, s + '1');
        } else {
            this.lookUpTable.put(n.c, new BitSequence(s));
        }
    }

    //The buildLookupTable method returns the inverse of the coding trie.
    public Map<Character, BitSequence> buildLookupTable() {
        helperLookUp(myTrie, "");
        return this.lookUpTable;
    }

    //@source ideas adapted from princeton implementation give in spec
    private Node build() {
        for (char chars: this.frequencyTable.keySet()) {
            this.minpq.insert(new Node(chars, this.frequencyTable.get(chars),
                    null, null));
        }
        while (this.minpq.size() > 1) {
            Node l = this.minpq.delMin();
            Node r = this.minpq.delMin();
            Node p = new Node('\0', l.freq + r.freq, l, r);
            this.minpq.insert(p);
        }
        return this.minpq.delMin();
    }

}
