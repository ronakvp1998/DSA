package strivers.linkedlist.ll.mediumProblemsLL;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 138. Copy List with Random Pointer (Medium)
 * * A linked list of length n is given such that each node contains an additional
 * random pointer, which could point to any node in the list, or null.
 * * Construct a deep copy of the list. The deep copy should consist of exactly n
 * brand new nodes, where each new node has its value set to the value of its
 * corresponding original node. Both the next and random pointer of the new nodes
 * should point to new nodes in the copied list such that the pointers in the
 * original list and copied list represent the same list state. None of the
 * pointers in the new list should point to nodes in the original list.
 * * For example, if there are two nodes X and Y in the original list, where
 * X.random --> Y, then for the corresponding two nodes x and y in the copied
 * list, x.random --> y.
 * * Return the head of the copied linked list.
 * * Example 1:
 * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * * Example 2:
 * Input: head = [[1,1],[2,1]]
 * Output: [[1,1],[2,1]]
 * * Example 3:
 * Input: head = [[3,null],[3,0],[3,null]]
 * Output: [[3,null],[3,0],[3,null]]
 * * Constraints:
 * 0 <= n <= 1000
 * -10^4 <= Node.val <= 10^4
 * Node.random is null or is pointing to some node in the linked list.
 * * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * ============================================================================
 * Phase 1: Optimal Approach - O(1) Auxiliary Space (Interleaving Nodes)
 * Phase 2: Brute Force Approach - O(N) Space (HashMap)
 * Phase 3: Alternative Approach - Recursive HashMap mapping
 * ============================================================================
 */

import java.util.HashMap;
import java.util.Map;

public class CopyListWithRandomPointer {

