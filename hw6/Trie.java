import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Trie {
    private static final int R = 256;
    private Node root = new Node();

    class Node {
        boolean isEndofWord;
        Node[] nextNodes;

        Node() {
            isEndofWord = false;
            nextNodes = new Node[R];
        }
    }

    public void put(String key) {
        put(this.root, key, 0);
    }

    Node put(Node node, String key, int d) {
        if (node == null) {
            node = new Node();
        }

        if (key.length() == d) {
            node.isEndofWord = true;
            return node;
        }

        char c = key.charAt(d);
        node.nextNodes[c] = put(node.nextNodes[c], key, d + 1);

        return node;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.nextNodes[c], key, d + 1);
    }

    public LinkedList<String> keysWithPrefix(String prefix) {
        LinkedList<String> results = new LinkedList<>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, LinkedList<String> results) {
        if (x == null) return;
        if (x.isEndofWord) {
            results.add(prefix.toString());
        }
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.nextNodes[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public boolean keysThatMatch(String key) {
        Node n = root;

        for (int i = 0; i < key.length(); i++) {
            char character = key.charAt(i);
            n = n.nextNodes[character];
        }

        return n.isEndofWord;
    }

}
