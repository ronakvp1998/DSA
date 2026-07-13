package strivers.arrays.medium;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * * Problem: 271. Encode and Decode Strings (Medium / Premium)
 * * Formal Problem Statement:
 * Design an algorithm to encode a list of strings to a string. The encoded string
 * is then sent over the network and is decoded back to the original list of strings.
 * * Machine 1 (sender) has the function:
 * string encode(vector<string> strs)
 * Machine 2 (receiver) has the function:
 * vector<string> decode(string s)
 * * Implement the encode and decode methods.
 * * * Constraints:
 * - 0 <= strs.length <= 200
 * - 0 <= strs[i].length <= 200
 * - strs[i] contains any possible characters out of 256 valid ASCII characters.
 * * * Examples:
 * Example 1:
 * Input: dummy_input = ["Hello","World"]
 * Output: ["Hello","World"]
 * Explanation:
 * Machine 1 encodes the list to a string. Machine 2 decodes it back.
 * * Example 2:
 * Input: dummy_input = [""]
 * Output: [""]
 * * Example 3:
 * Input: dummy_input = ["5#Hello", " ", ":::"]
 * Output: ["5#Hello", " ", ":::"]
 * * * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach (Length-Prefix Encoding) - The industry standard.
 * Phase 2: Brute Force Approach (Non-ASCII Delimiter) - The "Think it" stage.
 * Phase 3: Alternative Approach (Base64 + Streams) - A robust, modern API approach.
 */

import java.util.*;
import java.util.stream.Collectors;

