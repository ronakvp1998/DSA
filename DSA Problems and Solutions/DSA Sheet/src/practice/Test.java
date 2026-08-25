package practice;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        int arr[] = {5,2,1};
        int n = 3;
        System.out.println(sumSubsets(arr,n));
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        combsum3(1,k,n,new ArrayList<>(),res);
        return res;
    }
    private void combsum3(int index,int size,int target,List<Integer>temp,List<List<Integer>>res){
        if(target == 0 && temp.size() == size){
            res.add(new ArrayList<>(temp));
            return;
        }
        if(target < 0  || temp.size() > size){
            return;
        }
        for(int i = index;i<=9;i++){
            temp.add(i);
            combsum3(i+1,size,target-i,temp,res);
            temp.remove(temp.size()-1);
        }
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        subset(nums,0,new ArrayList<>(),res);
        return res;
    }

    private void subset(int nums[],int index,List<Integer>temp,List<List<Integer>>res){
        res.add(new ArrayList<>(temp));
        for(int i=index;i<nums.length;i++){
            if(i > index && nums[i] == nums[i-1]){
                continue;
            }
            temp.add(nums[i]);
            subset(nums,i+1,temp,res);
            temp.remove(temp.size()-1);
        }
    }

//    Subset Sum : Sum of all Subsets
//    Input: N = 3, arr[] = {5,2,1}
//    Output: 0,1,2,3,5,6,7,8

    private static List<Integer> sumSubsets(int arr[],int n){
        List<Integer> sumList = new ArrayList<>();
        Arrays.sort(arr);
        recSum(arr,0,0,sumList);
        Collections.sort(sumList);
        return sumList;
    }

    private static void recSum(int arr[],int index,int sum,List<Integer> sumList){
        sumList.add(sum);
        for(int i=index;i<arr.length;i++){
            if(i > index && arr[i] == arr[i-1]){
                continue;
            }
            recSum(arr,i+1,sum+arr[i],sumList);
        }
    }

}