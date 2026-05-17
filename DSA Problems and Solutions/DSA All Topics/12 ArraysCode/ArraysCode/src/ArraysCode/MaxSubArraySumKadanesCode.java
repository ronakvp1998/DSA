package ArraysCode;

import java.util.Arrays;
import java.util.OptionalInt;

public class MaxSubArraySumKadanesCode {

    public static void main(String[] args) {
//        int num[] = {-2,-3,4,-1,-2,1,5,-3};

//        int num [] = {-1,-2,-4,-6};
        int num [] = {-1,-2,-4,6};

        maxSubArrayKadane(num);
    }

    private static void maxSubArrayKadane(int[] num) {
        int currSum = 0;
        int maxSum = Integer.MIN_VALUE;


        for (int i = 0; i < num.length; i++) {
            currSum = currSum + num[i];
            if (currSum < 0) {
                currSum = 0;
            }
            maxSum = Math.max(currSum, maxSum);
        }
        if (maxSum == 0) {
            // this is special  case where all elements are negative
           OptionalInt sum1  = Arrays.stream(num).max();
           maxSum = sum1.getAsInt();
        }
        System.out.println("max sum " + maxSum);

    }
}
