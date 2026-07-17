package practice;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    private static void backtrack(List<List<Integer>> result,
                                  List<Integer> current,int []nums,int start){
        result.add(new ArrayList<>(current));
        if(start == nums.length){
            return;
        }
        for(int i=start;i<nums.length;i++){
            current.add(nums[i]);
            backtrack(result,current,nums,i+1);
            current.remove(current.size()-1);
        }
    }

    private static void backtrac2(List<List<Integer>> res,List<Integer> current,int []nums,int index){
        if(index == nums.length){
            res.add(new ArrayList<>(current));
            return;
        }
        backtrac2(res,current,nums,index+1);
        current.add(nums[index]);
        backtrac2(res,current,nums,index+1);
        current.remove(current.size()-1);
    }
}
