package practice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Test {

    public static void main(String[] args) {
        int arr[] = {1,2,3};
        List<List<Integer>> res = new ArrayList<>();
        generateOneSumk(0,arr,new ArrayList<>(),res,0,3);
        for(List<Integer> i : res){
            System.out.println(i);
        }
    }

    public List<List<Integer>> subsetsWithDupForLoop(int[] nums) {
        Arrays.sort(nums);;
        List<List<Integer>> res = new ArrayList<>();
        solveForLoop(0,nums,new ArrayList<>(),res);
        return res;
    }

    private void solveForLoop(int start, int[] nums, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        for(int i=start;i<nums.length;i++){
            if(i > start && nums[i] == nums[i-1]){
                continue;
            }
            current.add(nums[i]);
            solveForLoop(i+1,nums,current,result);;
            current.remove(current.size()-1);
        }
    }

    public List<List<Integer>> subsetsOptimal(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(res,new ArrayList<>(),nums,0);
        return res;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> current, int[] nums, int start) {
        result.add(new ArrayList<>(current));
        if(start == nums.length){
            return;
        }
        for(int i=start;i<nums.length;i++){
            current.add(nums[i]);
            backtrack(result,current,nums,start+1);
            current.remove(current.size()-1);
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        comb2(0,candidates,target,new ArrayList<>(),res);
        return res;
    }
    public void comb2(int start,int cand[],int target,List<Integer>temp,List<List<Integer>>res){

        if(target == 0){
            res.add(new ArrayList<>(temp));
            return;
        }

        for(int i=start;i<cand.length;i++){
            if(i > start && cand[i] == cand[i-1]){
                continue;
            }
            if(cand[i] > target){
                break;
            }
            temp.add(cand[i]);
            comb2(i+1,cand,target-cand[i],temp,res);
            temp.remove(temp.size()-1);
        }
    }
}