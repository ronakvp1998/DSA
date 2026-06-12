package strivers.linkedlist.dll.mediumProblemsDLL;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * Remove Duplicates from a Sorted Doubly Linked List
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given a doubly linked list of nodes sorted by value in ascending order,
 * remove all duplicates such that each element appears only once.
 * Return the linked list, which must remain sorted.
 *
 * Example 1:
 * Input: head = [1, 1, 1, 2, 3]
 * Output: [1, 2, 3]
 * Explanation: The value 1 appears three times. We keep only the first occurrence.
 *
 * Example 2:
 * Input: head = [1, 2, 2, 3, 3, 4]
 * Output: [1, 2, 3, 4]
 * Explanation: The values 2 and 3 appear twice. We keep only one of each.
 *
 * Example 3:
 * Input: head = []
 * Output: []
 * * CONSTRAINTS:
 * The number of nodes in the list is in the range [0, 10^5].
 * -10^5 <= Node.val <= 10^5
 * The linked list is strictly sorted in non-decreasing order.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Single Pass In-Place Traversal
 * Phase 2: Brute Force Approach - Extract, Stream Distinct, and Rebuild
 * Phase 3: Alternative Approach - Hashing (Unsorted Fallback)
 * ============================================================================
 */
public class RemoveDuplicatesSortedDLL {

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
     * PHASE 1: OPTIMAL APPROACH (Single Pass In-Place Traversal)
     * ============================================================================
     * Detailed Intuition:
     * Since the Doubly Linked List (DLL) is already sorted, we are guaranteed that
     * any duplicate values will be directly adjacent to each other.
     * We can traverse the list with a single pointer (`curr`). At each node, we look
     * ahead to the `next` node. If `curr.val == curr.next.val`, it means we've found
     * a duplicate. Instead of moving `curr` forward, we bypass the duplicate node
     * by re-wiring `curr.next` to `curr.next.next`. We also make sure to update the
     * `prev` pointer of the newly attached node. If the values are different, it is
     * safe to advance our `curr` pointer.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the number of nodes in the DLL. We visit
     * each node exactly once. Rewiring pointers takes O(1) time.
     * - Space Complexity: O(1) auxiliary space. We only use a single pointer (`curr`),
     * utilizing zero heap space and O(1) stack space.
     * ============================================================================
     */
    public Node removeDuplicatesOptimal(Node head) {
        if (head == null) return null;

        Node curr = head;

        // Traverse until the second to last node
        while (curr != null && curr.next != null) {
            // If current node and the next node have the same value, bypass the next node
            if (curr.val == curr.next.val) {
                Node nextDistinctNode = curr.next.next;

                curr.next = nextDistinctNode;

                // If we aren't at the tail, wire the backward 'prev' pointer
                if (nextDistinctNode != null) {
                    nextDistinctNode.prev = curr;
                }
            } else {
                // Only advance if no duplicate was found, allowing us to handle
                // multiple duplicates in a row (e.g., 1 -> 1 -> 1)
                curr = curr.next;
            }
        }

        return head;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Extract, Stream Distinct, Rebuild)
     * ============================================================================
     * Detailed Intuition:
     * If pointer manipulation is tricky, the brute force mental model involves
     * abstracting the data away from the linked list structure entirely.
     * 1. Traverse the DLL and dump every integer into an ArrayList.
     * 2. Leverage the Java 8 Stream API to filter out duplicates using `.distinct()`.
     * 3. Construct a completely new DLL from the resulting distinct values.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Extracting takes O(N), Stream `.distinct()` operates
     * in O(N) using a LinkedHashSet under the hood, and rebuilding takes O(N).
     * - Space Complexity: O(N) heap space to hold the extracted values in a list,
     * plus additional O(N) heap space to instantiate the brand new DLL nodes.
     * O(1) auxiliary stack space.
     * ============================================================================
     */
    public Node removeDuplicatesBruteForce(Node head) {
        if (head == null) return null;

        List<Integer> values = new ArrayList<>();
        Node curr = head;

        // 1. Extract values
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }

        // 2. Filter using Java 8 Streams
        List<Integer> distinctValues = values.stream()
                .distinct()
                .collect(Collectors.toList());

        // 3. Rebuild a brand new Doubly Linked List
        Node dummy = new Node(-1);
        Node tail = dummy;

        for (int val : distinctValues) {
            Node newNode = new Node(val);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        Node newHead = dummy.next;
        if (newHead != null) {
            newHead.prev = null; // Sever the dummy node connection
        }

        return newHead;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Hashing - Unsorted Fallback)
     * ============================================================================
     * Detailed Intuition:
     * What if the interviewer throws a curveball and says: "What if the Doubly Linked
     * List was NOT sorted?"
     * In an unsorted list, duplicates are not adjacent. To solve this, we can use a
     * HashSet to record values as we traverse. If we encounter a node whose value is
     * already in our Set, we delete that node by wiring its `prev` and `next`
     * pointers together.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Single pass traversal, with O(1) average time complexity
     * for HashSet lookups and insertions.
     * - Space Complexity: O(N) auxiliary heap space to store up to N distinct elements
     * inside the HashSet.
     * ============================================================================
     */
    public Node removeDuplicatesHashing(Node head) {
        if (head == null) return null;

        Set<Integer> seen = new HashSet<>();
        Node curr = head;

        while (curr != null) {
            if (seen.contains(curr.val)) {
                // Duplicate found, bypass this node
                Node prevNode = curr.prev;
                Node nextNode = curr.next;

                if (prevNode != null) {
                    prevNode.next = nextNode;
                }
                if (nextNode != null) {
                    nextNode.prev = prevNode;
                }
            } else {
                seen.add(curr.val);
            }
            curr = curr.next;
        }

        return head;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        RemoveDuplicatesSortedDLL solver = new RemoveDuplicatesSortedDLL();

        System.out.println("=== Test Case 1: Standard Input (Multiple occurrences) ===");
        // Input: [1, 1, 1, 2, 3]
        System.out.print("Optimal Output:      ");
        printDLL(solver.removeDuplicatesOptimal(buildDLL(new int[]{1, 1, 1, 2, 3})));
        System.out.print("Brute Force Output:  ");
        printDLL(solver.removeDuplicatesBruteForce(buildDLL(new int[]{1, 1, 1, 2, 3})));
        System.out.print("Hashing Output:      ");
        printDLL(solver.removeDuplicatesHashing(buildDLL(new int[]{1, 1, 1, 2, 3})));

        System.out.println("\n=== Test Case 2: Scattered Duplicates ===");
        // Input: [1, 2, 2, 3, 3, 4]
        System.out.print("Optimal Output:      ");
        printDLL(solver.removeDuplicatesOptimal(buildDLL(new int[]{1, 2, 2, 3, 3, 4})));

        System.out.println("\n=== Test Case 3: All Same Elements ===");
        // Input: [7, 7, 7, 7]
        System.out.print("Optimal Output:      ");
        printDLL(solver.removeDuplicatesOptimal(buildDLL(new int[]{7, 7, 7, 7})));

        System.out.println("\n=== Test Case 4: No Duplicates ===");
        // Input: [1, 2, 3, 4, 5]
        System.out.print("Optimal Output:      ");
        printDLL(solver.removeDuplicatesOptimal(buildDLL(new int[]{1, 2, 3, 4, 5})));

        System.out.println("\n=== Test Case 5: Empty List / Zero-Value Edge Case ===");
        // Input: []
        System.out.print("Optimal Output:      ");
        printDLL(solver.removeDuplicatesOptimal(buildDLL(new int[]{})));
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

        // Backward traversal to verify 'prev' pointers are completely correct
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