package strivers.linkedlist.ll.mediumProblemsLL;

/**
 * ============================================================================
 * 206. Reverse Linked List
 * ============================================================================
 *
 * Problem Statement:
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [5,4,3,2,1]
 *
 * Example 2:
 * Input: head = [1,2]
 * Output: [2,1]
 *
 * Example 3:
 * Input: head = []
 * Output: []
 *
 * Constraints:
 * - The number of nodes in the list is the range [0, 5000].
 * - -5000 <= Node.val <= 5000
 *
 * Follow up: A linked list can be reversed either iteratively or recursively.
 * Could you implement both?
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (ITERATIVE POINTERS)
 * ============================================================================
 * Initial State:
 * null      1  ->  2  ->  3  ->  null
 *  ^        ^
 * prev     curr
 *
 * Step 1:
 * null  <-  1      2  ->  3  ->  null
 *           ^      ^
 *         prev    curr
 *
 * Final State:
 * null  <-  1  <-  2  <-  3      null
 *                         ^       ^
 *                        prev    curr
 * ============================================================================
 */

import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;

public class ReverseLL {

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
     * Phase 1: Optimal Approach (Iterative In-Place Reversal)
     * ============================================================================
     * Detailed Intuition:
     * We do not need extra memory to reverse a linked list. By maintaining three
     * pointers (`prev`, `curr`, and `nextNode`), we can walk through the list and
     * flip the `next` pointer of the current node to point backward to `prev`.
     * We must save `curr.next` into `nextNode` before overwriting it so we do
     * not lose our path forward.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   We traverse the linked list exactly once, where N is the number of nodes.
     * - Space Complexity: O(1)
     *   Only three pointers are allocated in memory, requiring constant auxiliary space.
     *   No heap space is utilized.
     * ============================================================================
     */
    public ListNode reverseListOptimal(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            // 1. Save the forward link to prevent severing the list
            ListNode nextNode = curr.next;

            // 2. Reverse the current node's pointer to point backwards
            curr.next = prev;

            // 3. Slide the prev and curr pointers forward by one position
            prev = curr;
            curr = nextNode;
        }

        // When curr falls off the end (null), prev will be resting on the new head
        return prev;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach (Stack-Based Value Overwrite)
     * ============================================================================
     * Detailed Intuition:
     * If manipulating pointers is difficult to visualize, a brute force way is to
     * utilize a Stack (Last-In-First-Out data structure). We traverse the list once
     * to push all values onto the stack. We then traverse the original list a
     * second time from the head, popping values off the stack and overwriting
     * the existing node values.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   Requires two full passes over the list. O(N) + O(N) = O(N).
     * - Space Complexity: O(N) (Heap Space)
     *   A stack is allocated on the heap which grows linearly with the input size.
     * ============================================================================
     */
    public ListNode reverseListBruteForce(ListNode head) {
        if (head == null) return null;

        Stack<Integer> stack = new Stack<>();
        ListNode curr = head;

        // Pass 1: Push all values onto the stack
        while (curr != null) {
            stack.push(curr.val);
            curr = curr.next;
        }

        // Pass 2: Overwrite values from head to tail
        curr = head;
        while (curr != null) {
            curr.val = stack.pop();
            curr = curr.next;
        }

        return head;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach (Recursive Reversal)
     * ============================================================================
     * Detailed Intuition:
     * We can define the reversal of a list recursively: The reverse of a list
     * starting at `head` is the reverse of `head.next`, with the current `head`
     * appended to the very end of it.
     *
     * When the recursive call `reverseListRecursive(head.next)` returns, the
     * original `head.next` is now the TAIL of the reversed sub-list. We simply
     * set `head.next.next = head` to hook ourselves onto the end, and sever our
     * old forward link `head.next = null`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   Each node is visited exactly once during the recursive unwinding.
     * - Space Complexity: O(N) (Auxiliary Stack Space)
     *   The call stack depth will reach N before returning. This risks a
     *   StackOverflowError on extremely large lists, making the iterative
     *   approach preferred in production.
     * ============================================================================
     */
    public ListNode reverseListRecursive(ListNode head) {
        // Base case: empty list or single node
        if (head == null || head.next == null) {
            return head;
        }

        // Trust the recursion to reverse the remaining list
        ListNode reversedHead = reverseListRecursive(head.next);

        // head.next is currently the tail of the reversed sub-list.
        // Point its 'next' reference backwards to the current head.
        head.next.next = head;

        // Break the original forward reference to prevent cycles
        head.next = null;

        // The head of the reversed list bubbles up to the top caller
        return reversedHead;
    }

    /**
     * ============================================================================
     * 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        ReverseLL solution = new ReverseLL();

        System.out.println("--- Executing Testing Suite ---");

        // Example 1: [1, 2, 3, 4, 5] -> Optimal Iterative
        System.out.println("\nTest 1: Optimal Iterative Approach");
        ListNode head1 = buildList(1, 2, 3, 4, 5);
        System.out.println("Input:  " + listToString(head1));
        ListNode res1 = solution.reverseListOptimal(head1);
        System.out.println("Output: " + listToString(res1));

        // Example 2: [1, 2] -> Recursive Approach
        System.out.println("\nTest 2: Recursive Approach");
        ListNode head2 = buildList(1, 2);
        System.out.println("Input:  " + listToString(head2));
        ListNode res2 = solution.reverseListRecursive(head2);
        System.out.println("Output: " + listToString(res2));

        // Example 3: [] -> Edge Case (Empty List)
        System.out.println("\nTest 3: Empty List (Zero-value Edge Case)");
        ListNode head3 = buildList();
        System.out.println("Input:  " + listToString(head3));
        ListNode res3 = solution.reverseListOptimal(head3);
        System.out.println("Output: " + listToString(res3));

        // Example 4: Stack Brute Force Test
        System.out.println("\nTest 4: Brute Force Stack Approach");
        ListNode head4 = buildList(10, 20, 30);
        System.out.println("Input:  " + listToString(head4));
        ListNode res4 = solution.reverseListBruteForce(head4);
        System.out.println("Output: " + listToString(res4));
    }

    /**
     * Helper Method: Builds a linked list from an array of integers using Java 8 Streams.
     */
    private static ListNode buildList(int... values) {
        if (values == null || values.length == 0) return null;

        // Leveraging Java 8 streams for elegant mapping (for demonstration)
        java.util.List<ListNode> nodes = Arrays.stream(values)
                .mapToObj(ListNode::new)
                .collect(Collectors.toList());

        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).next = nodes.get(i + 1);
        }

        return nodes.get(0);
    }

    /**
     * Helper Method: Serializes the linked list back to a string format.
     */
    private static String listToString(ListNode head) {
        if (head == null) return "[]";

        StringBuilder sb = new StringBuilder("[");
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(", ");
            }
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }
}