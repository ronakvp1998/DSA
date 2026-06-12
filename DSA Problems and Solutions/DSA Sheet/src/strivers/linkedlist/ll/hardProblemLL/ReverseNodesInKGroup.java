package strivers.linkedlist.ll.hardProblemLL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * 25. Reverse Nodes in k-Group
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given the head of a linked list, reverse the nodes of the list k at a time,
 * and return the modified list.
 * * k is a positive integer and is less than or equal to the length of the linked
 * list. If the number of nodes is not a multiple of k then left-out nodes, in
 * the end, should remain as it is.
 * * You may not alter the values in the list's nodes, only nodes themselves may
 * be changed.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [2,1,4,3,5]
 * * Example 2:
 * Input: head = [1,2,3,4,5], k = 3
 * Output: [3,2,1,4,5]
 *
 * CONSTRAINTS:
 * The number of nodes in the list is n.
 * 1 <= k <= n <= 5000
 * 0 <= Node.val <= 1000
 * * Follow-up: Can you solve the problem in O(1) extra memory space?
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Iterative In-Place Reversal (O(1) Space)
 * Phase 2: Brute Force Approach - Extract, Reverse Chunks, and Rebuild
 * Phase 3: Alternative Approach - Recursive Reversal
 * ============================================================================
 */
