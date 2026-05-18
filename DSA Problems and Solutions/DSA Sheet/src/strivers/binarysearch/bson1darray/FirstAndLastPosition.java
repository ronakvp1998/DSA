package strivers.binarysearch.bson1darray;

/**
 * Masterclass Solution: 34. Find First and Last Position of Element in Sorted Array
 * * ==========================================
 * ### 1. Header & Problem Context
 * ==========================================
 * Problem Statement:
 * Given an array of integers nums sorted in non-decreasing order, find the starting
 * and ending position of a given target value.
 * * If target is not found in the array, return [-1, -1].
 * * You must write an algorithm with O(log n) runtime complexity.
 * * Constraints:
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * nums is a non-decreasing array.
 * -10^9 <= target <= 10^9
 * * Examples:
 * Example 1:
 * Input: nums = [5,7,7,8,8,10], target = 8
 * Output: [3,4]
 * * Example 2:
 * Input: nums = [5,7,7,8,8,10], target = 6
 * Output: [-1,-1]
 * * Example 3:
 * Input: nums = [], target = 0
 * Output: [-1,-1]
 * * Note: As this is an Array/Binary Search problem and NOT a Dynamic Programming problem,
 * the DP roadmap (2.1), recursion trees, and state visualizations are omitted.
 * We will follow Progressive Implementation Roadmap 2.2.
 */
public class FirstAndLastPosition {

    /**
     * ==========================================
     * Phase 1: Best and Recommended Approach (Two Binary Searches)
     * ==========================================
     * Detailed Intuition:
     * To achieve the strictly required O(log n) time complexity, we must use Binary Search.
     * Since the array contains duplicates, a standard binary search will just find *any* * occurrence of the target, not necessarily the bounds.
     * We solve this by splitting the logic into two distinct binary searches:
     * 1. Find First Position: When `nums[mid] == target`, we record the index as a potential
     * answer, but we continue searching the left half (`high = mid - 1`) to see if there
     * is an earlier occurrence.
     * 2. Find Last Position: When `nums[mid] == target`, we record the index as a potential
     * answer, but continue searching the right half (`low = mid + 1`) to find the latest occurrence.
     * * Complexity Analysis:
     * - Time Complexity: O(log N) + O(log N) = O(log N), where N is the number of elements.
     * We perform two independent binary searches.
     * - Space Complexity: O(1) Auxiliary Space. We only use primitive variables (low, high,
     * mid, ans) allocating a constant amount of memory on the stack. Heap space is O(1).
     */
    public static int[] searchRangeOptimal(int[] nums, int target) {
        int firstPosition = findFirst(nums, target);

        // Minor optimization: If first position is not found, the element doesn't exist.
        // No need to search for the last position.
        if (firstPosition == -1) {
            return new int[]{-1, -1};
        }

        int lastPosition = findLast(nums, target);

        return new int[]{firstPosition, lastPosition};
    }

