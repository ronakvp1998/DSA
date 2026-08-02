package practice;


import java.util.*;

public class Test {

    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m-1;
        int p2 = n-1;
        int p = m+n-1;
        while (p2 >= 0){
            if(p1 >= 0 && nums1[p1] > nums2[p2]){
                nums1[p] = nums1[p1];
                p1--;
            }else{
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {

        if(nums1 == null || nums2 == null){
            return;
        }
        if(nums1.length == 0 || nums2.length == 0){
            return;
        }

        int[] temp = new int[m+n];

        int i=0,j=0,k=0;
        while (i < m && j < n){
            if(nums1[i] < nums2[j]){
                temp[k] = nums1[i];
                k++;
                i++;
            } else {
                temp[k] = nums2[j];
                k++;
                j++;
            }
        }

        while (i < m){
            temp[k] = nums1[i];
            i++;
            k++;
        }

        while (j < n){
            temp[k] = nums2[j];
            j++;
            k++;
        }

        // Copy the sorted temp array back into nums1
        for (int ii = 0; ii < m + n; ii++) {
            nums1[ii] = temp[ii];
        }
    }

    public int[][] merge(int[][] intervals) {
        if(intervals == null || intervals.length == 0){
            return new int[0][];
        }
        List<List<Integer>> res = new ArrayList<>();
        int n = intervals.length;
        Arrays.sort(intervals,(a,b) -> a[0]-b[0]);
        int currentStart = 0;
        int currentEnd = 0;
        int previousEnd = intervals[0][1];
        res.add(Arrays.asList(intervals[0][0],intervals[0][1]));
        for(int i=1;i<n;i++){
            currentStart = intervals[i][0];
            currentEnd = intervals[i][1];
            if(currentStart <= previousEnd){
                int previousStart = res.get(res.size()-1).get(0);
                int newEnd = Math.max(currentEnd,previousEnd);
                res.remove(res.size()-1);
                res.add(Arrays.asList(previousStart,newEnd));
                previousEnd = newEnd;
            }else{
                res.add(Arrays.asList(currentStart,currentEnd));
                previousEnd = currentEnd;
            }

        }
        int arr[][] = new int[res.size()][2];
        for(int i=0;i<res.size();i++){
            arr[i][0] = res.get(i).get(0);
            arr[i][1] = res.get(i).get(1);
        }
        return arr;
    }

    public int countXOR(int nums[],int k){
        if(nums == null || nums.length == 0){
            return 0;
        }
        Map<Integer,Integer> prefixXorMap = new HashMap<>();
        int currentXor = 0,count = 0 , n = nums.length;
        prefixXorMap.put(0,1);
        for(int i=0;i<n;i++){
            currentXor ^= nums[i];
            int target = currentXor ^ k;
            if(prefixXorMap.containsKey(target)){
                count += prefixXorMap.get(target);
            }
            prefixXorMap.put(currentXor,prefixXorMap.getOrDefault(currentXor,0)+1);
        }
        return count;
    }

    public int longestSubSum(int nums[]){
        if(nums == null || nums.length == 0){
            return 0;
        }
        Map<Integer,Integer> prefixHashMap = new HashMap<>();
        int currentSum=0,maxLen=0;
        for(int i=0;i<nums.length;i++){
            currentSum += nums[i];
            if(currentSum == 0){
                maxLen = Math.max(maxLen,i+1);
            }else{
                if(prefixHashMap.containsKey(currentSum)){
                    int previousIdx = prefixHashMap.get(currentSum);
                    maxLen = Math.max(maxLen,i-previousIdx);
                }else{
                    prefixHashMap.put(currentSum,i);
                }
            }
        }
        return maxLen;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums == null || nums.length < 4){
            return res;
        }
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
                    long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];
                    if(sum == target){
                        res.add(Arrays.asList(nums[i],nums[j],nums[left],nums[right]));
                        while (left < right && nums[left] == nums[left+1]){
                            left++;
                        }
                        while (left < right && nums[right] == nums[right-1]){
                            right--;
                        }
                        left++;
                        right--;
                    } else if (sum > target) {
                        right--;
                    }else{
                        left++;
                    }
                }
            }
        }
        return res;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums == null || nums.length < 3){
            return res;
        }
        int n = nums.length;
        Arrays.sort(nums);
        for(int i=0;i<n-2;i++){
            if(nums[i] > 0){
                break;
            }
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            int left=i+1;
            int right = n-1;
            while (left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if(sum == 0){
                    res.add(Arrays.asList(nums[i],nums[left],nums[right]));
                    while (left < right && nums[left] == nums[left+1]){
                        left++;
                    }
                    while (left < right && nums[right] == nums[right-1]){
                        right--;
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
        List<Integer> res = new ArrayList<>();
        if(nums == null || nums.length == 0){
            return res;
        }
        int n = nums.length;
        int cand1=Integer.MIN_VALUE,count1=0;
        int cand2=Integer.MIN_VALUE,count2=0;

        for(int i=0;i<n;i++){
            if(nums[i] == cand1){
                count1++;
            } else if (nums[i] == cand2) {
                count2++;
            }else if(count1 == 0){
                cand1 = nums[i];
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = nums[i];
                count2 = 1;
            }else{
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        int threshold = n/3;
        for(int i=0;i<n;i++){
            if(cand1 == nums[i]){
                count1++;
            } else if (cand2 == nums[i]) {
                count2++;
            }
        }

        if(count1 > threshold){
            res.add(cand1);
        }

        if(count2 > threshold){
            res.add(cand2);
        }
        return res;
    }

}