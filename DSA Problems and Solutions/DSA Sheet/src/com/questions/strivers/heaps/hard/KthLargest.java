package com.questions.strivers.heaps.hard;

import java.util.PriorityQueue;

/**
 * ==================================================================================================
 * APPROACH: Min-Heap of fixed size K
 * ==================================================================================================
 * 1. A Min-Heap of size K will keep the K largest elements at any given time.
 * 2. The root (top) of this Min-Heap is the smallest of these K elements.
 * 3. Therefore, the root is exactly the Kth largest element.
 * ==================================================================================================
 */
class KthLargest {
    private PriorityQueue<Integer> minHeap;
    private int k;

    /**
     * Initializes the heap with the initial stream of numbers.
     * Time Complexity: O(N log K)
     */
    public KthLargest(int k, int[] nums) {
        this.k = k;
        // PriorityQueue is a Min-Heap by default in Java
        this.minHeap = new PriorityQueue<>(k);

        for (int num : nums) {
            add(num);
        }
    }

    /**
     * Adds a new value to the stream and returns the Kth largest.
     * Time Complexity: O(log K)
     */
    public int add(int val) {
        // 1. Add the new value to the heap
        minHeap.offer(val);

        // 2. If heap size exceeds k, remove the smallest element.
        // This ensures the heap only contains the 'k' largest elements.
        if (minHeap.size() > k) {
            minHeap.poll();
        }

        // 3. The smallest element in our heap of K-largest is the Kth largest overall.
        return minHeap.peek();
    }

    // Main method for testing
    public static void main(String[] args) {
        int k = 3;
        int[] nums = {4, 5, 8, 2};
        KthLargest kthLargest = new KthLargest(k, nums);

        System.out.println(kthLargest.add(3));  // returns 4
        System.out.println(kthLargest.add(5));  // returns 5
        System.out.println(kthLargest.add(10)); // returns 5
        System.out.println(kthLargest.add(9));  // returns 8
        System.out.println(kthLargest.add(4));  // returns 8
    }
}