    private static int findFirst(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        int ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                ans = mid;         // Record potential first position
                high = mid - 1;    // Force search left for earlier occurrences
            } else if (nums[mid] < target) {
                low = mid + 1;     // Search right
            } else {
                high = mid - 1;    // Search left
            }
        }
        return ans;
    }

    private static int findLast(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        int ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                ans = mid;         // Record potential last position
                low = mid + 1;     // Force search right for later occurrences
            } else if (nums[mid] < target) {
                low = mid + 1;     // Search right
            } else {
                high = mid - 1;    // Search left
            }
        }
        return ans;
    }

    /**
     * ==========================================
     * Phase 2: Brute Force Approach (Linear Scan) - The "Think it" stage.
     * ==========================================
     * Detailed Intuition:
     * If we ignore the O(log n) requirement for a moment, the simplest way to solve this
     * is a linear scan. We iterate through the array from left to right. The very first
     * time we see `target`, we record its index as `first`. Every subsequent time we see
     * `target`, we update `last`. This guarantees we find the exact boundaries.
     * * Complexity Analysis:
     * - Time Complexity: O(N) in the worst case, as we must iterate through the entire
     * array to ensure we found the absolute last occurrence.
     * - Space Complexity: O(1) Auxiliary Space. No extra data structures are used.
     */
    public static int[] searchRangeBruteForce(int[] nums, int target) {
        int first = -1;
        int last = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                if (first == -1) {
                    first = i; // Assign first only once
                }
                last = i;      // Continually update last
            }
        }
        return new int[]{first, last};
    }

    /**
     * ==========================================
     * Phase 3: Alternative Approach (Recursive Binary Search)
     * ==========================================
     * Detailed Intuition:
     * The logical transition from Phase 1 is implementing the exact same divide-and-conquer
     * logic using recursion. Instead of maintaining `low` and `high` in a while loop, we
     * pass them down the call stack along with our `ans` accumulator. This demonstrates
     * recursion mastery to the interviewer.
     * * Complexity Analysis:
     * - Time Complexity: O(log N). The search space halves at each recursive call.
     * - Space Complexity: O(log N) Auxiliary Stack Space due to recursive call frames.
     * Heap space remains O(1).
     */
    public static int[] searchRangeRecursive(int[] nums, int target) {
        int first = recursiveFindFirst(nums, target, 0, nums.length - 1, -1);
        if (first == -1) {
            return new int[]{-1, -1};
        }
        int last = recursiveFindLast(nums, target, 0, nums.length - 1, -1);
        return new int[]{first, last};
    }

    private static int recursiveFindFirst(int[] nums, int target, int low, int high, int ans) {
        if (low > high) return ans;

        int mid = low + (high - low) / 2;

        if (nums[mid] == target) {
            return recursiveFindFirst(nums, target, low, mid - 1, mid); // Search left
        } else if (nums[mid] < target) {
            return recursiveFindFirst(nums, target, mid + 1, high, ans); // Search right
        } else {
            return recursiveFindFirst(nums, target, low, mid - 1, ans);  // Search left
        }
    }

    private static int recursiveFindLast(int[] nums, int target, int low, int high, int ans) {
        if (low > high) return ans;

        int mid = low + (high - low) / 2;

        if (nums[mid] == target) {
            return recursiveFindLast(nums, target, mid + 1, high, mid); // Search right
        } else if (nums[mid] < target) {
            return recursiveFindLast(nums, target, mid + 1, high, ans); // Search right
        } else {
            return recursiveFindLast(nums, target, low, mid - 1, ans);  // Search left
        }
    }

    /**
     * ==========================================
     * 4. Testing Suite
     * ==========================================
     */
    public static void main(String[] args) {
        // Test Case 1: Standard case with multiple targets (Example 1)
        int[] nums1 = {5, 7, 7, 8, 8, 10};
        int target1 = 8;

        // Test Case 2: Target not found (Example 2)
        int[] nums2 = {5, 7, 7, 8, 8, 10};
        int target2 = 6;

        // Test Case 3: Empty array (Example 3)
        int[] nums3 = {};
        int target3 = 0;

        // Test Case 4: Array with a single element (matching)
        int[] nums4 = {1};
        int target4 = 1;

        // Test Case 5: Array with all identical elements
        int[] nums5 = {2, 2, 2, 2, 2};
        int target5 = 2;

        System.out.println("=== Phase 1: Optimal (Iterative Binary Search) ===");
        printResult("Test 1", target1, searchRangeOptimal(nums1, target1), new int[]{3, 4});
        printResult("Test 2", target2, searchRangeOptimal(nums2, target2), new int[]{-1, -1});
        printResult("Test 3", target3, searchRangeOptimal(nums3, target3), new int[]{-1, -1});
        printResult("Test 4", target4, searchRangeOptimal(nums4, target4), new int[]{0, 0});
        printResult("Test 5", target5, searchRangeOptimal(nums5, target5), new int[]{0, 4});

        System.out.println("\n=== Phase 2: Brute Force (Linear Scan) ===");
        printResult("Test 1", target1, searchRangeBruteForce(nums1, target1), new int[]{3, 4});
        printResult("Test 2", target2, searchRangeBruteForce(nums2, target2), new int[]{-1, -1});
        printResult("Test 5", target5, searchRangeBruteForce(nums5, target5), new int[]{0, 4});

        System.out.println("\n=== Phase 3: Alternative (Recursive Binary Search) ===");
        printResult("Test 1", target1, searchRangeRecursive(nums1, target1), new int[]{3, 4});
        printResult("Test 2", target2, searchRangeRecursive(nums2, target2), new int[]{-1, -1});
        printResult("Test 5", target5, searchRangeRecursive(nums5, target5), new int[]{0, 4});
    }

    // Helper formatter for clear output testing
    private static void printResult(String testName, int target, int[] result, int[] expected) {
        System.out.printf("%s (Target %d): [%2d, %2d] \t| Expected: [%2d, %2d]%n",
                testName, target, result[0], result[1], expected[0], expected[1]);
    }
}