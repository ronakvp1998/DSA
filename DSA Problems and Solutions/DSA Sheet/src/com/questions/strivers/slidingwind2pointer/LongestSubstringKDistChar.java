package com.questions.strivers.slidingwind2pointer;

// Longest substring having K district characters

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringKDistChar {

    public static void main(String[] args) {
        String s = "aaabbccd";
        int k=2;
        System.out.println(longestSubKDist2(s,k));
    }

    // approach 2 using sliding window
    public static int longestSubKDist2(String s, int k){
        int maxLen=0,l=0,r=0,n=s.length();
        Map<Character,Integer> map = new HashMap<>();
        while (r < n){
            char c = s.charAt(r);
            map.put(c,map.getOrDefault(c,0)+1);
            while (map.size() > k){
                char c2 = s.charAt(l);
                map.put(c2,map.get(c2)-1);
                if(map.get(c2) == 0){
                    map.remove(c2);
                }
                l++;
            }
            if(map.size() <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r++;
        }
        return maxLen;
    }

    // approach 1 brute force approach
    public static int longestSubKDist(String s, int k){
        int maxLen=0,n=s.length();
        Map<Character,Integer> map = new HashMap<>();
        for(int i=0;i<n;i++){
            map.clear();
            for(int j=i;j<n;j++){
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
