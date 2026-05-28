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
 * Given the head of a linked list, determine the length of a loop present in the linked list.
 * If there's no loop present, return 0.
 *
 * Constraints:
 * - The number of nodes in the list is in the range [0, 10^4].
 * - Node.val is between -10^5 and 10^5.
 * - `pos` (the index where the tail connects to form a cycle) is -1 or a valid index.
 * * Input/Output Formats & Examples:
 * Example 1:
 * Input: 1 -> 2 -> 3 -> 4 -> 5 -> (points back to 3)
 * Output: 3
 * Explanation: A cycle exists in the linked list starting at node 3 -> 4 -> 5 and then
 * back to 3. There are 3 nodes present in this cycle.
 *
 * Example 2:
 * Input: 1 -> 2 -> 3 -> 4 -> null
 * Output: 0
 * Explanation: The linked list is linear and does not have a loop hence return 0.
 *
 * Example 3 (Edge Case):
 * Input: 1 -> (points back to 1)
 * Output: 1
 * Explanation: A single node pointing to itself forms a loop of length 1.
 *
 * =========================================================================================
 * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * =========================================================================================
 * Phase 1: Optimal Approach - Floyd's Cycle-Finding Algorithm (Tortoise and Hare).
 * Phase 2: Brute Force Approach - Hashing (HashMap to track visited nodes and timestamps).
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.IntStream;

public class LengthOfLoop {

    /**
     * Definition for singly-linked list.
     */
    static class Node {
        int val;
        Node next;
        Node(int val) {
            this.val = val;
            this.next = null;
        }
    }

    /**
     * =====================================================================================
     * PHASE 1: OPTIMAL APPROACH (The Recommended Approach)
     * =====================================================================================
     * Detailed Intuition:
     * Instead of remembering every node we visit, we can use two pointers moving at different
     * speeds: a 'slow' pointer moving 1 step, and a 'fast' pointer moving 2 steps.
     * If a loop exists, the fast pointer will eventually "lap" the slow pointer and they
     * will meet inside the loop.
     * Once they meet, we know a loop exists. To find its length, we can keep the fast pointer
     * stationary at the meeting point and move the slow pointer one step at a time, counting
     * the steps until it reaches the fast pointer again.
     *
     * Complexity Analysis:
     * Time Complexity: O(N)
     * - The fast and slow pointers will meet in at most N steps. Counting the loop
     * takes at most N steps. Total time is strictly linear.
     * Space Complexity: O(1) Auxiliary Space
     * - We only allocate two pointer variables, regardless of the list size. No heap
     * space is utilized for mapping, and no auxiliary stack space is used since
     * it's iterative.
     */
    public static int countLoopLengthOptimal(Node head) {
        if (head == null || head.next == null) {
            return 0; // Empty list or single node without loop
        }

        Node slow = head;
        Node fast = head;

        // Step 1: Detect if a loop exists
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                // Loop detected!
                // Step 2: Count the length of the loop
                int count = 1;
                slow = slow.next;
                while (slow != fast) {
                    count++;
                    slow = slow.next;
                }
                return count;
            }
        }

        return 0; // No loop found
    }

    /**
     * =====================================================================================
     * PHASE 2: BRUTE FORCE APPROACH (The "Think it" stage)
     * =====================================================================================
     * Detailed Intuition:
     * As we traverse the linked list, we need a way to remember if we've seen a node before.
     * If we see a node for the second time, we have found a loop. To find the *length* of
     * the loop, we need to know *when* we first saw that node.
     * We can use a HashMap where the Key is the Node reference, and the Value is a "timer"
     * or "step count" indicating when we visited it. When we hit an already-visited node,
     * the length of the loop is simply: (current step count) - (step count when first visited).
     *
     * Complexity Analysis:
     * Time Complexity: O(N)
     * - We visit each node exactly once. HashMap insertions and lookups take O(1)
     * time on average. Total time is linear.
     * Space Complexity: O(N) Heap Space
     * - We store object references and their corresponding integers in the HashMap.
     * In the worst case (no loop), we store all N nodes in the map. Auxiliary stack
     * space is O(1).
     */
    public static int countLoopLengthBruteForce(Node head) {
        Map<Node, Integer> visitedTime = new HashMap<>();
        Node current = head;
        int timer = 0;

        while (current != null) {
            if (visitedTime.containsKey(current)) {
                // Loop found. Current timer minus the time we first saw it = loop length.
                return timer - visitedTime.get(current);
            }

            // Record the time we first visited this node
            visitedTime.put(current, timer);
            current = current.next;
            timer++;
        }

        return 0; // Reached the end of the list, no loop.
    }

    /**
     * =====================================================================================
     * 4. TESTING SUITE
     * =====================================================================================
     * Contains standard configurations, zero-value edge cases, and leverages Java 8
     * Streams to neatly execute and validate all test configurations.
     */
    public static void main(String[] args) {
        System.out.println("--- Executing Masterclass Testing Suite ---\n");

        // Test Case 1: Standard loop
        Node head1 = buildListWithLoop(new int[]{1, 2, 3, 4, 5}, 2); // Loop starts at index 2 (value 3)

        // Test Case 2: Linear list (No loop)
        Node head2 = buildListWithLoop(new int[]{1, 2, 3, 4}, -1); // -1 means no loop

        // Test Case 3: Empty list
        Node head3 = null;

        // Test Case 4: Single node, no loop
        Node head4 = buildListWithLoop(new int[]{1}, -1);

        // Test Case 5: Single node, self loop
        Node head5 = buildListWithLoop(new int[]{1}, 0);

        // Grouping tests into a structured stream evaluation using Java 8 features
        Object[][] tests = {
                {"TC1: Standard Loop of 3", head1, 3},
                {"TC2: Linear List", head2, 0},
                {"TC3: Empty List", head3, 0},
                {"TC4: Single Node No Loop", head4, 0},
                {"TC5: Single Node Self Loop", head5, 1}
        };

        Arrays.stream(tests).forEach(test -> {
            String name = (String) test[0];
            Node head = (Node) test[1];
            int expected = (int) test[2];

            int bruteForceRes = countLoopLengthBruteForce(head);
            int optimalRes = countLoopLengthOptimal(head);

            boolean passed = (bruteForceRes == expected) && (optimalRes == expected);

            System.out.printf("%-30s | Expected: %d | BruteForce: %d | Optimal: %d | Status: %s%n",
                    name, expected, bruteForceRes, optimalRes, passed ? "PASS ✅" : "FAIL ❌");
        });
    }

    /**
     * Helper method to construct linked lists for testing.
     * @param values The values to populate the list.
     * @param loopIndex The 0-based index where the tail should connect to form a loop.
     * Pass -1 for a linear list.
     * @return The head of the newly constructed linked list.
     */
    private static Node buildListWithLoop(int[] values, int loopIndex) {
        if (values == null || values.length == 0) return null;

        Node[] nodes = IntStream.of(values)
                .mapToObj(Node::new)
                .toArray(Node[]::new);

        for (int i = 0; i < nodes.length - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }

        if (loopIndex >= 0 && loopIndex < nodes.length) {
            // Connect the tail to the node at loopIndex
            nodes[nodes.length - 1].next = nodes[loopIndex];
        }

        return nodes[0];
    }
}
