package strivers.linkedlist.ll.mediumProblemsLL;

/**
 * ============================================================================
 * 143. REORDER LIST
 * ============================================================================
 * * FORMAL PROBLEM STATEMENT:
 * You are given the head of a singly linked-list. The list can be represented as:
 * L0 → L1 → … → Ln - 1 → Ln
 * * Reorder the list to be on the following form:
 * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
 * * You may not modify the values in the list's nodes. Only nodes themselves may be changed.
 * * EXAMPLES:
 * Example 1:
 * Input: head = [1,2,3,4]
 * Output: [1,4,2,3]
 * * Example 2:
 * Input: head = [1,2,3,4,5]
 * Output: [1,5,2,4,3]
 * * CONSTRAINTS:
 * - The number of nodes in the list is in the range [1, 5 * 10^4].
 * - 1 <= Node.val <= 1000
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReorderList {

    // Definition for singly-linked list.
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (In-Place Pointers) - The "Recommended" Stage
     * ============================================================================
     * * DETAILED INTUITION:
     * The reordering L0 → Ln → L1 → Ln-1 perfectly describes a pattern of taking
     * one node from the start, and one node from the end, alternatingly.
     * Since we cannot traverse a singly linked list backwards from the end, we can:
     * 1. Find the middle of the linked list using the Slow and Fast pointer technique.
     * 2. Reverse the second half of the linked list in-place.
     * 3. Merge the first half and the reversed second half by alternating their nodes.
     * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N)
     * Finding the middle takes O(N/2). Reversing takes O(N/2). Merging takes O(N/2).
     * Overall time is strictly linear.
     * - Space Complexity: O(1)
     * We are only rearranging existing pointers. No auxiliary stack or heap space
     * is consumed, making it fully in-place.
     */
    public void reorderListOptimal(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return;

        // Step 1: Find the middle of the list
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse the second half of the list
        ListNode prev = null;
        ListNode curr = slow.next;
        slow.next = null; // Sever the first half from the second half

        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }

        // Step 3: Merge the two halves
        ListNode first = head;
        ListNode second = prev; // 'prev' is the head of the reversed second half

        while (second != null) {
            ListNode temp1 = first.next;
            ListNode temp2 = second.next;

            first.next = second;
            second.next = temp1;

            first = temp1;
            second = temp2;
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Array Buffer) - The "Think it" Stage
     * ============================================================================
     * * DETAILED INTUITION:
     * If we ignore the in-place constraint (or just want to establish a baseline),
     * the easiest way to access nodes from both ends is to store their references
     * in a random-access data structure like an ArrayList.
     * Once all nodes are in an array, we can use two pointers (left at 0, right at N-1)
     * and iteratively wire left.next = right, and right.next = left + 1.
     * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N)
     * One pass to populate the list, and one pass using two pointers to wire nodes.
     * - Space Complexity: O(N)
     * We allocate heap space for an ArrayList holding N references to the ListNodes.
     */
    public void reorderListBruteForce(ListNode head) {
        if (head == null || head.next == null) return;

        List<ListNode> nodes = new ArrayList<>();
        ListNode curr = head;

        // Store all nodes in a list
        while (curr != null) {
            nodes.add(curr);
            curr = curr.next;
        }

        int left = 0;
        int right = nodes.size() - 1;

        // Rewire the next pointers
        while (left < right) {
            nodes.get(left).next = nodes.get(right);
            left++;

            if (left == right) break; // Reached the center

            nodes.get(right).next = nodes.get(left);
            right--;
        }

        // Nullify the next pointer of the last node to prevent cycles
        nodes.get(left).next = null;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Stack) - The "Another perspective" Stage
     * ============================================================================
     * * DETAILED INTUITION:
     * Similar to the Array approach, but using a Stack (LIFO) to naturally reverse
     * the order of the second half of the nodes. We push all nodes to a stack.
     * Then we iterate from the head, popping nodes from the stack to insert between
     * the current node and the next node. We stop when we've processed half the list.
     * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N)
     * One pass to push onto stack, and a half-pass to pop and merge.
     * - Space Complexity: O(N)
     * We allocate auxiliary space for the Stack holding N node references.
     */
    public void reorderListStack(ListNode head) {
        if (head == null || head.next == null) return;

        Stack<ListNode> stack = new Stack<>();
        ListNode curr = head;
        int size = 0;

        while (curr != null) {
            stack.push(curr);
            curr = curr.next;
            size++;
        }

        curr = head;
        // We only need to insert the back half into the front half
        for (int i = 0; i < size / 2; i++) {
            ListNode nextNode = curr.next;
            ListNode topNode = stack.pop();

            curr.next = topNode;
            topNode.next = nextNode;

            curr = nextNode;
        }

        // Nullify the last node's next pointer to prevent cycles
        curr.next = null;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        ReorderList solution = new ReorderList();

        System.out.println("=== Testing Optimal Approach ===");
        runTest(solution, new int[]{1, 2, 3, 4}, "Optimal");
        runTest(solution, new int[]{1, 2, 3, 4, 5}, "Optimal");

        System.out.println("\n=== Testing Brute Force Array Approach ===");
        runTest(solution, new int[]{1, 2, 3, 4}, "BruteForce");
        runTest(solution, new int[]{1, 2, 3, 4, 5}, "BruteForce");

        System.out.println("\n=== Testing Alternative Stack Approach ===");
        runTest(solution, new int[]{1, 2, 3, 4}, "Stack");
        runTest(solution, new int[]{1, 2, 3, 4, 5}, "Stack");

        System.out.println("\n=== Testing Edge Cases ===");
        runTest(solution, new int[]{1}, "Optimal"); // Single node
        runTest(solution, new int[]{1, 2}, "Optimal"); // Two nodes
        runTest(solution, new int[]{}, "Optimal"); // Empty list
    }

    // Helper method to execute and print tests cleanly
    private static void runTest(ReorderList solution, int[] values, String method) {
        ListNode head = buildList(values);
        System.out.print("Input:  ");
        printList(head);

        if (method.equals("Optimal")) solution.reorderListOptimal(head);
        else if (method.equals("BruteForce")) solution.reorderListBruteForce(head);
        else if (method.equals("Stack")) solution.reorderListStack(head);

        System.out.print("Output: ");
        printList(head);
        System.out.println("-".repeat(30));
    }

    // Helper method to build a linked list from an array
    private static ListNode buildList(int[] values) {
        if (values.length == 0) return null;
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        return dummy.next;
    }

    // Helper method to print a linked list
    private static void printList(ListNode head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }
        List<String> vals = new ArrayList<>();
        while (head != null) {
            vals.add(String.valueOf(head.val));
            head = head.next;
        }
        System.out.println("[" + String.join(", ", vals) + "]");
    }
}