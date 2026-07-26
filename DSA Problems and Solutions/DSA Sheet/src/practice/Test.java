package practice;

import strivers.arrays.medium.MaxSubArraySumPrint;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test {

    public static int[] phase3AlternativeHashing(int[] nums) {
        int n = nums.length;
        int [] hash = new int[n+1];
        int repeating = -1,missing = -1;
        for(int i=0;i<n;i++){
            hash[nums[i]]++;
        }
        for(int i=1;i<=n;i++){
            if(hash[i] == 2){
                repeating = i;
            } else if (hash[i] == 0) {
                missing = i;
            }
            if(repeating != -1 && missing != -1){
                break;
            }
        }
        return new int[]{repeating,missing};
    }

    public int[][] merge(int[][] intervals) {
        if(intervals == null || intervals.length <= 1){
            return intervals;
        }
        Arrays.sort(intervals,(a,b) -> Integer.compare(a[0],b[0]));
        List<int[]> merged = new ArrayList<>();
        int [] currentInterval = intervals[0];
        merged.add(currentInterval);
        for(int [] interval : intervals){
            int currentEnd = currentInterval[1];
            int nextBegin = interval[0];
            int nextEnd = interval[1];
            if(currentEnd >= nextBegin){
                currentInterval[1] = Math.max(currentEnd,nextEnd);
            }else{
                currentInterval = interval;
                merged.add(currentInterval);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }

    public int countSubarray(int arr[],int k){
        int n = arr.length;
        int xr=0,count=0;
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(0,1);
        for(int i=0;i<n;i++){
            xr ^= arr[i];
            int target = xr ^ k;
            if(map.containsKey(target)){
                count += map.get(target);
            }
            map.put(xr,map.getOrDefault(xr,0) + 1);
        }
        return count;
    }

    public int longestSubarray(int arr[],int n){
        HashMap<Integer,Integer> map = new HashMap<>();
        int maxLen=0,currentSum=0;
        for(int i=0;i<arr.length;i++){
            currentSum += arr[i];
            if(currentSum == 0){
                maxLen = i + 1;
            }else{
                if(map.containsKey(currentSum)){
                    int prevInd = map.get(currentSum);
                    maxLen = Math.max(maxLen,i-prevInd);
                }else{
                    map.put(currentSum,i);
                }
            }
        }
        return maxLen;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 4) return res;
        int n = nums.length;
        Arrays.sort(nums);
        for(int i=0;i<n-3;i++){
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            for(int j=i+1;j<n-2;j++){
                if(j > i+1 && nums[j] == nums[j-1]){
                    continue;
                }
                int left = j+1;
                int right = n-1;
                while (left < right){
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    if(sum == target){
                        res.add(List.of(nums[i],nums[j],nums[left],nums[right]));
                        while (left < right && nums[left] == nums[left+1]){
                            left++;
                        }
                        while (left < right && nums[right] == nums[right-1]){
                            right--;
                        }
                        left++;
                        right--;
                    } else if (sum < target) {
                        left++;
                    }else{
                        right--;
                    }
                }
            }
        }

        return res;
    }


    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;
        int n = nums.length;
        Arrays.sort(nums);
        for(int i=0;i<n-2;i++){
            if(nums[i] > 0){
                break;
            }
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            int left = i+1;
            int right = n-1;
            while (left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if(sum == 0){
                    res.add(List.of(nums[i],nums[left],nums[right]));
                    while (left < right && nums[right] == nums[right-1]){
                        right--;
                    }
                    while (left < right && nums[left] == nums[left+1]){
                        left++;
                    }
                    left++;
                    right--;
                } else if (sum > 0) {
                    right--;
                }else{
                    left++;
                }
            }
        }

        return res;
    }

    public List<Integer> majorityElement(int[] nums) {
        int cand1=Integer.MIN_VALUE,count1=0,cand2=Integer.MIN_VALUE,count2=0;
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;
        int n=nums.length;
        for(int i=0;i<n;i++){
            if(nums[i] == cand1){
                count1++;
            } else if (nums[i] == cand2) {
                count2++;
            } else if (count1 == 0) {
                cand1 = nums[i];
                count1 = 1;
            }else if(count2 == 0){
                cand2 = nums[i];
                count2 = 1;
            }else{
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        for(int i=0;i<n;i++){
            if(nums[i] == cand1){
                count1++;
            } else if (nums[i] == cand2) {
                count2++;
            }
        }
        int the = n/3;
        if(count1 > the){
            res.add(cand1);
        }
        if(count2 > the){
            res.add(cand2);
        }
        return res;
    }

}