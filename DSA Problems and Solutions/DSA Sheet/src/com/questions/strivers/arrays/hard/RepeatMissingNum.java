package com.questions.strivers.arrays.hard;

/*
🔗 Problem Link: https://takeuforward.org/data-structure/find-the-repeating-and-missing-numbers/

📌 Problem Statement:
You're given an array of size N containing numbers from 1 to N.
Exactly one number A is **repeated**, and another number B is **missing**.
You must return A and B in the form: {A, B}

📥 Example:
Input:  {3, 1, 2, 5, 3}
Output: {3, 4}
Explanation: 3 is repeated, 4 is missing
*/

public class RepeatMissingNum {

    /**
     * ✅ Approach 1: Brute Force
     * Count frequency of each number using nested loops.
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    private static int[] findMissingRepeatingNumbers(int[] a) {
        int n = a.length;
        int repeating = -1, missing = -1;

        // Iterate through 1 to n and count its occurrence in array
        for (int i = 1; i <= n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (a[j] == i) cnt++;
            }

            if (cnt == 2) repeating = i;
            else if (cnt == 0) missing = i;

            if (repeating != -1 && missing != -1) break;
        }

        return new int[]{repeating, missing};
    }

    /**
     * ✅ Approach 2: Hashing
     * Use an auxiliary array to count occurrences.
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    private static int[] findMissingRepeatingNumbers2(int[] a) {
        int n = a.length;
        int[] hash = new int[n + 1]; // Index from 1 to n

        // Count frequency of each number
        for (int i = 0; i < n; i++) {
            hash[a[i]]++;
        }

        int repeating = -1, missing = -1;
        for (int i = 1; i <= n; i++) {
            if (hash[i] == 2) repeating = i;
            else if (hash[i] == 0) missing = i;

            if (repeating != -1 && missing != -1) break;
        }

        return new int[]{repeating, missing};
    }

    /**
     * ✅ Approach 3: Mathematical Equations
     * Let:
     *  X = repeating number
     *  Y = missing number
     *  sum_diff = X - Y
     *  square_sum_diff = X^2 - Y^2
     *
     * Use formulas:
     *  X + Y = square_sum_diff / sum_diff
     *  X = (sum_diff + X+Y) / 2
     *  Y = X - sum_diff
     *example
     * x - y = -4
     * * x^2 - y^2 = -24
     * (x+y) = (x^2 - y^2) / (x - y) = 6
     * x - y = -4
     * x + y = 6
     * x => (6 - 4) / 2 = 1,  y => 1 - (-4) = 5
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    private static int[] findMissingRepeatingNumbers3(int[] a) {
        long n = a.length;

        // Expected sum and sum of squares
        long SN = (n * (n + 1)) / 2;
        long S2N = (n * (n + 1) * (2 * n + 1)) / 6;

        // Actual sum and sum of squares from array
        long S = 0, S2 = 0;
        for (int i = 0; i < n; i++) {
            S += a[i];
            S2 += (long)a[i] * (long)a[i];
        }

        long val1 = S - SN;        // X - Y
        long val2 = S2 - S2N;      // X^2 - Y^2

        val2 = val2 / val1;        // X + Y

        long x = (val1 + val2) / 2; // X = (X - Y + X + Y) / 2
        long y = x - val1;          // Y = X - (X - Y)

        return new int[]{(int)x, (int)y};
    }

    /**
     * ✅ Approach 4: XOR Method
     * Use XOR to cancel duplicates and isolate repeating/missing numbers.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    private static int[] findMissingRepeatingNumbers4(int[] a) {
        int n = a.length;
        int xr = 0;

        // Step 1: XOR of all elements in array and from 1 to n
        for (int i = 0; i < n; i++) {
            xr = xr ^ a[i];      // XOR with array elements
            xr = xr ^ (i + 1);   // XOR with numbers from 1 to n
        }

        // Step 2: Get rightmost set bit (differentiating bit)
        int number = (xr & ~(xr - 1));

        // Step 3: Divide elements into two buckets
        int zero = 0;
        int one = 0;

        for (int i = 0; i < n; i++) {
            if ((a[i] & number) != 0) one ^= a[i];
            else zero ^= a[i];
        }

        for (int i = 1; i <= n; i++) {
            if ((i & number) != 0) one ^= i;
            else zero ^= i;
        }

        // Step 4: Determine which is missing and which is repeating
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] == zero) cnt++;
        }

        if (cnt == 2) return new int[]{zero, one};
        return new int[]{one, zero};
    }

    public static void main(String[] args) {
        int[] a = {3, 1, 2, 5, 4, 6, 7, 5};

        // You can try different methods here
        int[] ans = findMissingRepeatingNumbers4(a); // Try 1, 2, 3, or 4
        System.out.println("The repeating and missing numbers are: {"
                + ans[0] + ", " + ans[1] + "}");
    }
}
