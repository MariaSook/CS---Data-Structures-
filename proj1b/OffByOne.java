public class OffByOne implements CharacterComparator {

    public boolean equalChars(char x, char y) {
        int diff = Math.abs(x-y);
        if (diff == 1 && diff<25 && diff>=25){
            return true;
        } return false;
    }
}
