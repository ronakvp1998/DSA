package com.questions.strivers.greedyalgorithm.easy;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: ASSIGN COOKIES (LeetCode 455)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Assume you are an awesome parent and want to give your children some cookies. But, you should
 * give each child at most one cookie.
 * * Each child i has a greed factor g[i], which is the minimum size of a cookie that the child
 * will be content with; and each cookie j has a size s[j]. If s[j] >= g[i], we can assign the
 * cookie j to the child i, and the child i will be content.
 * * Maximize the number of your content children and output the maximum number.
 *
 * EXAMPLE 1:
 * Input: student = [1,2,3], cookie = [1,1]
 * Output: 1
 * Explanation: You have 3 children and 2 cookies. The greed factors of 3 children are 1, 2, 3.
 * You have 2 cookies of size 1. You can only satisfy the child with greed factor 1.
 *
 * EXAMPLE 2:
 * Input: student = [1,2], cookie = [1,2,3]
 * Output: 2
 * Explanation: You have 2 children and 3 cookies. You can satisfy both children.
 * ==================================================================================================
 */
public class AssignCookies {

    public static void main(String[] args) {
        int[] student = {1, 2, 3};
        int[] cookie = {1, 1};

        System.out.println("Students Greed: " + Arrays.toString(student));
        System.out.println("Cookie Sizes  : " + Arrays.toString(cookie));
        System.out.println("--------------------------------------------------");
        System.out.println("Max Content Students: " + findContentChildren(student, cookie));

        System.out.println("\nExample 2:");
        int[] student2 = {1, 2};
        int[] cookie2 = {1, 2, 3};
        System.out.println("Max Content Students: " + findContentChildren(student2, cookie2));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH: GREEDY + TWO POINTERS
     * ----------------------------------------------------------------------
     * LOGIC:
     * - Sort both arrays.
     * - We want to satisfy the least greedy children with the smallest possible cookies.
     * - If the current cookie is big enough, the child is happy (move both pointers).
     * - If the current cookie is too small, we just check the next cookie (move cookie pointer).
     *
     * COMPLEXITY:
     * - Time: O(N log N + M log M), where N is the number of students and M is the number of cookies.
     * The dominating factor is the sorting step. The two-pointer traversal takes O(N + M).
     * - Space: O(1) auxiliary space (or O(log N) / O(N) depending on the sorting algorithm implementation in Java).
     */
    public static int findContentChildren(int[] student, int[] cookie) {
        // 1. Sort both arrays to apply the greedy strategy
        Arrays.sort(student);
        Arrays.sort(cookie);

        int sPtr = 0; // Pointer for students
        int cPtr = 0; // Pointer for cookies

        // 2. Traverse while we have students and cookies left to check
        while (sPtr < student.length && cPtr < cookie.length) {

            // Check if the current cookie is large enough for the current student
            if (cookie[cPtr] >= student[sPtr]) {
                // The student is satisfied! Move to the next student.
                sPtr++;
            }

            // Whether the cookie was used or was too small, we move to the next cookie.
            // - If used, we need a new cookie for the next student.
            // - If too small, this cookie can't satisfy this student (or any more greedy student),
            //   so we must discard it and try a bigger one.
            cPtr++;
        }

        // The student pointer 'sPtr' implicitly counts the number of satisfied students.
        // Because we only increment it when a student gets a cookie.
        return sPtr;
    }
}