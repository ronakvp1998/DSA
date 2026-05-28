package strivers.linkedlist.ll.mediumProblemsLL;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 2095. Delete the Middle Node of a Linked List
 * Difficulty: Medium
 * * * Formal Problem Statement:
 * You are given the head of a linked list. Delete the middle node, and return
 * the head of the modified linked list.
 * The middle node of a linked list of size n is the ⌊n / 2⌋th node from the
 * start using 0-based indexing, where ⌊x⌋ denotes the largest integer less than
 * or equal to x.
 * For n = 1, 2, 3, 4, and 5, the middle nodes are 0, 1, 1, 2, and 2, respectively.
 * * * Constraints:
 * - The number of nodes in the list is in the range [1, 10^5].
 * - 1 <= Node.val <= 10^5
 * * * Example 1:
 * Input: head = [1,3,4,7,1,2,6]
 * Output: [1,3,4,1,2,6]
 * Explanation: Since n = 7, node 3 with value 7 is the middle node.
 * * * Example 2:
 * Input: head = [1,2,3,4]
 * Output: [1,2,4]
 * Explanation: For n = 4, node 2 with value 3 is the middle node.
 * * * Example 3:
 * Input: head = [2,1]
 * Output: [2]
 * Explanation: For n = 2, node 1 with value 1 is the middle node. Node 0 is the
 * only node remaining.
 * ============================================================================
 */

public class DeleteMiddleNode {

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
     * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problems)
     * ============================================================================
     * Phase 1: Optimal Approach - Fast and Slow Pointers (One Pass)
     * Phase 2: Brute Force Approach - Two Passes (Count Length, then Delete)
     * ============================================================================
     */

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Fast and Slow Pointers)
     * ============================================================================
     * Detailed Intuition:
     * To delete a node in a singly linked list, we must have a pointer to the node
     * strictly BEFORE the one we want to delete. We can achieve this in one pass
     * using the Fast and Slow Pointer technique.
     * By starting `slow` at the head and `fast` two steps ahead (at `head.next.next`),
     * when `fast` reaches the end of the list, `slow` will have advanced exactly to
     * the node immediately preceding the middle node. We then bypass the middle node.
     * * Complexity Analysis:
     * - Time O(N): We traverse the list only once. The fast pointer examines each
     * node at most once, resulting in O(N/2) operations, which simplifies to O(N).
     * - Space O(1): We only allocate a few pointers (`slow` and `fast`). Auxiliary
     * stack space is O(1) and heap space is O(1).
     * ============================================================================
     */
    public ListNode deleteMiddleOptimal(ListNode head) {
        // Edge case: Empty list or list with a single node
        if (head == null || head.next == null) {
            return null;
        }

        // Initialize slow at head and fast two steps ahead to offset the slow
        // pointer so it stops exactly one node before the middle.
        ListNode slow = head;
        ListNode fast = head.next.next;

        // Traverse until fast reaches the end
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Delete the middle node
        slow.next = slow.next.next;

        return head;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Two Passes)
     * ============================================================================
     * Detailed Intuition:
     * The most straightforward "Think it" approach. If we don't know where the middle
     * is, we first find the total length `n` of the linked list.
     * The middle node is at index `n / 2`. To delete it, we must traverse again to
     * index `(n / 2) - 1` and update its `next` pointer to skip the middle node.
     * * Complexity Analysis:
     * - Time O(N): We traverse the entire list once to find the length (N steps),
     * and then traverse it again to the middle (N/2 steps). Total operations are
     * 1.5 * N, which simplifies strictly to O(N) time complexity.
     * - Space O(1): We only use integer variables and pointers. Auxiliary stack
     * space is O(1) and heap space is O(1).
     * ============================================================================
     */
    public ListNode deleteMiddleBruteForce(ListNode head) {
        // Edge case: Empty list or list with a single node
        if (head == null || head.next == null) {
            return null;
        }

        int length = 0;
        ListNode current = head;

        // First Pass: Calculate length
        while (current != null) {
            length++;
            current = current.next;
        }

        // Calculate the target node index to stop at (one before the middle)
        int midIndex = length / 2;
        current = head;

        // Second Pass: Navigate to the node right before the middle one
        for (int i = 0; i < midIndex - 1; i++) {
            current = current.next;
        }

        // Skip the target middle node
        current.next = current.next.next;

        return head;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Thorough evaluation covering standard lists and critical edge cases like
     * single-node and two-node lists.
     */
    public static void main(String[] args) {
        DeleteMiddleNode solver = new DeleteMiddleNode();

        // Define Test Cases
        int[][] testLists = {
                {1, 3, 4, 7, 1, 2, 6}, // Example 1: Odd length (7)
                {1, 2, 3, 4},          // Example 2: Even length (4)
                {2, 1},                // Example 3: Edge case length (2)
                {5},                   // Edge case: Single element (1)
                {1, 2, 3, 4, 5, 6, 7, 8} // Even length (8)
        };

        System.out.println("--- 🤖 Running DSA Testing Suite ---\n");

        for (int i = 0; i < testLists.length; i++) {
            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Input List: " + Arrays.toString(testLists[i]));

            // Test 1: Optimal Approach
            ListNode head1 = buildList(testLists[i]);
            ListNode res1 = solver.deleteMiddleOptimal(head1);
            System.out.println("Optimal Result   : " + formatList(res1));

            // Test 2: Brute Force Approach
            ListNode head2 = buildList(testLists[i]);
            ListNode res2 = solver.deleteMiddleBruteForce(head2);
            System.out.println("Brute Force      : " + formatList(res2));

            System.out.println("-".repeat(50));
        }
    }

    /**
     * Utility method: Builds a linked list from an integer array using Java 8 Streams.
     */
    private static ListNode buildList(int[] arr) {
        if (arr == null || arr.length == 0) return null;

        ListNode dummy = new ListNode(0);
        ListNode[] current = { dummy }; // Array wrapper to allow mutation inside lambda

        Arrays.stream(arr).forEach(val -> {
            current[0].next = new ListNode(val);
            current[0] = current[0].next;
        });

        return dummy.next;
    }

    /**
     * Utility method: Formats a linked list into a readable string using Java 8 Streams.
     */
    private static String formatList(ListNode head) {
        if (head == null) return "[]";

        List<String> values = new ArrayList<>();
        while (head != null) {
            values.add(String.valueOf(head.val));
            head = head.next;
        }
        return "[" + values.stream().collect(Collectors.joining(", ")) + "]";
    }
}
