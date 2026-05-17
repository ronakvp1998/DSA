package SheetQuestions.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ContainsDuplicates {

    public static void main(String[] args) {
        int nums[] = {1,2,3,4};
        System.out.println(checkDuplicate1(nums));
        System.out.println(checkDuplicate2(nums));
        System.out.println(checkDuplicate3(nums));

    }

    public static boolean checkDuplicate3(int nums[]){ // O(n log n)
        Arrays.sort(nums);
        for(int i= 1;i< nums.length;i++){
            if(nums[i] == nums[i-1]){
                return true;
            }
        }
        return false;
    }

    public static boolean checkDuplicate2(int nums[]){ // O(n^2)
        int n = nums.length;

        for(int i=0;i< n-1;i++){
            for(int j=i+1;j< n;j++){
                if(nums[i] == nums[j]){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkDuplicate1(int nums[]){ // O(n)
        HashMap<Integer,Integer> maps = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            Integer s = maps.get(nums[i]);
            if(s != null){
                maps.put(nums[i], s + 1);
            }
            else{
                maps.put(nums[i], 1);
            }
        }

        for(Map.Entry m : maps.entrySet()){
            if((Integer)m.getValue() > 1){
                return true;
            }
        }
        return false;
    }
}
