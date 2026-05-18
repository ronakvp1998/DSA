package strivers.string.hard;
import java.util.*;

/**
 * ============================================================================
 * 187. Repeated DNA Sequences
 * ============================================================================
 * * PROBLEM STATEMENT:
 * The DNA sequence is composed of a series of nucleotides abbreviated as
 * 'A', 'C', 'G', and 'T'.
 * For example, "ACGAATTCCG" is a DNA sequence.
 * When studying DNA, it is useful to identify repeated sequences within the DNA.
 * * Given a string s that represents a DNA sequence, return all the 10-letter-long
 * sequences (substrings) that occur more than once in a DNA molecule. You may
 * return the answer in any order.
 * * Example 1:
 * Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * Output: ["AAAAACCCCC","CCCCCAAAAA"]
 * * Example 2:
 * Input: s = "AAAAAAAAAAAAA"
 * Output: ["AAAAAAAAAA"]
 * * Constraints:
 * 1 <= s.length <= 10^5
 * s[i] is either 'A', 'C', 'G', or 'T'.
 * * ============================================================================
 */
public class RepeatedDNASequences {

    /**
     * ### Phase 1: Brute Force approach - The "Think it" stage.
     * * Detailed Intuition:
     * The most straightforward way to find repeated 10-character sequences is to
     * look at every possible 10-character window in the string, and then search
     * the rest of the string to see if that exact sequence appears again. We use
     * a Set to store the results to avoid adding the same repeated sequence multiple times.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2 * L), where N is the length of the string and L is the
     * substring length (10). For each of the (N-10) substrings, we scan the rest of
     * the string, performing string comparisons that take O(L) time.
     * - Space Complexity: O(N * L) for the heap space used by the `result` Set to store
     * the output strings. Auxiliary stack space is O(1).
     */
    public List<String> phase1BruteForce(String s) {
        Set<String> result = new HashSet<>();
        int n = s.length();

        if (n < 10) return new ArrayList<>();

        for (int i = 0; i <= n - 10; i++) {
            String currentSeq = s.substring(i, i + 10);
            for (int j = i + 1; j <= n - 10; j++) {
                if (s.substring(j, j + 10).equals(currentSeq)) {
                    result.add(currentSeq);
                    break;
                }
            }
        }
        return new ArrayList<>(result);
    }

    /**
     * ### Phase 2: Alternative Approaches (Hashing) - The "Refine it" stage.
     * * Detailed Intuition:
     * The brute force approach wastes time re-scanning the string. Instead, we can
     * iterate through the string just once using a sliding window of size 10.
     * We extract the substring and store it in a HashSet called `seen`. If we try to
     * add a substring to `seen` and it is already there, we know it's a duplicate.
     * * Complexity Analysis:
     * - Time Complexity: O(N * L), where N is string length and L is 10. We iterate
     * through the string once (O(N)), but extracting the substring and computing its
     * hash for the HashSet takes O(L) time.
     * - Space Complexity: O(N * L) for the heap space to store substrings in `seen`.
     */
    public List<String> phase2Hashing(String s) {
        Set<String> seen = new HashSet<>();
        Set<String> repeated = new HashSet<>();
        int n = s.length();

        if (n < 10) return new ArrayList<>();

        for (int i = 0; i <= n - 10; i++) {
            String currentSeq = s.substring(i, i + 10);
            if (!seen.add(currentSeq)) {
                repeated.add(currentSeq);
            }
        }
        return new ArrayList<>(repeated);
    }

    /**
     * ### Phase 3: Alternative Approaches (Bitmask / Rolling Hash) - The "Optimize it" stage.
     * * Detailed Intuition:
     * We can optimize the O(L) substring extraction by using a bitmask. Since there are
     * only 4 possible characters, we represent each with 2 bits (e.g., A=00, C=01).
     * A 10-character sequence requires exactly 20 bits, fitting inside a 32-bit Integer.
     * As our window slides right, we left-shift our integer, add the new character's bits,
     * and use a bitwise AND mask (0xFFFFF) to strip off the oldest character.
     * * Complexity Analysis:
     * - Time Complexity: O(N). We process each character exactly once in O(1) time.
     * - Space Complexity: O(N) heap space. We store Integers instead of Strings.
     */
    public List<String> phase3Bitmasking(String s) {
        int n = s.length();
        if (n < 10) return new ArrayList<>();

        Map<Character, Integer> dnaMap = new HashMap<>();
        dnaMap.put('A', 0); dnaMap.put('C', 1);
        dnaMap.put('G', 2); dnaMap.put('T', 3);

        Set<Integer> seenHashes = new HashSet<>();
        Set<String> repeated = new HashSet<>();

        int currentHash = 0;
        int mask = 0xFFFFF;

        for (int i = 0; i < n; i++) {
            currentHash = (currentHash << 2) | dnaMap.get(s.charAt(i));
            currentHash &= mask;

            if (i >= 9) {
                if (!seenHashes.add(currentHash)) {
                    repeated.add(s.substring(i - 9, i + 1));
                }
            }
        }
        return new ArrayList<>(repeated);
    }

