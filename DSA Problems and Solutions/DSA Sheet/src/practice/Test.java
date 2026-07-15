package practice;

public class Test {

    public int phase1OptimalGreedy(int[] gas, int[] cost) {
        int totalGas=0,totalCost=0,currentTank=0,startingStation=0;
        for(int i=0;i<gas.length;i++){
            totalGas += gas[i];
            totalCost += cost[i];
            currentTank += gas[i] - cost[i];
            if(currentTank < 0){
                startingStation = i+1;
                currentTank = 0;
            }
        }
        if(totalCost > totalGas){
            return -1;
        }
        return startingStation;
    }

    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        int n =nums.length;
        int currentSum=0,maxsum = Integer.MIN_VALUE;
        for(int i=0;i<n;i++){
            currentSum += nums[i];
            maxsum = Math.max(maxsum,currentSum);
            if(currentSum < 0){
                currentSum = 0;
            }
        }
        return maxsum;
    }
}
