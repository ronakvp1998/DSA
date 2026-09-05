package practice;

import java.util.*;

public class Test {

    public int subarraySumOptimal(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0,1);
        int count=0,runningSum=0;
        for(int num : nums){
            runningSum += num;
            int target = runningSum - k;
            if(map.containsKey(target)){
                count += map.get(target);
            }
            map.put(runningSum,map.getOrDefault(runningSum,0)+1);
        }
        return count;
    }

}