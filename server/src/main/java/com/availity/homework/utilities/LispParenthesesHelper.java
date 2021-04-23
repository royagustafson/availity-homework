package com.availity.homework.utilities;

/**
 *
 * @author roy.gustafson
 */
public final class LispParenthesesHelper {

    /**
     * Returns true if all the parentheses in the string are properly closed and nested
     *
     * @param lispCode a LISP type code snippet
     * @return if
     */
    public static boolean isValid(String lispCode) {

        int incomplete = 0;

        // Incomplete acts as a logical stack, should be 0 when it's all over
        // If greater than 0, there's a missing (
        // If less than 0, there's an extra (

        for (char c : lispCode.toCharArray()) {
            switch (c) {
                case '(': incomplete++; break;
                case ')': incomplete--; break;
            }
        }

        return incomplete == 0;
    }
}
