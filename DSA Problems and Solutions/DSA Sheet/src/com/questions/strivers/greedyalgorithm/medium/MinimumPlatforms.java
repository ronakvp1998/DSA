package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Minimum number of platforms required for a railway
 * ==================================================================================================
 * We are given two arrays that represent the arrival and departure times of trains that stop at
 * the platform. We need to find the minimum number of platforms needed at the railway station
 * so that no train has to wait.
 * * Note: Time is represented as an integer (e.g., 900 means 9:00 AM, 1130 means 11:30 AM).
 * If a train arrives at the exact same minute another departs (arr[i] == dep[j]), a new platform
 * is still needed for that exact minute.
 *
 * Example 1:
 * Input: arr[] = {900, 945, 955, 1100, 1500, 1800}, dep[] = {920, 1200, 1130, 1150, 1900, 2000}
 * Output: 3
 * Explanation: At 11:00, trains that arrived at 9:45 and 9:55 are still at the station. So, 3 platforms.
 *
 * Example 2:
 * Input: arr[] = {1020, 1200}, dep[] = {1050, 1230}
 * Output: 1
 * Explanation: The first train leaves before the second arrives. 1 platform is enough.
 * * ==================================================================================================
 * APPROACH: TWO POINTERS / EVENT SORTING (Line Sweep)
 * ==================================================================================================
 * Instead of tracking specific trains, we only care about the chronological timeline of "events"
 * (an arrival is a +1 to platforms, a departure is a -1 to platforms).
 * * By sorting the arrival and departure arrays INDEPENDENTLY, we lose track of which departure
 * belongs to which arrival. BUT THAT DOESN'T MATTER! We only care about how many trains are
 * physically at the station at any given time.
 * * We use two pointers to traverse the sorted arrays. If the next chronological event is an
 * arrival, we increment the platform count. If it's a departure, we decrement it. We track the
 * maximum platform count reached during this process.
 * ==================================================================================================
 */

import java.util.Arrays;

public class MinimumPlatforms {

    public static void main(String[] args) {
        // Test Case 1: Expected 3
        int[] arr1 = {900, 945, 955, 1100, 1500, 1800};
        int[] dep1 = {920, 1200, 1130, 1150, 1900, 2000};
        System.out.println("Test Case 1 (Expected: 3) -> Result: " + findPlatform(arr1, dep1));

        // Test Case 2: Expected 1
        int[] arr2 = {1020, 1200};
        int[] dep2 = {1050, 1230};
        System.out.println("Test Case 2 (Expected: 1) -> Result: " + findPlatform(arr2, dep2));
    }

    /**
     * Calculates the minimum number of platforms required.
     * * @param arr Array of arrival times
     * @param dep Array of departure times
     * @return The minimum platforms needed
     */
    public static int findPlatform(int[] arr, int[] dep) {
        // Edge Case: If there are no trains, 0 platforms are needed.
        if (arr.length == 0) return 0;

        // 1. Sort both arrival and departure arrays independently
        Arrays.sort(arr);
        Arrays.sort(dep);

        // 'platforms' tracks trains currently at the station.
        // We start with 1 because the first train (at arr[0]) will definitely need a platform.
        int platforms = 1;

        // 'maxPlatforms' tracks the highest number of platforms needed at any single moment.
        int maxPlatforms = 1;

        // Two Pointers:
        // 'i' points to the next arrival to process. We start at 1 since we handled arr[0] by making platforms as default 1.
        // 'j' points to the next departure to process. We start at 0.
        int i = 1;
        int j = 0;

        // 2. Traverse through both arrays representing the timeline of events
        while (i < arr.length && j < dep.length) {

            // If the next chronological event is an ARRIVAL (or happens at the exact same time as a departure)
            // Note: If arr[i] == dep[j], we process the arrival first (<=) because they cannot share the
            // platform at that exact minute.
            if (arr[i] <= dep[j]) {
                platforms++; // A train rolled in
                i++;         // Move to the next arrival event
            }
            // If the next chronological event is a DEPARTURE
            else {
                platforms--; // A train rolled out
                j++;         // Move to the next departure event
            }

            // Continuously update the peak number of platforms required
            maxPlatforms = Math.max(maxPlatforms, platforms);
        }

        return maxPlatforms;
    }
}