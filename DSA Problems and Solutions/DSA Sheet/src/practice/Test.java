package practice;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public int characterReplacement(String s, int k) {
        int left=0,right=0,n=s.length(),maxFreq = 0,maxLen=0;
        int freq[] = new int[26];
        while (right < n){
            char c = s.charAt(right);
            freq[c-'A']++;
            maxFreq = Math.max(maxFreq,freq[c-'A']);
            while ((right-left+1) - maxFreq > k){
                freq[s.charAt(left)-'A']--;
                left++;
            }
            maxLen = Math.max(maxLen,right-left+1);
            right++;
        }
        return maxLen;
    }

    public int totalFruitOptimal(int[] fruits) {
        int left =0, right=0,maxFruits = 0,n=fruits.length;
        HashMap<Integer,Integer> mapCount = new HashMap<>();
        while (right < n){
            mapCount.put(fruits[right],
                    mapCount.getOrDefault(fruits[right],0)+1);
            while (mapCount.size() > 2){
                mapCount.put(fruits[left],mapCount.getOrDefault(fruits[left],0)-1);
                if(mapCount.get(fruits[left]) == 0){
                    mapCount.remove(fruits[left]);
                }
                left++;
            }
            maxFruits = Math.max(maxFruits,right-left+1);
            right++;
        }
        return maxFruits;
    }

    public int longestOnes(int[] nums, int k) {
        int n=nums.length,l=0,r=0,maxCount = 0,countZeros=0;
        while (r < n){
            if(nums[r] == 0){
                countZeros++;
            }
            while (countZeros > k){
                l++;
                if(nums[l] == 0){
                    countZeros--;
                }
            }
            maxCount = Math.max(maxCount,r - l +1);
            r++;
        }
        return maxCount;
    }

    public int lengthOfLongestSubstringOptimal(String s) {
        if(s == null || s.isEmpty()){
            return 0;
        }
        int []charMap = new int[128];
        int maxLen=0,left=0,right=0,n=s.length();
        while (right < n){
            char currentChar = s.charAt(right);
            left = Math.max(left,charMap[currentChar]);
            maxLen = Math.max(maxLen,right-left+1);
            charMap[currentChar] = right + 1;
            right++;
        }
        return maxLen;
    }

    public static int minWindow(int target,int []nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        int l=0,r=0,sum = 0;
        int minLen = Integer.MAX_VALUE;
        int n = nums.length;
        while (r < n){
            sum += nums[r];
            while (sum >= target){
                minLen = Math.min(minLen,r-l+1);
                sum-=nums[l];
                l++;
            }
            r++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    // longest Subarray or substring where sum <= k
    private static int conditionWindow(int arr[] ,int k){
        int l=0,r=0,sum=0,maxLen=0,n=arr.length;
        while (r < n){
            sum = sum + arr[r];
            while (sum > k){
                sum = sum - arr[l];
                l++;
            }
            if(sum <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r++;
        }
        return maxLen;
    }

    private static int constantWindow(int arr[],int k){
        int l=0,r=k,sum=0,maxSum=0,n=arr.length;
        for(int i=0;i<r;i++){
            sum = sum + arr[i];
        }
        maxSum = sum;
        while (r < n-1){
            sum = sum - arr[l];
            l++;
            r++;
            sum = sum + arr[r];
            maxSum = Math.max(maxSum,sum);
        }
        return maxSum;
    }

}
