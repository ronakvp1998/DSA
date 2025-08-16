package com.questions.strivers.binarysearch.bsonanswers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
Problem Statement:
We are given an array 'arr' where arr[i] = number of pages in the i-th book, and 'm' students.
We must allocate books such that:
1. Each student gets at least one book.
2. Each book is assigned to exactly one student.
3. Books allocated to each student must be contiguous.
Goal: Minimize the maximum number of pages assigned to any student.

---

Examples:

Example 1:
Input: n = 4, m = 2, arr = {12, 34, 67, 90}
Output: 113
Explanation: Allocation = {12, 34, 67} | {90}, max pages = 113.

Example 2:
Input: n = 5, m = 4, arr = {25, 46, 28, 49, 24}
Output: 71
Explanation: Allocation = {25,46} | {28} | {49} | {24}, max pages = 71.

---

Approach 1: Brute Force (findPages)
1. Answer lies between [max(arr), sum(arr)].
   - max(arr) = at least one student must get the largest book.
   - sum(arr) = one student takes all books.
2. Try each possible max page limit from low = max(arr) to high = sum(arr).
3. For each limit, check how many students are required using countStudents().
4. If exactly m students are needed, return that limit.
Time Complexity: O((sum-max) * n) → very slow for large input.
Space Complexity: O(1).

Approach 2: Optimized Binary Search (findPages2)
1. Same range [max(arr), sum(arr)].
2. Apply binary search on this range:
   - mid = candidate max pages per student.
   - Use countStudents2() to calculate how many students are needed.
   - If students > m → mid too small → increase low.
   - Else mid is feasible → reduce high.
3. Return low (smallest feasible maximum).
Time Complexity: O(n log(sum-max)).
Space Complexity: O(1).

Helper Function: countStudents / countStudents2
- Greedy allocation:
  Start with 1st student, keep assigning books until pages exceed the current limit,
  then allocate next student. Count how many students are required.
*/

public class AllocateMinNumPages {

    /**
     * Helper function for both approaches.
     * Returns how many students are needed if max pages allowed = 'pages'.
     */
    public static int countStudents(ArrayList<Integer> arr, int pages) {
        int n = arr.size();
        int students = 1;       // Start with first student
        long pagesStudent = 0;  // Pages assigned to current student

        for (int i = 0; i < n; i++) {
            if (pagesStudent + arr.get(i) <= pages) {
                // Assign current book to same student
                pagesStudent += arr.get(i);
            } else {
                // Assign to next student
                students++;
                pagesStudent = arr.get(i);
            }
        }
        return students;
    }

    /**
     * Brute Force Approach
     * Try all possible answers between [max(arr), sum(arr)].
     *
     * Time Complexity: O((sum-max) * n)
     * Space Complexity: O(1)
     */
    public static int findPages(ArrayList<Integer> arr, int n, int m) {
        if (m > n) return -1; // Impossible if more students than books

        int low = Collections.max(arr); // At least one student gets largest book
        int high = arr.stream().mapToInt(Integer::intValue).sum(); // One student gets all

        for (int pages = low; pages <= high; pages++) {
            if (countStudents(arr, pages) == m) {
                return pages;
            }
        }
        return low;
    }

    /**
     * Optimized Binary Search Approach
     * Search for the minimum maximum pages.
     *
     * Time Complexity: O(n log(sum-max))
     * Space Complexity: O(1)
     */
    public static int findPages2(ArrayList<Integer> arr, int n, int m) {
        if (m > n) return -1; // Impossible if more students than books

        int low = Collections.max(arr);
        int high = arr.stream().mapToInt(Integer::intValue).sum();

        // Binary Search
        while (low <= high) {
            int mid = (low + high) / 2;
            int students = countStudents(arr, mid);

            if (students > m) {
                // Too many students → increase max pages
                low = mid + 1;
            } else {
                // Valid allocation → try smaller pages
                high = mid - 1;
            }
        }
        return low; // Smallest feasible max pages
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(25, 46, 28, 49, 24));
        int n = 5;
        int m = 4;

        // Brute Force
        int ans1 = findPages(arr, n, m);
        System.out.println("Brute Force Answer: " + ans1);

        // Binary Search
        int ans2 = findPages2(arr, n, m);
        System.out.println("Binary Search Answer: " + ans2);
    }
}
