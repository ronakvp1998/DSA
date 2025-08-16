package com.questions.strivers.binarysearch.bsonanswers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
Problem Statement:
------------------
We are given an array of size 'N' where each element represents the length of a board.
We also have 'K' painters who will paint these boards. Each painter can only paint
continuous sections of boards, and 1 unit of board length takes 1 unit of time to paint.

We need to find the minimum possible time to paint all boards when painters work simultaneously.

Intuition:
----------
This problem is the same as "Allocate Books".
We want to divide the boards among 'K' painters such that:
1. Each painter gets continuous boards.
2. The maximum workload (time) among all painters is minimized.

Approach:
---------
1. The minimum time a painter must take = max(board length)
   (because a painter must at least paint the largest single board).
2. The maximum time possible = sum(all boards)
   (if only one painter paints everything).
3. We binary search between [low = max(board), high = sum(board)]
   to find the minimum possible maximum time.
4. For each mid value:
   - Check how many painters are required if max allowed time = mid.
   - If painters required <= K, it means we can try smaller max time → move left.
   - If painters required > K, we need more time → move right.

We solve this with two methods:
1. **Linear Search Method** (Brute Force) → O(N * sum(boards))
2. **Binary Search Method** (Optimal) → O(N * log(sum(boards)))
*/

public class PainterPartitionProblem {

    // Helper function to count how many painters are required if
    // each painter cannot exceed "time" amount of work.
    public static int countPainters(ArrayList<Integer> boards, int time) {
        int n = boards.size();
        int painters = 1; // at least one painter is needed
        long boardsPainter = 0; // workload of current painter

        for (int i = 0; i < n; i++) {
            // If adding current board does not exceed "time", assign it
            if (boardsPainter + boards.get(i) <= time) {
                boardsPainter += boards.get(i);
            } else {
                // Otherwise, assign to next painter
                painters++;
                boardsPainter = boards.get(i);
            }
        }
        return painters;
    }

    // Brute force approach (Linear Search)
    public static int findLargestMinDistance(ArrayList<Integer> boards, int k) {
        // Lower bound = max single board (cannot be less than this)
        int low = Collections.max(boards);
        // Upper bound = sum of all boards (one painter does everything)
        int high = boards.stream().mapToInt(Integer::intValue).sum();

        // Try every possible time between low and high
        for (int time = low; time <= high; time++) {
            if (countPainters(boards, time) <= k) {
                return time; // first feasible time is the answer
            }
        }
        return low;
    }

    // Helper function (same logic as above, for binary search version)
    public static int countPainters2(ArrayList<Integer> boards, int time) {
        int n = boards.size();
        int painters = 1;
        long boardsPainter = 0;
        for (int i = 0; i < n; i++) {
            if (boardsPainter + boards.get(i) <= time) {
                boardsPainter += boards.get(i);
            } else {
                painters++;
                boardsPainter = boards.get(i);
            }
        }
        return painters;
    }

    // Optimal approach using Binary Search
    public static int findLargestMinDistance2(ArrayList<Integer> boards, int k) {
        int low = Collections.max(boards);
        int high = boards.stream().mapToInt(Integer::intValue).sum();

        // Binary Search on answer space
        while (low <= high) {
            int mid = (low + high) / 2;
            int painters = countPainters2(boards, mid);

            if (painters > k) {
                // Too many painters needed → increase workload capacity
                low = mid + 1;
            } else {
                // Enough or fewer painters → try smaller workload
                high = mid - 1;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        ArrayList<Integer> boards = new ArrayList<>(Arrays.asList(10, 20, 30, 40));
        int k = 2;

        // Brute force
        int ans1 = findLargestMinDistance(boards, k);
        System.out.println("Brute Force Answer: " + ans1);

        // Binary search optimized
        int ans2 = findLargestMinDistance2(boards, k);
        System.out.println("Binary Search Answer: " + ans2);
    }
}

/*
Complexity Analysis:
--------------------
1. countPainters():
   - Time = O(N), iterates through boards
   - Space = O(1)

2. Brute Force (findLargestMinDistance):
   - Time = O(N * (sum(boards) - max(boards))) ≈ O(N * sum(boards))
   - Space = O(1)

3. Binary Search (findLargestMinDistance2):
   - Time = O(N * log(sum(boards) - max(boards)))
   - Space = O(1)

Clearly, the binary search approach is optimal.
*/
