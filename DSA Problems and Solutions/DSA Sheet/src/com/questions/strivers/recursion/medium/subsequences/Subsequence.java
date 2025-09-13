package com.questions.strivers.recursion.medium.subsequences;

import java.util.ArrayList;

public class Subsequence {

    public static void main(String[] args) {
        int arr[] = {1,2,1};
        int k = 2;
        // Example usage
        // subsequences3(0,arr,new ArrayList<>(),0,k);  // print any one subsequence
        System.out.println(subsequences4(0,arr,0,k,0)); // count subsequences with sum = k
    }

    /*
     Problem Statement:
     -----------------
     Given an array of integers, you need to generate subsequences and perform
     different tasks:
        1. Print all subsequences.
        2. Print all subsequences whose sum = k.
        3. Print any one subsequence whose sum = k.
        4. Count all subsequences whose sum = k.

     Approach:
     ---------
     - Use recursion with backtracking to explore all possible subsequences.
     - At each index, we have 2 choices:
          a) Pick the current element.
          b) Do not pick the current element.
     - Base case is reached when 'index == arr.length'.
     - Depending on the problem (print/count), handle the base case accordingly.
    */

    // 4. Count all the subsequences with sum = k
    /**
     * Function: Count all subsequences with sum = k
     *
     * Time Complexity: O(2^n)
     * - Each element has 2 choices (pick / not pick).
     * - We only count, no extra work per subsequence.
     *
     * Space Complexity: O(n)
     * - Recursion stack depth is at most n.
     */
    public static int subsequences4(int index, int arr[], int sum, int k, int count) {
        // Base case: reached end of array
        if(index == arr.length){
            if(sum == k){ // subsequence found
                count++;
            }
            return count;
        }
        // Pick the element
        int l = subsequences4(index+1, arr, sum + arr[index], k, count);
        // Do not pick the element
        int r = subsequences4(index+1, arr, sum, k, count);

        return l + r; // total count
    }

    // 3. Print any ONE subsequence with sum = k
    /**
     * Function: Print ANY ONE subsequence with sum = k
     *
     * Time Complexity: O(2^n * n)
     * - Worst case explores all subsequences.
     * - Each subsequence may take up to O(n) to print.
     * - May terminate early when one valid subsequence is found.
     *
     * Space Complexity: O(n)
     * - Recursion depth = n.
     * - 'ds' list can hold at most n elements.
     */
    public static boolean subsequences3(int index, int arr[],
                                        ArrayList<Integer> ds, int sum, int k) {
        if(index == arr.length){
            if(sum == k){
                System.out.println(ds); // print the found subsequence
                return true; // stop further recursion
            }else{
                return false;
            }
        }
        // Pick current element
        sum = sum + arr[index];
        ds.add(arr[index]);
        if(subsequences3(index+1, arr, ds, sum, k)){
            return true;
        }

        // Backtrack (remove picked element)
        sum = sum - arr[index];
        ds.remove(ds.size()-1);

        // Do not pick current element
        if(subsequences3(index+1, arr, ds, sum, k)){
            return true;
        }
        return false;
    }

    // 2. Print ALL subsequences with sum = k
    /**
     * Function: Print ALL subsequences with sum = k
     *
     * Time Complexity: O(2^n * n)
     * - Each of the 2^n subsequences may take O(n) to copy/print.
     *
     * Space Complexity: O(2^n * n)
     * - Recursion depth = O(n).
     * - 'ds' temporary list = O(n).
     * - Storing all subsequences = O(2^n * n).
     */
    public static void subsequence2(int index, int arr[], ArrayList<Integer> ds,
                                    ArrayList<ArrayList<Integer>> ans, int sum, int k) {
        if(index == arr.length){
            if(sum == k){
                ans.add(new ArrayList<>(ds)); // store subsequence
            }
            return;
        }
        // Pick current element
        sum = sum + arr[index];
        ds.add(arr[index]);
        subsequence2(index+1, arr, ds, ans, sum, k);

        // Backtrack (remove picked element)
        ds.remove(ds.size()-1);
        sum = sum - arr[index];

        // Do not pick current element
        subsequence2(index+1, arr, ds, ans, sum, k);
    }

    // 1. Print ALL subsequences
    /**
     * Function: Print ALL subsequences
     *
     * Time Complexity: O(2^n * n)
     * - 2^n subsequences generated.
     * - Each subsequence takes O(n) to copy into ans.
     *
     * Space Complexity: O(2^n * n)
     * - Recursion depth = O(n).
     * - 'ds' temporary list = O(n).
     * - Storing all subsequences = O(2^n * n).
     */
    public static void subsequence1(int index, int arr[], ArrayList<Integer> ds,
                                    ArrayList<ArrayList<Integer>> ans) {
        if(index == arr.length){
            ans.add(new ArrayList<>(ds)); // store subsequence
            return;
        }
        // Pick current element
        ds.add(arr[index]);
        subsequence1(index+1, arr, ds, ans);

        // Backtrack (remove picked element)
        ds.remove(ds.size()-1);

        // Do not pick current element
        subsequence1(index+1, arr, ds, ans);
    }
}

/*
Time Complexity Analysis:
-------------------------
- For n elements, each element has 2 choices (pick or not pick).
- Therefore, total subsequences = 2^n.
- Traversing each subsequence takes O(n) in worst case when printing.
1) subsequence1 → O(2^n * n)
2) subsequence2 → O(2^n * n)
3) subsequences3 → O(2^n * n) but may stop early once one valid subsequence is found.
4) subsequences4 → O(2^n) (only counts, no storage/printing).

Space Complexity Analysis:
--------------------------
- Recursive call stack depth = O(n).
- Temporary list 'ds' can store up to n elements.
- For storing all subsequences → O(2^n * n).
- If only counting (subsequences4) → O(n).
*/
