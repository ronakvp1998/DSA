package strivers.linkedlist.ll.mediumProblemsLL;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 160. Intersection of Two Linked Lists (Easy)
 * * Formal problem statement:
 * Given the heads of two singly linked-lists headA and headB, return the node
 * at which the two lists intersect. If the two linked lists have no intersection
 * at all, return null.
 * * The test cases are generated such that there are no cycles anywhere in the
 * entire linked structure.
 * Note that the linked lists must retain their original structure after the
 * function returns.
 * * Example 1:
 * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
 * Output: Intersected at '8'
 * Explanation: The intersected node's value is 8. From the head of A, it reads
 * as [4,1,8,4,5]. From the head of B, it reads as [5,6,1,8,4,5]. There are 2
 * nodes before the intersected node in A; There are 3 nodes before the intersected node in B.
 * * Example 2:
 * Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * Output: Intersected at '2'
 * Explanation: The intersected node's value is 2.
 * * Example 3:
 * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * Output: No intersection
 * Explanation: The two lists do not intersect, so return null.
 * * Constraints:
 * - The number of nodes of listA is in the m.
 * - The number of nodes of listB is in the n.
 * - 1 <= m, n <= 3 * 10^4
 * - 1 <= Node.val <= 10^5
 * - 0 <= skipA <= m
 * - 0 <= skipB <= n
 * - intersectVal is 0 if listA and listB do not intersect.
 * - intersectVal == listA[skipA] == listB[skipB] if listA and listB intersect.
 * * Follow up: Could you write a solution that runs in O(m + n) time and use only O(1) memory?
 * ============================================================================
 */