    /**
     * ### Phase 4: Alternative Approaches (Generalized Rabin-Karp) - The "Scale it" stage.
     * * Detailed Intuition:
     * Phase 3's bitmasking is brilliant but fragile; it breaks if the alphabet size grows
     * or the sequence length exceeds what fits in an integer (e.g., L > 15).
     * Rabin-Karp solves this scalability by using a true rolling hash with a prime modulus
     * to prevent integer overflow. We treat the sequence as a number in a specific base.
     * Formula: currentHash = (currentHash * base - leftChar * base^L + rightChar) % modulus.
     * * Complexity Analysis:
     * - Time Complexity: Expected O(N), Worst-case O(N * L). In the rare event of a hash
     * collision, we would need an O(L) string comparison to verify. With a large prime,
     * this is strictly O(N) in practice.
     * - Space Complexity: O(N) heap space to store the Long hashes in the set. O(1) stack.
     */
    public List<String> phase4RabinKarp(String s) {
        int n = s.length();
        int L = 10;
        if (n < L) return new ArrayList<>();

        // Base 4 for the 4 DNA characters. (Could easily be 26 for English alphabet).
        int base = 4;
        // A large prime modulus to prevent integer overflow and minimize collisions.
        long modulus = (long) Math.pow(10, 9) + 7;

        Map<Character, Integer> dnaMap = new HashMap<>();
        dnaMap.put('A', 0); dnaMap.put('C', 1);
        dnaMap.put('G', 2); dnaMap.put('T', 3);

        // Calculate base^L % modulus (Used to remove the leftmost character from the window)
        long aL = 1;
        for (int i = 1; i <= L; i++) {
            aL = (aL * base) % modulus;
        }

        long currentHash = 0;
        Set<Long> seenHashes = new HashSet<>();
        Set<String> repeated = new HashSet<>();

        for (int i = 0; i < n; i++) {
            // 1. Add the new character to the rolling hash
            currentHash = (currentHash * base + dnaMap.get(s.charAt(i))) % modulus;

            // 2. Remove the leftmost character that falls out of the L-length window
            if (i >= L) {
                // We add 'modulus' before the final modulo to handle negative numbers in Java
                currentHash = (currentHash - dnaMap.get(s.charAt(i - L)) * aL % modulus + modulus) % modulus;
            }

            // 3. Start checking for duplicates once we have a full window
            if (i >= L - 1) {
                if (!seenHashes.add(currentHash)) {
                    // Note: In a rigorous production environment, you would verify the actual
                    // string here to rule out highly improbable hash collisions.
                    repeated.add(s.substring(i - L + 1, i + 1));
                }
            }
        }
        return new ArrayList<>(repeated);
    }

    /**
     * ### 4. Testing Suite
     * Thoroughly tests all approaches against standard, overlapping, and edge cases.
     */
    public static void main(String[] args) {
        RepeatedDNASequences solver = new RepeatedDNASequences();

        String test1 = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        String test2 = "AAAAAAAAAAAAA";
        String test3 = "ACGT"; // Edge case: length < 10
        String test4 = "GAGAGAGAGAGA"; // Overlapping repeats

        String[] tests = {test1, test2, test3, test4};

        System.out.println("==================================================");
        System.out.println("Running Testing Suite for Repeated DNA Sequences");
        System.out.println("==================================================\n");

        for (int i = 0; i < tests.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": s = \"" + tests[i] + "\"");

            List<String> res1 = solver.phase1BruteForce(tests[i]);
            List<String> res2 = solver.phase2Hashing(tests[i]);
            List<String> res3 = solver.phase3Bitmasking(tests[i]);
            List<String> res4 = solver.phase4RabinKarp(tests[i]);

            System.out.println("  Phase 1 (Brute Force): " + res1);
            System.out.println("  Phase 2 (Hashing)    : " + res2);
            System.out.println("  Phase 3 (Bitmasking) : " + res3);
            System.out.println("  Phase 4 (Rabin-Karp) : " + res4);

            // Validation (Checking if all optimized methods match the brute force baseline)
            boolean passed = res2.containsAll(res1) && res1.containsAll(res2) &&
                    res3.containsAll(res1) && res1.containsAll(res3) &&
                    res4.containsAll(res1) && res1.containsAll(res4);

            System.out.println("  Status: " + (passed ? "PASS" : "FAIL") + "\n");
        }
    }
}