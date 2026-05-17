package com.questions.strivers.heaps.hard;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * ==================================================================================================
 * APPROACH: Two Heaps (Balancing Act)
 * ==================================================================================================
 * 1. maxHeap (Lower Half): Stores the smaller numbers. Root is the largest of the smalls.
 * 2. minHeap (Upper Half): Stores the larger numbers. Root is the smallest of the larges.
 * 3. Property: maxHeap.size() is always equal to or one greater than minHeap.size().
 * ==================================================================================================
 */
class MedianFinder {
    private PriorityQueue<Integer> maxHeap; // Lower half
    private PriorityQueue<Integer> minHeap; // Upper half

    public MedianFinder() {
        // Max-heap for the lower half
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        // Min-heap for the upper half (default)
        minHeap = new PriorityQueue<>();
    }

    /**
     * Adds a number and maintains the balance between heaps.
     * Time Complexity: O(log N)
     */
    public void addNum(int num) {
        // Step 1: Add to maxHeap (lower half)
        maxHeap.offer(num);

        // Step 2: Balancing - The largest of lower half must move to upper half
        minHeap.offer(maxHeap.poll());

        // Step 3: Maintain size property (maxHeap size >= minHeap size)
        if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    /**
     * Returns the median based on heap tops.
     * Time Complexity: O(1)
     */
    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            // Odd number of elements: maxHeap root is the median
            return (double) maxHeap.peek();
        } else {
            // Even number of elements: average of both roots
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        System.out.println(mf.findMedian()); // Output: 1.5
        mf.addNum(3);
        System.out.println(mf.findMedian()); // Output: 2.0
    }
}