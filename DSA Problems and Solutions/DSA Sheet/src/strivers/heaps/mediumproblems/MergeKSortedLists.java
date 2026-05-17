package com.questions.strivers.heaps.mediumproblems;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 23. Merge k Sorted Lists (Hard)
 * ==================================================================================================
 * You are given an array of k linked-lists, each sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Example 1:
 * Input: lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Collect, Sort, and Rebuild)
 * ==================================================================================================
 * 1. Traverse all k linked lists and collect all values into a dynamic list (ArrayList).
 * 2. Sort the collected values using Collections.sort() -> O(N log N).
 * 3. Create a new linked list and populate it with the sorted values.
 * Drawback: High space overhead and ignores the fact that input lists are already sorted.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Min-Heap / Priority Queue)
 * ==================================================================================================
 * We use a Min-Heap to keep track of the smallest current element across all k lists.
 * 1. Add the head of each of the k linked lists into the Min-Heap.
 * 2. While the heap is not empty:
 * a. Extract the minimum node from the heap.
 * b. Add this node to our result linked list.
 * c. If the extracted node has a "next" node, add that next node to the heap.
 * 3. Since the heap size is at most k, extracting the minimum takes O(log k).
 * ==================================================================================================
 */

import java.util.PriorityQueue;

/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class MergeKSortedLists {

    public static void main(String[] args) {
        // Mocking Input: [[1,4,5],[1,3,4],[2,6]]
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6));

        ListNode[] lists = new ListNode[]{l1, l2, l3};

        ListNode result = mergeKLists(lists);

        // Print Result
        while (result != null) {
            System.out.print(result.val + (result.next != null ? " -> " : ""));
            result = result.next;
        }
    }

    /**
     * Merges k sorted lists using a Min-Heap.
     * Time Complexity: O(N log k) where N is total nodes and k is number of lists.
     * Space Complexity: O(k) for the Priority Queue.
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        // Edge case: if input array is empty
        if (lists == null || lists.length == 0) return null;

        // 1. Initialize a Min-Heap (Priority Queue)
        // We provide a comparator to sort based on the node's value.
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

        // 2. Push the head of each list into the heap
        for (ListNode head : lists) {
            if (head != null) {
                minHeap.add(head);
            }
        }

        // 3. Create a dummy head to simplify list building
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;

        // 4. Extract min and add the next node from that specific list
        while (!minHeap.isEmpty()) {
            // Get the smallest node available across all lists
            ListNode smallest = minHeap.poll();

            // Append to our merged list
            current.next = smallest;
            current = current.next;

            // If this list has more nodes, push the next one into the heap
            if (smallest.next != null) {
                minHeap.add(smallest.next);
            }
        }

        return dummy.next;
    }
}