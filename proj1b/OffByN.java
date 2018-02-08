public class OffByN implements CharacterComparator {
    private int difference;

    public OffByN(int N) {
        difference = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = Math.abs(x - y);
        if (diff == difference) {
            return true;
        }
        return false;
    }

}