public class IntersectionOfTwoLinkedListsMasterclass {

    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    /**
     * ============================================================================
     * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
     * ============================================================================
     * Phase 1: Optimal Approach - Two Pointers Magic
     * * Detailed Intuition:
     * This is the most elegant solution, addressing the O(m + n) time and O(1) space
     * follow-up perfectly. The core issue is that the lists might be of different
     * lengths, meaning if we traverse them simultaneously, we won't hit the
     * intersection at the same time.
     * * However, if pointer A traverses listA and then jumps to listB, and pointer B
     * traverses listB and then jumps to listA, both pointers will travel exactly
     * length(A) + length(B) steps. This perfectly aligns them! If there is an
     * intersection, they will arrive at the intersecting node simultaneously. If
     * there isn't, they will both arrive at 'null' simultaneously.
     * * Complexity Analysis:
     * - Time Complexity: O(m + n)
     * In the worst case, each pointer traverses both linked lists entirely once.
     * - Space Complexity: O(1) auxiliary space.
     * We only use two pointers, independent of the sizes of the lists.
     * ============================================================================
     */
    public ListNode getIntersectionNodeOptimal(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        ListNode ptrA = headA;
        ListNode ptrB = headB;

        // Loop runs until they meet. If there's no intersection, they will meet at null.
        while (ptrA != ptrB) {
            // Move to the next node or switch lists
            ptrA = (ptrA == null) ? headB : ptrA.next;
            ptrB = (ptrB == null) ? headA : ptrB.next;
        }

        return ptrA;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach - Nested Iteration
     * * Detailed Intuition:
     * The "Think it" stage. The simplest way to find an intersection is to take every
     * node in listA and check if it exists in listB by iterating through listB completely.
     * We are checking for REFERENCE equality, not value equality.
     * * Complexity Analysis:
     * - Time Complexity: O(m * n)
     * For each of the 'm' nodes in listA, we traverse up to 'n' nodes in listB.
     * - Space Complexity: O(1) auxiliary space.
     * ============================================================================
     */
    public ListNode getIntersectionNodeBruteForce(ListNode headA, ListNode headB) {
        ListNode currA = headA;

        while (currA != null) {
            ListNode currB = headB;
            while (currB != null) {
                // Comparing references, not values
                if (currA == currB) {
                    return currA;
                }
                currB = currB.next;
            }
            currA = currA.next;
        }

        return null;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach A - HashSet
     * * Detailed Intuition:
     * We can optimize the Brute Force O(m * n) time by trading memory. If we traverse
     * listA and put all node references into a Hash Table, we can then traverse listB
     * and check if any node exists in the Hash Table in O(1) time. The first match
     * is the intersection.
     * * Complexity Analysis:
     * - Time Complexity: O(m + n)
     * We traverse listA once, then listB once. Hashing operations take O(1) on average.
     * - Space Complexity: O(m) auxiliary heap space.
     * We store 'm' nodes of listA in the HashSet.
     * ============================================================================
     */
    public ListNode getIntersectionNodeHashSet(ListNode headA, ListNode headB) {
        Set<ListNode> nodesInA = new HashSet<>();

        ListNode currA = headA;
        while (currA != null) {
            nodesInA.add(currA);
            currA = currA.next;
        }

        ListNode currB = headB;
        while (currB != null) {
            if (nodesInA.contains(currB)) {
                return currB;
            }
            currB = currB.next;
        }

        return null;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach B - Length Difference Syncing
     * * Detailed Intuition:
     * Another O(1) space, O(m+n) time alternative. If one list is longer than the
     * other by 'diff' nodes, the intersection can only happen 'diff' nodes AFTER
     * the head of the longer list. We calculate the lengths, advance the pointer
     * of the longer list by the difference, and then advance both pointers 1-by-1
     * until they meet.
     * * Complexity Analysis:
     * - Time Complexity: O(m + n)
     * Finding lengths takes O(m + n). Aligning and traversing takes at most O(max(m, n)).
     * - Space Complexity: O(1) auxiliary space.
     * ============================================================================
     */
    public ListNode getIntersectionNodeLengthDiff(ListNode headA, ListNode headB) {
        int lenA = getLength(headA);
        int lenB = getLength(headB);

        // Align the start pointers
        while (lenA > lenB) {
            headA = headA.next;
            lenA--;
        }
        while (lenB > lenA) {
            headB = headB.next;
            lenB--;
        }

        // Move together
        while (headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }

        return headA;
    }

    private int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        IntersectionOfTwoLinkedListsMasterclass solution = new IntersectionOfTwoLinkedListsMasterclass();

        // TestCase Definitions: {listA, listB, skipA, skipB, intersectVal}
        int[][] testCases = {
                {4, 1, 8, 4, 5}, {5, 6, 1, 8, 4, 5}, {2}, {3}, {8}, // Example 1
                {1, 9, 1, 2, 4}, {3, 2, 4}, {3}, {1}, {2},          // Example 2
                {2, 6, 4}, {1, 5}, {3}, {2}, {0}                    // Example 3 (No intersection)
        };

        System.out.println("--- Starting Test Suite for Intersection of Two Linked Lists ---");

        for (int i = 0; i < testCases.length; i += 5) {
            int[] arrA = testCases[i];
            int[] arrB = testCases[i + 1];
            int skipA = testCases[i + 2][0];
            int skipB = testCases[i + 3][0];
            int expectedVal = testCases[i + 4][0];

            // Build exact memory structure matching LeetCode's environment
            ListNode[] heads = buildIntersectingLists(arrA, arrB, skipA, skipB);
            ListNode headA = heads[0];
            ListNode headB = heads[1];

            System.out.println("\nTest Case " + ((i / 5) + 1) + ":");
            System.out.println("List A: " + Arrays.toString(arrA));
            System.out.println("List B: " + Arrays.toString(arrB));
            System.out.println("Expected Intersection Val: " + (expectedVal == 0 ? "null" : expectedVal));

            // Run Optimal
            ListNode res1 = solution.getIntersectionNodeOptimal(headA, headB);
            System.out.println("Optimal (Two Pointers)   : " + (res1 == null ? "null" : res1.val));

            // Run Brute Force
            ListNode res2 = solution.getIntersectionNodeBruteForce(headA, headB);
            System.out.println("Brute Force (Nested loop): " + (res2 == null ? "null" : res2.val));

            // Run HashSet
            ListNode res3 = solution.getIntersectionNodeHashSet(headA, headB);
            System.out.println("HashSet Alternative      : " + (res3 == null ? "null" : res3.val));

            // Run Length Diff
            ListNode res4 = solution.getIntersectionNodeLengthDiff(headA, headB);
            System.out.println("Length Diff Alternative  : " + (res4 == null ? "null" : res4.val));
        }
    }

    /**
     * Utility method: Constructs two linked lists that intersect exactly
     * at the given indices, simulating memory references perfectly.
     */
    private static ListNode[] buildIntersectingLists(int[] arrA, int[] arrB, int skipA, int skipB) {
        ListNode dummyA = new ListNode(0);
        ListNode dummyB = new ListNode(0);
        ListNode currA = dummyA;
        ListNode currB = dummyB;

        // Build unique parts of list A
        for (int i = 0; i < skipA; i++) {
            currA.next = new ListNode(arrA[i]);
            currA = currA.next;
        }

        // Build unique parts of list B
        for (int i = 0; i < skipB; i++) {
            currB.next = new ListNode(arrB[i]);
            currB = currB.next;
        }

        // Build the shared intersection list (if skipA is within bounds of A)
        ListNode intersectionNode = null;
        if (skipA < arrA.length) {
            intersectionNode = new ListNode(arrA[skipA]);
            ListNode currShared = intersectionNode;
            for (int i = skipA + 1; i < arrA.length; i++) {
                currShared.next = new ListNode(arrA[i]);
                currShared = currShared.next;
            }
        }

        // Attach the intersection to the end of both unique prefix lists
        currA.next = intersectionNode;
        currB.next = intersectionNode;

        return new ListNode[]{dummyA.next, dummyB.next};
    }
}