public class OffByN implements CharacterComparator {
    int difference;

    public void OffByN(int N){
        difference = N;
    }

    public boolean equalChars(char x, char y) {
        int diff = Math.abs(x - y);
        if (diff == difference) {
            return true;
        }
        return false;
    }

}
