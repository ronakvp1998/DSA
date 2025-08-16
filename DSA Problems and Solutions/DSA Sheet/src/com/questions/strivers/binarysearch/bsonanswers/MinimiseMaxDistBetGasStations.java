package com.questions.strivers.binarysearch.bsonanswers;

import java.util.PriorityQueue;

/*
10^-6 of the actual answer will be accepted. For example, if the actual answer is 0.65421678124, it is okay to return 0.654216. Our answer will be accepted if that is the same as the actual answer up to the 6th decimal place.

Examples

Example 1:
Input Format: N = 5, arr[] = {1,2,3,4,5}, k = 4
Result: 0.5
Explanation: One of the possible ways to place 4 gas stations is {1,1.5,2,2.5,3,3.5,4,4.5,5}. Thus the maximum difference between adjacent gas stations is 0.5. Hence, the value of ‘dist’ is 0.5. It can be shown that there is no possible way to add 4 gas stations in such a way that the value of ‘dist’ is lower than this.
Example 2:
Input Format: N = 10, arr[] = {1,2,3,4,5,6,7,8,9,10}, k = 1
Result: 1
Explanation: One of the possible ways to place 1 gas station is {1,1.5,2,3,4,5,6,7,8,9,10}. Thus the maximum difference between adjacent gas stations is still 1. Hence, the value of ‘dist’ is 1. It can be shown that there is no possible way to add 1 gas station in such a way that the value of ‘dist’ is lower than this.

Let’s understand how to place the new gas stations so that the maximum distance between two consecutive gas stations is reduced.


Let’s consider a small example like this: given gas stations = {1, 7} and k = 2.


Observation: A possible arrangement for placing 2 gas stations is as follows: {1, 7, 8, 9}. In this arrangement, the new gas stations are positioned after the last existing one. Prior to adding the new stations, the maximum distance between stations was 6 (i.e. the distance between 1 and 7). Even after placing the 2 new stations, the maximum distance remains unchanged at 6.
 */
public class MinimiseMaxDistBetGasStations {
    public static double minimiseMaxDistance(int[] arr, int k) {
        int n = arr.length; //size of array.
        int[] howMany = new int[n - 1];

        //Pick and place k gas stations:
        for (int gasStations = 1; gasStations <= k; gasStations++) {
            //Find the maximum section
            //and insert the gas station:
            double maxSection = -1;
            int maxInd = -1;
            for (int i = 0; i < n - 1; i++) {
                double diff = arr[i + 1] - arr[i];
                double sectionLength =
                        diff / (double)(howMany[i] + 1);
                if (sectionLength > maxSection) {
                    maxSection = sectionLength;
                    maxInd = i;
                }
            }
            //insert the current gas station:
            howMany[maxInd]++;
        }

        //Find the maximum distance i.e. the answer:
        double maxAns = -1;
        for (int i = 0; i < n - 1; i++) {
            double diff = arr[i + 1] - arr[i];
            double sectionLength =
                    diff / (double)(howMany[i] + 1);
            maxAns = Math.max(maxAns, sectionLength);
        }
        return maxAns;
    }




    public static class Pair {
        double first;
        int second;

        Pair(double first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public static double minimiseMaxDistance2(int[] arr, int k) {
        int n = arr.length; // size of array.
        int[] howMany = new int[n - 1];
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Double.compare(b.first, a.first));

        // insert the first n-1 elements into pq
        // with respective distance values:
        for (int i = 0; i < n - 1; i++) {
            pq.add(new Pair(arr[i + 1] - arr[i], i));
        }

        // Pick and place k gas stations:
        for (int gasStations = 1; gasStations <= k; gasStations++) {
            // Find the maximum section
            // and insert the gas station:
            Pair tp = pq.poll();
            int secInd = tp.second;

            // insert the current gas station:
            howMany[secInd]++;

            double inidiff = arr[secInd + 1] - arr[secInd];
            double newSecLen = inidiff / (double) (howMany[secInd] + 1);
            pq.add(new Pair(newSecLen, secInd));
        }

        return pq.peek().first;
    }



    public static int numberOfGasStationsRequired3(double dist, int[] arr) {
        int n = arr.length; // size of the array
        int cnt = 0;
        for (int i = 1; i < n; i++) {
            int numberInBetween = (int)((arr[i] - arr[i - 1]) / dist);
            if ((arr[i] - arr[i - 1]) == (dist * numberInBetween)) {
                numberInBetween--;
            }
            cnt += numberInBetween;
        }
        return cnt;
    }

    public static double minimiseMaxDistance3(int[] arr, int k) {
        int n = arr.length; // size of the array
        double low = 0;
        double high = 0;

        //Find the maximum distance:
        for (int i = 0; i < n - 1; i++) {
            high = Math.max(high, (double)(arr[i + 1] - arr[i]));
        }

        //Apply Binary search:
        double diff = 1e-6 ;
        while (high - low > diff) {
            double mid = (low + high) / (2.0);
            int cnt = numberOfGasStationsRequired3(mid, arr);
            if (cnt > k) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return high;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int k = 4;
        double ans = minimiseMaxDistance(arr, k);
        System.out.println("The answer is: " + ans);
    }



}
