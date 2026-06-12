package strivers.linkedlist.dll.mediumProblemsDLL;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * Delete all occurrences of a given key in a doubly linked list
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * You are given the head of a doubly Linked List and a key.
 * Delete all occurrences of the given key and return the head of the modified list.
 *
 * Example 1:
 * Input: head = [2, 1, 1, 2, 1], key = 1
 * Output: [2, 2]
 * Explanation: All occurrences of 1 are deleted.
 *
 * Example 2:
 * Input: head = [1, 1, 1], key = 1
 * Output: []
 * Explanation: All nodes contain the key, so the list becomes empty.
 * * Example 3:
 * Input: head = [1, 2, 3], key = 4
 * Output: [1, 2, 3]
 * Explanation: The key is not present, list remains unchanged.
 *
 * CONSTRAINTS:
 * 0 <= Number of nodes <= 10^5
 * -10^5 <= Node.val <= 10^5
 * -10^5 <= key <= 10^5
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - In-place Single Pass (O(1) Space)
 * Phase 2: Brute Force Approach - Extract, Filter, and Rebuild
 * Phase 3: Alternative Approach - Recursive Deletion
 * ============================================================================
 */
public class DeleteKeyInDLL {

    /**
     * Definition for doubly-linked list.
     */
    public static class Node {
        int val;
        Node prev;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (In-place Single Pass)
     * ============================================================================
     * Detailed Intuition:
     * To achieve the optimal O(1) space complexity, we must modify the list in place.
     * We traverse the Doubly Linked List (DLL) node by node. When we find a node
     * matching the `key`, we need to wire its `prev` node to its `next` node, effectively
     * bypassing and dropping the current node.
     * * We must handle three crucial edge cases:
     * 1. Deleting the head node (need to update the overall head pointer and nullify the new head's prev).
     * 2. Deleting the tail node (need to carefully handle the next node being null).
     * 3. Deleting an intermediate node (standard bypass).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the number of nodes in the DLL. We visit
     * each node exactly once.
     * - Space Complexity: O(1) auxiliary space, as we are only manipulating a few
     * pointers (`curr`, `prevNode`, `nextNode`) regardless of list size.
     * ============================================================================
     */
    public Node deleteAllOccurrencesOptimal(Node head, int key) {
        Node curr = head;

        while (curr != null) {
            if (curr.val == key) {
                // Case 1: The node to delete is the head
                if (curr == head) {
                    head = head.next;
                    if (head != null) {
                        head.prev = null;
                    }
                }
                // Case 2 & 3: The node is in the middle or at the tail
                else {
                    Node prevNode = curr.prev;
                    Node nextNode = curr.next;

                    if (prevNode != null) {
                        prevNode.next = nextNode;
                    }
                    if (nextNode != null) {
                        nextNode.prev = prevNode;
                    }
                }
            }
            curr = curr.next;
        }

        return head;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Extract, Filter, Rebuild) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If pointer manipulation is prone to errors, the simplest mental model is to
     * avoid doing it in-place entirely. We can:
     * 1. Traverse the list and collect all values into a standard Java collection.
     * 2. Use Java 8 Streams to filter out all occurrences of the `key`.
     * 3. Rebuild a brand new Doubly Linked List from the filtered values.
     * * Complexity Analysis:
     * - Time Complexity: O(N). Extracting takes O(N), stream filtering takes O(N),
     * and rebuilding takes O(N). Overall time is linear.
     * - Space Complexity: O(N) heap space to hold the extracted values in an ArrayList
     * and to instantiate the brand new nodes for the rebuilt DLL.
     * ============================================================================
     */
    public Node deleteAllOccurrencesBruteForce(Node head, int key) {
        if (head == null) return null;

        List<Integer> values = new ArrayList<>();
        Node curr = head;

        // 1. Extract values
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }

        // 2. Filter out the key using Streams
        List<Integer> filteredValues = values.stream()
                .filter(val -> val != key)
                .collect(Collectors.toList());

        if (filteredValues.isEmpty()) return null;

        // 3. Rebuild the DLL
        Node dummy = new Node(-1);
        Node tail = dummy;

        for (int val : filteredValues) {
            Node newNode = new Node(val);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        Node newHead = dummy.next;
        if (newHead != null) {
            newHead.prev = null; // Detach from dummy
        }

        return newHead;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Recursive Deletion)
     * ============================================================================
     * Detailed Intuition:
     * We can define this recursively: The modified DLL starting at `head` is the
     * result of deleting the key from the rest of the list (`head.next`).
     * If `head.val == key`, we skip the `head` and just return the processed remainder.
     * If `head.val != key`, we attach the processed remainder to `head.next`, establish
     * the `prev` pointer back to `head`, and return `head`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), as each node triggers one recursive call.
     * - Space Complexity: O(N) auxiliary stack space due to the call stack reaching
     * depth N. Not suitable for very large lists, but an excellent display of
     * recursive pointer wiring.
     * ============================================================================
     */
    public Node deleteAllOccurrencesRecursive(Node head, int key) {
        // Base case
        if (head == null) {
            return null;
        }

        // Recursive call for the rest of the list
        Node nextHead = deleteAllOccurrencesRecursive(head.next, key);

        if (head.val == key) {
            // Skip current node. If nextHead exists, its prev becomes null
            // since it's the new head of this subproblem.
            if (nextHead != null) {
                nextHead.prev = null;
            }
            return nextHead;
        } else {
            // Keep current node, link it with the recursively processed tail
            head.next = nextHead;
            if (nextHead != null) {
                nextHead.prev = head;
            }
            return head;
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
        DeleteKeyInDLL solver = new DeleteKeyInDLL();

        System.out.println("=== Test Case 1: Standard Input (Multiple occurrences) ===");
        // Input: [2, 1, 1, 2, 1], key = 1
        System.out.print("Optimal Output:      ");
        printDLL(solver.deleteAllOccurrencesOptimal(buildDLL(new int[]{2, 1, 1, 2, 1}), 1));
        System.out.print("Brute Force Output:  ");
        printDLL(solver.deleteAllOccurrencesBruteForce(buildDLL(new int[]{2, 1, 1, 2, 1}), 1));
        System.out.print("Recursive Output:    ");
        printDLL(solver.deleteAllOccurrencesRecursive(buildDLL(new int[]{2, 1, 1, 2, 1}), 1));

        System.out.println("\n=== Test Case 2: All Nodes Match Key ===");
        // Input: [1, 1, 1], key = 1
        System.out.print("Optimal Output:      ");
        printDLL(solver.deleteAllOccurrencesOptimal(buildDLL(new int[]{1, 1, 1}), 1));

        System.out.println("\n=== Test Case 3: No Nodes Match Key ===");
        // Input: [1, 2, 3], key = 4
        System.out.print("Optimal Output:      ");
        printDLL(solver.deleteAllOccurrencesOptimal(buildDLL(new int[]{1, 2, 3}), 4));

        System.out.println("\n=== Test Case 4: Key at Head and Tail ===");
        // Input: [5, 2, 3, 5], key = 5
        System.out.print("Recursive Output:    ");
        printDLL(solver.deleteAllOccurrencesRecursive(buildDLL(new int[]{5, 2, 3, 5}), 5));

        System.out.println("\n=== Test Case 5: Empty List ===");
        // Input: [], key = 1
        System.out.print("Optimal Output:      ");
        printDLL(solver.deleteAllOccurrencesOptimal(buildDLL(new int[]{}), 1));
    }

    // --- Helper Methods for Testing Suite --- //

    private static Node buildDLL(int[] values) {
        if (values == null || values.length == 0) return null;
        Node head = new Node(values[0]);
        Node tail = head;
        for (int i = 1; i < values.length; i++) {
            Node newNode = new Node(values[i]);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        return head;
    }

    private static void printDLL(Node head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }

        // Forward traversal to print and find tail
        List<String> forward = new ArrayList<>();
        Node curr = head;
        Node tail = null;
        while (curr != null) {
            forward.add(String.valueOf(curr.val));
            tail = curr;
            curr = curr.next;
        }

        // Optional: Backward traversal to verify 'prev' pointers are completely correct
        List<String> backward = new ArrayList<>();
        curr = tail;
        while (curr != null) {
            backward.add(String.valueOf(curr.val));
            curr = curr.prev;
        }
        Collections.reverse(backward);

        // If forward and backward match, our DLL is structurally sound!
        boolean isValid = forward.equals(backward);

        System.out.println("[" + String.join(" <-> ", forward) + "] (Prev Pointers Valid: " + isValid + ")");
    }
}