public class EncodeDecodeStringsMasterclass {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Length-Prefix / Chunked Transfer Encoding)
     * ========================================================================
     * * Detailed Intuition:
     * If we just join strings with a delimiter like "#", a problem arises if
     * the strings themselves contain "#".
     * To solve this, we prepend the length of the string followed by a delimiter
     * before the actual string characters (e.g., "5#Hello5#World").
     * When decoding, we read the integer up to the "#" delimiter, which tells
     * us exactly how many characters to consume next. This makes the algorithm
     * completely agnostic to the contents of the strings (they can safely contain
     * any characters, including "#").
     * * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the total number of characters across
     * all strings in the list. Both encoding and decoding traverse the data
     * exactly once.
     * - Space Complexity: O(N) Heap Space for the encoded string and the decoded
     * list. O(1) Auxiliary Stack Space.
     */
    public static class OptimalCodec {
        public String encode(List<String> strs) {
            StringBuilder sb = new StringBuilder();
            for (String s : strs) {
                sb.append(s.length()).append('#').append(s);
            }

            return sb.toString();
        }

        public List<String> decode(String s) {
            List<String> result = new ArrayList<>();
            int i = 0;
            while (i < s.length()) {
                // Find the delimiter which separates the length from the string
                int delimiterPos = s.indexOf('#', i);

                // Extract the length
                int size = Integer.parseInt(s.substring(i, delimiterPos));

                // Extract the actual string
                int stringStart = delimiterPos + 1;
                String str = s.substring(stringStart, stringStart + size);
                result.add(str);

                // Move the pointer to the start of the next encoded block
                i = stringStart + size;
            }
            return result;
        }
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE / NAIVE APPROACH (Non-ASCII Delimiter)
     * ========================================================================
     * * Detailed Intuition:
     * The problem constraints state that strings will only contain characters
     * from the 256 valid ASCII set. Therefore, any character outside this
     * range (like Unicode character 257) is physically impossible to exist
     * in the input strings. We can safely use it as an un-escaped delimiter.
     * (Note: While easy, this breaks down in the real world if the system
     * suddenly supports full UTF-8).
     * * * Complexity Analysis:
     * - Time Complexity: O(N), where N is total characters. `String.join` and
     * `String.split` run in linear time.
     * - Space Complexity: O(N) Heap Space for string allocations.
     */
    public static class BruteForceCodec {
        // Character 257 (ā) is outside the 256 valid ASCII characters
        private final char DELIMITER = (char) 257;

        public String encode(List<String> strs) {
            if (strs.isEmpty()) return String.valueOf((char) 258); // Special empty list marker
            return String.join(String.valueOf(DELIMITER), strs);
        }

        public List<String> decode(String s) {
            if (s.equals(String.valueOf((char) 258))) return new ArrayList<>();
            // -1 limit in split() ensures trailing empty strings are preserved
            return new ArrayList<>(Arrays.asList(s.split(String.valueOf(DELIMITER), -1)));
        }
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Base64 Encoding & Java 8 Streams)
     * ========================================================================
     * * Detailed Intuition:
     * Base64 encoding converts any binary/character data into a safe, restricted
     * alphabet (A-Z, a-z, 0-9, +, /, =). Crucially, a Base64 string will NEVER
     * contain a space (" ") or a comma (",").
     * Therefore, we can Base64-encode each string, then safely join them with
     * commas. This leverages Java 8 Streams for a functional, elegant pipeline.
     * * * Complexity Analysis:
     * - Time Complexity: O(N). Base64 encoding/decoding processes byte arrays
     * in linear time.
     * - Space Complexity: O(N). Base64 increases string size by ~33%, maintaining
     * a linear space relationship.
     */
    public static class AlternativeCodec {
        public String encode(List<String> strs) {
            if (strs.isEmpty()) return "EMPTY_LIST_FLAG";

            return strs.stream()
                    .map(s -> Base64.getEncoder().encodeToString(s.getBytes()))
                    .collect(Collectors.joining(","));
        }

        public List<String> decode(String s) {
            if (s.equals("EMPTY_LIST_FLAG")) return new ArrayList<>();

            return Arrays.stream(s.split(",", -1)) // -1 preserves empty Base64 blocks
                    .map(encoded -> new String(Base64.getDecoder().decode(encoded)))
                    .collect(Collectors.toList());
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        // Instantiate Codecs
        OptimalCodec optimal = new OptimalCodec();
        BruteForceCodec bruteForce = new BruteForceCodec();
        AlternativeCodec alternative = new AlternativeCodec();

        // Define Test Cases
        List<List<String>> testCases = Arrays.asList(
                Arrays.asList("Hello", "World"),                           // Standard
                Arrays.asList(""),                                         // Single empty string
                Arrays.asList("", ""),                                     // Multiple empty strings
                Arrays.asList("5#Hello", " ", ":::", "#", "10#"),          // Strings with delimiters
                new ArrayList<>(),                                         // Completely empty list
                Arrays.asList("C++", "Java", "Python", "Go")               // Normal array
        );

        int testNum = 1;
        for (List<String> original : testCases) {
            System.out.println("==================================================");
            System.out.println("Test Case " + testNum++ + " (Original): " + original);

            // Test Optimal
            String optEncoded = optimal.encode(original);
            List<String> optDecoded = optimal.decode(optEncoded);
            System.out.println("Phase 1 (Optimal) Encoded : " + optEncoded);
            System.out.println("Phase 1 (Optimal) Decoded : " + optDecoded);
            System.out.println("Match? : " + original.equals(optDecoded));
            System.out.println("-");

            // Test Brute Force
            String bfEncoded = bruteForce.encode(original);
            List<String> bfDecoded = bruteForce.decode(bfEncoded);
            System.out.println("Phase 2 (Brute Force) Encoded : [Hidden for console safety]");
            System.out.println("Phase 2 (Brute Force) Decoded : " + bfDecoded);
            System.out.println("Match? : " + original.equals(bfDecoded));
            System.out.println("-");

            // Test Alternative (Base64)
            String altEncoded = alternative.encode(original);
            List<String> altDecoded = alternative.decode(altEncoded);
            System.out.println("Phase 3 (Alternative) Encoded : " + altEncoded);
            System.out.println("Phase 3 (Alternative) Decoded : " + altDecoded);
            System.out.println("Match? : " + original.equals(altDecoded));

            System.out.println("==================================================\n");
        }
    }
}