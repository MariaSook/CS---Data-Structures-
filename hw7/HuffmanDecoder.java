public class HuffmanDecoder {

    public static void main(String[] args) {
        ObjectReader objectReader = new ObjectReader(args[0]);
        Object one = objectReader.readObject();
        Object two = objectReader.readObject();
        Object three = objectReader.readObject();
        BinaryTrie htrie = (BinaryTrie) one;
        int numsymbols = (int) two;
        BitSequence b = (BitSequence) three;
        char[] text = new char[numsymbols];
        for (int i = 0; i < numsymbols; i++) {
            Match match = htrie.longestPrefixMatch(b);
            text[i] = match.getSymbol();
            b = b.allButFirstNBits(match.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], text);
    }
}
