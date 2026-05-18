package strivers.bitmanipulation.basics;


/**
 * ============================================================================
 * UNIVERSAL BASE CONVERTER (ANY BASE TO ANY BASE)
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Design an algorithm to convert a number represented as a string from a given
 * source base to a target base. Provide specialized methods for common bases
 * (Binary, Decimal, Hexadecimal) and a universal method for any base.
 * * * CONSTRAINTS:
 * - 2 <= sourceBase, targetBase <= 36
 * - The input string represents a valid integer in the sourceBase.
 * - Characters for bases > 10 should use standard English letters (A-Z or a-z).
 * - The input number fits within a 64-bit signed integer (Java 'long').
 * * * EXAMPLES:
 * Example 1:
 * Input: number = "1010", sourceBase = 2, targetBase = 10
 * Output: "10"
 * Explanation: Binary "1010" is 10 in Decimal.
 * * Example 2:
 * Input: number = "255", sourceBase = 10, targetBase = 16
 * Output: "FF"
 * Explanation: Decimal 255 is FF in Hexadecimal.
 * * Example 3:
 * Input: number = "1Z", sourceBase = 36, targetBase = 10
 * Output: "71"
 * Explanation: 1 * 36^1 + 35 * 36^0 = 36 + 35 = 71.
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: THE "DECIMAL BRIDGE"
 * ============================================================================
 * To convert a number from Base A to Base B, we don't need an O(N^2) mapping
 * between every possible base pair. Instead, we use Base 10 as a universal bridge.
 * * [ Base A String ] ----(Parse)----> [ Base 10 Integer ] ----(Format)----> [ Base B String ]
 * * STEP 1: Parse Base A -> Decimal
 * "1A" (Base 16)
 * -> (1 * 16^1) + (10 * 16^0)
 * -> 16 + 10 = 26 (Decimal)
 * * STEP 2: Format Decimal -> Base B
 * 26 to Base 2
 * 26 / 2 = 13  (Remainder: 0)
 * 13 / 2 = 6   (Remainder: 1)
 * 6  / 2 = 3   (Remainder: 0)
 * 3  / 2 = 1   (Remainder: 1)
 * 1  / 2 = 0   (Remainder: 1)
 * Read remainders bottom-to-top -> "11010"
 * ============================================================================
 */
public class BaseConversionMaster {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE / MANUAL MATHEMATICAL APPROACH (The "Think it" stage)
     * ========================================================================
     * APPROACH & STEPS:
     * 1. baseToDecimal: Iterate through the string from left to right. Maintain
     * a running total, multiplying it by the sourceBase and adding the current
     * character's integer value.
     * 2. decimalToBase: Repeatedly modulo the decimal number by the targetBase
     * to get the least significant digit, then divide by targetBase. Map the
     * remainder to its character representation and reverse the final string.
     * 3. convertBase: Chain the two methods together using Base 10 as the bridge.
     * * INTUITION:
     * This proves to the interviewer that you understand positional numeral systems.
     * Every position 'i' from the right represents `digit * base^i`. We use Horner's
     * method (running total * base + digit) to compute this in a single left-to-right
     * pass without needing Math.pow().
     * * COMPLEXITY:
     * - Time: O(N + M) where N is the length of the input string, and M is the
     * length of the output string. Both parsing and formatting are linear.
     * - Space: O(M) heap space to construct the resulting String via StringBuilder.
     * O(1) auxiliary stack space.
     * ========================================================================
     */

