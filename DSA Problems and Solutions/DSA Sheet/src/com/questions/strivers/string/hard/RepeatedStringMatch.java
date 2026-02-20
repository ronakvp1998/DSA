package com.questions.strivers.string.hard;

/**
 * ==================================================================================================
 * APPROACH: String Building and Bounds Checking
 * ==================================================================================================
 * 1. Keep appending string 'a' to a StringBuilder until its length is >= length of 'b'.
 * 2. Check if 'b' is a substring. If yes, return the current count.
 * 3. Append 'a' one more time (to handle wrap-around cases) and check again.
 * 4. If still not found, it is impossible; return -1.
 * ==================================================================================================
 */
public class RepeatedStringMatch {

    public static int repeatedStringMatch(String a, String b) {
        StringBuilder sb = new StringBuilder(a);
        int count = 1;

        // Step 1: Repeat 'a' until its length is at least the length of 'b'
        // This is the minimum possible number of repeats to fit 'b'.
        while (sb.length() < b.length()) {
            sb.append(a);
            count++;
        }

        // Step 2: Check if 'b' is now a substring
        if (sb.indexOf(b) != -1) {
            return count;
        }

        // Step 3: Append one more 'a' to handle the case where 'b' starts
        // near the end of the previous sequence and wraps around.
        sb.append(a);
        if (sb.indexOf(b) != -1) {
            return count + 1;
        }

        // Step 4: If not found by now, it's impossible.
        return -1;
    }

    public static void main(String[] args) {
        String a1 = "abcd", b1 = "cdabcdab";
        System.out.println("Example 1: " + repeatedStringMatch(a1, b1)); // Output: 3

        String a2 = "a", b2 = "aa";
        System.out.println("Example 2: " + repeatedStringMatch(a2, b2)); // Output: 2

        String a3 = "abc", b3 = "cabcabca";
        System.out.println("Example 3: " + repeatedStringMatch(a3, b3)); // Output: 4
    }
}