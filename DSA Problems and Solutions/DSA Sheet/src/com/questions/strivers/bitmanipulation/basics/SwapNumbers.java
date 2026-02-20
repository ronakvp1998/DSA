package com.questions.strivers.bitmanipulation.basics;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Swap two numbers without a temporary variable
 * ==================================================================================================
 * Given two integers a and b, swap them in-place using only 2 variables
 * (without using a temporary third variable).
 *
 * Example 1:
 * Input: a = 5, b = 10
 * Output: a = 10, b = 5
 *
 * Example 2:
 * Input: a = -100, b = -200
 * Output: a = -200, b = -100
 * * ==================================================================================================
 * APPROACH 1: BRUTE FORCE / MATHEMATICAL (Addition and Subtraction)
 * ==================================================================================================
 * We can use basic arithmetic to store the "sum" of both numbers in one variable,
 * then subtract the values one by one to isolate the original numbers in swapped positions.
 * * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bit Manipulation using XOR)
 * ==================================================================================================
 * The Bitwise XOR (^) operation has a unique property:
 * 1. X ^ X = 0 (Self-inverse)
 * 2. X ^ 0 = X (Identity)
 * 3. (A ^ B) ^ A = B
 * 4. (A ^ B) ^ B = A
 * * By XORing the two variables three times in a specific order, we can swap their values
 * without needing extra memory or risking the integer overflow issues found in addition.
 * ==================================================================================================
 */

public class SwapNumbers {

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        // Test Case 1
        int a1 = 5, b1 = 10;
        System.out.println("Test Case 1 (Before): a = " + a1 + ", b = " + b1);
        swapOptimal(a1, b1); // Note: Java passes by value, so logic is demonstrated inside the method.

        // Practical demonstration with array to show persistent swap
        int[] arr = {5, 10};
        swapInArray(arr);
        System.out.println("Test Case 1 (After Optimal Swap): a = " + arr[0] + ", b = " + arr[1]);
        System.out.println("--------------------------------------------------");

        // Test Case 2
        int a2 = -100, b2 = -200;
        System.out.println("Test Case 2 (Before): a = " + a2 + ", b = " + b2);
        int[] arr2 = {-100, -200};
        swapArithmetic(arr2);
        System.out.println("Test Case 2 (After Arithmetic Swap): a = " + arr2[0] + ", b = " + arr2[1]);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: MATHEMATICAL (Addition & Subtraction)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. a = a + b (Store sum in 'a')
     * 2. b = a - b (Total - b = original 'a', now stored in 'b')
     * 3. a = a - b (Total - new 'b' = original 'b', now stored in 'a')
     */
    public static void swapArithmetic(int[] arr) {
        arr[0] = arr[0] + arr[1];
        arr[1] = arr[0] - arr[1];
        arr[0] = arr[0] - arr[1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Bit Manipulation / XOR)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. a = a ^ b (Store combined XOR in 'a')
     * 2. b = a ^ b (Combined ^ original 'b' = original 'a')
     * 3. a = a ^ b (Combined ^ new 'b' = original 'b')
     */
    public static void swapInArray(int[] arr) {
        // Step 1: XOR both and store in a
        arr[0] = arr[0] ^ arr[1];

        // Step 2: XOR the new 'a' with 'b' to get the original 'a'
        arr[1] = arr[0] ^ arr[1];

        // Step 3: XOR the new 'a' with the new 'b' to get the original 'b'
        arr[0] = arr[0] ^ arr[1];
    }

    // Demonstrative standalone method
    public static void swapOptimal(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        // Inside this method, a is now 10 and b is 5.
    }
}