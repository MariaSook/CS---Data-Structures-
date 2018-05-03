public class HuffmanDecoder {

    public static void main(String[] args) {
        ObjectReader objectreader = new ObjectReader(args[0]);
        Object one = objectreader.readObject();
        Object two = objectreader.readObject();
        Object three = objectreader.readObject();
        BinaryTrie hTrie = (BinaryTrie) one;
        int numsymbols = (int) two;
        BitSequence bits = (BitSequence) three;
        char[] originTxt = new char[numsymbols];
        for (int i = 0; i < numsymbols; i++) {
            Match match = hTrie.longestPrefixMatch(bits);
            originTxt[i] = match.getSymbol();
            bits = bits.allButFirstNBits(match.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], originTxt);
    }
}
