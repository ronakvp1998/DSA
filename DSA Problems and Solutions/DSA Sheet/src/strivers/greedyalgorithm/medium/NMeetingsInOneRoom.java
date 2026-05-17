package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: N Meetings in One Room
 * ==================================================================================================
 * There is one meeting room in a firm. You are given two arrays, start and end each of size N.
 * For an index 'i', start[i] denotes the starting time of the ith meeting while end[i] will denote
 * the ending time of the ith meeting.
 * Find the maximum number of meetings that can be accommodated if only one meeting can happen in
 * the room at a particular time. Print the order in which these meetings will be performed.
 *
 * Example 1:
 * Input: N = 6, start[] = {1,3,0,5,8,5}, end[] = {2,4,6,7,9,9}
 * Output: [1, 2, 4, 5]
 * Explanation: Maximum 4 meetings can be conducted:
 * Meeting 1 (1-2), Meeting 2 (3-4), Meeting 4 (5-7), and Meeting 5 (8-9).
 *
 * Example 2:
 * Input: N = 2, start[] = {1,5}, end[] = {7,8}
 * Output: [1]
 * Explanation: Only one meeting can take place because they overlap or one is just enough.
 * * ==================================================================================================
 * APPROACH: GREEDY ALGORITHM (Sort by End Time)
 * ==================================================================================================
 * The problem requires us to schedule the maximum number of non-overlapping meetings.
 * To maximize the count, we should always greedily pick the meeting that ends the EARLIEST.
 * Why? Because finishing a meeting as early as possible frees up the room for the maximum
 * number of future meetings.
 * * 1. Store the meetings in a custom object containing start time, end time, and original index.
 * 2. Sort the meetings in ascending order based on their END time.
 * 3. Iterate through the sorted meetings. Keep track of the 'lastEndTime'.
 * 4. If a meeting's start time is strictly greater than the 'lastEndTime', we can accommodate it.
 * 5. Update the 'lastEndTime' and store the original 1-based index of the meeting.
 * ==================================================================================================
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NMeetingsInOneRoom {

    public static void main(String[] args) {
        // Test Case 1
        int[] start1 = {1, 3, 0, 5, 8, 5};
        int[] end1 = {2, 4, 6, 7, 9, 9};
        System.out.println("Test Case 1 (Expected: [1, 2, 4, 5]) -> " + maxMeetings(start1, end1));

        // Test Case 2
        int[] start2 = {1, 5};
        int[] end2 = {7, 8};
        System.out.println("Test Case 2 (Expected: [1])          -> " + maxMeetings(start2, end2));
    }

    /**
     * Helper class to bind start time, end time, and original position together.
     * This is much cleaner and more object-oriented than using 2D arrays in an interview.
     */
    static class Meeting {
        int start;
        int end;
        int pos; // 1-based index

        Meeting(int start, int end, int pos) {
            this.start = start;
            this.end = end;
            this.pos = pos;
        }
    }

    /**
     * Finds the maximum number of meetings and their original order.
     * * @param start Array containing start times of meetings
     * @param end   Array containing end times of meetings
     * @return      A list of 1-based indices representing the scheduled meetings
     */
    public static List<Integer> maxMeetings(int[] start, int[] end) {
        int n = start.length;
        List<Meeting> meetings = new ArrayList<>();

        // 1. Combine the start, end, and position into a single Meeting object
        for (int i = 0; i < n; i++) {
            // Note: Position is 1-based as per standard problem requirements
            meetings.add(new Meeting(start[i], end[i], i + 1));
        }

        // 2. Sort the meetings strictly by their END time in ascending order.
        // If two meetings have the same end time, their order doesn't strictly matter for maximizing count,
        // but sorting by position as a tie-breaker maintains stability.
        Collections.sort(meetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting m1, Meeting m2) {
                if (m1.end < m2.end) {
                    return -1; // m1 ends earlier, put it first
                } else if (m1.end > m2.end) {
                    return 1;  // m2 ends earlier, put it first
                } else {
                    return m1.pos - m2.pos; // Tie-breaker: earlier original position
                }
            }
        });

        List<Integer> result = new ArrayList<>();

        // Track the end time of the last successfully scheduled meeting.
        // Initialize to -1 to ensure the very first valid meeting gets picked.
        int lastEndTime = -1;

        // 3. Greedily select non-overlapping meetings
        for (Meeting currentMeeting : meetings) {

            // Check if the room is free.
            // A meeting can only start IF its start time is strictly GREATER than the end time of the previous meeting.
            if (currentMeeting.start > lastEndTime) {

                // Add the 1-based index of this meeting to our result
                result.add(currentMeeting.pos);

                // Update the room's occupied status to the end time of this new meeting
                lastEndTime = currentMeeting.end;
            }
        }

        return result;
    }
}