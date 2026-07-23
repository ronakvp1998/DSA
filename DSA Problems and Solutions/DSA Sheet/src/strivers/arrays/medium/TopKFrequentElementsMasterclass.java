package strivers.arrays.medium;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * * Problem: 347. Top K Frequent Elements (Medium)
 * * * Formal Problem Statement:
 * Given an integer array nums and an integer k, return the k most frequent
 * elements. You may return the answer in any order.
 * * * Constraints:
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * - k is in the range [1, the number of unique elements in the array].
 * - It is guaranteed that the answer is unique.
 * * * Follow up:
 * Your algorithm's time complexity must be better than O(n log n), where n is
 * the array's size.
 * * * Examples:
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 * * Example 3:
 * Input: nums = [1,2,1,2,1,2,3,1,3,2], k = 2
 * Output: [1,2]
 * * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach (Bucket Sort) - The best and recommended approach.
 * Phase 2: Brute Force Approach (Sorting & Streams) - The "Think it" stage.
 * Phase 3: Alternative Approach (Min-Heap / Priority Queue) - A common interview solution.
 */

import java.util.*;
import java.util.stream.Collectors;

public class TopKFrequentElementsMasterclass {


    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Min-Heap / Priority Queue)
     * ========================================================================
     * * Detailed Intuition:
     * Instead of sorting the entire map of unique elements, we only need the top k.
     * We can maintain a Min-Heap of size k. We iterate through our frequency map,
     * adding elements to the heap. The heap is ordered by frequency (smallest at the top).
     * If the heap's size exceeds k, we `poll()` the root (which is the element
     * with the lowest frequency currently in the heap). By the end, only the top k
     * highest-frequency elements remain in the heap.
     * * * Complexity Analysis:
     * - Time Complexity: O(N log k)
     * Building the frequency map takes O(N). Inserting into a heap of size k takes
     * O(log k). Doing this for U unique elements takes O(U log k). Worst case: O(N log k).
     * - Space Complexity: O(N) Heap Space, O(1) Auxiliary Stack Space
     * O(N) for the HashMap and O(k) for the Priority Queue.
     */
    public int[] topKFrequentAlternative(int[] nums, int k) {
        // 1. Build frequency map
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // 2. Create Min-Heap ordered by frequency
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
                (n1, n2) -> frequencyMap.get(n1) - frequencyMap.get(n2)
        );

        // 3. Maintain heap of size k
        for (int num : frequencyMap.keySet()) {
            minHeap.add(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // Evict the element with the lowest frequency
            }
        }

        // 4. Extract results
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll();
        }
        return result;
    }

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Bucket Sort)
     * ========================================================================
     * * Detailed Intuition:
     * To beat the O(N log N) time complexity of standard sorting, we can use
     * Bucket Sort. The core realization is that the maximum frequency of any
     * element cannot exceed the length of the array (N).
     * We create an array of Lists ("buckets") where the index represents the
     * frequency of the elements. After counting frequencies, we place elements
     * into their respective frequency buckets. Finally, we iterate backwards
     * from the highest possible frequency (N) down to 0, gathering the top k elements.
     * * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We iterate through the array to build the frequency map O(N). We iterate
     * through the map to populate the buckets O(N). Finally, we iterate through
     * the buckets array of size N+1 to gather results O(N). Total = O(N).
     * - Space Complexity: O(N) Heap Space, O(1) Auxiliary Stack Space
     * The HashMap takes O(U) space where U is unique elements (U <= N).
     * The bucket array of Lists takes O(N) space.
     */
    public int[] topKFrequentOptimal(int[] nums, int k) {
        // 1. Build frequency map
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // 2. Create buckets where index = frequency
        // Max frequency is nums.length, so array size needs to be nums.length + 1
        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[nums.length + 1];

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int num = entry.getKey();
            int freq = entry.getValue();
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(num);
        }

        // 3. Gather top k elements from right to left (highest frequency first)
        int[] result = new int[k];
        int index = 0;

        for (int i = buckets.length - 1; i >= 0 && index < k; i--) {
            if (buckets[i] != null) {
                for (int num : buckets[i]) {
                    result[index++] = num;
                    if (index == k) return result;
                }
            }
        }
        return result;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Sorting & java 8 Streams)
     * ========================================================================
     * * Detailed Intuition:
     * The most intuitive way to solve this is to count the frequency of each
     * element, and then sort the unique elements based on their frequencies in
     * descending order. We then pick the first k elements.
     * This implementation leverages Java 8 Stream API for a highly declarative,
     * clean functional pipeline.
     * * * Complexity Analysis:
     * - Time Complexity: O(N log N)
     * Counting frequencies takes O(N). Sorting the map entries takes O(U log U)
     * where U is unique elements. In the worst case (all elements unique), U = N,
     * so sorting takes O(N log N).
     * - Space Complexity: O(N) Heap Space, O(1) Auxiliary Stack Space
     * The frequency map and intermediate stream data structures require O(N) space.
     */
    public int[] topKFrequentBruteForce(int[] nums, int k) {
        return Arrays.stream(nums)
                // 1. Box primitives to Integers
                .boxed()
                // 2. Group by element and count frequencies -> Map<Integer, Long>
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                // 3. Stream the map entries
                .entrySet().stream()
                // 4. Sort entries by frequency (value) in descending order
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                // 5. Limit to top k
                .limit(k)
                // 6. Map back to just the keys (the original numbers)
                .mapToInt(Map.Entry::getKey)
                // 7. Collect into an int array
                .toArray();
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        TopKFrequentElementsMasterclass solution = new TopKFrequentElementsMasterclass();

        // Define Test Cases
        // Each test case: [nums array], k
        Object[][] testCases = {
                {new int[]{1, 1, 1, 2, 2, 3}, 2},
                {new int[]{1}, 1},
                {new int[]{1, 2, 1, 2, 1, 2, 3, 1, 3, 2}, 2},
                {new int[]{-1, -1}, 1} // Edge case: Negative numbers
        };

        int testNum = 1;
        for (Object[] test : testCases) {
            int[] nums = (int[]) test[0];
            int k = (int) test[1];

            System.out.println("==================================================");
            System.out.println("Test Case " + testNum++ + ": nums = " + Arrays.toString(nums) + ", k = " + k);

            // Note: Results can be returned in any order, so arrays might look slightly different
            // but will contain the exact same subset of numbers.

            // Test Optimal (Bucket Sort)
            int[] optimalRes = solution.topKFrequentOptimal(nums, k);
            System.out.println("Phase 1 (Bucket Sort)     : " + Arrays.toString(optimalRes));

            // Test Brute Force (Streams)
            int[] bruteRes = solution.topKFrequentBruteForce(nums, k);
            System.out.println("Phase 2 (Brute Force)     : " + Arrays.toString(bruteRes));

            // Test Alternative (Min-Heap)
            int[] altRes = solution.topKFrequentAlternative(nums, k);
            System.out.println("Phase 3 (Min-Heap)        : " + Arrays.toString(altRes));
            System.out.println("==================================================\n");
        }
    }
}