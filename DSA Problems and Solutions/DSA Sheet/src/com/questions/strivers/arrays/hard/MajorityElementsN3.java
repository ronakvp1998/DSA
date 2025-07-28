package com.questions.strivers.arrays.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// ✅ Problem: Find elements that appear more than ⌊n/3⌋ times in an array
// https://takeuforward.org/data-structure/majority-elementsn-3-times-find-the-elements-that-appears-more-than-n-3-times-in-the-array/

public class MajorityElementsN3 {

    /*
    🔹 Approach 1: Brute Force
    ----------------------------------
    For each element, count its frequency using a nested loop.
    If its frequency > n/3 and not already in result list → add it.

    Time Complexity: O(n^2)
    Space Complexity: O(1) (excluding output list)
    */
    public static List<Integer> majorityElement(int[] v) {
        int n = v.length; // size of the array
        List<Integer> ls = new ArrayList<>(); // output list to store majority elements

        for (int i = 0; i < n; i++) {
            // Check if current element is already in result list
            if (ls.size() == 0 || ls.get(0) != v[i]) {
                int cnt = 0;
                // Count frequency of v[i]
                for (int j = 0; j < n; j++) {
                    if (v[j] == v[i]) {
                        cnt++;
                    }
                }

                // If frequency is more than n/3, add to result
                if (cnt > (n / 3))
                    ls.add(v[i]);
            }

            // Only two elements can occur more than n/3 times
            if (ls.size() == 2) break;
        }

        return ls;
    }

    /*
    🔹 Approach 2: HashMap-based Frequency Count
    --------------------------------------------
    Use a HashMap to count frequencies of each element.
    If frequency crosses n/3 + 1, add to result list.

    Time Complexity: O(n)
    Space Complexity: O(n)
    */
    public static List<Integer> majorityElement2(int[] v) {
        int n = v.length;
        List<Integer> ls = new ArrayList<>();

        HashMap<Integer, Integer> mpp = new HashMap<>(); // maps element → count
        int mini = (int)(n / 3) + 1; // threshold for majority

        for (int i = 0; i < n; i++) {
            int value = mpp.getOrDefault(v[i], 0);
            mpp.put(v[i], value + 1); // update count

            // Add element to result list if it crosses threshold
            if (mpp.get(v[i]) == mini) {
                ls.add(v[i]);
            }

            // Only 2 majority elements are possible
            if (ls.size() == 2) break;
        }

        return ls;
    }

    /*
    🔹 Approach 3: Extended Boyer-Moore Voting Algorithm
    -----------------------------------------------------
    Only at most 2 elements can occur more than n/3 times.
    Maintain 2 candidates and their counts.

    First Pass → Find potential candidates
    Second Pass → Verify their frequencies

    Time Complexity: O(n)
    Space Complexity: O(1)
    */
    public static List<Integer> majorityElement3(int[] v) {
        int n = v.length;

        int cnt1 = 0, cnt2 = 0; // counters for 2 elements
        int el1 = Integer.MIN_VALUE; // candidate 1
        int el2 = Integer.MIN_VALUE; // candidate 2

        // 🧠 First Pass: Find two potential majority elements
        for (int i = 0; i < n; i++) {
            if (cnt1 == 0 && el2 != v[i]) {
                cnt1 = 1;
                el1 = v[i];
            } else if (cnt2 == 0 && el1 != v[i]) {
                cnt2 = 1;
                el2 = v[i];
            } else if (v[i] == el1) cnt1++;
            else if (v[i] == el2) cnt2++;
            else {
                cnt1--;
                cnt2--;
            }
        }

        // 🧠 Second Pass: Verify frequencies of el1 and el2
        cnt1 = 0;
        cnt2 = 0;
        for (int i = 0; i < n; i++) {
            if (v[i] == el1) cnt1++;
            if (v[i] == el2) cnt2++;
        }

        List<Integer> ls = new ArrayList<>();
        int mini = (int)(n / 3) + 1;
        if (cnt1 >= mini) ls.add(el1);
        if (cnt2 >= mini && el2 != el1) ls.add(el2);

        // Optional: sort result
        // Collections.sort(ls); // TC: O(1) since at most 2 elements

        return ls;
    }

    public static void main(String args[]) {
        int[] arr = {11, 33, 33, 11, 33, 11}; // test case
        List<Integer> ans = majorityElement(arr); // try changing to majorityElement2 or 3
        System.out.print("The majority elements are: ");
        for (int i = 0; i < ans.size(); i++) {
            System.out.print(ans.get(i) + " ");
        }
        System.out.println();
    }
}
