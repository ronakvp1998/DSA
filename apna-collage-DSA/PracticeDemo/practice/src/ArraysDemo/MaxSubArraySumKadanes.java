package ArraysDemo;

public class MaxSubArraySumKadanes {

    public static void main(String[] args) {
        int num[] = {1, -2, 6, -1, 3};
//        int num[] = {2,4,6,8,10};
        maxSubArraySumKadanes(num);
    }

    private static void maxSubArraySumKadanes(int[] num) {
        int ms = Integer.MIN_VALUE;
        int cs = 0;
        for(int i=0;i<num.length;i++){
            cs = cs + num[i];
            if(cs < 0){
                cs = 0;
            }
            ms = Math.max(cs,ms);
        }
        System.out.println(ms);
    }
}
