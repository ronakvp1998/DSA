package com.questions.strivers.arrays.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {

        // ------------------- 1. BASIC RECURSIVE BACKTRACKING (LIST BUILDING) -----------------------
        public static List<List<Integer>> generatePermutationsRecursive(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            recursivePermute(nums, new ArrayList<>(), result);
            return result;
        }

        private static void recursivePermute(int[] nums, List<Integer> current, List<List<Integer>> result) {
            if (current.size() == nums.length) {
                result.add(new ArrayList<>(current));
                return;
            }

            for (int num : nums) {
                if (!current.contains(num)) { // Avoid duplicates
                    current.add(num);
                    recursivePermute(nums, current, result);
                    current.remove(current.size() - 1); // backtrack
                }
            }
        }

        // ------------------- 2. BACKTRACKING WITH SWAP (int[]) -----------------------
        public static List<List<Integer>> generatePermutationsSwap(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            permuteWithSwap(nums, 0, result);
            return result;
        }

        private static void permuteWithSwap(int[] nums, int index, List<List<Integer>> result) {
            if (index == nums.length) {
                List<Integer> perm = new ArrayList<>();
                for (int num : nums) perm.add(num);
                result.add(perm);
                return;
            }

            for (int i = index; i < nums.length; i++) {
                swap(nums, index, i);
                permuteWithSwap(nums, index + 1, result);
                swap(nums, index, i); // backtrack
            }
        }

        // ------------------- 3. VISITED ARRAY FOR STRINGS -----------------------
        public static List<String> generateStringPermutations(String s) {
            List<String> result = new ArrayList<>();
            boolean[] visited = new boolean[s.length()];
            backtrackStringPermutations(s, new StringBuilder(), visited, result);
            return result;
        }

        private static void backtrackStringPermutations(String s, StringBuilder current, boolean[] visited, List<String> result) {
            if (current.length() == s.length()) {
                result.add(current.toString());
                return;
            }

            for (int i = 0; i < s.length(); i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    current.append(s.charAt(i));
                    backtrackStringPermutations(s, current, visited, result);
                    current.deleteCharAt(current.length() - 1); // backtrack
                    visited[i] = false;
                }
            }
        }

        // ------------------- 4. ITERATIVE USING next_permutation -----------------------
        public static List<List<Integer>> generatePermutationsIterative(int[] nums) {
            Arrays.sort(nums);
            List<List<Integer>> result = new ArrayList<>();

            do {
                List<Integer> perm = new ArrayList<>();
                for (int num : nums) perm.add(num);
                result.add(perm);
            } while (nextPermutation(nums));

            return result;
        }

        private static boolean nextPermutation(int[] nums) {
            int i = nums.length - 2;
            while (i >= 0 && nums[i] >= nums[i + 1]) i--;

            if (i < 0) return false;

            int j = nums.length - 1;
            while (nums[j] <= nums[i]) j--;

            swap(nums, i, j);
            reverse(nums, i + 1, nums.length - 1);

            return true;
        }

        // ------------------- 5. SLIDING WINDOW STYLE RECURSION (for Strings) -----------------------
        public static List<String> generateSlidingWindowPermutations(String s) {
            List<String> result = new ArrayList<>();
            slidingWindowPermute(s.toCharArray(), 0, result);
            return result;
        }

        private static void slidingWindowPermute(char[] arr, int index, List<String> result) {
            if (index == arr.length) {
                result.add(new String(arr));
                return;
            }

            for (int i = index; i < arr.length; i++) {
                swap(arr, index, i);
                slidingWindowPermute(arr, index + 1, result);
                swap(arr, index, i); // backtrack
            }
        }

        // ------------------- UTILITIES -----------------------
        private static void swap(int[] nums, int i, int j) {
            int temp = nums[i]; nums[i] = nums[j]; nums[j] = temp;
        }

        private static void swap(char[] arr, int i, int j) {
            char temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
        }

        private static void reverse(int[] nums, int left, int right) {
            while (left < right) swap(nums, left++, right--);
        }

        // ------------------- MAIN METHOD FOR TESTING -----------------------
        public static void main(String[] args) {
            int[] nums = {1, 2, 3};
            String str = "ABC";

            System.out.println("1. Recursive Backtracking:");
            List<List<Integer>> recursive = generatePermutationsRecursive(nums.clone());
            recursive.forEach(System.out::println);

            System.out.println("\n2. Backtracking with Swap:");
            List<List<Integer>> swapBased = generatePermutationsSwap(nums.clone());
            swapBased.forEach(System.out::println);

            System.out.println("\n3. Visited Array (String):");
            List<String> visited = generateStringPermutations(str);
            visited.forEach(System.out::println);

            System.out.println("\n4. Iterative (next_permutation):");
            List<List<Integer>> iterative = generatePermutationsIterative(nums.clone());
            iterative.forEach(System.out::println);

            System.out.println("\n5. Sliding Window Permutation (String):");
            List<String> sliding = generateSlidingWindowPermutations(str);
            sliding.forEach(System.out::println);
        }
    }

/*
====================================================================================
APPROACHES INCLUDED:

1. Recursive Backtracking (List Building)
   - Time: O(n!) | Space: O(n) (recursion + list)
   - Simple but less efficient due to contains() check

2. Backtracking with Swapping (int array)
   - Time: O(n!) | Space: O(n)
   - Efficient and clean for arrays

3. Backtracking with Visited Array (string)
   - Time: O(n!) | Space: O(n) for visited[] and recursion
   - Good for string permutations

4. Iterative using next_permutation
   - Time: O(n! * n) | Space: O(n)
   - Lexicographic order permutations

5. Sliding Window Recursion (String)
   - Time: O(n!) | Space: O(n)
   - Similar to swap-based but for character arrays

====================================================================================
*/

