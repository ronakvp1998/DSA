package practice;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public List<List<Integer>> combinationSum2ForLoop(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        solveForLoop(0,candidates,target,new ArrayList<>(),res);
        return res;
    }

    private void solveForLoop(int start,int []candidates,int target,List<Integer> current,
                              List<List<Integer>> res){
        if(target == 0){
            res.add(new ArrayList<>(current));
            return;
        }
        for(int i=start;i<candidates.length;i++){
            if(i > start && candidates[i] == candidates[i-1]){
                continue;
            }
            if(candidates[i] > target){
                break;
            }
            current.add(candidates[i]);
            solveForLoop(i+1,candidates,target-candidates[i],current,res);
            current.remove(current.size()-1);
        }
    }

}
