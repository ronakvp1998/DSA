package strivers.linkedlist.ll.hardProblemLL;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 23. Merge k Sorted Lists
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * PROBLEM STATEMENT:
 * You are given an array of k linked-lists lists, each linked-list is sorted
 * in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Example 1:
 * Input: lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 * Explanation: The linked-lists are:
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * merging them into one sorted linked list:
 * 1->1->2->3->4->4->5->6
 * * Example 2:
 * Input: lists = []
 * Output: []
 * * Example 3:
 * Input: lists = [[]]
 * Output: []
 * * CONSTRAINTS:
 * k == lists.length
 * 0 <= k <= 10^4
 * 0 <= lists[i].length <= 500
 * -10^4 <= lists[i][j] <= 10^4
 * lists[i] is sorted in ascending order.
 * The sum of lists[i].length will not exceed 10^4.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Priority Queue / Min-Heap
 * Phase 2: Brute Force Approach - Collect, Sort, and Rebuild
 * Phase 3: Alternative Approach - Divide and Conquer
 * ============================================================================
 */
public class MergeKSortedLists {

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
     * PHASE 1: OPTIMAL APPROACH (Min-Heap / Priority Queue)
     * ============================================================================
     * Detailed Intuition:
     * Since every individual list is already sorted, the globally smallest element
     * must be among the heads of the k lists. If we maintain a Min-Heap of size k
     * storing the current node of each list, we can extract the absolute minimum
     * in O(log k) time. Once a node is extracted and appended to our result list,
     * we insert its 'next' node into the heap. This elegantly scales without
     * having to repeatedly scan all k lists.
     * * Complexity Analysis:
     * - Time Complexity: O(N log k), where N is the total number of nodes across
     * all lists. Extracting and inserting into a heap of size k takes O(log k),
     * and we do this for all N nodes.
     * - Space Complexity: O(k) explicitly for the auxiliary heap space to store
     * at most k nodes at any given time. O(1) auxiliary stack space.
     * ============================================================================
     * Initialization: Start by putting only the head node of each of the $k$ linked lists into the Priority Queue.
     * Extraction: Pop the smallest node out of the Priority Queue and attach it to your final merged list.
     * Replacement: Immediately take the .next node from the exact same list you just popped from, and push it into the Priority Queue.
     */
    public ListNode mergeKListsOptimal(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        // Min-Heap ordered by ListNode value
        PriorityQueue<ListNode> pq = new PriorityQueue<>(
                lists.length,
                Comparator.comparingInt(a -> a.val)
        );

        // Initialize the heap with the head of each non-empty list
        for (ListNode node : lists) {
            if (node != null) {
                pq.add(node);
            }
        }

        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;

        // Continuously extract the minimum node and push its successor
        while (!pq.isEmpty()) {
            ListNode minNode = pq.poll();
            current.next = minNode;
            current = current.next;

            if (minNode.next != null) {
                pq.add(minNode.next);
            }
        }

        return dummy.next;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Collect, Sort, Rebuild) -> The "Think it" stage
     * ============================================================================
     * Detailed Intuition:
     * If we ignore the fact that the inner lists are already sorted, the most
     * straightforward way to solve this is to extract every single integer from
     * every node across all linked lists, throw them into a large array/list, sort
     * that list, and then build a brand new linked list from the sorted values.
     * * Here, we utilize Java 8 Streams to cleanly map, extract, sort, and process
     * the collection of nodes.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N), where N is the total number of nodes.
     * Extracting takes O(N), sorting takes O(N log N), and rebuilding takes O(N).
     * - Space Complexity: O(N) heap space to hold all values in an ArrayList
     * before creating the new linked list.
     * ============================================================================
     */
    public ListNode mergeKListsBruteForce(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        List<Integer> allValues = new ArrayList<>();

        // Traverse all lists and collect values
        for (ListNode head : lists) {
            ListNode curr = head;
            while (curr != null) {
                allValues.add(curr.val);
                curr = curr.next;
            }
        }

        if (allValues.isEmpty()) return null;

        // Use Java 8 Streams to sort and generate a list of ListNodes
        List<ListNode> sortedNodes = allValues.stream()
                .sorted()
                .map(ListNode::new)
                .collect(Collectors.toList());

        // Connect the newly created nodes
        for (int i = 0; i < sortedNodes.size() - 1; i++) {
            sortedNodes.get(i).next = sortedNodes.get(i + 1);
        }

        return sortedNodes.get(0);
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Divide and Conquer)
     * ============================================================================
     * Detailed Intuition:
     * Instead of a Priority Queue, we can pair up the k lists and merge each pair
     * using the classic "Merge Two Sorted Lists" algorithm. After the first pass,
     * we are left with k/2 lists. We repeat this process until only 1 list remains.
     * This avoids maintaining a dynamic data structure like a heap and optimizes
     * space overhead compared to the brute force approach.
     * * Complexity Analysis:
     * - Time Complexity: O(N log k). In each pass, we process all N nodes.
     * There are log(k) passes as we halve the number of lists each time.
     * - Space Complexity: O(1) auxiliary space (if done iteratively like below).
     * ============================================================================
     */
    public ListNode mergeKListsDivideAndConquer(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        int interval = 1;
        while (interval < lists.length) {
            for (int i = 0; i + interval < lists.length; i = i + interval * 2) {
                lists[i] = mergeTwoLists(lists[i], lists[i + interval]);
            }
            interval *= 2;
        }

        return lists[0];
    }

    // Helper method for Divide and Conquer
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }

        curr.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        MergeKSortedLists solver = new MergeKSortedLists();

        System.out.println("=== Test Case 1: Standard Input ===");
        // Input: [[1,4,5],[1,3,4],[2,6]]
        ListNode[] lists1 = {
                buildList(new int[]{1, 4, 5}),
                buildList(new int[]{1, 3, 4}),
                buildList(new int[]{2, 6})
        };
        System.out.print("Optimal Output: ");
        printList(solver.mergeKListsOptimal(lists1)); // Note: mutates input, need fresh lists for next tests

        ListNode[] lists1b = {
                buildList(new int[]{1, 4, 5}),
                buildList(new int[]{1, 3, 4}),
                buildList(new int[]{2, 6})
        };
        System.out.print("Brute Force Output: ");
        printList(solver.mergeKListsBruteForce(lists1b));

        ListNode[] lists1c = {
                buildList(new int[]{1, 4, 5}),
                buildList(new int[]{1, 3, 4}),
                buildList(new int[]{2, 6})
        };
        System.out.print("Divide & Conquer Output: ");
        printList(solver.mergeKListsDivideAndConquer(lists1c));

        System.out.println("\n=== Test Case 2: Empty Array ===");
        // Input: []
        ListNode[] lists2 = new ListNode[0];
        System.out.print("Optimal Output: ");
        printList(solver.mergeKListsOptimal(lists2));

        System.out.println("\n=== Test Case 3: Array with Empty Lists ===");
        // Input: [[]]
        ListNode[] lists3 = { null };
        System.out.print("Optimal Output: ");
        printList(solver.mergeKListsOptimal(lists3));
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