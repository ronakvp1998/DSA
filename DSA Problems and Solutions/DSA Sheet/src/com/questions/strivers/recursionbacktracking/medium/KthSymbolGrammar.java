package com.questions.strivers.recursionbacktracking.medium;

/*
Problem Statement:
------------------
We define a grammar sequence as follows:

Row 1: 0
Row 2: 0 1
Row 3: 0 1 1 0
Row 4: 0 1 1 0 1 0 0 1
... and so on.

Rules:
- Each "0" in a row generates "01" in the next row.
- Each "1" in a row generates "10" in the next row.

Task:
Given two integers n (row number) and k (position in that row, 1-based index),
return the K-th symbol in row N.

Example:
--------
Input: n = 4, k = 5
Row 4 = 0 1 1 0 1 0 0 1
Answer = 1 (5th symbol)

Approach:
---------
We use recursion:
1. Base Case:
   - Row 1 contains only "0". So, kthGrammar(1, 1) = 0.

2. Recursive Case:
   - Length of row n = 2^(n-1).
   - Find the midpoint of the row.
   - If k lies in the first half (k <= mid):
       kthGrammar(n, k) = kthGrammar(n-1, k)
   - If k lies in the second half (k > mid):
       kthGrammar(n, k) = complement of kthGrammar(n-1, k-mid)
       (since second half is opposite of the first half).

This way, instead of constructing the entire row, we use recursion to trace back to Row 1.

Time Complexity:
----------------
- Each recursive call reduces n by 1 → O(n) recursive calls.
- Hence, Time Complexity = O(n).

Space Complexity:
-----------------
- Recursive call stack depth = O(n).
- No extra space used apart from recursion.
- Hence, Space Complexity = O(n).
*/

public class KthSymbolGrammar {

    // Recursive function to find Kth symbol in Nth row
    public static int kthGrammar(int n, int k) {
        // Base case: first row, first symbol is always 0
        if (n == 1 && k == 1) {
            return 0;
        }

        // Length of row n = 2^(n-1)
        int mid = (int) Math.pow(2, n - 1) / 2;

        if (k <= mid) {
            // If k is in the first half, same as Kth symbol in previous row
            return kthGrammar(n - 1, k);
        } else {
            // If k is in the second half, it's the opposite of (k-mid)th symbol in previous row
            return kthGrammar(n - 1, k - mid) == 0 ? 1 : 0;
        }
    }

    public static void main(String[] args) {
        int n = 4; // Row number
        int k = 5; // Position in the row (1-based index)

        System.out.println("The " + k + "th symbol in row " + n + " is: " + kthGrammar(n, k));
    }
}
