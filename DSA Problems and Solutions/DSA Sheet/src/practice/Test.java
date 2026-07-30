package practice;

import strivers.arrays.medium.MaxSubArraySumPrint;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

    public int longestSubarrayOptimalMap(int[] nums, int k) {
        Map<Long,Integer> prefixSumMap = new HashMap<>();
        int maxLen = 0;
        long currentSum=0;
        int n = nums.length;
        for(int i=0;i<n;i++){
            currentSum += nums[i];
            if(currentSum == k){
                maxLen = Math.max(maxLen,i+1);
            }
            long target = currentSum - k;
            if(prefixSumMap.containsKey(target)){
                maxLen = Math.max(maxLen,i-prefixSumMap.get(target));
            }
            if(!prefixSumMap.containsKey(currentSum)){
                prefixSumMap.put(currentSum,i);
            }
        }
        return maxLen;
    }

    public int findMaxConsecutiveOnesOptimal(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        int maxCount=0,currentCount=0;
        for(int num : nums){
            if(num == 1){
                currentCount++;
                if(currentCount > maxCount){
                    maxCount = currentCount;
                }
            }else{
                currentCount = 0;
            }
        }
        return maxCount;
    }

    public List<Integer> findUnionStreams(int[] arr1, int[] arr2) {
        int i=0,j=0,n=arr1.length,m=arr2.length;
        ArrayList<Integer> list = new ArrayList<>();
        while (i < n && j < m){
            if(arr1[i] <= arr2[j] ){
                if(list.isEmpty() || !list.get(list.size()-1).equals(arr1[i])){
                    list.add(arr1[i]);
                }
                i++;
            }else {
                if(list.isEmpty() || !list.get(list.size()-1).equals(arr2[j])) {
                    list.add(arr2[j]);
                }
                j++;
            }
        }
        while (i<n){
            if(list.isEmpty() || !list.get(list.size()-1).equals(arr1[i])){
                list.add(arr1[i]);
            }
            i++;
        }
        while (j<m){
            if(list.isEmpty() || !list.get(list.size()-1).equals(arr2[j])){
                list.add(arr2[j]);
            }
            j++;
        }
        return list;
    }

    public void moveZeroes(int[] nums) {
        if(nums == null || nums.length == 0){
            return;
        }
        int n = nums.length;

        int insertPos = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i] != 0){
                if(i != insertPos){
                    swap(nums,i,insertPos);
                }
                insertPos++;
            }
        }
    }

    private void swap(int []nums,int a,int b){
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    public int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        int i = 0;
        for(int j=1;j<nums.length;j++){
            if(nums[j] != nums[i]){
                i++;
                nums[i] = nums[j];
            }
        }
        return i+1;
    }

    public boolean check(int[] nums) {
        if(nums == null || nums.length == 0){
            return true;
        }
        int n = nums.length,drift=0;
        for(int i=0;i<nums.length;i++ ){
            if(nums[i] > nums[i+1]%n){
                drift++;
            }
            if(drift > 0){
                return false;
            }
        }
        return true;
    }

    private int[] secondLarge(int nums[]){
        if(nums == null || nums.length == 0){
            return new int[]{};
        }

        int largest = Integer.MIN_VALUE,secondLarge = Integer.MIN_VALUE;
        int smallest = Integer.MAX_VALUE,secondSmall = Integer.MAX_VALUE;

        for(int i=0;i<nums.length;i++){
            if(largest < nums[i]){
                secondLarge = largest;
                largest = nums[i];
            } else if (secondLarge < nums[i] && nums[i] != largest) {
                secondLarge = nums[i];
            }
            if(smallest > nums[i]){
                secondSmall = smallest;
                smallest = nums[i];
            } else if (secondSmall > nums[i] && nums[i] != smallest) {
                secondSmall = nums[i];
            }
        }
        return new int[]{secondLarge,secondSmall};
    }

}