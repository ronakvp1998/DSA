package strivers.linkedlist.ll.mediumProblemsLL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 25. Reverse Nodes in k-Group (Hard)
 * * * Problem Statement:
 * Given the head of a linked list, reverse the nodes of the list k at a time,
 * and return the modified list.
 * * k is a positive integer and is less than or equal to the length of the linked
 * list. If the number of nodes is not a multiple of k then left-out nodes, in
 * the end, should remain as it is.
 * * You may not alter the values in the list's nodes, only nodes themselves may
 * be changed.
 * * * Example 1:
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [2,1,4,3,5]
 * * * Example 2:
 * Input: head = [1,2,3,4,5], k = 3
 * Output: [3,2,1,4,5]
 * * * Constraints:
 * - The number of nodes in the list is n.
 * - 1 <= k <= n <= 5000
 * - 0 <= Node.val <= 1000
 * * * Follow-up:
 * Can you solve the problem in O(1) extra memory space?
 * ============================================================================
 */
public class ReverseKGroupMasterclass {

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
     * Phase 1: Optimal Approach - One-pass Iterative Simulation (O(1) Space)
     * * * Detailed Intuition:
     * To satisfy the follow-up of O(1) extra space, we must handle the reversal
     * iteratively. The strategy relies on first counting the total nodes to know
     * exactly how many k-sized groups we have.
     * We use a `dummy` node to safely handle the head of the list. For each group
     * of size k, we perform a standard linked-list reversal. The critical part is
     * re-linking the newly reversed group back to the main list. We maintain a
     * `prevGroupTail` pointer to anchor the previous part of the list to the newly
     * reversed head of the current group.
     * * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We traverse the list once to count the nodes, and effectively once more to
     * reverse the nodes in chunks. Overall time is linear.
     * - Space Complexity: O(1) auxiliary heap/stack space.
     * We only use a few pointer variables regardless of the input size N.
     * ============================================================================
     */
    public ListNode reverseKGroupOptimal(ListNode head, int k) {
        if (head == null || k == 1) return head;

        // Step 1: Count total nodes
        int count = 0;
        ListNode curr = head;
        while (curr != null) {
            count++;
            curr = curr.next;
        }

        // Step 2: Dummy node initialization
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroupTail = dummy;
        curr = head;

        // Step 3: Reverse k nodes at a time
        while (count >= k) {
            ListNode prev = null;
            ListNode next = null;
            ListNode currentGroupTail = curr; // The first node becomes the tail after reversal

            // Standard reversal for k nodes
            for (int i = 0; i < k; i++) {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            // Re-wire the connections
            prevGroupTail.next = prev; // Connect the previous part to the new head of this group
            currentGroupTail.next = curr; // Connect the new tail to the next part of the list

            // Move our anchor for the next iteration
            prevGroupTail = currentGroupTail;
            count -= k;
        }

        return dummy.next;
    }

    /**
     * ============================================================================
     * Phase 2: Recursive Approach - The "Think it" Stage
     * * * Detailed Intuition:
     * During an interview, the recursive approach is often the easiest to map out
     * conceptually. The problem has a clear sub-problem structure: Reverse the
     * first k nodes, and point the tail of that reversed group to the result of
     * recursively processing the rest of the list. The base case is when fewer
     * than k nodes remain, in which case we simply return the head unmodified.
     * * * Complexity Analysis:
     * - Time Complexity: O(N)
     * Every node is visited twice (once to check bounds, once to reverse).
     * - Space Complexity: O(N / k) auxiliary stack space.
     * The recursion goes N/k levels deep. This violates the follow-up requirement
     * for O(1) memory, but is an excellent stepping stone algorithm.
     * ============================================================================
     */
    public ListNode reverseKGroupRecursive(ListNode head, int k) {
        // Check if we have k nodes left to reverse
        ListNode curr = head;
        int count = 0;
        while (curr != null && count != k) {
            curr = curr.next;
            count++;
        }

        // If we have k nodes, reverse them
        if (count == k) {
            curr = reverseKGroupRecursive(curr, k); // recursive call for the next segment

            // Reverse current k-group
            ListNode prev = curr; // prev is initialized to the head of the next reversed segment
            ListNode current = head;
            for (int i = 0; i < k; i++) {
                ListNode next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            }
            return prev; // prev is the new head of this k-group
        }

        // If less than k nodes remain, return head as is
        return head;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach - Stack Based
     * * * Detailed Intuition:
     * A stack naturally reverses order (LIFO). We can push k nodes onto a Stack.
     * Once the stack has k nodes, we pop them off one by one, linking them
     * together to form the reversed list. If we reach the end and the stack has
     * fewer than k nodes, we link them in their original order.
     * * * Complexity Analysis:
     * - Time Complexity: O(N)
     * Pushing and popping each node takes O(1) time.
     * - Space Complexity: O(k) auxiliary heap space.
     * We use a Stack to hold exactly k nodes at any given time.
     * ============================================================================
     */
    public ListNode reverseKGroupStack(ListNode head, int k) {
        if (head == null || k == 1) return head;

        Stack<ListNode> stack = new Stack<>();
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;
        ListNode curr = head;

        while (curr != null) {
            ListNode temp = curr;
            int count = 0;

            // Push k nodes to stack
            while (curr != null && count < k) {
                stack.push(curr);
                curr = curr.next;
                count++;
            }

            // If we collected k nodes, pop to reverse
            if (count == k) {
                while (!stack.isEmpty()) {
                    prev.next = stack.pop();
                    prev = prev.next;
                }
                prev.next = curr; // temporarily connect to the rest
            } else {
                // If fewer than k nodes, attach the original order segment and break
                prev.next = temp;
            }
        }

        return dummy.next;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        ReverseKGroupMasterclass solution = new ReverseKGroupMasterclass();

        // Test Cases Definition
        int[][] listValues = {
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5, 6, 7, 8}, // Clean multiple of k
                {1, 2},                   // Edge: List smaller than k
                {1}                       // Edge: Single element
        };
        int[] kValues = {2, 3, 4, 3, 1};

        System.out.println("--- Starting Test Suite for Reverse Nodes in k-Group ---");

        for (int i = 0; i < listValues.length; i++) {
            int[] arr = listValues[i];
            int k = kValues[i];

            System.out.println("\nTest Case " + (i + 1) + ": head = " + Arrays.toString(arr) + ", k = " + k);

            // Test Phase 1: Optimal
            ListNode res1 = solution.reverseKGroupOptimal(buildList(arr), k);
            System.out.println("Optimal Iterative (O(1) Space): " + serializeList(res1));

            // Test Phase 2: Recursive
            ListNode res2 = solution.reverseKGroupRecursive(buildList(arr), k);
            System.out.println("Recursive         (O(N/k) Space): " + serializeList(res2));

            // Test Phase 3: Stack
            ListNode res3 = solution.reverseKGroupStack(buildList(arr), k);
            System.out.println("Stack Based       (O(k) Space)  : " + serializeList(res3));
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