import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    /* You must use this palindrome, and not instantiate
     new Palindromes, or the autograder might be upset. */
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalidrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));

        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertFalse(palindrome.isPalindrome("aaaaab"));


        //overloaded Palindrome -- how to write the test cases?!??!?!?!?1
        //how to test non-numeric values
        assertTrue(palindrome.isPalindrome("acdb", offByOne));
        assertTrue(palindrome.isPalindrome("sdfafgbect", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome(";:", offByOne));

        assertFalse(palindrome.isPalindrome("aaaaaaa", offByOne));
        assertFalse(palindrome.isPalindrome("abcdefg", offByOne));
        assertFalse(palindrome.isPalindrome("asdgfhrhtrstrefd", offByOne));
        assertFalse(palindrome.isPalindrome(";p", offByOne));

    }


}
