public class HuffmanDecoder {

    public static void main(String[] args) {
        ObjectReader objectReader = new ObjectReader(args[0]);
        BinaryTrie htrie = (BinaryTrie) objectReader.readObject();
        int numsymbols = (int) objectReader.readObject();
        BitSequence b = (BitSequence) objectReader.readObject();
        char[] text = new char[numsymbols];
        for (int i = 0; i < numsymbols; i++) {
            Match match = htrie.longestPrefixMatch(b);
            text[i] = match.getSymbol();
            b = b.allButFirstNBits(match.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], text);
    }
}
