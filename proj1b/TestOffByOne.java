import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testequalChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('r', 'q'));

        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('a', 'e'));
        assertFalse(offByOne.equalChars('z', 'a'));
    }

}
