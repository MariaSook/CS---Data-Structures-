
import java.util.LinkedList;

public class Trie {
    private static final int R = 256;
    private Node root = new Node();

    //@source lecture slides
    private class Node {
        boolean isEndofWord;
        Node[] children;

        private Node() {
            this.isEndofWord = false;
            this.children = new Node[R];
        }
    }

    //@source lecture slides
    public void put(String key) {
        putHelper(this.root, 0, key);
    }

    //@source lectures slides
    Node putHelper(Node node, int index, String key) {
        if (node == null) {
            node = new Node();
        }

        if (index == key.length()) {
            node.isEndofWord = true;
            return node;
        }

        char c = key.charAt(index);
        node.children[c] = putHelper(node.children[c], index + 1, key);

        return node;
    }


    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }

        Node xhelper = x;
        int dhelper = d;

        while (x != null) {
            if (d == key.length()) {
                return xhelper;
            }
            char c = key.charAt(d);
            xhelper = xhelper.children[c];
            dhelper += 1;
        }
        return xhelper;
    }

    public LinkedList<String> keyswithpref(String prefix) {
        LinkedList<String> results = new LinkedList<>();
        Node n = get(root, prefix, 0);
        StringBuilder pref = new StringBuilder(prefix);
        findkeyswithpref(n, pref, results);
        return results;
    }

    //@helen dang talked me through a logical implementation of the Trie
    private void findkeyswithpref(Node n, StringBuilder prefix, LinkedList<String> results) {
        if (n == null) {
            return;
        }

        if (n.isEndofWord) {
            String s = prefix.toString();
            results.add(s);
        }
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            findkeyswithpref(n.children[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public boolean keyBoolVal(String key) {
        Node n = root;

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            n = n.children[c];
        }
        return n.isEndofWord;
    }

}
