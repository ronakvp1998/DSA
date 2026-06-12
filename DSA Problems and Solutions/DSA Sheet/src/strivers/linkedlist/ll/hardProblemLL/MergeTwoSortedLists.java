package strivers.linkedlist.ll.hardProblemLL;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ============================================================================
 * 21. Merge Two Sorted Lists
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * PROBLEM STATEMENT:
 * You are given the heads of two sorted linked lists list1 and list2.
 * Merge the two lists into one sorted list. The list should be made by
 * splicing together the nodes of the first two lists.
 * Return the head of the merged linked list.
 *
 * Example 1:
 * Input: list1 = [1,2,4], list2 = [1,3,4]
 * Output: [1,1,2,3,4,4]
 *
 * Example 2:
 * Input: list1 = [], list2 = []
 * Output: []
 *
 * Example 3:
 * Input: list1 = [], list2 = [0]
 * Output: [0]
 *
 * CONSTRAINTS:
 * The number of nodes in both lists is in the range [0, 50].
 * -100 <= Node.val <= 100
 * Both list1 and list2 are sorted in non-decreasing order.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Iterative Two Pointers
 * Phase 2: Brute Force Approach - Collect, Sort, and Rebuild
 * Phase 3: Alternative Approach - Recursive Merge
 * ============================================================================
 */
public class MergeTwoSortedLists {

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
     * PHASE 1: OPTIMAL APPROACH (Iterative Two Pointers)
     * ============================================================================
     * Detailed Intuition:
     * Since both linked lists are already sorted, we can use a "Two Pointer"
     * approach. We maintain a pointer for each list and compare their current
     * values. The smaller value is appended to our new merged list, and we advance
     * the pointer in the list from which the smaller value was taken.
     * Using a "dummy" head node simplifies the edge cases surrounding the
     * initialization of the merged list. Once one list is exhausted, we can simply
     * append the remainder of the other list in O(1) time.
     * * Complexity Analysis:
     * - Time Complexity: O(N + M), where N and M are the lengths of list1 and
     * list2. We traverse each node at most once.
     * - Space Complexity: O(1) auxiliary space (no heap space used for new nodes,
     * only reusing existing nodes, and O(1) stack space).
     * ============================================================================
     */
    public ListNode mergeTwoListsOptimal(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // Attach the remaining elements if any list is not fully traversed
        current.next = (list1 != null) ? list1 : list2;

        return dummy.next;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Collect, Sort, Rebuild) -> The "Think it" stage
     * ============================================================================
     * Detailed Intuition:
     * If we ignore the constraint that the lists are already sorted, the most
     * basic approach is to dump all the values from both lists into a collection,
     * sort that collection, and then construct a completely new linked list.
     * We can leverage Java 8 Stream API here to concatenate, sort, and map
     * the extracted values cleanly.
     * * Complexity Analysis:
     * - Time Complexity: O((N + M) log(N + M)). Extracting takes O(N + M),
     * sorting the combined list takes O((N + M) log(N + M)), and rebuilding
     * takes O(N + M).
     * - Space Complexity: O(N + M) heap space to hold all values in Lists and
     * to instantiate the brand new ListNodes. O(1) auxiliary stack space.
     * ============================================================================
     */
    public ListNode mergeTwoListsBruteForce(ListNode list1, ListNode list2) {
        List<Integer> values1 = extractValues(list1);
        List<Integer> values2 = extractValues(list2);

        // Use Java 8 Streams to concatenate, sort, and generate new ListNodes
        List<ListNode> sortedNodes = Stream.concat(values1.stream(), values2.stream())
                .sorted()
                .map(ListNode::new)
                .collect(Collectors.toList());

        if (sortedNodes.isEmpty()) {
            return null;
        }

        // Link the newly created nodes together
        for (int i = 0; i < sortedNodes.size() - 1; i++) {
            sortedNodes.get(i).next = sortedNodes.get(i + 1);
        }

        return sortedNodes.get(0);
    }

    // Helper for Brute Force
    private List<Integer> extractValues(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return list;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Recursive Merge)
     * ============================================================================
     * Detailed Intuition:
     * We can define the merge operation recursively: The head of the merged list
     * will be the smaller of the two current heads. The rest of the merged list
     * is the result of recursively calling the merge function on the "next" node
     * of the smaller head and the other list.
     * * Complexity Analysis:
     * - Time Complexity: O(N + M). Every recursive call processes one node.
     * - Space Complexity: O(N + M) auxiliary stack space due to the recursion
     * depth reaching the sum of the lengths of both lists. O(1) heap space
     * since we re-wire existing nodes.
     * ============================================================================
     */
    public ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
        // Base cases
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        // Recursive step
        if (list1.val <= list2.val) {
            list1.next = mergeTwoListsRecursive(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsRecursive(list1, list2.next);
            return list2;
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        MergeTwoSortedLists solver = new MergeTwoSortedLists();

        System.out.println("=== Test Case 1: Standard Input ===");
        // Input: list1 = [1,2,4], list2 = [1,3,4]
        System.out.print("Optimal Output:      ");
        printList(solver.mergeTwoListsOptimal(
                buildList(new int[]{1, 2, 4}), buildList(new int[]{1, 3, 4})));

        System.out.print("Brute Force Output:  ");
        printList(solver.mergeTwoListsBruteForce(
                buildList(new int[]{1, 2, 4}), buildList(new int[]{1, 3, 4})));

        System.out.print("Recursive Output:    ");
        printList(solver.mergeTwoListsRecursive(
                buildList(new int[]{1, 2, 4}), buildList(new int[]{1, 3, 4})));

        System.out.println("\n=== Test Case 2: Both Empty ===");
        // Input: list1 = [], list2 = []
        System.out.print("Optimal Output:      ");
        printList(solver.mergeTwoListsOptimal(buildList(new int[]{}), buildList(new int[]{})));

        System.out.println("\n=== Test Case 3: One Empty, One with Zero (Zero-Value Edge Case) ===");
        // Input: list1 = [], list2 = [0]
        System.out.print("Optimal Output:      ");
        printList(solver.mergeTwoListsOptimal(buildList(new int[]{}), buildList(new int[]{0})));

        System.out.println("\n=== Test Case 4: Different Lengths & Negatives ===");
        // Input: list1 = [-5, 0, 5, 10], list2 = [-2, 4]
        System.out.print("Recursive Output:    ");
        printList(solver.mergeTwoListsRecursive(
                buildList(new int[]{-5, 0, 5, 10}), buildList(new int[]{-2, 4})));
    }

    // --- Helper Methods for Testing Suite --- //

    private static ListNode buildList(int[] values) {
        if (values == null || values.length == 0) return null;
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        return dummy.next;
    }

    private static void printList(ListNode head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }
        List<String> result = new ArrayList<>();
        while (head != null) {
            result.add(String.valueOf(head.val));
            head = head.next;
        }
        System.out.println("[" + String.join(" -> ", result) + "]");
    }
}