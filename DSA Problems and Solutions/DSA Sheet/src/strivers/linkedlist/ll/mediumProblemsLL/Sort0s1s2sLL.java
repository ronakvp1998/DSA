package strivers.linkedlist.ll.mediumProblemsLL;

/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION
 * ============================================================================
 * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * * ### 1. Header & Problem Context
 * * Problem Statement:
 * Given a linked list containing only 0's, 1's, and 2's, sort the linked list
 * by rearranging the links (not by changing the data values).
 * * Constraints:
 * - The number of nodes in the list is in the range [1, 10^5].
 * - Node.val is either 0, 1, or 2.
 * - MUST sort by changing links, altering node.val is strictly prohibited.
 * * Examples:
 * Input:  1 -> 2 -> 0 -> 1 -> 0 -> 2 -> NULL
 * Output: 0 -> 0 -> 1 -> 1 -> 2 -> 2 -> NULL
 * * Input:  2 -> 1 -> 2 -> 0 -> 0 -> 1 -> NULL
 * Output: 0 -> 0 -> 1 -> 1 -> 2 -> 2 -> NULL
 * * ============================================================================
 * ### 2.2. Progressive Implementation Roadmap (Non-DP)
 * * Phase 1: Optimal Approach (Dutch National Flag variation for Linked Lists)
 * - Use three separate dummy nodes to collect 0s, 1s, and 2s.
 * - Reconnect the three isolated chains together.
 * * Phase 2: Brute Force Approach (Merge Sort)
 * - Since data mutation is forbidden, standard counting sort fails
 * the constraints if we just replace data.
 * - Merge sort guarantees O(N log N) time while strictly changing
 * links and manipulating pointers.
 * * Phase 3: Alternative Approach (Data Replacement - Naive)
 * - The "illegal" but common first instinct. Count occurrences of
 * 0, 1, and 2, then overwrite list values. Included purely for
 * completeness of thought process.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sort0s1s2sLL {

    // Standard LinkedList Node definition
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; next = null; }
    }

    /**
     * ============================================================================
     * Phase 1: Optimal Approach - Three Pointer Partitioning
     * ============================================================================
     * Detailed Intuition:
     * Instead of moving data or doing complex recursive splits, we can physically
     * segregate the nodes into three separate buckets (linked lists) representing
     * 0s, 1s, and 2s. We use dummy heads for each bucket to avoid tedious null
     * checks. We traverse the original list once, appending each node to its
     * respective bucket. Finally, we stitch the ends of the 0-bucket to the
     * start of the 1-bucket, and the 1-bucket to the 2-bucket.
     * * Complexity Analysis:
     * Time Complexity: O(N) where N is the number of nodes. We traverse the list
     * exactly once.
     * Space Complexity: O(1) auxiliary space. We only allocate three dummy nodes
     * and a few pointers; no heap scaling or recursion stack.
     */
    public static ListNode sortOptimal(ListNode head) {
        if (head == null || head.next == null) return head;

        // Dummy nodes to anchor the three separate lists
        ListNode zeroDummy = new ListNode(-1);
        ListNode oneDummy = new ListNode(-1);
        ListNode twoDummy = new ListNode(-1);

        // Tail pointers for the three lists
        ListNode zeroTail = zeroDummy;
        ListNode oneTail = oneDummy;
        ListNode twoTail = twoDummy;

        ListNode curr = head;

        // Partition the original list into three separate chains
        while (curr != null) {
            if (curr.val == 0) {
                zeroTail.next = curr;
                zeroTail = zeroTail.next;
            } else if (curr.val == 1) {
                oneTail.next = curr;
                oneTail = oneTail.next;
            } else {
                twoTail.next = curr;
                twoTail = twoTail.next;
            }
            curr = curr.next;
        }

        // Connect the three chains together safely
        // 1. Attach the end of 0s to the beginning of 1s (or 2s if no 1s exist)
        zeroTail.next = (oneDummy.next != null) ? oneDummy.next : twoDummy.next;

        // 2. Attach the end of 1s to the beginning of 2s
        oneTail.next = twoDummy.next;

        // 3. Terminate the 2s list to avoid cycles
        twoTail.next = null;

        // The new head is the first valid node after zeroDummy
        return zeroDummy.next;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach - Merge Sort
     * ============================================================================
     * Detailed Intuition:
     * If an interviewer strictly bans changing node values and the candidate
     * cannot remember the three-dummy-node trick, a classic Merge Sort algorithm
     * for linked lists is the safest fallback. It breaks the list into halves,
     * sorts them recursively, and merges them back by rewiring the `next` pointers.
     * * Complexity Analysis:
     * Time Complexity: O(N log N). Splitting takes O(log N) steps, and merging
     * takes O(N) at each level.
     * Space Complexity: O(log N) auxiliary stack space for recursion. O(1) heap
     * space since merging is done in-place.
     */
    public static ListNode sortBruteForce(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode mid = getMiddle(head);
        ListNode rightHalf = mid.next;
        mid.next = null; // Split the list

        ListNode left = sortBruteForce(head);
        ListNode right = sortBruteForce(rightHalf);

        return merge(left, right);
    }

    // Helper method for Phase 2
    private static ListNode getMiddle(ListNode head) {
        if (head == null) return head;
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // Helper method for Phase 2
    private static ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
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
     * Phase 3: Alternative Approach - Counting Sort (Data Replacement)
     * ============================================================================
     * Detailed Intuition:
     * This is the "naive" thought process. Count the number of 0s, 1s, and 2s
     * in the first pass. In the second pass, overwrite the values of the nodes.
     * NOTE: This violates the "sort by changing links" constraint of the prompt,
     * but is included as it is the mathematical base-case for thinking about
     * finite-set sorting.
     * * Complexity Analysis:
     * Time Complexity: O(N) - Two passes.
     * Space Complexity: O(1) auxiliary space.
     */
    public static ListNode sortAlternative(ListNode head) {
        int[] counts = new int[3];
        ListNode curr = head;

        // Pass 1: Count occurrences
        while (curr != null) {
            counts[curr.val]++;
            curr = curr.next;
        }

        // Pass 2: Overwrite data (Constraint Violation)
        curr = head;
        for (int i = 0; i < 3; i++) {
            while (counts[i] > 0 && curr != null) {
                curr.val = i;
                counts[i]--;
                curr = curr.next;
            }
        }
        return head;
    }

    /**
     * ============================================================================
     * 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Running Testing Suite for Sort Linked List of 0s, 1s, and 2s\n");

        // Define test cases using Java 8 Stream API
//        <List<Integer>> testCases = Stream.of(
//                Arrays.asList(1, 2, 0, 1, 0, 2),
//                Arrays.asList(2, 1, 2, 0, 0, 1),
//                Arrays.asList(2, 2, 2, 2),
//                Arrays.asList(0, 0, 0),
//                Arrays.asList(2, 0, 2),
//                Arrays.asList(1, 1, 0, 0),
//                Arrays.asList(1), Arrays.asList()).collect(Collectors.toList());
//
//        for (int i = 0; i < testCases.size(); i++) {
//            System.out.println("Test Case " + (i + 1) + ": Input Arrays -> " + testCases.get(i));
//
//            // Test Phase 1: Optimal
//            ListNode headOptimal = buildList(testCases.get(i));
//            ListNode resOptimal = sortOptimal(headOptimal);
//            System.out.println("   [Optimal]     : " + printList(resOptimal));
//
//            // Test Phase 2: Brute Force
//            ListNode headBrute = buildList(testCases.get(i));
//            ListNode resBrute = sortBruteForce(headBrute);
//            System.out.println("   [Brute Force] : " + printList(resBrute));
//
//            System.out.println("-".repeat(50));
//        }
    }

    // --- Utility Methods for Testing ---

    private static ListNode buildList(List<Integer> values) {
        if (values == null || values.isEmpty()) return null;
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        return dummy.next;
    }

    private static String printList(ListNode head) {
        if (head == null) return "NULL";
        StringBuilder sb = new StringBuilder();
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val).append(" -> ");
            curr = curr.next;
        }
        sb.append("NULL");
        return sb.toString();
    }
}


