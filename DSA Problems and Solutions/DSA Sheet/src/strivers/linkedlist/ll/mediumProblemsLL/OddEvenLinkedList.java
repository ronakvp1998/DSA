package strivers.linkedlist.ll.mediumProblemsLL;

/**
 * As an AI acting as your Senior DSA Interviewer and Competitive Programming Evaluator,
 * I have structured this masterclass-level solution exactly as requested. Everything from
 * the formal problem statement to the space-time complexity analysis is contained within
 * these comments.
 *
 * =========================================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * =========================================================================================
 * Problem Statement:
 * Given the head of a singly linked list, group all the nodes with odd indices together
 * followed by the nodes with even indices, and return the reordered list.
 * * The first node is considered odd, and the second node is even, and so on.
 * Note that the relative order inside both the even and odd groups should remain as it was
 * in the input.
 * * You must solve the problem in O(1) extra space complexity and O(n) time complexity.
 *
 * Constraints:
 * - The number of nodes in the linked list is in the range [0, 10^4].
 * - -10^6 <= Node.val <= 10^6
 *
 * Input/Output Formats & Examples:
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [1,3,5,2,4]
 *
 * Example 2:
 * Input: head = [2,1,3,5,6,4,7]
 * Output: [2,3,6,7,1,5,4]
 *
 * =========================================================================================
 * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * =========================================================================================
 * Phase 1: Optimal Approach - Two Pointers (Odd & Even) with splitting and merging.
 * Phase 2: Brute Force Approach - Auxiliary Array/List storage for separation.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class OddEvenLinkedList {

    /**
     * Definition for singly-linked list.
     */
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * =====================================================================================
     * PHASE 1: OPTIMAL APPROACH (The Recommended Approach)
     * =====================================================================================
     * Detailed Intuition:
     * To achieve O(1) extra space, we must rearrange the pointers in-place. We can maintain
     * two separate conceptual lists: one for odd-indexed nodes and one for even-indexed nodes.
     * We initialize an `odd` pointer at the head (index 1) and an `even` pointer at head.next
     * (index 2). We must also save the head of the even list (`evenHead`) so we can attach
     * it to the end of the odd list once we finish traversing.
     * * In each step of the loop, we bypass the next node to wire the `odd` pointer to the next
     * odd node, and similarly for the `even` pointer. We advance both until `even` or
     * `even.next` becomes null, indicating we've reached the end of the list. Finally, we
     * link the tail of our extracted odd list to the `evenHead`.
     *
     * Complexity Analysis:
     * Time Complexity: O(n)
     * - We traverse the linked list exactly once. Each node is visited and its pointer is
     * modified in constant time.
     * Space Complexity: O(1) Auxiliary Space
     * - We only use a few pointer variables (`odd`, `even`, `evenHead`), requiring zero
     * additional heap space. It is fully iterative, so no auxiliary stack space is used.
     */
    public static ListNode oddEvenListOptimal(ListNode head) {
        // Base cases: if list is empty or has only one/two nodes, no change is needed.
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even; // Preserve the start of the even list

        // Traverse and rewire
        while (even != null && even.next != null) {
            odd.next = even.next;       // Link current odd to the next odd node
            odd = odd.next;             // Move odd pointer forward

            even.next = odd.next;       // Link current even to the next even node
            even = even.next;           // Move even pointer forward
        }

        // Connect the end of the odd list to the start of the even list
        odd.next = evenHead;

        return head;
    }

    /**
     * =====================================================================================
     * PHASE 2: BRUTE FORCE APPROACH (The "Think it" stage)
     * =====================================================================================
     * Detailed Intuition:
     * If we ignore the O(1) space constraint, the most straightforward way to solve this is
     * to physically extract the node values (or references) into two separate auxiliary lists:
     * one for odd indices and one for even indices.
     * * We traverse the list with a counter, adding elements to their respective ArrayLists.
     * After fully traversing, we overwrite the original list's node values sequentially,
     * first dumping all values from the odd list, followed by all values from the even list.
     *
     * Complexity Analysis:
     * Time Complexity: O(n)
     * - We traverse the list once to populate the arrays, and once again to overwrite the
     * node values. 2 * O(n) simplifies to O(n).
     * Space Complexity: O(n) Heap Space
     * - We store exactly N node values in two ArrayLists. This violates the O(1) space
     * requirement but serves as a stepping stone to the optimal pointer manipulation logic.
     */
    public static ListNode oddEvenListBruteForce(ListNode head) {
        if (head == null) return null;

        List<Integer> odds = new ArrayList<>();
        List<Integer> evens = new ArrayList<>();

        ListNode current = head;
        int index = 1;

        // Step 1: Segregate values into two lists based on their index
        while (current != null) {
            if (index % 2 != 0) {
                odds.add(current.val);
            } else {
                evens.add(current.val);
            }
            current = current.next;
            index++;
        }

        // Step 2: Overwrite the original list values
        current = head;
        for (int val : odds) {
            current.val = val;
            current = current.next;
        }
        for (int val : evens) {
            current.val = val;
            current = current.next;
        }

        return head;
    }

    /**
     * =====================================================================================
     * 4. TESTING SUITE
     * =====================================================================================
     * Rigorous testing against standard cases and tricky edge cases (empty list, single
     * node, two nodes). Utilizes Java 8 Streams for clean execution and validation.
     */
    public static void main(String[] args) {
        System.out.println("--- Executing Masterclass Testing Suite ---\n");

        Object[][] testCases = {
                {"TC1: Standard Odd Length", new int[]{1, 2, 3, 4, 5}, new int[]{1, 3, 5, 2, 4}},
                {"TC2: Standard Even Length", new int[]{2, 1, 3, 5, 6, 4, 7}, new int[]{2, 3, 6, 7, 1, 5, 4}},
                {"TC3: Empty List", new int[]{}, new int[]{}},
                {"TC4: Single Node", new int[]{1}, new int[]{1}},
                {"TC5: Two Nodes", new int[]{1, 2}, new int[]{1, 2}},
                {"TC6: All Identical Values", new int[]{2, 2, 2, 2}, new int[]{2, 2, 2, 2}}
        };

        Arrays.stream(testCases).forEach(test -> {
            String name = (String) test[0];
            int[] input = (int[]) test[1];
            int[] expected = (int[]) test[2];

            // Test Brute Force
            ListNode headBF = buildList(input);
            ListNode resBF = oddEvenListBruteForce(headBF);
            int[] outBF = extractValues(resBF);

            // Test Optimal
            ListNode headOpt = buildList(input);
            ListNode resOpt = oddEvenListOptimal(headOpt);
            int[] outOpt = extractValues(resOpt);

            boolean passed = Arrays.equals(outBF, expected) && Arrays.equals(outOpt, expected);

            System.out.printf("%-25s | Expected: %-20s | Optimal: %-20s | Status: %s%n",
                    name,
                    Arrays.toString(expected),
                    Arrays.toString(outOpt),
                    passed ? "PASS ✅" : "FAIL ❌");
        });
    }

    // --- Helper Methods for Testing Suite ---

    /**
     * Converts an integer array to a Singly Linked List.
     */
    private static ListNode buildList(int[] values) {
        if (values == null || values.length == 0) return null;
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }
        return dummy.next;
    }

    /**
     * Extracts values from a Singly Linked List into an integer array for easy comparison.
     */
    private static int[] extractValues(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
