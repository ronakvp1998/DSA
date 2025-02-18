package com.questions.strivers.slidingwind2pointer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestSubstring {

    public static void main(String[] args) {
        String s = "cadbzabcd";
        System.out.println(longestSubstring2(s));
    }

    // approach 2 sliding window
    public static int longestSubstring2(String s){
        int maxLen = 0, left = 0,right = 0,n = s.length();
        Map<Character,Integer> hash = new HashMap<>();
        while (right < n){
            Integer res = hash.get(s.charAt(right));
            if(res != null){
                if(res >= left){
                    left = res + 1;
                }
            }
            int len = right-left+1;
            maxLen = Math.max(len,maxLen);
            hash.put(s.charAt(right),right);
            right++;
        }
        return maxLen;
    }


    // approach 1 generate all the substrings
    public static int longestSubstring1(String s){
        int n=s.length(), maxLen=0;
        for (int i=0;i<n;i++){
            int [] map = new int[256];
            Arrays.fill(map,0);
            for(int j=i;j<n;j++){
                if(map[ s.charAt(j)] == 1){
                    break;
                }
                int len = j-i+1;
                maxLen = Math.max(maxLen,len);
                map[s.charAt(j)] = 1;
            }
        }
        return maxLen;
    }
}
