package ArraysDemo;

public class MaxSubArraySumPrefixCode {

    public static void main(String[] args) {
        int num[] = {1, -2, 6, -1, 3};
//        int num[] = {2,4,6,8,10};
        maxSubArrayPrefix(num);
    }

    private static void maxSubArrayPrefix(int[] num) {
        int currSum = 0;
        int maxSum = Integer.MIN_VALUE;
        int prefix[] = new int[num.length];
        prefix[0] = 0;

        for(int i=1;i<prefix.length;i++){
            prefix[i] = prefix[i-1] + num[i];
        }

        for(int i=0;i<num.length;i++){

            for(int j=0;j<num.length;j++){
                int start = i;
                int end = j;
                currSum = start == 0 ? prefix[end] : prefix[end] - prefix[start-1];
                if(maxSum < currSum){
                    maxSum = currSum;
                }
            }
        }
        System.out.println("Max sum " + maxSum);

    }
}