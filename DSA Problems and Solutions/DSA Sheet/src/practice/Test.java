package practice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Test {

    public static void main(String[] args) {
        int arr[] = {1,2,3};
        int res = 0;
        System.out.println(countSubSeq(0,arr,arr.length,3));
    }


    public List<List<Integer>> subsetsOptimal(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack2(result, new ArrayList<>(), nums, 0);
        return result;
    }

    // Pattern 1: Subsets WITHOUT a for loop
    private void backtrack2(List<List<Integer>> result, List<Integer> current, int[] nums, int index) {
        // Base Case: We have made a Yes/No decision for every single element
        if (index == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // CHOICE 2: Include the current element
        current.add(nums[index]);
        backtrack2(result, current, nums, index + 1);
        current.remove(current.size() - 1);

        // CHOICE 1: Exclude the current element
        backtrack2(result, current, nums, index + 1);

    }

    private void backtrack(List<List<Integer>> result, List<Integer> current, int[] nums, int start) {

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

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        comdSum2(0,candidates,target,new ArrayList<>(),res);
        return res;
    }

    private static void comdSum2(int start,int cand[],int target,List<Integer> temp,List<List<Integer>>res){
        if(start > cand.length){
            return;
        }
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
            comdSum2(i+1,cand,target-cand[i],temp,res);
            temp.remove(temp.size()-1);
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<List<Integer>> res = new ArrayList<>();
        comdSum(0,candidates,target,new ArrayList<>(),res);
        return res;
    }

    private static void comdSum(int i,int cand[],int target,List<Integer> temp,List<List<Integer>>res){
        if(i >= cand.length){
            return;
        }
        if(target == 0){
            res.add(new ArrayList<>(temp));
            return;
        }
        if(cand[i] <= target){
            temp.add(cand[i]);
            comdSum(i,cand,target-cand[i],temp,res);
            temp.remove(temp.size()-1);
        }
        comdSum(i+1,cand,target,temp,res);
    }

    private static int countSubSeq(int index, int arr[], int n,int target) {
        if(index == n){
            if(target == 0){
                return 1;
            }else {
                return 0;
            }
        }
        int left = countSubSeq(index+1,arr,n,target - arr[index]);
        int right = countSubSeq(index+1,arr,n,target);
        return left+right;

    }

    private static void printSub(int i,int sum,int arr[],List<Integer>temp,List<List<Integer>>res){
        if(i == arr.length){
            if(sum == 0){
                res.add(new ArrayList<>(temp));
            }
            return;
        }
        if(arr[i] <= sum){
        temp.add(arr[i]);
        printSub(i+1,sum-arr[i],arr,temp,res);
        temp.remove(temp.size()-1);
        }
        printSub(i+1,sum,arr,temp,res);
    }

    private static void binaryString(int n,StringBuilder sb,List<String> res){

        if(sb.length() == n){
            res.add(sb.toString());
            return;
        }

        sb.append('0');
        binaryString(n,sb,res);
        sb.deleteCharAt(sb.length()-1);

        sb.append('1');
        binaryString(n,sb,res);
        sb.deleteCharAt(sb.length()-1);
    }

}