package com.questions.strivers.recursion.medium;

public class KthSymbolGrammar {

    // Recursive function to find Kth symbol in Nth row
    public static int kthGrammar(int n, int k) {
        // Base case: first row, first symbol is always 0
        if (n == 1 && k == 1) {
            return 0;
        }

        // Length of row n is 2^(n-1)
        int mid = (int) Math.pow(2, n - 1) / 2;

        if (k <= mid) {
            // If k is in the first half, it is same as Kth symbol in previous row
            return kthGrammar(n - 1, k);
        } else {
            // If k is in the second half, it's the opposite of (k-mid)th symbol in previous row
            return kthGrammar(n - 1, k - mid) == 0 ? 1 : 0;
        }
    }

    public static void main(String[] args) {
        int n = 4; // row number
        int k = 5; // position in the row

        System.out.println("The " + k + "th symbol in row " + n + " is: " + kthGrammar(n, k));
    }
}
