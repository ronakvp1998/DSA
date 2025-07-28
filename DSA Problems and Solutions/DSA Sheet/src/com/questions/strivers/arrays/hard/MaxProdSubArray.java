package com.questions.strivers.arrays.hard;

/*
https://takeuforward.org/data-structure/maximum-product-subarray-in-an-array/

🧠 Problem Statement:
Given an array that contains both negative and positive integers,
find the contiguous subarray that has the maximum product.

---

🔁 Example 1:
Input: Nums = [1,2,3,4,5,0]
Output: 120 → (1×2×3×4×5)

🔁 Example 2:
Input: Nums = [1,2,-3,0,-4,-5]
Output: 20 → (-4 × -5)

*/
public class MaxProdSubArray {

    // 🔴 Brute Force Approach - Method 1
    static int maxProductSubArray(int arr[]) {
        int result = Integer.MIN_VALUE;  // holds the maximum product seen so far

        // Iterate over all subarrays [i..j]
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int prod = 1;

                // Multiply elements from i to j
                for (int k = i; k <= j; k++) {
                    prod *= arr[k];
                }

                // Update max product
                result = Math.max(result, prod);
            }
        }

        return result;
    }

    /*
    🧠 Approach:
    Generate all subarrays, compute product for each, and store the maximum.

    ⏱ Time Complexity: O(n^3)
    ⏳ Space Complexity: O(1)
    */


    // 🔴 Improved Brute Force Approach - Method 2
    static int maxProductSubArray2(int arr[]) {
        int result = arr[0]; // initialize result with first element

        // Fix starting index i
        for (int i = 0; i < arr.length - 1; i++) {
            int p = arr[i]; // start with current element

            // Try all subarrays starting from i
            for (int j = i + 1; j < arr.length; j++) {
                result = Math.max(result, p); // update result with product so far
                p *= arr[j]; // multiply next element
            }

            result = Math.max(result, p); // include final multiplication
        }

        return result;
    }

    /*
    🧠 Approach:
    Generate subarrays starting from i and keep multiplying the running product.
    Slightly better than Method 1 as it avoids recomputing products from scratch.

    ⏱ Time Complexity: O(n^2)
    ⏳ Space Complexity: O(1)
    */


    // 🟡 Prefix and Suffix Product Trick - Method 3
    public static int maxProductSubArray3(int[] arr) {
        int n = arr.length;

        int pre = 1, suff = 1; // pre for prefix product, suff for suffix product
        int ans = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            // Reset prefix and suffix if product becomes 0
            if (pre == 0) pre = 1;
            if (suff == 0) suff = 1;

            pre *= arr[i];             // left-to-right prefix product
            suff *= arr[n - i - 1];    // right-to-left suffix product

            // take max of prefix and suffix and update answer
            ans = Math.max(ans, Math.max(pre, suff));
        }

        return ans;
    }

    /*
    🧠 Approach:
    This uses two passes:
    - Left-to-right: tracks product of prefix.
    - Right-to-left: tracks product of suffix.
    Reset to 1 when 0 is encountered to avoid zeroing effect.
    Handles negative numbers by trying both directions.

    ⏱ Time Complexity: O(n)
    ⏳ Space Complexity: O(1)
    */


    // 🟢 Optimal DP-like approach using Kadane’s Variant - Method 4
    static int maxProductSubArray4(int arr[]) {
        // prod1 = max product ending at current index
        // prod2 = min product ending at current index
        // result = maximum product so far
        int prod1 = arr[0], prod2 = arr[0], result = arr[0];

        for (int i = 1; i < arr.length; i++) {
            int curr = arr[i];

            // Temporary variable needed as prod1 and prod2 will be updated
            int temp = Math.max(curr, Math.max(prod1 * curr, prod2 * curr));

            // Update prod2 to store min product ending here
            prod2 = Math.min(curr, Math.min(prod1 * curr, prod2 * curr));

            // Update prod1 to max product ending here
            prod1 = temp;

            // Update global max product
            result = Math.max(result, prod1);
        }

        return result;
    }

    /*
    🧠 Approach:
    Similar to Kadane’s Algorithm, but tracks both:
    - Maximum product so far (because negative * negative = positive)
    - Minimum product so far (needed when negative numbers flip signs)

    This handles all cases in a single pass.

    ⏱ Time Complexity: O(n)
    ⏳ Space Complexity: O(1)
    */


    // 🔍 Test Driver
    public static void main(String[] args) {
        int nums[] = {1, 2, -3, 0, -4, -5};

        // You can replace with any version of maxProductSubArray
        int answer = maxProductSubArray4(nums);  // Best optimal approach

        System.out.print("The maximum product subarray is: " + answer);
    }
}
