package strivers.linkedlist.ll.mediumProblemsLL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: Add One to a Number Represented by Linked List
 * * * Problem Statement:
 * A number is represented by a linked list, where each node contains a single
 * digit. The most significant digit is at the head of the list. Add 1 to the
 * number and return the head of the modified linked list.
 * * * Example 1:
 * Input: head = [1, 2, 3]
 * Output: [1, 2, 4]
 * Explanation: 123 + 1 = 124.
 * * * Example 2:
 * Input: head = [9, 9, 9]
 * Output: [1, 0, 0, 0]
 * Explanation: 999 + 1 = 1000.
 * * * Example 3:
 * Input: head = [0]
 * Output: [1]
 * Explanation: 0 + 1 = 1.
 * * * Constraints:
 * - 1 <= Number of nodes <= 10^5
 * - 0 <= Node.val <= 9
 * - The linked list does not contain any leading zeros, except when the list
 * represents the number 0 itself.
 * ============================================================================
 */
public class AddOneToLinkedListMasterclass {

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
     * Phase 1: Optimal Approach - Sentinel Node / Rightmost Non-9 Tracking
     * * * Detailed Intuition:
     * When adding 1 to a number, the only digits that change are the rightmost
     * 9s (which become 0s) and the first non-9 digit to the left of them (which
     * increments by 1).
     * Instead of reversing the list or using a recursion stack, we can simply
     * iterate through the list and keep a pointer to the rightmost node that is
     * NOT a 9.
     * * Once we reach the end of the list:
     * 1. If all digits were 9s (our pointer is still null), we prepend a new
     * node with value '1' and change all original 9s to 0s.
     * 2. Otherwise, we increment the identified non-9 node and change all
     * subsequent nodes to 0s.
     * * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We traverse the linked list twice at most (once to find the non-9, once
     * to update the trailing 9s). This gives a strict linear bound.
     * - Space Complexity: O(1) auxiliary heap/stack space.
     * We only use a couple of pointers. No recursion stack or list reversal
     * overhead is incurred.
     * ============================================================================
     */
    public ListNode addOneOptimal(ListNode head) {
        ListNode notNine = null;
        ListNode curr = head;

        // Find the rightmost node that is not a 9
        while (curr != null) {
            if (curr.val != 9) {
                notNine = curr;
            }
            curr = curr.next;
        }

        // Edge Case: The number is strictly made of 9s (e.g., 999)
        if (notNine == null) {
            ListNode newHead = new ListNode(1);
            newHead.next = head;

            // Turn all subsequent 9s into 0s
            curr = head;
            while (curr != null) {
                curr.val = 0;
                curr = curr.next;
            }
            return newHead;
        }

        // Standard Case: Increment the rightmost non-9 and cascade 0s
        notNine.val++;
        curr = notNine.next;
        while (curr != null) {
            curr.val = 0;
            curr = curr.next;
        }

        return head;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach - Reverse, Add, Reverse Back
     * * * Detailed Intuition:
     * The "Think it" stage usually prompts candidates to simulate manual addition.
     * Manual addition works from right to left (least significant to most
     * significant). Since it's a singly linked list, we can't iterate backward.
     * We reverse the list, add 1 with a carry variable, and then reverse the
     * list back to its original orientation.
     * * * Complexity Analysis:
     * - Time Complexity: O(N)
     * Reversing takes O(N), adding takes O(N), reversing back takes O(N).
     * Total Time: 3 * O(N) = O(N).
     * - Space Complexity: O(1) auxiliary heap/stack space.
     * Pointer manipulation requires constant extra space.
     * ============================================================================
     */
    public ListNode addOneBruteForce(ListNode head) {
        head = reverseList(head);
        ListNode curr = head;
        ListNode prev = null;
        int carry = 1;

        while (curr != null && carry > 0) {
            int sum = curr.val + carry;
            curr.val = sum % 10;
            carry = sum / 10;
            prev = curr;
            curr = curr.next;
        }

        // If a carry still remains after processing the whole list (e.g., 999)
        if (carry > 0) {
            prev.next = new ListNode(carry);
        }

        return reverseList(head);
    }

    // Helper method to reverse a singly linked list
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach - Elegant Recursion (Backtracking)
     * * * Detailed Intuition:
     * A recursive approach is another way to process the list from right to left.
     * We traverse to the very end of the list. The base case (reaching null)
     * returns a carry of `1`. As the call stack unwinds, we add the returning
     * carry to the current node's value, update the node, and return the new
     * carry up the chain.
     * * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We visit each node exactly once during the recursive descent and ascent.
     * - Space Complexity: O(N) auxiliary stack space.
     * The depth of the recursion tree equals the length of the list. In extreme
     * cases (list length 10^5), this WILL cause a StackOverflowError, making
     * Phase 1 the clear optimal choice for production code.
     * ============================================================================
     */
    public ListNode addOneRecursive(ListNode head) {
        int carry = helper(head);

        if (carry > 0) {
            ListNode newHead = new ListNode(carry);
            newHead.next = head;
            return newHead;
        }

        return head;
    }

    private int helper(ListNode node) {
        if (node == null) {
            return 1; // Our initial "add one" carry
        }

        int sum = node.val + helper(node.next);
        node.val = sum % 10;
        return sum / 10;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        AddOneToLinkedListMasterclass solution = new AddOneToLinkedListMasterclass();

        // Test Cases Definition
        int[][] listValues = {
                {1, 2, 3},          // Standard case
                {9, 9, 9},          // Cascade carry case
                {0},                // Edge Case: Zero
                {8, 9, 9, 9},       // Partial cascade
                {1, 9, 8, 9}        // Carry stops midway
        };

        System.out.println("--- Starting Test Suite for Add One to Linked List ---");

        for (int i = 0; i < listValues.length; i++) {
            int[] arr = listValues[i];

            System.out.println("\nTest Case " + (i + 1) + ": head = " + Arrays.toString(arr));

            // Deep copy lists so mutable approaches do not interfere with each other
            ListNode head1 = buildList(arr);
            ListNode head2 = buildList(arr);
            ListNode head3 = buildList(arr);

            // Test Phase 1: Optimal
            ListNode res1 = solution.addOneOptimal(head1);
            System.out.println("Optimal Approach   (O(1) Space) : " + serializeList(res1));

            // Test Phase 2: Brute Force (Reverse)
            ListNode res2 = solution.addOneBruteForce(head2);
            System.out.println("Brute Force        (Reverse)    : " + serializeList(res2));

            // Test Phase 3: Recursive
            ListNode res3 = solution.addOneRecursive(head3);
            System.out.println("Recursive Approach (O(N) Space) : " + serializeList(res3));
        }
    }

    /**
     * Utility method: Builds a linked list from an array.
     */
    private static ListNode buildList(int[] nums) {
        if (nums == null || nums.length == 0) return null;
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