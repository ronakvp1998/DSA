package strivers.linkedlist.dll.mediumProblemsDLL;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * Find Pairs with Given Sum in Doubly Linked List
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given a sorted doubly linked list of distinct positive integers and a target sum,
 * find all pairs in the list that sum to the given target. The pairs should be
 * returned in ascending order of the first element.
 *
 * Example 1:
 * Input: head = [1, 2, 4, 5, 6, 8, 9], target = 7
 * Output: [[1, 6], [2, 5]]
 * Explanation: 1+6 = 7, 2+5 = 7.
 *
 * Example 2:
 * Input: head = [1, 5, 6], target = 6
 * Output: []
 * Explanation: No two distinct elements sum up to 6.
 *
 * Example 3:
 * Input: head = [1, 2, 3, 4, 9], target = 5
 * Output: [[1, 4], [2, 3]]
 *
 * CONSTRAINTS:
 * 1 <= Number of nodes <= 10^5
 * 1 <= Node.val <= 10^5
 * 1 <= target <= 10^5
 * The linked list is strictly sorted in ascending order.
 * All node values are distinct.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Two Pointers at Extremes
 * Phase 2: Brute Force Approach - Extract and Stream (Nested Iteration)
 * Phase 3: Alternative Approach - Hashing (HashSet)
 * ============================================================================
 */
public class FindPairsGivenSumDLL {

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
     * PHASE 1: OPTIMAL APPROACH (Two Pointers)
     * ============================================================================
     * Detailed Intuition:
     * Since the Doubly Linked List is strictly sorted, we can elegantly apply the
     * classic "Two Pointer" technique. We initialize a 'left' pointer at the head
     * and a 'right' pointer at the tail.
     * 1. If left.val + right.val == target, we found a pair. Add it, and advance
     * both pointers inwards.
     * 2. If the sum is strictly less than the target, the current left value is
     * too small. We move 'left' one step to the right to increase the sum.
     * 3. If the sum is strictly greater than the target, the current right value
     * is too large. We move 'right' one step to the left to decrease the sum.
     * We stop when left and right pointers meet or cross (left.val >= right.val).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the number of nodes in the DLL. We first
     * traverse the list to find the tail O(N), and then our two pointers traverse
     * inwards, visiting each node at most once O(N).
     * - Space Complexity: O(1) auxiliary space. We only use two pointers. (The O(K)
     * heap space used to store the resulting pairs is standard output space and
     * not counted towards algorithmic overhead).
     * ============================================================================
     */
    public List<List<Integer>> findPairsOptimal(Node head, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (head == null) return result;

        // Find the tail of the Doubly Linked List
        Node left = head;
        Node right = head;
        while (right.next != null) {
            right = right.next;
        }

        // Two pointer traversal
        while (left != null && right != null && left.val < right.val) {
            int currentSum = left.val + right.val;

            if (currentSum == target) {
                result.add(Arrays.asList(left.val, right.val));
                left = left.next;
                right = right.prev;
            } else if (currentSum < target) {
                left = left.next; // Need a larger sum
            } else {
                right = right.prev; // Need a smaller sum
            }
        }

        return result;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Extract & Stream) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If we ignore the sorted nature of the DLL and the presence of 'prev' pointers,
     * the simplest logic is: for every node, iterate through all subsequent nodes
     * to see if they sum up to the target.
     * To heavily demonstrate Java 8 Streams as requested, we will extract the
     * elements into a List, and use IntStream to conceptually emulate a nested
     * loop, flat-mapping valid pairs into the final result.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2), where N is the total number of nodes. For each of
     * the N nodes, we look ahead at N-i nodes.
     * - Space Complexity: O(N) heap space to hold all values in an ArrayList.
     * O(1) auxiliary stack space.
     * ============================================================================
     */
    public List<List<Integer>> findPairsBruteForce(Node head, int target) {
        if (head == null) return new ArrayList<>();

        List<Integer> values = new ArrayList<>();
        Node curr = head;

        // Extract values
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }

        // Use Java 8 Streams to generate pairs (i, j) where i < j
        return IntStream.range(0, values.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, values.size())
                        .filter(j -> values.get(i) + values.get(j) == target)
                        .mapToObj(j -> Arrays.asList(values.get(i), values.get(j))))
                .collect(Collectors.toList());
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Hashing)
     * ============================================================================
     * Detailed Intuition:
     * If we were dealing with a Singly Linked List, finding the tail and traversing
     * backwards would be impossible. In that scenario, Hashing is an excellent
     * alternative. As we traverse the list, we calculate the required 'complement'
     * (target - current.val). If we've already stored the complement in our HashSet,
     * we found a pair! Because the list is already sorted, the complement (seen earlier)
     * will correctly be smaller than the current value, keeping our output pairs sorted.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We traverse the list exactly once. HashSet lookups
     * and insertions are O(1) on average.
     * - Space Complexity: O(N) explicitly for the auxiliary heap space to store
     * the elements in the HashSet.
     * ============================================================================
     */
    public List<List<Integer>> findPairsHashing(Node head, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        Node curr = head;

        while (curr != null) {
            int complement = target - curr.val;

            // Check if we have seen the needed complement previously
            if (seen.contains(complement)) {
                // Complement was seen earlier, so it's strictly less than curr.val
                result.add(Arrays.asList(complement, curr.val));
            }

            seen.add(curr.val);
            curr = curr.next;
        }

        return result;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        FindPairsGivenSumDLL solver = new FindPairsGivenSumDLL();

        System.out.println("=== Test Case 1: Standard Input (Multiple Pairs) ===");
        // Input: [1, 2, 4, 5, 6, 8, 9], target = 7
        Node list1 = buildDLL(new int[]{1, 2, 4, 5, 6, 8, 9});
        System.out.println("Optimal Output:     " + solver.findPairsOptimal(list1, 7));
        System.out.println("Brute Force Output: " + solver.findPairsBruteForce(list1, 7));
        System.out.println("Hashing Output:     " + solver.findPairsHashing(list1, 7));

        System.out.println("\n=== Test Case 2: No Pairs Found ===");
        // Input: [1, 5, 6], target = 6
        Node list2 = buildDLL(new int[]{1, 5, 6});
        System.out.println("Optimal Output:     " + solver.findPairsOptimal(list2, 6));

        System.out.println("\n=== Test Case 3: Empty List Edge Case ===");
        // Input: [], target = 5
        Node list3 = buildDLL(new int[]{});
        System.out.println("Optimal Output:     " + solver.findPairsOptimal(list3, 5));

        System.out.println("\n=== Test Case 4: Only 1 Element Edge Case ===");
        // Input: [5], target = 5
        Node list4 = buildDLL(new int[]{5});
        System.out.println("Optimal Output:     " + solver.findPairsOptimal(list4, 5));

        System.out.println("\n=== Test Case 5: Target is split evenly (Invalid since distinct elements) ===");
        // Input: [2, 3, 4], target = 6 (Expect [], as there's only one '3')
        Node list5 = buildDLL(new int[]{2, 3, 4});
        System.out.println("Hashing Output:     " + solver.findPairsHashing(list5, 6));
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
}