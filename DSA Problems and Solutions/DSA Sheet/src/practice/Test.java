package practice;

import strivers.arrays.medium.MaxSubArraySumPrint;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test {

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;

    }

    public int[] topKFrequent(int[] nums, int k){
        int n = nums.length;
        return Arrays.stream(nums).boxed()
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream()
                .sorted((a,b) -> Long.compare(b.getValue(),a.getValue()))
                .mapToInt(Map.Entry::getKey).limit(k).toArray();
    }


    int prefixSums[];
    public Test(int[] nums) {
        this.prefixSums = new int[nums.length+1];
        for(int i=0;i<nums.length;i++){
            prefixSums[i+1] = prefixSums[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return prefixSums[right + 1] - prefixSums[left];
    }

    public int findDuplicate(int[] nums) {
        int slow=nums[0],fast=nums[0];
        do{
            slow = nums[slow];
            fast = nums[nums[fast]];
        }while (slow != fast);

        slow = nums[0];
        while (slow != fast){
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    public static List<List<Integer>> generateSubarrays(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        for(int i=0;i<n;i++){
            for(int j=i;j<n;j++){
                res.add(Arrays.stream(nums,i,j+1).boxed().collect(Collectors.toList()));
            }
        }
        return res;
    }

    public static List<List<Integer>> generateSubsequencesRecursive(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        findSubsequences(nums,0,new ArrayList<>(),res);
        return res;
    }

    public static void findSubsequences(int []nums,int index,List<Integer> path,
                                        List<List<Integer>> res){

        if(index == nums.length){
            res.add(new ArrayList<>(path));
            return;
        }
        path.add(nums[index]);
        findSubsequences(nums,index+1,path,res);
        path.remove(path.size()-1);
        findSubsequences(nums,index+1,path,res);
    }

}