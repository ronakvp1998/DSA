package strivers.linkedlist.ll.mediumProblemsLL;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 148. Sort List
 * Difficulty: Medium
 * * Formal Problem Statement:
 * Given the head of a linked list, return the list after sorting it in
 * ascending order.
 * * Constraints:
 * - The number of nodes in the list is in the range [0, 5 * 10^4].
 * - -10^5 <= Node.val <= 10^5
 * * Follow up: Can you sort the linked list in O(n logn) time and O(1) memory
 * (i.e. constant space)?
 * * Example 1:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 * * Example 2:
 * Input: head = [-1,5,3,4,0]
 * Output: [-1,0,3,4,5]
 * * Example 3:
 * Input: head = []
 * Output: []
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortListMasterclass {

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
     * Phase 1: Optimal Approach - Bottom-Up Merge Sort (O(1) Space)
     * Phase 2: Brute Force Approach - Collection Sort (O(N) Space)
     * Phase 3: Alternative Approach - Top-Down Merge Sort (O(log N) Space)
     * ============================================================================
     */

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Bottom-Up Merge Sort)
     * ============================================================================
     * Detailed Intuition:
     * To satisfy the strict follow-up constraints of O(n log n) time and O(1)
     * space, we cannot use recursion (which consumes O(log n) stack space).
     * Instead, we must use an iterative Bottom-Up Merge Sort.
     * * We process the list in sublists of exponentially increasing sizes
     * (step = 1, 2, 4, 8...). In each pass, we detach two sublists of size `step`,
     * merge them, and append them to a growing sorted list. We repeat this until
     * `step` exceeds the total length of the list.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). Calculating length takes O(N). The outer loop
     * doubles `step` each time, taking O(log N) iterations. The inner loop merges
     * nodes taking O(N) work per pass. Overall Time: O(N log N).
     * - Space Complexity: O(1). We strictly modify pointers in place. Auxiliary
     * stack space is O(1) and heap space is O(1) (only a dummy node and a few
     * tracking pointers are instantiated).
     * ============================================================================
     */
    public ListNode sortListOptimal(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 1. Find the total length of the list
        int length = 0;
        ListNode curr = head;
        while (curr != null) {
            length++;
            curr = curr.next;
        }

        ListNode dummy = new ListNode(0, head);

        // 2. Bottom-Up Merge Sort
        for (int step = 1; step < length; step <<= 1) { // step *= 2
            ListNode tail = dummy;
            curr = dummy.next;

            while (curr != null) {
                // Get the left sublist of size `step`
                ListNode left = curr;
                ListNode right = split(left, step);

                // Get the next starting point for the next iteration
                curr = split(right, step);

                // Merge the two sublists and attach to our sorted chain
                tail = mergeAndReturnTail(left, right, tail);
            }
        }

        return dummy.next;
    }

    /**
     * Helper to divide the list into two parts.
     * Advances `size` steps from `head`, severs the connection, and returns
     * the head of the remaining list.
     */
    private ListNode split(ListNode head, int size) {
        if (head == null) return null;
        for (int i = 1; head.next != null && i < size; i++) {
            head = head.next;
        }
        ListNode right = head.next;
        head.next = null; // Sever the connection
        return right;
    }

    /**
     * Helper to merge two sorted lists.
     * Appends the merged result to `prev` and returns the new tail.
     */
    private ListNode mergeAndReturnTail(ListNode l1, ListNode l2, ListNode prev) {
        ListNode curr = prev;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }

        // Append remaining nodes
        curr.next = (l1 != null) ? l1 : l2;

        // Traverse to the end to return the new tail
        while (curr.next != null) {
            curr = curr.next;
        }
        return curr;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Extract, Sort, Rebuild)
     * ============================================================================
     * Detailed Intuition:
     * The "Think it" stage. If pointer manipulation is tricky, the easiest way
     * to sort a linked list is to extract all its values into a standard array
     * or list, use the language's highly optimized built-in sorting algorithm,
     * and then reconstruct the linked list from the sorted values.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). Copying to array takes O(N). Java's
     * Collections.sort (Timsort) takes O(N log N). Rebuilding takes O(N).
     * - Space Complexity: O(N). Auxiliary heap space of O(N) is required to store
     * all the node values in the ArrayList.
     * ============================================================================
     */
    public ListNode sortListBruteForce(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        List<Integer> values = new ArrayList<>();
        ListNode curr = head;

        // Step 1: Extract all values
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }

        // Step 2: Sort the values utilizing Java 8 Stream / Collections API
        values.sort(Integer::compareTo);

        // Step 3: Rebuild the linked list by mutating values in place
        curr = head;
        for (int val : values) {
            curr.val = val;
            curr = curr.next;
        }

        return head;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Top-Down Merge Sort)
     * ============================================================================
     * Detailed Intuition:
     * This is the standard, recursive divide-and-conquer strategy. We use the
     * Fast and Slow pointer technique to find the exact middle of the list. We
     * cut the list in half, recursively sort both halves, and then merge the
     * two sorted halves back together.
     * * Note: While this achieves O(n log n) time, it fails the strict O(1) space
     * constraint because the recursive call stack grows logarithmically.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). The list is bisected log N times, and
     * merging them at each level takes O(N) time.
     * - Space Complexity: O(log N). The auxiliary stack space used by recursion
     * is bounded by O(log N). Heap space is O(1).
     * ============================================================================
     */
    public ListNode sortListAlternative(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // Find middle and sever the list into two halves
        ListNode mid = getMid(head);
        ListNode rightHalf = mid.next;
        mid.next = null; // Sever the connection

        // Recursively sort both halves
        ListNode leftSorted = sortListAlternative(head);
        ListNode rightSorted = sortListAlternative(rightHalf);

        // Merge sorted halves
        return merge(leftSorted, rightSorted);
    }

    private ListNode getMid(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next; // offset to find lower mid for even lengths

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode curr = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }

        curr.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        SortListMasterclass solver = new SortListMasterclass();

        // Define Test Cases
        int[][] testLists = {
                {4, 2, 1, 3},              // Example 1: Random order
                {-1, 5, 3, 4, 0},          // Example 2: Negative numbers
                {},                        // Example 3: Empty list
                {1},                       // Edge Case: Single element
                {5, 4, 3, 2, 1},           // Reverse order
                {1, 1, 1, 1}               // Identical elements
        };

        System.out.println("--- \uD83E\uDD16 Running DSA Testing Suite ---\n");

        for (int i = 0; i < testLists.length; i++) {
            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Input List: " + Arrays.toString(testLists[i]));

            // Clone to avoid modifying the same list reference across tests
            ListNode head1 = buildList(testLists[i]);
            ListNode res1 = solver.sortListOptimal(head1);
            System.out.println("Optimal (Bottom-Up) : " + formatList(res1));

            ListNode head2 = buildList(testLists[i]);
            ListNode res2 = solver.sortListBruteForce(head2);
            System.out.println("Brute Force (Array) : " + formatList(res2));

            ListNode head3 = buildList(testLists[i]);
            ListNode res3 = solver.sortListAlternative(head3);
            System.out.println("Alternative (Top-Dn): " + formatList(res3));

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
