package strivers.linkedlist.ll.mediumProblemsLL;


/**
 * ============================================================================
 * 142. Linked List Cycle II
 * ============================================================================
 *
 * Problem Statement:
 * Given the head of a linked list, return the node where the cycle begins.
 * If there is no cycle, return null.
 *
 * There is a cycle in a linked list if there is some node in the list that can
 * be reached again by continuously following the next pointer. Internally, pos
 * is used to denote the index of the node that tail's next pointer is connected
 * to (0-indexed). It is -1 if there is no cycle. Note that pos is not passed
 * as a parameter.
 *
 * Do not modify the linked list.
 *
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: tail connects to node index 1
 * Explanation: There is a cycle in the linked list, where tail connects to
 * the second node.
 *
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: tail connects to node index 0
 * Explanation: There is a cycle in the linked list, where tail connects to
 * the first node.
 *
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: no cycle
 * Explanation: There is no cycle in the linked list.
 *
 * Constraints:
 * - The number of the nodes in the list is in the range [0, 10^4].
 * - -10^5 <= Node.val <= 10^5
 * - pos is -1 or a valid index in the linked-list.
 *
 * Follow up: Can you solve it using O(1) (i.e. constant) memory?
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (FLOYD'S CYCLE DETECTION MATH)
 * ============================================================================
 * Why does resetting the slow pointer to the head and moving both at 1x speed
 * find the cycle's start? Let's break down the math.
 *
 * Let:
 * L1 = Distance from the Head to the Cycle Start node.
 * L2 = Distance from the Cycle Start node to the Meeting Point.
 * C  = Length of the cycle.
 *
 * 1. Slow pointer distance traveled when they meet: D_slow = L1 + L2
 * 2. Fast pointer distance traveled when they meet: D_fast = L1 + L2 + n * C
 *    (Fast may have looped 'n' times before they meet).
 *
 * Because Fast moves 2x as fast as Slow:
 * 2 * (D_slow) = D_fast
 * 2 * (L1 + L2) = L1 + L2 + n * C
 * L1 + L2 = n * C
 * L1 = n * C - L2
 *
 * What does "n * C - L2" mean?
 * It is exactly the distance from the Meeting Point to the Cycle Start!
 * If you start at the Meeting Point, travel forward by (n * C - L2), you will
 * land exactly on the Cycle Start node.
 *
 * Therefore:
 * Distance from Head to Cycle Start (L1) == Distance from Meeting Point to
 * Cycle Start. If we put one pointer at Head and keep one at Meeting Point,
 * and move them at the same speed (1 step), they WILL collide at Cycle Start.
 * ============================================================================
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LoopStartingPoint {

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
    }

    /**
     * ============================================================================
     * Phase 1: Optimal Approach (Floyd's Tortoise and Hare)
     * ============================================================================
     * Detailed Intuition:
     * This algorithm operates in two phases:
     * Phase 1 (Intersection): Use a slow pointer (1 step) and a fast pointer
     * (2 steps). If there is no cycle, fast reaches null. If there is a cycle,
     * they will eventually collide at some node inside the cycle.
     * Phase 2 (Entrance Detection): Once a collision is detected, we leave the
     * fast pointer at the collision node and move the slow pointer back to the
     * head of the list. We then move both pointers at a speed of 1 step per
     * iteration. The mathematical proof (shown in the header) guarantees that
     * they will collide exactly at the entrance to the cycle.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   Phase 1 takes at most N iterations. Phase 2 takes at most N iterations.
     *   Overall runtime is strictly linear.
     * - Space Complexity: O(1)
     *   We only utilize two pointers (`slow` and `fast`). No auxiliary heap space
     *   or call stack space is used, satisfying the follow-up constraint.
     * ============================================================================
     */
    public ListNode detectCycleOptimal(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;

        // Phase 1: Find if there is a cycle and the intersection point
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }

        // If no cycle was detected, return null
        if (!hasCycle) {
            return null;
        }

        // Phase 2: Find the entrance to the cycle
        slow = head; // Reset slow pointer to the head

        while (slow != fast) {
            slow = slow.next; // Move at 1x speed
            fast = fast.next; // Move at 1x speed
        }

        // Both pointers now point to the start of the cycle
        return slow;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach (Hashing)
     * ============================================================================
     * Detailed Intuition:
     * The simplest mental model to find the cycle entrance is to track our history.
     * As we traverse the linked list node by node, we add the actual memory
     * reference of the node to a HashSet. The very first time we attempt to add
     * a node that is already present in our HashSet, we have found the exact node
     * where the loop begins.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   We traverse the linked list at most once. Hash set lookups and insertions
     *   are O(1) on average.
     * - Space Complexity: O(N) (Heap Space)
     *   We must store a reference to every node we visit. If the cycle loops
     *   at the very last node back to the second-to-last node, we will store N-1
     *   nodes in the HashSet.
     * ============================================================================
     */
    public ListNode detectCycleBruteForce(ListNode head) {
        Set<ListNode> visitedNodes = new HashSet<>();
        ListNode curr = head;

        while (curr != null) {
            // If the set already has the node, it's our cycle entrance!
            if (visitedNodes.contains(curr)) {
                return curr;
            }

            visitedNodes.add(curr);
            curr = curr.next;
        }

        return null; // Hit the end of the list, no cycle.
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approaches (Theoretical)
     * ============================================================================
     * 1. Destructive Marking (Violates Constraints)
     *    Intuition: Change the value of visited nodes to an out-of-bounds marker
     *    (e.g., 1000000). The first time we hit a node with this value, it's the
     *    start of the cycle.
     *    Why disqualified: The problem explicitly states "Do not modify the
     *    linked list."
     *
     * 2. Two-Pointers (Distance Measurement)
     *    While Floyd's algorithm is the standard, Brent's Algorithm (teleporting
     *    turtle and moving rabbit) can also be used. It generally performs fewer
     *    pointer evaluations than Floyd's but follows a similar O(N) Time and
     *    O(1) Space footprint. Floyd's is preferred for interview simplicity.
     * ============================================================================
     */

    /**
     * ============================================================================
     * 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        LoopStartingPoint solution = new LoopStartingPoint();

        System.out.println("--- Executing Testing Suite ---");

        // Test 1: Cycle at index 1 -> [3, 2, 0, -4]
        ListNode head1 = buildListWithCycle(new int[]{3, 2, 0, -4}, 1);
        System.out.println("\nTest 1: [3, 2, 0, -4], pos = 1 (Expected: Node with value 2)");
        ListNode res1Opt = solution.detectCycleOptimal(head1);
        System.out.println("Optimal (Floyd):  " + (res1Opt != null ? res1Opt.val : "null"));

        ListNode res1BF = solution.detectCycleBruteForce(head1);
        System.out.println("Brute Force:      " + (res1BF != null ? res1BF.val : "null"));

        // Test 2: Cycle at index 0 -> [1, 2]
        ListNode head2 = buildListWithCycle(new int[]{1, 2}, 0);
        System.out.println("\nTest 2: [1, 2], pos = 0 (Expected: Node with value 1)");
        ListNode res2Opt = solution.detectCycleOptimal(head2);
        System.out.println("Optimal (Floyd):  " + (res2Opt != null ? res2Opt.val : "null"));

        // Test 3: No cycle -> [1]
        ListNode head3 = buildListWithCycle(new int[]{1}, -1);
        System.out.println("\nTest 3: [1], pos = -1 (Expected: null)");
        ListNode res3Opt = solution.detectCycleOptimal(head3);
        System.out.println("Optimal (Floyd):  " + (res3Opt != null ? res3Opt.val : "null"));

        // Test 4: Edge Case - Empty List
        ListNode head4 = buildListWithCycle(new int[]{}, -1);
        System.out.println("\nTest 4: [], pos = -1 (Expected: null)");
        ListNode res4Opt = solution.detectCycleOptimal(head4);
        System.out.println("Optimal (Floyd):  " + (res4Opt != null ? res4Opt.val : "null"));

        // Test 5: Long list cycle test
        int[] longArray = IntStream.range(0, 1000).toArray(); // 0 to 999
        ListNode head5 = buildListWithCycle(longArray, 500); // Cycle at 500
        System.out.println("\nTest 5: [0...999], pos = 500 (Expected: Node with value 500)");
        ListNode res5Opt = solution.detectCycleOptimal(head5);
        System.out.println("Optimal (Floyd):  " + (res5Opt != null ? res5Opt.val : "null"));
    }

    /**
     * Helper Method: Builds a linked list from an array and creates a cycle.
     * Uses Java 8 Streams to construct the list elegantly.
     */
    private static ListNode buildListWithCycle(int[] values, int pos) {
        if (values == null || values.length == 0) return null;

        // Map values to Node objects using Java 8 Streams
        List<ListNode> nodes = IntStream.of(values)
                .mapToObj(ListNode::new)
                .collect(Collectors.toList());

        // Connect next pointers
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).next = nodes.get(i + 1);
        }

        // Establish the cycle if 'pos' is a valid index
        if (pos >= 0 && pos < nodes.size()) {
            ListNode tail = nodes.get(nodes.size() - 1);
            ListNode cycleTarget = nodes.get(pos);
            tail.next = cycleTarget;
        }

        return nodes.get(0);
    }
}
