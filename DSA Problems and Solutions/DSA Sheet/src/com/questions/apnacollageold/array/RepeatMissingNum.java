package com.questions.apnacollageold.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
// pg 4
// code 9
// You are given a read only array of n integers from 1 to n.
//
//Each integer appears exactly once except A which appears twice and B which is missing.
//
//Return A and B.
//
//Note: Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
//
//Note that in your output A should precede B.
//
//Example:
//
//Input:[3 1 2 5 3]
//
//Output:[3, 4]
//
//A = 3, B = 4
public class RepeatMissingNum {

    public static void main(String[] args) {
        Integer arr[] = {3,1,2,5,3};
        System.out.println(repeatedNumber2(Arrays.asList(arr)));
    }

    // using formula TC O(n) SC O(1)
    private static ArrayList<Integer> repeatedNumber2(final List<Integer> A) {
        long n = A.size();

        long expectedSum = n * (n+1)/2;
        long expectedSumSqr = n * (n+1)*(2*n+1)/6;

        long actualSum = 0, actualSumSqr = 0;
        for(Integer a : A){
            actualSum += a;
            actualSumSqr += (long) (a*a);
        }

        long val1 =  (actualSum - expectedSum);
        long val2 =  (actualSumSqr - expectedSumSqr);

        long val3 = val2/val1;

        int x =(int) (val1 + val3) / 2;
        int y  = (int) (x - val1);

        return new ArrayList<>(Arrays.asList((int) x,(int) y));

    }


    // using hashmap SC O(n)
    private static ArrayList<Integer> repeatedNumber(final List<Integer> A) {
        HashMap<Integer,Integer> map = new HashMap<>();
        int duplicate = -1;
        int missing = -1;
        for(Integer a : A){
            map.put(a,map.getOrDefault(a,0) + 1);
        }

        for(int i=1;i<= A.size();i++){
            int count = map.getOrDefault(i,0);
            if(count == 2){
                duplicate = i;
            }if(count == 0){
                missing = i;
            }
        }
        return new ArrayList<>(Arrays.asList(duplicate,missing));
    }

}
