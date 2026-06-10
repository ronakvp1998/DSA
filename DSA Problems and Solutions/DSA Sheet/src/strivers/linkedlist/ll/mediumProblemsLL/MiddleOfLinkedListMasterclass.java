package strivers.linkedlist.ll.mediumProblemsLL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 876. Middle of the Linked List (Easy)
 * * Formal problem statement:
 * Given the head of a singly linked list, return the middle node of the linked list.
 * If there are two middle nodes, return the second middle node.
 * * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [3,4,5]
 * Explanation: The middle node of the list is node 3.
 * * Example 2:
 * Input: head = [1,2,3,4,5,6]
 * Output: [4,5,6]
 * Explanation: Since the list has two middle nodes with values 3 and 4, we return the second one.
 * * Constraints:
 * - The number of nodes in the list is in the range [1, 100].
 * - 1 <= Node.val <= 100
 * ============================================================================
 */
public class MiddleOfLinkedListMasterclass {

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
     * Phase 1: Optimal Approach - Fast and Slow Pointers (Tortoise and Hare)
     * * Detailed Intuition:
     * This is the industry-standard approach for finding the middle of a linked list
     * in a single pass. We initialize two pointers, `slow` and `fast`, both starting
     * at the head. The `slow` pointer moves one step at a time, while the `fast`
     * pointer moves two steps. By the time the `fast` pointer reaches the end of the
     * list (either `fast` is null or `fast.next` is null), the `slow` pointer will
     * have traveled exactly half the distance, landing on the middle node.
     * Because we want the second middle node in an even-lengthed list, starting both
     * at the head perfectly lands `slow` on the second middle node when `fast`
     * safely hits `null`.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We traverse the linked list exactly once. The `fast` pointer takes N/2
     * iterations to reach the end.
     * - Space Complexity: O(1) auxiliary stack space.
     * We only allocate two pointers (`slow` and `fast`), regardless of the input
     * list's size. No heap space or recursion stack is used.
     * ============================================================================
     */
    public ListNode middleNodeOptimal(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach - Count and Traverse
     * * Detailed Intuition:
     * The "Think it" stage naturally leads to calculating the length of the list
     * first. Since a linked list does not store its size, we must traverse it
     * entirely to count the nodes. Once we have the total count `N`, we know the
     * middle node is at index `N / 2`. We then iterate from the head a second time,
     * stopping at the calculated index.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We do two passes: one full pass to count the nodes (N steps) and a second
     * half-pass to find the middle (N/2 steps). Total steps = 1.5N, which drops
     * the constant to O(N).
     * - Space Complexity: O(1) auxiliary stack space.
     * We only use integer counters and a single pointer for traversal.
     * ============================================================================
     */
    public ListNode middleNodeBruteForce(ListNode head) {
        int count = 0;
        ListNode current = head;

        // Pass 1: Count total nodes
        while (current != null) {
            count++;
            current = current.next;
        }

        // Pass 2: Traverse to the middle
        int middleIndex = count / 2;
        current = head;
        for (int i = 0; i < middleIndex; i++) {
            current = current.next;
        }

        return current;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach - Array Mapping
     * * Detailed Intuition:
     * Arrays give us O(1) constant time access to any index, which linked lists
     * lack. By traversing the linked list once and storing object references to
     * each node inside an ArrayList, we can simply return the node at index
     * `list.size() / 2`. This is a classic time-space tradeoff.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We traverse the linked list exactly once to populate the array list.
     * Accessing the middle element takes O(1) time.
     * - Space Complexity: O(N) auxiliary heap space.
     * We are storing N node references in an ArrayList dynamically created on
     * the heap.
     * ============================================================================
     */
    public ListNode middleNodeAlternative(ListNode head) {
        List<ListNode> nodes = new ArrayList<>();
        ListNode current = head;

        while (current != null) {
            nodes.add(current);
            current = current.next;
        }

        return nodes.get(nodes.size() / 2);
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Thoroughly tests all approaches against standard and edge cases using
     * cleanly generated lists and Java 8 Streams for serialization.
     */
    public static void main(String[] args) {
        MiddleOfLinkedListMasterclass solution = new MiddleOfLinkedListMasterclass();

        // Test Cases Definition
        int[][] testCases = {
                {1, 2, 3, 4, 5},       // Standard odd length
                {1, 2, 3, 4, 5, 6},    // Standard even length
                {1},                   // Edge case: Single node
                {1, 2}                 // Edge case: Two nodes
        };

        System.out.println("--- Starting Test Suite for Middle of the Linked List ---");

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = testCases[i];
            System.out.println("\nTest Case " + (i + 1) + ": head = " + Arrays.toString(arr));

            // Test Phase 1: Optimal
            ListNode head1 = buildList(arr);
            ListNode res1 = solution.middleNodeOptimal(head1);
            System.out.println("Optimal (Two Pointers)  : " + serializeList(res1));

            // Test Phase 2: Brute Force
            ListNode head2 = buildList(arr);
            ListNode res2 = solution.middleNodeBruteForce(head2);
            System.out.println("Brute Force (Count pass): " + serializeList(res2));

            // Test Phase 3: Alternative
            ListNode head3 = buildList(arr);
            ListNode res3 = solution.middleNodeAlternative(head3);
            System.out.println("Alternative (ArrayList) : " + serializeList(res3));
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
     * Utility method: Serializes a linked list from the given node to the end,
     * converting it into a String format utilizing Java 8 Stream API.
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