    // Sub-Step 1.1: Helper to map a character to its integer value (e.g., 'A' -> 10)
    private static int charToVal(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'A' && c <= 'Z') return c - 'A' + 10;
        if (c >= 'a' && c <= 'z') return c - 'a' + 10;
        throw new IllegalArgumentException("Invalid character: " + c);
    }

    // Sub-Step 1.2: Helper to map an integer to its character value (e.g., 10 -> 'A')
    private static char valToChar(int val) {
        if (val >= 0 && val <= 9) return (char) (val + '0');
        if (val >= 10 && val <= 35) return (char) (val - 10 + 'A');
        throw new IllegalArgumentException("Invalid value for base character: " + val);
    }

    // Step 1: Base X to Decimal (Base 10)
    public static long baseToDecimal(String numStr, int sourceBase) {
        if (sourceBase < 2 || sourceBase > 36) {
            throw new IllegalArgumentException("Base must be between 2 and 36");
        }

        long result = 0;
        boolean isNegative = numStr.startsWith("-");
        int startIndex = isNegative ? 1 : 0;

        for (int i = startIndex; i < numStr.length(); i++) {
            int digitValue = charToVal(numStr.charAt(i));
            if (digitValue >= sourceBase) {
                throw new NumberFormatException("Digit " + numStr.charAt(i) + " is out of bounds for base " + sourceBase);
            }
            // Horner's Method: result = result * base + digit
            result = result * sourceBase + digitValue;
        }

        return isNegative ? -result : result;
    }

    // Step 2: Decimal (Base 10) to Base Y
    public static String decimalToBase(long decimal, int targetBase) {
        if (targetBase < 2 || targetBase > 36) {
            throw new IllegalArgumentException("Base must be between 2 and 36");
        }
        if (decimal == 0) return "0";

        boolean isNegative = decimal < 0;
        decimal = Math.abs(decimal);
        StringBuilder sb = new StringBuilder();

        while (decimal > 0) {
            int remainder = (int) (decimal % targetBase);
            sb.append(valToChar(remainder));
            decimal /= targetBase;
        }

        if (isNegative) {
            sb.append("-");
        }

        // The remainders are generated in reverse order (least significant digit first)
        return sb.reverse().toString();
    }

    // Step 3: The Universal Converter (Bridge)
    public static String convertBaseManual(String number, int sourceBase, int targetBase) {
        if (sourceBase == targetBase) return number.toUpperCase();

        long decimalValue = baseToDecimal(number, sourceBase);
        return decimalToBase(decimalValue, targetBase);
    }

    /**
     * ========================================================================
     * PHASE 2: ALTERNATIVE APPROACH - JAVA BUILT-IN APIs (The "Perfect it" stage)
     * ========================================================================
     * APPROACH:
     * Use Java's native Long.parseLong(String, radix) and Long.toString(long, radix).
     * * INTUITION:
     * While the manual approach shows algorithmic understanding, in a production
     * environment, you should never reinvent the wheel. Java's native parsing
     * is highly optimized, handles edge cases (like Long.MIN_VALUE), and is
     * less prone to human error.
     *
     * Long.parseLong(number, sourceBase) (The First Half):
     * This tells Java: "Take this number string, read it as if it is in sourceBase, and calculate its standard integer value."
     * In Java, a standard primitive long is mathematically treated as a Base 10 decimal number by default when we interact with it.
     *
     * Long.toString(decimalValue, targetBase) (The Second Half):
     * This tells Java: "Now take that standard Base 10 integer (decimalValue) and format it into a new string using the targetBase."
     * * COMPLEXITY:
     * - Time: O(N) -> Internal implementations still must parse and format.
     * - Space: O(M) -> Output string allocation.
     * ========================================================================
     */
    public static String convertBaseNative(String number, int sourceBase, int targetBase) {
        try {
            long decimalValue = Long.parseLong(number, sourceBase);
            return Long.toString(decimalValue, targetBase).toUpperCase();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input for the given source base.", e);
        }
    }

    /*
    1. Long.parseLong(String s, int radix)This method is the heavy lifter for converting a string into a primitive 64-bit signed integer (long).
    How it works under the hood:
    It doesn't use Math.pow() (which is floating-point math and relatively slow).
    Instead, it uses the exact same Horner's Method we built in our manual approach.
    It iterates through the string left-to-right, multiplying the running total by the radix and adding the integer value of the current character.
    Crucial Constraints & Quirks:The Radix Limits: The radix must be between Character.MIN_RADIX (2) and Character.MAX_RADIX (36).
    Why 36? Because we use 10 digits (0-9) plus 26 letters of the English alphabet (A-Z). $10 + 26 = 36$.
    Strict Validation: If you pass a radix outside of 2–36, it will throw a NumberFormatException.Character Case: It is completely case-insensitive.
    "ff", "FF", and "fF" will all parse identically in Base 16.
    Signs: It natively understands the ASCII minus sign (-) and the ASCII plus sign (+) as the first character.
    Overflow Protection: Before doing the result = result * radix + digit math, it constantly checks if the next multiplication will exceed Long.MAX_VALUE (or Long.MIN_VALUE).
    If it predicts an overflow, it throws a NumberFormatException.

    2. Long.toString(long i, int radix)This method does the exact opposite:
    it takes a primitive long in memory (which is always stored as binary) and formats it into a human-readable string in the specified base.
    How it works under the hood:It uses the modulo and division approach.
    However, for performance, Java has highly optimized bit-shifting implementations for bases that are powers of 2 (Base 2, 8, and 16).
    For example, Long.toHexString(long i) skips division entirely and just masks bits!Crucial Constraints & Quirks (The "Silent Fallback"):
    The Silent Base 10 Fallback: This is a famous Java quirk.
    If you pass a radix less than 2 or greater than 36 to Long.toString(), it does not throw an exception.
    Instead, it silently defaults to Base 10!Always Lowercase:
    Unlike parsing (which is case-insensitive), Long.toString() will always generate strings using lowercase letters for bases > 10 (e.g., it will output "ff", not "FF").
    If you want uppercase, you must manually chain .toUpperCase().
    Zero Allocation Optimization: If the number is 0, it doesn't do any math. It just immediately returns the cached string "0".
     */


    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== Phase 1: Manual Mathematical Conversions ===");

        // 1. Binary to Decimal
        System.out.println("Binary '1010' to Decimal: " +
                convertBaseManual("1010", 2, 10) + " (Expected: 10)");

        // 2. Decimal to Hexadecimal
        System.out.println("Decimal '255' to Hex: " +
                convertBaseManual("255", 10, 16) + " (Expected: FF)");

        // 3. Hexadecimal to Binary
        System.out.println("Hex '1A' to Binary: " +
                convertBaseManual("1A", 16, 2) + " (Expected: 11010)");

        // 4. Base 36 to Decimal (Using Letters)
        System.out.println("Base 36 '1Z' to Decimal: " +
                convertBaseManual("1Z", 36, 10) + " (Expected: 71)");

        // 5. Edge Case: Zero
        System.out.println("Octal '0' to Hex: " +
                convertBaseManual("0", 8, 16) + " (Expected: 0)");

        // 6. Edge Case: Negative Numbers
        System.out.println("Binary '-111' to Decimal: " +
                convertBaseManual("-111", 2, 10) + " (Expected: -7)");

        System.out.println("\n=== Phase 2: Native Java API Conversions ===");
        System.out.println("Hex 'FF' to Binary: " +
                convertBaseNative("FF", 16, 2) + " (Expected: 11111111)");
        System.out.println("Base 36 'JAVA' to Base 16: " +
                convertBaseNative("JAVA", 36, 16) + " (Expected: 8A41E)");
    }
}