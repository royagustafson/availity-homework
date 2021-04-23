package com.availity.homework.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LispParenthesesHelperTest {

    @Test
    void isValid() {
        String input = "(level 1 nested (level 2 nested) level 1 again)";
        boolean expected = true;
        boolean actual = LispParenthesesHelper.isValid(input);
        assertEquals(expected, actual);
    }

    @Test
    void isValid_missing() {
        String input = "(level 1 nested (level 2 nested) level 1 again";
        boolean expected = false;
        boolean actual = LispParenthesesHelper.isValid(input);
        assertEquals(expected, actual);
    }

    @Test
    void isValid_hanging() {
        String input = "(level 1 nested (level 2 nested) level 1 again))";
        boolean expected = false;
        boolean actual = LispParenthesesHelper.isValid(input);
        assertEquals(expected, actual);
    }
}