package practice;

import strivers.arrays.medium.MaxSubArraySumPrint;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test {

    public int[] productExceptSelf(int[] nums) {
        if(nums == null || nums.length == 0){
            return null;
        }
        int n = nums.length;
        int left[] = new int[n];
        int right[] = new int[n];
        int res[] = new int [n];
        left[0] = 1;
        right[n-1] = 1;
        for(int i=1;i<n;i++){
            left[i] = left[i-1] * nums[i-1];
        }
        for(int i=n-2;i>=0;i--){
            right[i] = right[i+1] * nums[i+1];
        }
        for(int i=0;i<n;i++){
            res[i] = left[i] * right[i];
        }
        return res;
    }

    public int findDuplicate(int[] nums) {
        if (nums == null || nums.length <= 1) return -1;

        int slow=nums[0];
        int fast=nums[0];
        do {
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

    public boolean isValidSudoku(char[][] board) {
        boolean row[][] = new boolean[9][9];
        boolean col[][] = new boolean[9][9];
        boolean box[][] = new boolean[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j] == '.'){
                    continue;
                }
                int val = board[i][j] - '1';
                int boxKey = (i/3)*3+(j/3);
                if (row[i][val] || col[j][val] || box[boxKey][val]){
                    return false;
                }
                row[i][val] = true;
                col[j][val] = true;
                box[boxKey][val] = true;
            }
        }
        return true;
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Long> map = Arrays.stream(nums).boxed()
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingLong(map::get));

        for(Map.Entry<Integer,Long> i : map.entrySet()){
            pq.offer(i.getKey());
            if(pq.size() > k){
                pq.poll();
            }
        }
        int res[] = new int[k];
        for(int i=0;i<k;i++){
            res[i] = pq.poll();
        }
        return res;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> map = new HashMap<>();
        if(strs == null || strs.length == 0){
            return new ArrayList<>();
        }
        int n = strs.length;
        for(int i=0;i<n;i++){
            String s = strs[i];
            int freq[] = new int[26];
            for(char c : s.toCharArray()){
                freq[c - 'a']++;
            }
            String key = Arrays.toString(freq);
            map.computeIfAbsent(key,k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        Map<Integer,Integer> prefixSumMap = new HashMap<>();
        prefixSumMap.put(0,1);
        int count=0,runningSum=0;
        for(int num: nums){
            runningSum += num;
            int target = runningSum - k;

        }
        return count;
    }

    public int longestConsecutive(int[] nums) {
        if(nums == null || nums.length==0){
            return 0;
        }
        int n = nums.length;
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<n;i++) {
            set.add(nums[i]);
        }
        int maxCount = 0;
        for(int i : set){
            if(!set.contains(i-1)){
                int count = 1;
                int currentNum = i;
                while (set.contains(currentNum+1)){
                    count++;
                    currentNum++;
                }
                maxCount = Math.max(maxCount,count);
            }
        }
        return maxCount;
    }

    public List<Integer> findLeader(int[] arr) {
        List<Integer> res= new ArrayList<>();
        int n = arr.length;
        if (n == 0) {
            return res;
        }
        int rightMax = arr[n-1];
        res.add(rightMax);
        for(int i=n-2;i>=0;i--){
            int a = arr[i];
            if(a >= rightMax){
                res.add(a);
                rightMax = a;
            }
        }
        Collections.reverse(res);
        return res;
    }

    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n-2;
        while (i >= 0 && nums[i] >= nums[i+1]){
            i--;
        }
        if(i > 0){

        }
        reverse(nums,i+1,nums.length-1);
    }

    private void reverse(int nums[],int start,int end){
        while (start <= end){
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        boolean [] vis = new boolean[n];
        List<List<Integer>> res = new ArrayList<>();
        permuatation(nums,vis,res,new ArrayList<>());
        return res;
    }

    private void permuatation(int []nums,boolean[] vis,List<List<Integer>> res,List<Integer> currentList){
        if(currentList.size() == nums.length){
            res.add(new ArrayList<>(currentList));
                return;
        }
        for(int i=0;i<nums.length;i++){
            if(!vis[i]){
                vis[i] = true;
                currentList.add(nums[i]);
                permuatation(nums,vis,res,currentList);
                currentList.remove(currentList.size()-1);
                vis[i] = false;
            }
        }
    }

    public int[] rearrangeArray(int[] nums) {

        int n = nums.length;
        int res[] = new int[n];
        int pos=0,neg=1;
        for(int i=0;i<n;i++){
            if(nums[i] > 0){
                res[pos] = nums[i];
                pos += 2;
            }else{
                res[neg] = nums[i];
                neg += 2;
            }
        }
        return res;
    }

    public int maxProfit(int[] prices) {
        int buy = prices[0];
        int maxProfit = 0;
        for(int i=1;i<prices.length;i++){
            int profit = prices[i] - buy;
            if(prices[i] < buy){
                buy = prices[i];
            }else{
                maxProfit = Math.max(maxProfit,profit);
            }
        }
        return maxProfit;
    }

    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length==0) {
            return -1;
        }
        int n = nums.length;
        int maxSum = Integer.MIN_VALUE,currentSum=0;
        for (int i=0;i<n; i++) {
            currentSum += nums[i];
            maxSum = Math.max(currentSum, maxSum);
            if(currentSum < 0){
                currentSum = 0;
            }
        }
        return maxSum;
    }

    public int majorityElement(int[] nums) {
        if(nums == null || nums.length==0) {
            return -1;
        }
        int majorityElement=nums[0],count=1;
        for(int i=1;i<nums.length;i++){
            if(nums[i] == majorityElement){
                count++;
            }else{
                count--;
            }
            if(count < 0){
                majorityElement = nums[i];
                count = 1;
            }
        }
        return majorityElement;
    }

    public void sortColors(int[] nums) {
        int n = nums.length;
        int low=0,mid=0,high=n-1;
        while (mid <= high){
            if(nums[mid] == 0){
                swap(nums,low,mid);
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                mid++;
            }else{
                swap(nums,mid,high);
                high--;
            }
        }
    }

    private void swap(int nums[],int a,int b){
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        Map<Integer,Integer> map = new HashMap<>();

        for(int i=0;i<n;i++){
            int diff = target - nums[i];
            if(map.containsKey(diff)){
                return new int[]{map.get(diff),i};
            }
            map.put(nums[i],i);
        }
        return new int[]{-1,-1};
    }

}