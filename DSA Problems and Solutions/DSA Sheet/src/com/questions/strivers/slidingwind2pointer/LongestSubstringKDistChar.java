package com.questions.strivers.slidingwind2pointer;

// Longest substring having K district characters

/*Longest Substring With At Most K Distinct Characters
        Examples: Input : s = "aababbcaacc" , k = 2 Output : 6
        Explanation : The longest substring with at most two distinct characters is "aababb".
        The length of the string 6. Input : s = "abcddefg" , k = 3 Output : 4
        Explanation : The longest substring with at most three distinct characters is "bcdd".
        The length of the string 4. */
import java.util.HashMap;
import java.util.Map;

public class LongestSubstringKDistChar {

    public static void main(String[] args) {
        String s = "aaabbccd";
        int k=2;
        System.out.println(longestSubKDist2(s,k));
//        System.out.println(longestSubKDist1(s,k));

    }

    // approach 2 using sliding window
    public static int longestSubKDist2(String s, int k){
        int maxLen=0,left=0,right=0,n=s.length();
        Map<Character,Integer>map = new HashMap<>();
        while (right < n){
            map.put(s.charAt(right),map.getOrDefault(s.charAt(right),0) + 1);
            while (map.size() > k){
                map.put(s.charAt(left),map.get(s.charAt(left)) - 1);
                if(map.get(s.charAt(left)) == 0){
                    map.remove(s.charAt(left));
                }
                left++;
            }
            if(map.size() <= k){
                maxLen = Math.max(maxLen,right - left +1);
            }
            right++;
        }
        return maxLen;
    }

    // approach 1 brute force approach
    // find all substring & use map DS & maintain the frequency
    public static int longestSubKDist1(String s, int k){
        int maxLen = 0, n = s.length();
        Map<Character,Integer> map = new HashMap<>();
        for(int i=0;i<s.length();i++){
            map.clear();
            for(int j=i;j<s.length();j++){
                map.put(s.charAt(j),map.getOrDefault(s.charAt(j),0)+1);
                if(map.size() <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }
}
