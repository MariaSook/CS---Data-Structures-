public class Palindrome extends OffByOne{
    public Deque<Character> wordToDeque(String word) {
        /* given a String, will return a Deque where the
        characters appear in the same order as in the String
        return d deque should have 'first letter' then next
        all the way to the last letter.
         */
        Deque<Character> deq = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deq.addLast(word.charAt(i));
        }
        return deq;
    }

    private String dequetoString(Deque d) {
        String s = "";
        if (d.size() == 0) {
            return s;
        }
        return s + d.removeFirst() + dequetoString(d);
    }

    public boolean isPalindrome(String word) {
        Deque palindromeword = wordToDeque(word);
        if (palindromeword.size() == 0 || palindromeword.size() == 1) {
            return true;
        } else if (palindromeword.removeFirst() != palindromeword.removeLast()) {
            return false;
        }
        return isPalindrome(dequetoString(palindromeword));
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque palindromeword = wordToDeque(word);

        if (palindromeword.size() == 0 || palindromeword.size()== 1){
            return true;
        } else if (cc.equalChars(word.charAt(0), word.charAt(word.length()-1))){
            palindromeword.removeFirst();
            palindromeword.removeLast();
            String newword = dequetoString(palindromeword);
            return isPalindrome(newword, cc);
        } return false;
    }

}


