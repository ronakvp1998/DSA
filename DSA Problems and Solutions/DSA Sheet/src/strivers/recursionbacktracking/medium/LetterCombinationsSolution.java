package strivers.recursionbacktracking.medium;

/**
 * ============================================================================
 * 17. Letter Combinations of a Phone Number
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a string containing digits from 2-9 inclusive, return all possible
 * letter combinations that the number could represent. Return the answer in
 * any order.
 *
 * A mapping of digits to letters (just like on the telephone buttons) is
 * given below. Note that 1 does not map to any letters.
 * (2 -> "abc", 3 -> "def", 4 -> "ghi", 5 -> "jkl", 6 -> "mno",
 *  7 -> "pqrs", 8 -> "tuv", 9 -> "wxyz")
 *
 * EXAMPLES:
 * Example 1:
 * Input: digits = "23"
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * Example 2:
 * Input: digits = "2"
 * Output: ["a","b","c"]
 *
 * CONSTRAINTS:
 * - 0 <= digits.length <= 4 (Standard LeetCode edge case allows 0)
 * - digits[i] is a digit in the range ['2', '9'].
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Recursion Tree for For-Loop Approach)
 * ============================================================================
 * For digits = "23":
 * '2' maps to "abc", '3' maps to "def"
 *
 * State format: (Current Path, Digit Index)
 *
 *                                  ("", 0)
 *                        /            |            \
 *                char 'a'          char 'b'          char 'c'
 *                   /                 |                 \
 *               ("a", 1)           ("b", 1)           ("c", 1)
 *              /   |   \          /   |   \          /   |   \
 *            'd'  'e'  'f'      'd'  'e'  'f'      'd'  'e'  'f'
 *            /     |     \      /     |     \      /     |     \
 *       ("ad",2)("ae",2)("af",2) ... (and so on)
 *         ✅       ✅       ✅
 *
 * Final Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class LetterCombinationsSolution {

    // Mapping digits to their corresponding characters on a telephone keypad
    private static final String[] KEYPAD = {
            "",     // 0
            "",     // 1
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
    };

    /**
     * ============================================================================
     * PHASE 1A: Optimal Approach - For-Loop Based Backtracking (Explore Candidates)
     * ============================================================================
     * Detailed Intuition:
     * This uses the standard N-ary tree exploration pattern. We recursively process
     * the input string digit by digit. For the current digit, we retrieve its
     * corresponding string of letters from the `KEYPAD`. We use a `for` loop to
     * iterate over these letters. For each letter, we append it to our current
     * path, recursively call the function for the next digit, and then backtrack
     * by removing the letter to try the next one.
     *
     * Complexity Analysis:
     * - Time Complexity: O(4^N * N)
     *   Where N is the length of digits. In the worst case (e.g., "7" or "9"),
     *   each digit maps to 4 letters, so there are 4^N combinations. Copying each
     *   combination of length N into the result list takes O(N) time.
     * - Space Complexity: O(N) auxiliary stack space + O(4^N * N) heap space.
     *   The recursion depth goes up to N. The result list holds up to 4^N strings
     *   of length N.
     * ============================================================================
     */
    public List<String> letterCombinationsForLoop(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return result;
        }
        solveForLoop(0, digits, new StringBuilder(), result);
        return result;
    }

    private void solveForLoop(int index, String digits, StringBuilder current, List<String> result) {
        // Base case: If we've processed all digits, the combination is complete
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        // Get the letters that the current digit maps to
        String letters = KEYPAD[digits.charAt(index) - '0'];

        // Loop through the candidate letters for this specific digit slot
        for (int i = 0; i < letters.length(); i++) {
            current.append(letters.charAt(i));           // INCLUDE candidate
            solveForLoop(index + 1, digits, current, result); // RECURSE to next digit
            current.deleteCharAt(current.length() - 1);  // BACKTRACK
        }
    }

    /**
     * ============================================================================
     * PHASE 1B: Alternative Approach - Pure Recursion (Pick / Don't Pick)
     * ============================================================================
     * Detailed Intuition:
     * To achieve this without a `for` loop, we translate the "iteration over
     * letters" into recursive states. At any point, we have two choices:
     * 1. PICK the current character for this digit slot, and move on to the FIRST
     *    character of the NEXT digit slot.
     * 2. DON'T PICK the current character, and instead look at the NEXT character
     *    of the SAME digit slot.
     * This brilliantly converts an N-ary tree into a strict Binary Decision Tree.
     *
     * Complexity Analysis:
     * - Time Complexity: O(4^N * N)
     *   We visit exactly the same number of valid leaf nodes, though the binary
     *   tree has more intermediary nodes than the N-ary tree.
     * - Space Complexity: O(N * 4) -> O(N) auxiliary stack space.
     *   Because we recurse for both digit and character changes, the max depth
     *   is slightly deeper (up to 4 recursive calls per digit), but still O(N).
     * ============================================================================
     */
    public List<String> letterCombinationsPickDontPick(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return result;
        }
        solvePickDontPick(digits, 0, 0, new StringBuilder(), result);
        return result;
    }

    private void solvePickDontPick(String digits, int digitIndex, int charIndex, StringBuilder current, List<String> result) {
        // Base case 1: Successfully formed a combination
        if (digitIndex == digits.length()) {
            result.add(current.toString());
            return;
        }

        String letters = KEYPAD[digits.charAt(digitIndex) - '0'];

        // Base case 2: Exhausted all characters for the current digit
        if (charIndex == letters.length()) {
            return;
        }

        // CHOICE 1: Pick the current character and move to the next digit
        current.append(letters.charAt(charIndex));
        solvePickDontPick(digits, digitIndex + 1, 0, current, result);
        current.deleteCharAt(current.length() - 1); // Backtrack

        // CHOICE 2: Don't pick the current character, try the next character for the same digit
        solvePickDontPick(digits, digitIndex, charIndex + 1, current, result);
    }

    /**
     * ============================================================================
     * PHASE 3: Alternative Approach - Iterative (BFS / Queue)
     * ============================================================================
     * Detailed Intuition:
     * We can solve this iteratively by treating it as a Breadth-First Search (BFS).
     * We initialize a queue with an empty string `""`.
     * For each digit in the input, we determine how many items are currently in
     * the queue. We dequeue each item, append every possible letter for the
     * current digit, and enqueue the new strings back.
     *
     * Complexity Analysis:
     * - Time Complexity: O(4^N * N)
     *   String concatenation in the queue takes time, proportional to string length.
     * - Space Complexity: O(4^N * N)
     *   The queue will hold 4^N elements at the final level, taking up significant
     *   heap space instead of recursion stack space.
     * ============================================================================
     */
    public List<String> letterCombinationsIterative(String digits) {
        LinkedList<String> queue = new LinkedList<>();
        if (digits == null || digits.isEmpty()) {
            return queue;
        }

        queue.add(""); // Initialize with an empty combination

        for (int i = 0; i < digits.length(); i++) {
            int digit = digits.charAt(i) - '0';
            String letters = KEYPAD[digit];

            int size = queue.size();
            // Process all existing combinations in the queue
            for (int j = 0; j < size; j++) {
                String current = queue.poll();
                // Append each new letter and add back to the queue
                for (char c : letters.toCharArray()) {
                    queue.add(current + c);
                }
            }
        }

        return queue;
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        LetterCombinationsSolution solution = new LetterCombinationsSolution();

        String[] testCases = {
                "23",   // Standard Example 1
                "2",    // Standard Example 2
                "",     // Edge Case: Empty String
                "79"    // Edge Case: 4-letter mappings
        };

        System.out.println("====== LETTER COMBINATIONS DSA EVALUATION ======\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": digits = \"" + testCases[i] + "\"");

            // Test Phase 1A: For-Loop Backtrack
            List<String> resForLoop = solution.letterCombinationsForLoop(testCases[i]);
            System.out.println("  [For-Loop Backtrack] : " + formatResult(resForLoop));

            // Test Phase 1B: Pick / Don't Pick Recursion
            List<String> resPickDontPick = solution.letterCombinationsPickDontPick(testCases[i]);
            System.out.println("  [Pick/Don't Pick]    : " + formatResult(resPickDontPick));

            // Test Phase 3: Iterative Queue
            List<String> resIterative = solution.letterCombinationsIterative(testCases[i]);
            System.out.println("  [Iterative Queue]    : " + formatResult(resIterative));

            System.out.println("--------------------------------------------------");
        }
    }

    // Utility to format List<String> cleanly for the console using Java 8 Streams
    private static String formatResult(List<String> res) {
        if (res.isEmpty()) return "[]";
        return "[" + res.stream()
                .map(str -> "\"" + str + "\"")
                .collect(Collectors.joining(", ")) + "]";
    }
}