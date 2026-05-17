package com.questions.strivers.heaps.mediumproblems;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 621. Task Scheduler (Medium)
 * ==================================================================================================
 * Given a characters array tasks, representing the tasks a CPU needs to do, where each
 * letter represents a different task. Tasks could be done in any order. Each task is
 * done in one unit of time. For each unit of time, the CPU could complete either one
 * task or just be idle.
 *
 * However, there is a non-negative integer n that represents the cooldown period between
 * two same tasks (i.e., because of cooldown period n, there must be at least n units of
 * time between any two same tasks).
 *
 * Return the minimum number of units of time that the CPU will take to finish all tasks.
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE / SIMULATION (Max-Heap + Queue)
 * ==================================================================================================
 * 1. Count frequencies of each task.
 * 2. Push frequencies into a Max-Heap.
 * 3. In each cycle of length (n + 1):
 * - Pull tasks from heap and execute them.
 * - Store executed tasks in a temporary list to "cool down".
 * - After the cycle, push tasks back into the heap if they still have remaining units.
 * Drawback: Complex to implement and slower due to heap operations for every time unit.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Greedy Mathematics)
 * ==================================================================================================
 * The total time is dominated by the task with the maximum frequency (f_max).
 * 1. Identify the maximum frequency (maxFreq).
 * 2. The number of "blocks" created by the max frequency task is (maxFreq - 1).
 * 3. Each block has length (n + 1).
 * 4. Total time = (maxFreq - 1) * (n + 1) + (number of tasks with max frequency).
 * 5. If the number of tasks is greater than this calculated value, the answer is tasks.length.
 * ==================================================================================================
 */

import java.util.*;

public class TaskScheduler {

    public static void main(String[] args) {
        char[] tasks1 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n1 = 2;
        System.out.println("Test Case 1 (n=2): " + leastIntervalOptimal(tasks1, n1)); // Output: 8

        char[] tasks2 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n2 = 0;
        System.out.println("Test Case 2 (n=0): " + leastIntervalOptimal(tasks2, n2)); // Output: 6
    }

    /**
     * OPTIMAL APPROACH: Mathematical Greedy Calculation
     * Time Complexity: O(N) where N is number of tasks (to count frequencies)
     * Space Complexity: O(1) as we only store 26 task frequencies
     */
    public static int leastIntervalOptimal(char[] tasks, int n) {
        // 1. Count frequency of each task
        int[] freq = new int[26];
        for (char c : tasks) {
            freq[c - 'A']++;
        }

        // 2. Find the maximum frequency
        Arrays.sort(freq);
        int maxFreq = freq[25];

        // 3. Count how many tasks have that same maximum frequency
        // (e.g., if A and B both appear 3 times)
        int maxFreqCount = 0;
        for (int f : freq) {
            if (f == maxFreq) {
                maxFreqCount++;
            }
        }

        // 4. Calculate the minimum intervals using the formula:
        // (Max_Freq - 1) * (n + 1) + Count_of_Max_Freq_Tasks
        int partCount = maxFreq - 1;
        int partLength = n + 1;
        int minIntervals = partCount * partLength + maxFreqCount;

        // 5. If the calculated slots are fewer than the total tasks,
        // it means we have enough tasks to fill the idle slots perfectly.
        return Math.max(tasks.length, minIntervals);
    }

    /**
     * SIMULATION APPROACH: Max-Heap
     * Time Complexity: O(Time_Units * log 26)
     */
    public static int leastIntervalHeap(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char c : tasks) freq[c - 'A']++;

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int f : freq) if (f > 0) maxHeap.add(f);

        int time = 0;
        while (!maxHeap.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            // Try to fill a cycle of (n + 1) slots
            for (int i = 0; i <= n; i++) {
                if (!maxHeap.isEmpty()) {
                    temp.add(maxHeap.poll() - 1);
                }
                time++;
                if (maxHeap.isEmpty() && temp.stream().allMatch(x -> x <= 0)) break;
            }
            // Add remaining tasks back to heap
            for (int f : temp) {
                if (f > 0) maxHeap.add(f);
            }
        }
        return time;
    }
}