    // Standard LeetCode Node Definition
    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * ========================================================================
     * PHASE 2: Optimize (HashMap) approach
     * ========================================================================
     * Detailed Intuition:
     * To create a deep copy, we need a way to connect the `next` and `random`
     * pointers of the new nodes exactly like the original. We can use a HashMap
     * to keep a direct 1:1 mapping between an Original Node and its Deep Clone.
     * * Step 1: Traverse the list and create a cloned node for every original node.
     * Store the mapping Map<Original, Clone>.
     * Step 2: Traverse the list again. For every original node, look up its
     * clone in the map. Set the clone's next and random pointers by
     * looking up the original node's next and random references in the map.
     * * Complexity Analysis:
     * Time Complexity: O(N) - Two passes through the linked list. Map lookups are O(1).
     * Space Complexity: O(N) - Heap space consumed by the HashMap storing N entries.
     * ========================================================================
     */
    public static Node copyRandomListBruteForce(Node head) {
        if (head == null) return null;

        Map<Node, Node> oldToNewMap = new HashMap<>();

        // First pass: create all cloned nodes and populate map
        Node current = head;
        while (current != null) {
            oldToNewMap.put(current, new Node(current.val));
            current = current.next;
        }

        // Second pass: assign next and random pointers based on the map
        current = head;
        while (current != null) {
            Node clone = oldToNewMap.get(current);
            clone.next = oldToNewMap.get(current.next);
            clone.random = oldToNewMap.get(current.random);
            current = current.next;
        }

        return oldToNewMap.get(head);
    }

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Interleaving Nodes)
     * ========================================================================
     * Detailed Intuition:
     * In top-tier technical interviews, avoiding the O(N) auxiliary space of a
     * HashMap is the primary discriminator for this problem. To map an original
     * node to its clone without external data structures, we can weave the
     * cloned nodes directly into the original list.
     * * Step 1: Create a cloned node and insert it immediately after its original
     * counterpart. (A -> A' -> B -> B')
     * Step 2: Traverse the modified list. If original node X has a random pointer
     * to Y, then the cloned node X' (which is X.next) will have its random
     * pointer point to Y' (which is Y.next).
     * Step 3: Unweave the lists. Restore the original list's next pointers and
     * extract the cloned list.
     * * Complexity Analysis:
     * Time Complexity: O(N) - We iterate through the list three times (Step 1, 2, 3),
     * which reduces to O(N).
     * Space Complexity: O(1) Auxiliary Space - We only use a few pointers. (The
     * space allocated for the new deep-copied list itself is O(N),
     * but auxiliary memory is O(1)).
     * ========================================================================
     */
    public static Node copyRandomListOptimal(Node head) {
        if (head == null) return null;

        // Step 1: Create cloned nodes and interleave them with original nodes.
        Node current = head;
        while (current != null) {
            Node clone = new Node(current.val);
            clone.next = current.next;
            current.next = clone;
            current = clone.next;
        }

        // Step 2: Assign random pointers for the cloned nodes.
        current = head;
        while (current != null) {
            if (current.random != null) {
                // The clone's random is the clone of the original's random
                current.next.random = current.random.next;
            }
            current = current.next.next;
        }

        // Step 3: Unweave the linked list to separate original and cloned lists.
        current = head;
        Node pseudoHead = new Node(0);
        Node cloneTail = pseudoHead;

        while (current != null) {
            Node clone = current.next;
            current.next = clone.next; // Restore original list
            cloneTail.next = clone;    // Build cloned list

            cloneTail = cloneTail.next;
            current = current.next;
        }

        return pseudoHead.next;
    }


    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Recursive Graph Traversal)
     * ========================================================================
     * Detailed Intuition:
     * Treat the linked list as a graph where `next` and `random` are edges.
     * We can traverse the graph using DFS (recursion). We use a HashMap to keep
     * track of already visited/cloned nodes to prevent infinite loops (cycles
     * via random pointers).
     * * Complexity Analysis:
     * Time Complexity: O(N) - Each node is visited and processed exactly once.
     * Space Complexity: O(N) - HashMap heap space + O(N) implicit Call Stack space.
     * ========================================================================
     */
    private static Map<Node, Node> visitedMap = new HashMap<>();

    public static Node copyRandomListRecursive(Node head) {
        if (head == null) return null;

        // If we have already cloned this node, return the clone
        if (visitedMap.containsKey(head)) {
            return visitedMap.get(head);
        }

        // Create a new clone and immediately put it in the map to handle cycles
        Node clone = new Node(head.val);
        visitedMap.put(head, clone);

        // Recursively clone next and random
        clone.next = copyRandomListRecursive(head.next);
        clone.random = copyRandomListRecursive(head.random);

        return clone;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("Executing Testing Suite for Copy List with Random Pointer...\n");

        // Helper to run tests
        runTest("Example 1", new Integer[][]{{7, null}, {13, 0}, {11, 4}, {10, 2}, {1, 0}});
        runTest("Example 2", new Integer[][]{{1, 1}, {2, 1}});
        runTest("Edge Case (Empty List)", new Integer[][]{});
    }

    private static void runTest(String testName, Integer[][] nodeData) {
        System.out.println("--- " + testName + " ---");
        Node head = buildList(nodeData);

        System.out.println("Original List:");
        printList(head);

        Node optimalClone = copyRandomListOptimal(head);
        System.out.println("Optimal Deep Copy:");
        printList(optimalClone);

        System.out.println();
    }

    // Utility to build list from array format (e.g., [[7,null],[13,0],...])
    private static Node buildList(Integer[][] data) {
        if (data.length == 0) return null;

        Node[] nodes = new Node[data.length];
        for (int i = 0; i < data.length; i++) {
            nodes[i] = new Node(data[i][0]);
        }

        for (int i = 0; i < data.length; i++) {
            if (i < data.length - 1) nodes[i].next = nodes[i + 1];
            if (data[i][1] != null) nodes[i].random = nodes[data[i][1]];
        }

        return nodes[0];
    }

    // Utility to print the list in [[val, random_index]] format
    private static void printList(Node head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }

        // Map to find index of random nodes for printing
        Map<Node, Integer> nodeToIndex = new HashMap<>();
        Node current = head;
        int index = 0;
        while (current != null) {
            nodeToIndex.put(current, index++);
            current = current.next;
        }

        current = head;
        System.out.print("[");
        while (current != null) {
            Integer randomIndex = current.random != null ? nodeToIndex.get(current.random) : null;
            System.out.print("[" + current.val + "," + randomIndex + "]");
            if (current.next != null) System.out.print(",");
            current = current.next;
        }
        System.out.println("]");
    }
}