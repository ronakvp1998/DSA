package SheetQuestions.arrays;

//Given an array of N integers where each value represents the number of chocolates in a packet. Each packet can have a variable number of chocolates. There are m students, the task is to distribute chocolate packets such that:

//        Each student gets one packet.
//        The difference between the number of chocolates in the packet with maximum chocolates and the packet with minimum chocolates given to the students is minimum.

//Input : arr[] = {7, 3, 2, 4, 9, 12, 56} , m = 3
//        Output: Minimum Difference is 2
//        Explanation:
//        We have seven packets of chocolates and we need to pick three packets for 3 students
//        If we pick 2, 3 and 4, we get the minimum difference between maximum and minimum packet sizes.

import java.util.*;

public class ChocolateDistributionProblem {

    public static void main(String[] args) {
        int arr[] =  {3, 4, 1, 9, 56, 7, 9, 12}  ;
        int m = 3;
        System.out.println(chocolateDistPro1(arr,m));
    }

    // Naive Approach for Chocolate Distribution Problem
//    The idea is to generate all subsets of size m of arr[0..n-1]. For every subset,
//    find the difference between the maximum and minimum elements in it. Finally, return the minimum difference.

    public static int chocolateDistPro1(int arr[], int m){
        int n = arr.length;
        if(m > n){
            return -1;
        }

        List<Integer > subsets = new ArrayList<>();
        return findMinDiffUntil(arr,n,m,0,subsets);
    }

    private static int findMinDiffUntil(int arr[],int n,int m,int index, List<Integer> subsets){
        System.out.println("old subset " + subsets);
        if(subsets.size() == m){
            return Collections.max(subsets) - Collections.min(subsets);
        }

        if(index == n){
            return Integer.MAX_VALUE;
        }

        subsets.add(arr[index]);
        System.out.println("add " + subsets) ;
        int include = findMinDiffUntil(arr,n,m,index+1,subsets);
        System.out.println("include " +subsets + ", include ret " + include);
        subsets.remove(subsets.size() - 1);
        System.out.println("remove " +subsets);
        int exclude = findMinDiffUntil(arr,n,m,index+1,subsets);
        System.out.println("exclude "+subsets +", exclude ret " + exclude);

        int min = Math.min(include,exclude);
        System.out.println("include " + include + ", exclude " + exclude +", Min " + min);
        return min;

    }


    // 2 using sorting
//    Time Complexity: O(N*log(N))
//    Auxiliary Space: O(1)

    private static int chocolateDistPro2(int arr[], int m){
        if(arr.length == 0 || m == 0){
            return 0;
        }
        Arrays.sort(arr);
        if(arr.length-1 < m){
            return -1;
        }
        int min_diff = Integer.MAX_VALUE;
        for(int i=0;i<arr.length;i++){
            int nextWindow = i+m-1;
            if(nextWindow >= arr.length){
                break;
            }

            int diff = arr[nextWindow] - arr[i];
            min_diff = Math.min(diff,min_diff);
        }
        return min_diff;
    }
}
