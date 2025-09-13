package com.questions.strivers.recursionbacktracking.medium;

/*
Problem Statement:
------------------
The Josephus Problem is a famous theoretical problem:
- There are N people standing in a circle, numbered from 1 to N.
- Starting from the first person, every K-th person is eliminated in the circle.
- This elimination continues until only one person remains.
- The task is to find the position of this last remaining person (safe position).

Example:
--------
Input: N = 7, K = 3
Elimination order: 3 → 6 → 2 → 7 → 5 → 1
Safe position = 4

Approach:
---------
We use recursion and mathematical induction.

1. Base Case:
   - If only one person remains (n = 1), the safe position is 0
     (0-based index, which corresponds to position 1 in 1-based index).

2. Recursive Case:
   - Suppose we know the safe position for (n-1) people.
   - When eliminating every k-th person in n people:
     new_position = (previous_safe_position + k) % n

   This recurrence ensures correct positioning after each elimination.

3. Convert final answer to 1-based index (since people are usually numbered 1...N).

Time Complexity:
----------------
- Each recursive call reduces n by 1.
- Total recursive calls = O(n).
- Hence, Time Complexity = O(n).

Space Complexity:
-----------------
- Recursive stack depth = O(n).
- No extra data structures used.
- Hence, Space Complexity = O(n).
*/

public class JosephusProblem {

    // Recursive function to find the safe position (0-based index)
    public static int josephus(int n, int k) {
        // Base case: when only one person is left, return 0 (safe position in 0-based indexing)
        if (n == 1) {
            return 0;
        }

        // Recursive case:
        // Safe position formula:
        // (safe position of (n-1) people + k) % n
        return (josephus(n - 1, k) + k) % n;
    }

    public static void main(String[] args) {
        int n = 7;  // Number of people in the circle
        int k = 3;  // Step size (every 3rd person is eliminated)

        // Convert result from 0-based index to 1-based index by adding +1
        int safePosition = josephus(n, k) + 1;

        System.out.println("The safe position is: " + safePosition);
    }
}
