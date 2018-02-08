public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        /* given a String, will return a Deque where the
        characters appear int he same order as in the String
        return d deque should have 'first letter' then next
        all the way to the last letter.
         */
        Deque<Character> deq = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++){
            deq.addLast(word.charAt(i));
        }
        return deq;
    }

    private String dequetoString(Deque d){
        String s = "";
        if (d.size() == 0){
            return s;
        } return s + d.removeFirst() + dequetoString(d);
    }

    //question -- can it be a palendrome if it's like %bb% ? -- so have non-letters in the palendrome?

    public boolean isPalindrome(String word){
        Deque palindromeword = wordToDeque(word);
        if (palindromeword.size() == 0 || palindromeword.size() == 1){
            return true;
        } else if (palindromeword.removeFirst() != palindromeword.removeLast()){
            return false;
        } return isPalindrome(dequetoString(palindromeword));
    }


//    //unsure how to call Off by one method here -- red highlighted below is a problem
//    public boolean isPalindrome(String word, CharacterComparator cc){
//        Deque palindromeword = wordToDeque(word);
//        if (palindromeword.size() == 0 || palindromeword.size()== 1){
//            return true;
//        } else if (cc.OffByOne(palindromeword.removeFirst(), palindromeword.removeLast()) == false){
//            return false;
//        } return isPalindrome(dequetoString(palindromeword));
//    }


    }


