package strivers.linkedlist.ll.linkedlist1d;

/**
 * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * Objective: Provide a masterclass-level solution for LeetCode 237: Delete Node in a Linked List.
 *
 * ==========================================================================================
 * 1. Header & Problem Context
 * ==========================================================================================
 * Problem Statement:
 * There is a singly-linked list `head` and we want to delete a node `node` in it.
 * You are given the node to be deleted `node`. You will not be given access to the first node of `head`.
 * All the values of the linked list are unique, and it is guaranteed that the given node `node`
 * is not the last node in the linked list.
 *
 * Delete the given node. Note that by deleting the node, we do not mean removing it from memory. We mean:
 * - The value of the given node should not exist in the linked list.
 * - The number of nodes in the linked list should decrease by one.
 * - All the values before node should be in the same order.
 * - All the values after node should be in the same order.
 *
 * Examples:
 * Example 1:
 * Input: head = [4,5,1,9], node = 5
 * Output: [4,1,9]
 * Explanation: You are given the second node with value 5, the linked list should become 4 -> 1 -> 9.
 *
 * Example 2:
 * Input: head = [4,5,1,9], node = 1
 * Output: [4,5,9]
 * Explanation: You are given the third node with value 1, the linked list should become 4 -> 5 -> 9.
 *
 * Constraints:
 * - The number of the nodes in the given list is in the range [2, 1000].
 * - -1000 <= Node.val <= 1000
 * - The value of each node in the list is unique.
 * - The node to be deleted is in the list and is not a tail node.
 * ==========================================================================================
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteNodeInLinkedList {

    /**
     * Standard LeetCode definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * ==========================================================================================
     * Phase 1: Optimal Approach - The "Best and Recommended" Stage
     * ==========================================================================================
     *
     * Detailed Intuition:
     * In a standard Linked List deletion, we traverse from the head to find the node *before*
     * the target node so we can update `prev.next = target.next`.
     * However, here we do not have the head pointer, making it impossible to access the previous node.
     *
     * Since we cannot delete the current node physically from the chain (due to lack of 'prev'),
     * we can instead make the current node *mimic* the next node.
     * 1. Copy the value of the next node into the current node.
     * 2. Bypass the next node by updating the current node's `next` pointer to `next.next`.
     *
     * This effectively deletes the next node from memory while our given node perfectly
     * replaces it, achieving the exact result required.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1). We perform exactly two constant-time operations (one value
     *   assignment and one pointer reassignment).
     * - Space Complexity: O(1). No auxiliary stack or heap space is utilized.
     */
    public void deleteNodeOptimal(ListNode node) {
        // Step 1: Copy the value of the next node into the current node
        node.val = node.next.val;

        // Step 2: Bypass the next node, effectively deleting it from the list
        node.next = node.next.next;
    }

    /**
     * ==========================================================================================
     * Phase 2: Brute Force Approach - The "Think it" Stage
     * ==========================================================================================
     *
     * Detailed Intuition:
     * If one does not immediately see the O(1) trick, a logical brute-force thought process
     * without the head pointer is to shift all subsequent values leftward by one position.
     *
     * We can traverse from the given node to the end of the list. At each step, we copy the
     * value of the next node into the current node. When we reach the second-to-last node,
     * we sever the link to the last node.
     *
     * While this produces the correct output, it is highly inefficient because it rewrites
     * data unnecessarily instead of simply shifting pointers.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the number of nodes from the given node to the tail.
     *   We must traverse the remainder of the linked list.
     * - Space Complexity: O(1). We only use a constant amount of extra pointer variables.
     */
    public void deleteNodeBruteForce(ListNode node) {
        ListNode current = node;

        // Traverse until the second-to-last node
        while (current.next.next != null) {
            current.val = current.next.val; // Shift value left
            current = current.next;         // Move forward
        }

        // Shift the very last value
        current.val = current.next.val;

        // Sever the tail node
        current.next = null;
    }

    /**
     * ==========================================================================================
     * Phase 3: Alternative Approaches
     * ==========================================================================================
     * For this specific problem, the constraints strictly bind us (no head pointer, target is
     * never the tail). The O(1) in-place value swapping is the universally accepted and only
     * true optimal solution for this specific LeetCode variant.
     * Techniques like Two Pointers, Hashing, or Sliding Window are not applicable as we
     * cannot traverse backward or from the head.
     */


    /**
     * ==========================================================================================
     * 4. Testing Suite
     * ==========================================================================================
     */
    public static void main(String[] args) {
        DeleteNodeInLinkedList solution = new DeleteNodeInLinkedList();

        System.out.println("--- Testing Optimal Approach ---");
        testCase(solution, new int[]{4, 5, 1, 9}, 5, true);
        testCase(solution, new int[]{4, 5, 1, 9}, 1, true);
        testCase(solution, new int[]{1, 2}, 1, true); // Edge case: Minimal length list

        System.out.println("\n--- Testing Brute Force Approach ---");
        testCase(solution, new int[]{4, 5, 1, 9}, 5, false);
        testCase(solution, new int[]{4, 5, 1, 9}, 1, false);
    }

    /**
     * Helper method to run a specific test case.
     * Utilizes Java 8 Streams to process the array input.
     */
    private static void testCase(DeleteNodeInLinkedList solution, int[] arr, int targetVal, boolean useOptimal) {
        // Build Linked List using Java 8 Stream API logic
        List<ListNode> nodes = Arrays.stream(arr)
                .mapToObj(ListNode::new)
                .collect(Collectors.toList());

        // Link the nodes together
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).next = nodes.get(i + 1);
        }

        ListNode head = nodes.get(0);

        // Find the node to delete
        ListNode targetNode = head;
        while (targetNode != null && targetNode.val != targetVal) {
            targetNode = targetNode.next;
        }

        System.out.print("Original List: ");
        printList(head);
        System.out.println("Deleting node with value: " + targetVal);

        // Execute the appropriate deletion method
        if (targetNode != null) {
            if (useOptimal) {
                solution.deleteNodeOptimal(targetNode);
            } else {
                solution.deleteNodeBruteForce(targetNode);
            }
        }

        System.out.print("Modified List: ");
        printList(head);
        System.out.println("-".repeat(30));
    }

    /**
     * Helper method to print the linked list.
     */
    private static void printList(ListNode head) {
        ListNode current = head;
        StringBuilder sb = new StringBuilder();
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        System.out.println(sb.toString());

    }
}
