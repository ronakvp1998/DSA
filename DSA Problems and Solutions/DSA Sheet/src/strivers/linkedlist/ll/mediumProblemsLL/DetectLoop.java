package strivers.linkedlist.ll.mediumProblemsLL;

/**
 * ============================================================================
 * 141. Linked List Cycle
 * ============================================================================
 *
 * Problem Statement:
 * Given head, the head of a linked list, determine if the linked list has a
 * cycle in it.
 *
 * There is a cycle in a linked list if there is some node in the list that can
 * be reached again by continuously following the next pointer. Internally, pos
 * is used to denote the index of the node that tail's next pointer is connected
 * to. Note that pos is not passed as a parameter.
 *
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 *
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to
 * the 1st node (0-indexed).
 *
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to
 * the 0th node.
 *
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: false
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
 * CONCEPTUAL VISUALIZATION (FLOYD'S CYCLE DETECTION)
 * ============================================================================
 * Example 1: [3, 2, 0, -4], pos = 1
 *
 * List Graph:
 *   3  ->  2  ->  0  -> -4
 *          ^             |
 *          |_____________|
 *
 * Tortoise (Slow) & Hare (Fast) Traversal:
 * Step 0: Slow at 3, Fast at 3
 * Step 1: Slow at 2, Fast at 0
 * Step 2: Slow at 0, Fast at 2 (Fast looped around -4 -> 2)
 * Step 3: Slow at -4, Fast at -4  --> COLLISION! Cycle detected.
 * ============================================================================
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DetectLoop {

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
     * To solve this with O(1) memory, we use two pointers moving at different
     * speeds: a "slow" pointer moving one step at a time, and a "fast" pointer
     * moving two steps.
     *
     * Think of two runners on a track. If the track is a straight line (no cycle),
     * the fast runner will eventually reach the finish line (null). However, if
     * the track is circular (has a cycle), the fast runner will eventually lap
     * the slow runner. When they meet at the exact same node, we definitively
     * prove a cycle exists.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   If there is no cycle, the fast pointer reaches the end in N/2 steps.
     *   If there is a cycle, the maximum distance between them is bounded by N.
     *   Since the distance closes by 1 step each iteration, they will meet in
     *   at most N iterations.
     * - Space Complexity: O(1)
     *   Only two pointers (slow and fast) are allocated. No heap space or
     *   auxiliary stack space is utilized.
     * ============================================================================
     */
    public boolean hasCycleOptimal(ListNode head) {
        // Empty list or single node without a self-loop cannot have a cycle.
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        // Traverse while the fast pointer has valid nodes to jump to
        while (fast != null && fast.next != null) {
            slow = slow.next;          // Move 1 step
            fast = fast.next.next;     // Move 2 steps

            // Collision detected! They are on the exact same node instance.
            if (slow == fast) {
                return true;
            }
        }

        // Fast pointer reached the end of the list; no cycle exists.
        return false;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach (Hashing)
     * ============================================================================
     * Detailed Intuition:
     * The most intuitive way to detect a cycle is to remember every node we have
     * visited. As we traverse the list, we store the object reference of each
     * node in a HashSet. If we encounter a node that is already in our set,
     * we have traveled in a circle.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   We visit each node at most once. HashSet insertions and lookups take
     *   O(1) average time.
     * - Space Complexity: O(N) (Heap Space)
     *   In the worst case (no cycle or cycle at the very end), we store all N
     *   node references in the HashSet on the heap.
     * ============================================================================
     */
    public boolean hasCycleBruteForce(ListNode head) {
        Set<ListNode> visitedNodes = new HashSet<>();
        ListNode curr = head;

        while (curr != null) {
            // If the set already contains the node reference, it's a cycle.
            if (visitedNodes.contains(curr)) {
                return true;
            }
            visitedNodes.add(curr);
            curr = curr.next;
        }

        return false;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach (Destructive Value Marking)
     * ============================================================================
     * Detailed Intuition:
     * If modifying the input data is permitted, we can use the problem constraints
     * to our advantage. The node values are constrained between -10^5 and 10^5.
     * We can pick a marker value outside this range (e.g., 1000000).
     *
     * As we traverse, we check if the current node's value is our marker. If it
     * is, we've been here before (cycle). If not, we change its value to the marker
     * and move forward.
     *
     * *Note: In a real interview, explicitly state that mutating the input
     * structure is generally frowned upon unless strictly isolated, but it proves
     * a deep understanding of constraints and space optimization.*
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   Single traversal through the linked list.
     * - Space Complexity: O(1)
     *   No auxiliary data structures are used, modifying in-place.
     * ============================================================================
     */
    public boolean hasCycleDestructive(ListNode head) {
        ListNode curr = head;
        // A value safely outside the -10^5 to 10^5 constraint
        final int VISITED_MARKER = 1000000;

        while (curr != null) {
            if (curr.val == VISITED_MARKER) {
                return true; // We've seen this node before!
            }

            // Mark the node as visited
            curr.val = VISITED_MARKER;
            curr = curr.next;
        }

        return false;
    }

    /**
     * ============================================================================
     * 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        DetectLoop solution = new DetectLoop();

        System.out.println("--- Executing Testing Suite ---");

        // Test 1: Cycle at index 1 -> [3, 2, 0, -4]
        ListNode head1 = buildListWithCycle(new int[]{3, 2, 0, -4}, 1);
        System.out.println("\nTest 1: [3, 2, 0, -4], pos = 1 (Expected: true)");
        System.out.println("Optimal (Floyd):  " + solution.hasCycleOptimal(head1));

        // Rebuild list for non-destructive brute-force test
        ListNode head1BF = buildListWithCycle(new int[]{3, 2, 0, -4}, 1);
        System.out.println("Brute Force:      " + solution.hasCycleBruteForce(head1BF));

        // Test 2: Cycle at index 0 -> [1, 2]
        ListNode head2 = buildListWithCycle(new int[]{1, 2}, 0);
        System.out.println("\nTest 2: [1, 2], pos = 0 (Expected: true)");
        System.out.println("Optimal (Floyd):  " + solution.hasCycleOptimal(head2));

        // Test 3: No cycle -> [1]
        ListNode head3 = buildListWithCycle(new int[]{1}, -1);
        System.out.println("\nTest 3: [1], pos = -1 (Expected: false)");
        System.out.println("Optimal (Floyd):  " + solution.hasCycleOptimal(head3));

        // Test 4: Edge Case - Empty List
        ListNode head4 = buildListWithCycle(new int[]{}, -1);
        System.out.println("\nTest 4: [], pos = -1 (Expected: false)");
        System.out.println("Optimal (Floyd):  " + solution.hasCycleOptimal(head4));

        // Test 5: Destructive Approach Check
        ListNode head5 = buildListWithCycle(new int[]{5, 6, 7, 8}, 2);
        System.out.println("\nTest 5: Destructive marking test [5, 6, 7, 8], pos=2 (Expected: true)");
        System.out.println("Destructive:      " + solution.hasCycleDestructive(head5));
    }

    /**
     * Helper Method: Builds a linked list from an array and creates a cycle.
     * Uses Java 8 IntStream for node mapping.
     */
    private static ListNode buildListWithCycle(int[] values, int pos) {
        if (values == null || values.length == 0) return null;

        // Create all nodes using Java 8 Streams
        List<ListNode> nodes = IntStream.of(values)
                .mapToObj(ListNode::new)
                .collect(Collectors.toList());

        // Link nodes sequentially
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).next = nodes.get(i + 1);
        }

        // Create the cycle if pos is valid
        if (pos >= 0 && pos < nodes.size()) {
            ListNode tail = nodes.get(nodes.size() - 1);
            ListNode cycleTarget = nodes.get(pos);
            tail.next = cycleTarget;
        }

        return nodes.get(0);
    }
}
