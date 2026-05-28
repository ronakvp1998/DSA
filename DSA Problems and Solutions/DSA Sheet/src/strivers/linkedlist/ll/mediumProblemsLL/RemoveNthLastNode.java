package strivers.linkedlist.ll.mediumProblemsLL;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 19. Remove Nth Node From End of List
 * Difficulty: Medium
 * * Formal Problem Statement:
 * Given the head of a linked list, remove the nth node from the end of the
 * list and return its head.
 * * Constraints:
 * - The number of nodes in the list is sz.
 * - 1 <= sz <= 30
 * - 0 <= Node.val <= 100
 * - 1 <= n <= sz
 * * Follow up: Could you do this in one pass?
 * * Example 1:
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 * * Example 2:
 * Input: head = [1], n = 1
 * Output: []
 * * Example 3:
 * Input: head = [1,2], n = 1
 * Output: [1]
 * ============================================================================
 */
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveNthLastNode {

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
     * PHASE 1: OPTIMAL APPROACH (One Pass - Two Pointers)
     * ============================================================================
     * Detailed Intuition:
     * To satisfy the "one pass" follow-up, we use a classic two-pointer strategy
     * (fast and slow). If we advance the 'fast' pointer 'n' steps ahead of the
     * 'slow' pointer, we create a sliding window of size 'n'. When 'fast' traverses
     * to the absolute end of the list (null), the 'slow' pointer will naturally
     * trail behind and land exactly on the node immediately preceding the one we
     * need to delete.
     * * We use a 'dummy' node pointing to the head. This elegantly handles the edge
     * case where the node to be removed is the very first node (head) of the list,
     * preventing NullPointerExceptions and complex conditional logic.
     * * Complexity Analysis:
     * - Time Complexity: O(sz) - We traverse the linked list exactly once.
     * - Space Complexity: O(1) - We only allocate two pointers. Auxiliary stack space
     * is O(1) and heap space is O(1) (excluding the dummy node).
     */
    public ListNode removeNthFromEndOptimal(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode slow = dummy;
        ListNode fast = dummy;

        // Advance fast pointer n + 1 steps to create the necessary gap
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // Move both pointers until fast reaches the end
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // Remove the nth node from the end
        slow.next = slow.next.next;

        return dummy.next;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Two Passes)
     * ============================================================================
     * Detailed Intuition:
     * The most straightforward "Think it" approach. If we don't know the list's length,
     * we cannot easily identify the nth node from the back. Thus, we dedicate our
     * first pass strictly to calculating the total length 'sz'.
     * Once we have 'sz', the node to remove is located at index (sz - n) from the
     * start (0-indexed). Our second pass simply traverses to the (sz - n - 1)-th node
     * and reroutes its 'next' pointer to skip the target node.
     * * Complexity Analysis:
     * - Time Complexity: O(sz) - We traverse the list completely once, and then
     * partially a second time. Technically O(2 * sz), which simplifies to O(sz).
     * - Space Complexity: O(1) - Only simple integer variables and a pointer are used.
     * Auxiliary stack space is O(1) and heap space is O(1).
     */
    public ListNode removeNthFromEndBruteForce(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        int length = 0;
        ListNode current = head;

        // First Pass: Calculate length
        while (current != null) {
            length++;
            current = current.next;
        }

        // Second Pass: Navigate to the node right before the one we want to remove
        length -= n;
        current = dummy;
        while (length > 0) {
            length--;
            current = current.next;
        }

        // Skip the target node
        current.next = current.next.next;

        return dummy.next;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Recursion)
     * ============================================================================
     * Detailed Intuition:
     * We can exploit the call stack to simulate reversing the list. By recursing to
     * the very end of the list first, we can count the nodes on the way back up
     * (as the recursion unwinds). When our state tracker reaches 'n', it means the
     * current node is the target for deletion. We simply return its 'next' node to
     * the previous call, effectively removing it from the chain.
     * * Note: We use a single-element array to pass the counter by reference across
     * the recursive call stack.
     * * Complexity Analysis:
     * - Time Complexity: O(sz) - Every node is visited once during the recursive dive.
     * - Space Complexity: O(sz) - Auxiliary stack space is exactly O(sz) due to the
     * recursion depth reaching the end of the list. Heap space is O(1).
     */
    public ListNode removeNthFromEndRecursive(ListNode head, int n) {
        // count[0] will track our position from the end as we unwind the stack
        int[] count = new int[1];
        return recursiveHelper(head, n, count);
    }

    private ListNode recursiveHelper(ListNode node, int n, int[] count) {
        if (node == null) {
            return null;
        }

        // Dive to the end
        node.next = recursiveHelper(node.next, n, count);

        // Unwind and count
        count[0]++;

        // If we are at the target node, skip it by returning its next child
        if (count[0] == n) {
            return node.next;
        }

        return node;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Thorough evaluation covering standard lists, single element removals, and
     * complete wipeouts (e.g., removing the only element).
     */
    public static void main(String[] args) {
        RemoveNthLastNode solver = new RemoveNthLastNode();

        // Define Test Cases
        int[][] testLists = {
                {1, 2, 3, 4, 5}, // Standard case
                {1},             // Edge case: single element
                {1, 2},          // Edge case: two elements, remove head
                {1, 2}           // Edge case: two elements, remove tail
        };
        int[] nValues = {2, 1, 2, 1};

        System.out.println("--- 🤖 Running DSA Testing Suite ---\n");

        for (int i = 0; i < testLists.length; i++) {
            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Input List: " + Arrays.toString(testLists[i]) + ", n = " + nValues[i]);

            // Test 1: Optimal Approach
            ListNode head1 = buildList(testLists[i]);
            ListNode res1 = solver.removeNthFromEndOptimal(head1, nValues[i]);
            System.out.println("Optimal Result   : " + formatList(res1));

            // Test 2: Brute Force Approach
            ListNode head2 = buildList(testLists[i]);
            ListNode res2 = solver.removeNthFromEndBruteForce(head2, nValues[i]);
            System.out.println("Brute Force      : " + formatList(res2));

            // Test 3: Recursive Approach
            ListNode head3 = buildList(testLists[i]);
            ListNode res3 = solver.removeNthFromEndRecursive(head3, nValues[i]);
            System.out.println("Recursive Result : " + formatList(res3));
            System.out.println("-".repeat(40));
        }
    }

    /**
     * Utility method: Builds a linked list from an integer array using Java 8 Streams.
     */
    private static ListNode buildList(int[] arr) {
        if (arr == null || arr.length == 0) return null;

        ListNode dummy = new ListNode(0);
        ListNode[] current = { dummy }; // Array used to allow mutation inside lambda

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
        List<String> values = new ArrayList<>();
        while (head != null) {
            values.add(String.valueOf(head.val));
            head = head.next;
        }
        return "[" + values.stream().collect(Collectors.joining(", ")) + "]";
    }
}