public class ReverseNodesInKGroup {

    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Iterative In-Place Reversal)
     * ============================================================================
     * Detailed Intuition:
     * To satisfy the O(1) memory space follow-up, we must manipulate the pointers
     * in place rather than creating new nodes or relying on the call stack.
     * The strategy involves:
     * 1. Counting the total number of nodes to know exactly how many full k-groups exist.
     * 2. Using a dummy node to simplify edge cases at the head of the list.
     * 3. Iterating exactly `total_nodes / k` times. In each iteration, we perform a
     * standard linked list reversal on a window of `k` nodes.
     * 4. Carefully rewiring the 'before' and 'after' pointers for each group so the
     * reversed chunk seamlessly integrates with the rest of the list.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the number of nodes in the list. We traverse
     * the list once to count nodes, and essentially traverse it again to reverse.
     * - Space Complexity: O(1) auxiliary space, as we only allocate a few pointer variables.
     * ============================================================================
     */
    public ListNode reverseKGroupOptimal(ListNode head, int k) {
        if (head == null || k == 1) return head;

        // Step 1: Count total number of nodes
        int count = 0;
        ListNode curr = head;
        while (curr != null) {
            count++;
            curr = curr.next;
        }

        // Step 2: Initialize dummy node
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        curr = head;
        ListNode nex;

        // Step 3: Reverse in chunks of k
        while (count >= k) {
            curr = pre.next; // First node of the current k-group
            nex = curr.next; // Second node of the current k-group

            // Standard pointer reversal for k-1 links
            for (int i = 1; i < k; i++) {
                curr.next = nex.next;
                nex.next = pre.next;
                pre.next = nex;
                nex = curr.next;
            }

            // Move 'pre' to the end of the newly reversed group
            pre = curr;
            count -= k;
        }

        return dummy.next;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Extract, Reverse Chunks, Rebuild) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If we ignore the space constraints and pointer manipulation complexities, the
     * simplest mental model is:
     * 1. Dump all linked list values into an array/List.
     * 2. Chunk the List into sizes of `k`.
     * 3. Reverse the elements within each chunk (leaving the remainder intact).
     * 4. Rebuild a brand new linked list from the mutated values.
     * Here, we heavily leverage Java Collections and Streams for a clean, expressive setup.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the total number of nodes. Reversing chunks
     * in an array takes linear time overall.
     * - Space Complexity: O(N) heap space to hold all values in an ArrayList and to
     * rebuild the resulting nodes.
     * ============================================================================
     */
    public ListNode reverseKGroupBruteForce(ListNode head, int k) {
        if (head == null || k <= 1) return head;

        // 1. Extract values
        List<Integer> values = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }

        int n = values.size();

        // 2 & 3. Reverse chunks of size k
        for (int i = 0; i + k <= n; i += k) {
            int left = i;
            int right = i + k - 1;
            while (left < right) {
                int temp = values.get(left);
                values.set(left, values.get(right));
                values.set(right, temp);
                left++;
                right--;
            }
        }

        // 4. Rebuild the Linked List using Streams
        if (values.isEmpty()) return null;

        List<ListNode> rebuiltNodes = values.stream()
                .map(ListNode::new)
                .collect(Collectors.toList());

        for (int i = 0; i < rebuiltNodes.size() - 1; i++) {
            rebuiltNodes.get(i).next = rebuiltNodes.get(i + 1);
        }

        return rebuiltNodes.get(0);
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Recursive)
     * ============================================================================
     * Detailed Intuition:
     * A highly elegant alternative utilizing the call stack.
     * 1. We first check if there are at least `k` nodes remaining. If not, we return
     * the head as is (base case).
     * 2. If there are `k` nodes, we reverse just those `k` nodes iteratively.
     * 3. The `head` pointer, after reversal, becomes the tail of this local k-group.
     * We recursively assign `head.next` to the result of `reverseKGroupRecursive`
     * on the remaining nodes.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Each node is visited twice (once for length check,
     * once for reversal).
     * - Space Complexity: O(N / k) auxiliary stack space, since a stack frame is
     * created for every k-group. This violates the O(1) space follow-up requirement
     * but serves as excellent interview discussion material.
     * ============================================================================
     */
    public ListNode reverseKGroupRecursive(ListNode head, int k) {
        if (head == null || k == 1) return head;

        // Check if there are at least k nodes left
        ListNode curr = head;
        int count = 0;
        while (curr != null && count != k) {
            curr = curr.next;
            count++;
        }

        // If we have a full group of k nodes, reverse them
        if (count == k) {
            curr = reverseKGroupRecursive(curr, k); // recursively solve for the remaining list

            // Reverse current k-group
            while (count > 0) {
                ListNode nextNode = head.next;
                head.next = curr;
                curr = head;
                head = nextNode;
                count--;
            }
            head = curr; // curr becomes the new head of this reversed group
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
        ReverseNodesInKGroup solver = new ReverseNodesInKGroup();

        System.out.println("=== Test Case 1: Example 1 (k = 2) ===");
        // Input: [1,2,3,4,5], k = 2
        ListNode list1a = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Optimal Output:      ");
        printList(solver.reverseKGroupOptimal(list1a, 2));

        ListNode list1b = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Brute Force Output:  ");
        printList(solver.reverseKGroupBruteForce(list1b, 2));

        ListNode list1c = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Recursive Output:    ");
        printList(solver.reverseKGroupRecursive(list1c, 2));

        System.out.println("\n=== Test Case 2: Example 2 (k = 3) ===");
        // Input: [1,2,3,4,5], k = 3
        ListNode list2 = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Optimal Output:      ");
        printList(solver.reverseKGroupOptimal(list2, 3));

        System.out.println("\n=== Test Case 3: Edge Case (k = 1, No reversal expected) ===");
        // Input: [1,2,3,4,5], k = 1
        ListNode list3 = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Optimal Output:      ");
        printList(solver.reverseKGroupOptimal(list3, 1));

        System.out.println("\n=== Test Case 4: Edge Case (length < k) ===");
        // Input: [1,2,3], k = 5
        ListNode list4 = buildList(new int[]{1, 2, 3});
        System.out.print("Optimal Output:      ");
        printList(solver.reverseKGroupOptimal(list4, 5));

        System.out.println("\n=== Test Case 5: Exact Multiple (length == k) ===");
        // Input: [1,2,3,4], k = 4
        ListNode list5 = buildList(new int[]{1, 2, 3, 4});
        System.out.print("Optimal Output:      ");
        printList(solver.reverseKGroupOptimal(list5, 4));
    }

    // --- Helper Methods for Testing Suite --- //

    private static ListNode buildList(int[] values) {
        if (values == null || values.length == 0) return null;
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        return dummy.next;
    }

    private static void printList(ListNode head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }
        List<String> result = new ArrayList<>();
        while (head != null) {
            result.add(String.valueOf(head.val));
            head = head.next;
        }
        System.out.println("[" + String.join(" -> ", result) + "]");
    }
}