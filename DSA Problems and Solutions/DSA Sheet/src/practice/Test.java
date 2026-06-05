package practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test {

    static class ListNode{
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode ansHead = null;
        if(list1.val <= list2.val){
            ansHead.val = list1.val;
        }else{
            ansHead.val = list2.val;
        }
        ListNode ansTemp = ansHead;
        while (list1 != null && list2 !=null){
            if(list1.val <= list2.val){
                ansTemp.next = new ListNode(list1.val);
                list1 = list1.next;
            }else{
                ansTemp.next = new ListNode(list2.val);
                list2 = list2.next;
            }
        }
        while (list1 != null){
            ansTemp.next = new ListNode(list1.val);
            list1 = list1.next;
        }
        while (list2 != null){
            ansTemp.next = new ListNode(list2.val);
            list2 = list2.next;
        }
        return ansHead;
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null){
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev;
    }

    public int findMin(int[] nums) {
        int n = nums.length;
        int low=0,high = n-1;
        while (low <= high){
            int mid = low + (high-low)/2;
            if(nums[mid] > nums[high]){
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        return nums[low];
    }

    public int minEatingSpeed(int[] piles, int h) {
        int n = piles.length;
        int low = 0,high=Arrays.stream(piles).max().getAsInt();
        int maxK = 0;
        while (low < high){
            int mid = low + (high - low)/2;
            if(checkSpeed(mid,piles) >= h){
                high = mid - 1;
                maxK = mid;
            }else{
                low = mid + 1;
            }
        }
        return maxK;
    }

    private int checkSpeed(int speed,int[] piles){
        int time = 0;
        for(int pile=0;pile<piles.length;pile++){
            time += (pile/speed) + 1;
        }
        return time;
    }

    public int search(int[] nums, int target) {
        int n = nums.length;
        int low=0,high=n-1;
        while (low<=high){
            int mid = low + (high-low)/2;
            if(nums[mid] == target){
                return mid;
            }
            if(nums[low] <= nums[mid]){
                if(target >= nums[low] && target < nums[mid]){
                    high = mid-1;
                }else{
                    low = mid+1;
                }
            }else{
                if(target > nums[mid] && target <= nums[high]){
                    low = mid+1;
                }else{
                    high = mid-1;
                }
            }
        }
        return -1;
    }

    public boolean checkInclusionAlternative(String s1, String s2) {

        if(s1.length() > s2.length()){
            return false;
        }
        int []s1Count = new int[26];
        int []s2Count = new int[26];

        for(int i=0;i<s1.length();i++){
            s1Count[s1.charAt(i) - 'a']++;
            s2Count[s2.charAt(i) - 'a']++;
        }
        for(int i=0;i<s2.length()-s1.length();i++){
            if(Arrays.equals(s1Count,s2Count)){
                return true;
            }
            s2Count[s2.charAt(i) - 'a']--;
            s2Count[s2.charAt(i + s1.length()) - 'a']++;
        }
        return Arrays.equals(s1Count,s2Count);
    }

    public int characterReplacement(String s, int k) {
        if(s == null || s.length() == 0){
            return 0;
        }
        int[] map = new int[26];
        int left=0,right=0,n=s.length();
        int maxLen = 0,maxFreq=0;
        while (right < n){
            char c = s.charAt(right);
            map[c - 'a']++;
            maxFreq = Math.max(maxFreq,map[c - 'a']);
            if((right-left+1) - maxFreq > k){
                map[s.charAt(left) - 'a']--;
                left++;
            }
            maxLen = Math.max(maxLen,right-left+1);
            right++;
        }
        return maxLen;
    }

    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }
        int[] charMap = new int[128];
        int maxLen = 0;
        for(int left=0,right=0;right<s.length();right++){
            char currentChar = s.charAt(right);
            left = Math.max(left,charMap[currentChar] + 1);
            maxLen = Math.max(maxLen,right-left+1);
            charMap[currentChar] = right;
        }
        return maxLen;
    }

    public int maxProfit(int[] prices) {
        int maxProfit = 0,n = prices.length;
        int buy = prices[0];
        for(int i=1;i<n;i++){
            int profit = prices[i] - buy;
            maxProfit = Math.max(maxProfit,profit);
            if(prices[i] < buy){
                buy = prices[i];
            }
        }
        return maxProfit;
    }

    public int trapPrecomputedArrays(int[] height) {
        int n = height.length;
        if(n == 0){
            return 0;
        }
        int leftMax[] = new int[n];
        leftMax[0] = height[0];
        for(int i=1;i<n;i++){
            leftMax[i] = Math.max(height[i],leftMax[i-1]);
        }
        int rightMax[] = new int[n];
        rightMax[n-1] = height[n-1];
        for(int i=n-2;i>=0;i--){
            rightMax[i] = Math.max(height[i],rightMax[i+1]);
        }
        int trappedWater = 0;
        for(int i=0;i<n;i++){
            int waterLevel = Math.min(leftMax[i],rightMax[i]);
            trappedWater += waterLevel - height[i];
        }
        return trappedWater;
    }

    public static int maxArea(int[] height) {
        int n = height.length;
        int left=0,right=n-1;
        int maxArea=0;
        while (left < right){
            int length = Math.min(height[left],height[right]);
            int width = right - left;
            int currentArea = length * width;
            maxArea = Math.max(maxArea,currentArea);
            if(height[left] < height[right]){
                left++;
            }else{
                right--;
            }
        }
        return maxArea;

    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>>ans = new ArrayList<>();
        if(nums == null || nums.length < 3){
            return ans;
        }
        int n = nums.length;
        Arrays.sort(nums);
        for(int i=0;i<n-2;i++){
            int left=i+1, right=n-1;
            if(nums[i] > 0){
                break;
            }
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            while (left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if(sum == 0){
                    ans.add(List.of(nums[i],nums[left],nums[right]));
                    while (left < right && nums[left] == nums[left+1]){
                        left++;
                    }
                    while (left<right && nums[right] == nums[right-1]){
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                }else{
                    right--;
                }
            }
        }
        return ans;
    }
}
