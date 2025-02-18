package com.questions.practice.slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringRepchar {

    public static void main(String[] args) {
        String s = "cadbzabcd";
        System.out.println(longestSubRep2(s));
    }

    // Approach 2 longest substring without repeating char using sliding window
    public static int longestSubRep2(String s){
        Map<Character,Integer> map = new HashMap<>();
        int l=0,r=0,maxLen=0,n=s.length();
        while (r < n){
            Integer res = map.get(s.charAt(r));
            if(res != null){
                if(res >= l){
                    l = res + 1;
                }
            }
            int len = r-l+1;
            maxLen = Math.max(maxLen,len);
            map.put(s.charAt(r),r);
            r++;
        }
        return maxLen;
    }


    // Approach 1 longest substring without repeating char
    public static int longestSubRep(String s){
        int maxLen = 0;
        for(int i=0;i<s.length();i++){
            int hash[] = new int[256];
            for(int j=i;j<s.length();j++){
                if(hash[s.charAt(j)] == 1){
                    break;
                }
                int len = j-i+1;
                maxLen = Math.max(maxLen,len);
                hash[s.charAt(j)] = 1;
            }
        }
        return maxLen;
    }
}
