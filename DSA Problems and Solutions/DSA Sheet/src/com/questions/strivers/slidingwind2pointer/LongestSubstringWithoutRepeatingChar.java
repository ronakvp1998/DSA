package com.questions.strivers.slidingwind2pointer;
/* L3. Longest Substring Without Repeating Characters
        Hint
        Given a string s, find the length of the longest substring without duplicate characters.
        Example 1: Input: s = "abcabcbb" Output: 3
        Explanation: The answer is "abc", with the length of 3.
        Example 2: Input: s = "bbbbb" Output: 1
        Explanation: The answer is "b", with the length of 1.
        Example 3: Input: s = "pwwkew" Output: 3
        Explanation: The answer is "wke", with the length of 3.
        Notice that the answer must be a substring, "pwke" is a subsequence and not a substring. */

import java.util.Arrays;

public class LongestSubstringWithoutRepeatingChar {
    public static void main(String[] args) {
        String s = "cadbzabcd";
//        System.out.println(longestSubstringWithoutRepeatingChar1(s));
        System.out.println(longestSubstringWithoutRepeatingChar2(s));
    }

    // Approach2 using sliding window
    public static int longestSubstringWithoutRepeatingChar2(String s){
        int map[] = new int [256];
        int l=0,r=0,maxLen=0,n=s.length();
        while (r < n){
            if(map[s.charAt(r)] != -1){
                if(map[s.charAt(r)] >= l){
                    l = map[s.charAt(r)] + 1;
                }
            }
            int len = r - l + 1;
            maxLen = Math.max(maxLen, len);
            map[s.charAt(r)] = r ;
            r++;
        }
        return maxLen;
    }

    // Approach1 generate all substring
    public static int longestSubstringWithoutRepeatingChar1(String s){
        int n = s.length(),maxLen = Integer.MIN_VALUE;
        for(int i=0;i<s.length();i++){
            int map[] = new int[256];
            for(int j=i;j<s.length();j++){
                if(map[s.charAt(j)] == 1){
                    break;
                }
                int len = j - i + 1;
                maxLen = Math.max(maxLen,len);
                map[s.charAt(j)] = 1;
            }
        }
        return maxLen;
    }

}
