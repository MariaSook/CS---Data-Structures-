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

    public boolean isPalindrome(String word){
        Deque palindromeword = wordToDeque(word);
        if (word.length() == 0 || word.length()==1){
            return true;
        } else if (palindromeword.removeFirst() != palindromeword.removeLast()){
            return false;
        } return isPalindrome(dequetoString(palindromeword));
    }

//    public boolean isPalindrome(String word, CharacterComparator cc){
//
//    }

    }


