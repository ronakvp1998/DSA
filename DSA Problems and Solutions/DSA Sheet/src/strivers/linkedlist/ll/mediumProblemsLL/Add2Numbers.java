package strivers.linkedlist.ll.mediumProblemsLL;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 2. Add Two Numbers (Medium)
 * * Problem Statement:
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order, and each of their nodes contains a
 * single digit. Add the two numbers and return the sum as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the
 * number 0 itself.
 * * Example 1:
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 * * Example 2:
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 * * Example 3:
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 * * Constraints:
 * - The number of nodes in each linked list is in the range [1, 100].
 * - 0 <= Node.val <= 9
 * - It is guaranteed that the list represents a number that does not have leading zeros.
 * ============================================================================
 */
public class Add2Numbers {

    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * ============================================================================
     * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
     * ============================================================================
     * Phase 1: Optimal Approach - One-pass Iterative Simulation
     * * Detailed Intuition:
     * Since the numbers are stored in reverse order, the head of the linked list
     * represents the least significant digit (1s place). This perfectly aligns with
     * how we perform manual arithmetic addition (from right to left).
     * We can traverse both lists simultaneously, adding the values of the corresponding
     * nodes along with any 'carry' from the previous column.
     * * To cleanly handle the initialization of our result list and avoid edge-case
     * null checks on the head, we use a 'Dummy Head' node. We iterate as long as
     * there are nodes remaining in either l1 or l2, OR if there is a lingering carry
     * left over (e.g., 9 + 9 = 18, we need a new node for the 1).
     * * Complexity Analysis:
     * - Time Complexity: O(max(N, M))
     * Where N and M are the lengths of l1 and l2. We traverse the longest list
     * exactly once.
     * - Space Complexity: O(1) auxiliary space.
     * (Note: The space required for the output list is O(max(N, M)), but auxiliary
     * heap/stack space used by our algorithm is strictly O(1)).
     * ============================================================================
     */
    public ListNode addTwoNumbersOptimal(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode current = dummyHead;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;

            int sum = x + y + carry;
            carry = sum / 10;

            current.next = new ListNode(sum % 10);
            current = current.next;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return dummyHead.next;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach - Parse, Add, and Rebuild
     * * Detailed Intuition:
     * The "Think it" stage usually prompts candidates to try extracting the numbers,
     * adding them using standard math operations, and then building a new list.
     * However, the constraints state list length can be up to 100. A 100-digit number
     * wildly exceeds the capacity of standard primitives (long max is ~19 digits).
     * Thus, we must use Java's BigInteger. We extract the digits, reverse them to
     * form standard string representations, parse to BigInteger, sum them, and
     * rebuild the list.
     * * Complexity Analysis:
     * - Time Complexity: O(N + M)
     * Parsing nodes to strings, instantiating BigIntegers, performing arbitrary
     * precision addition, and rebuilding lists involves heavy constant factors.
     * - Space Complexity: O(N + M) auxiliary space.
     * StringBuilders and BigInteger objects consume heap space proportional to
     * the size of the input lists.
     * ============================================================================
     */
    public ListNode addTwoNumbersBruteForce(ListNode l1, ListNode l2) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        while (l1 != null) {
            sb1.append(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            sb2.append(l2.val);
            l2 = l2.next;
        }

        // Reverse because lists are given in reverse order
        BigInteger num1 = new BigInteger(sb1.reverse().toString());
        BigInteger num2 = new BigInteger(sb2.reverse().toString());

        BigInteger sum = num1.add(num2);
        String sumStr = sum.toString();

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // Read the sum string backwards to create the reversed linked list
        for (int i = sumStr.length() - 1; i >= 0; i--) {
            current.next = new ListNode(Character.getNumericValue(sumStr.charAt(i)));
            current = current.next;
        }

        return dummy.next;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach - Elegant Recursion
     * * Detailed Intuition:
     * Addition inherently contains overlapping subproblems: the addition of the
     * next column is just the same problem but with different inputs and a carry.
     * We can express this recursively. The base case is when both nodes are null
     * AND the carry is 0. Otherwise, we calculate the current digit, create a node,
     * and recursively attach the rest of the sum to its 'next' pointer.
     * * Complexity Analysis:
     * - Time Complexity: O(max(N, M))
     * We visit each node exactly once.
     * - Space Complexity: O(max(N, M)) auxiliary stack space.
     * The depth of the recursion tree will go as deep as the longest list
     * plus one (if there is a final carry). This can cause StackOverflowErrors
     * on massive lists, which makes Phase 1 the strictly better choice.
     * ============================================================================
     */
    public ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2) {
        return helper(l1, l2, 0);
    }

    private ListNode helper(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        int x = (l1 != null) ? l1.val : 0;
        int y = (l2 != null) ? l2.val : 0;

        int sum = x + y + carry;
        ListNode node = new ListNode(sum % 10);

        node.next = helper(
                (l1 != null) ? l1.next : null,
                (l2 != null) ? l2.next : null,
                sum / 10
        );

        return node;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Includes utility methods utilizing Java 8 Stream API for rapid list generation
     * and stringification.
     */
    public static void main(String[] args) {
        Add2Numbers solution = new Add2Numbers();

        // Test Cases Definition
        int[][] testCase1 = {{2, 4, 3}, {5, 6, 4}};
        int[][] testCase2 = {{0}, {0}};
        int[][] testCase3 = {{9, 9, 9, 9, 9, 9, 9}, {9, 9, 9, 9}};

        // Edge Case: Zero value inputs with extreme length disparages
        int[][] testCase4 = {{0, 0, 0, 0, 1}, {0, 0, 1}};

        List<int[][]> allTests = Arrays.asList(testCase1, testCase2, testCase3, testCase4);

        System.out.println("--- Starting Test Suite for Add Two Numbers ---");

        for (int i = 0; i < allTests.size(); i++) {
            int[] arr1 = allTests.get(i)[0];
            int[] arr2 = allTests.get(i)[1];

            System.out.println("\nTest Case " + (i + 1) + ": l1 = " + Arrays.toString(arr1) + ", l2 = " + Arrays.toString(arr2));

            // Test Phase 1: Optimal
            ListNode res1 = solution.addTwoNumbersOptimal(buildList(arr1), buildList(arr2));
            System.out.println("Optimal Approach   : " + serializeList(res1));

            // Test Phase 2: Brute Force
            ListNode res2 = solution.addTwoNumbersBruteForce(buildList(arr1), buildList(arr2));
            System.out.println("Brute Force (BigInt): " + serializeList(res2));

            // Test Phase 3: Recursive
            ListNode res3 = solution.addTwoNumbersRecursive(buildList(arr1), buildList(arr2));
            System.out.println("Recursive Approach : " + serializeList(res3));
        }
    }

    /**
     * Utility method: Builds a linked list from an array using traditional
     * iteration (Stream mapping to objects is slightly convoluted for linked references).
     */
    private static ListNode buildList(int[] nums) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int num : nums) {
            curr.next = new ListNode(num);
            curr = curr.next;
        }
        return dummy.next;
    }

    /**
     * Utility method: Serializes a linked list back into a String format
     * utilizing Java 8 Collections & Stream API.
     */
    private static String serializeList(ListNode head) {
        List<Integer> values = new ArrayList<>();
        while (head != null) {
            values.add(head.val);
            head = head.next;
        }

        return "[" + values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "]";
    }
}