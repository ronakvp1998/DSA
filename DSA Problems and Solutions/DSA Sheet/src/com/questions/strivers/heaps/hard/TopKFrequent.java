package com.questions.strivers.heaps.hard;

import java.util.*;

/**
 * ==================================================================================================
 * APPROACH: HashMap + Min-Heap
 * ==================================================================================================
 * 1. Count frequencies using a HashMap.
 * 2. Use a Min-Heap to keep the 'k' most frequent elements.
 * 3. By using a Min-Heap, the least frequent of the 'top k' is always at the root.
 * ==================================================================================================
 */
public class TopKFrequent {

    public static int[] topKFrequent(int[] nums, int k) {
        // Step 1: Count frequencies O(N)
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // Step 2: Use a Min-Heap to track top K frequencies O(N log k)
        // We sort by map value (frequency)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
                (a, b) -> countMap.get(a) - countMap.get(b)
        );

        for (int num : countMap.keySet()) {
            minHeap.add(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // Remove element with the lowest frequency
            }
        }

        // Step 3: Convert heap to result array O(k log k)
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll();
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.println(Arrays.toString(topKFrequent(nums, k))); // Output: [1, 2]
    }
}