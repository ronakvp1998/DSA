package com.questions.strivers.slidingwind2pointer.length;
/* L8 424. Longest Repeating Character Replacement
        You are given a string s and an integer k.
        You can choose any character of the string and change it to any other uppercase English character.
        You can perform this operation at most k times.
        Return the length of the longest substring containing the same letter you can get after performing the above operations.
        Example 1: Input: s = "ABAB", k = 2 Output: 4
        Explanation: Replace the two 'A's with two 'B's or vice versa.
        Example 2: Input: s = "AABABBA", k = 1 Output: 4
        Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
        The substring "BBBB" has the longest repeating letters, which is 4.
        There may exists other ways to achieve this answer too. */

public class LongestRepeatCharReplace {

    public static void main(String[] args) {
        String s = "AABABBA";
        int k = 2;
        System.out.println(longestRepeatCharReplace2(s,k));
    }

    // approach 2
    private static int longestRepeatCharReplace2(String s ,int k){
        int l=0,r=0,maxLen=0,maxFreq=0;
        int hash[] = new int[26];
        while (r < s.length()){
            hash[s.charAt(r) - 'A']++;
            maxFreq = Math.max(maxFreq,hash[s.charAt(r) - 'A']);
            while ((r-l+1) - maxFreq > k){
                hash[s.charAt(l) - 'A']--;
                maxFreq = 0;
                for(int i=0;i<26;i++){
                    maxFreq = Math.max(maxFreq,hash[i]);
                }
                l = l + 1;
            }
            if((r-l+1) - maxFreq <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r++;
        }
        return maxLen;
    }


    // approach 1
    private static int longestRepeatCharReplace(String s ,int k){
        int maxLen = 0,n=s.length();
        for(int i=0;i<n;i++){
            int hash[] = new int[26];
            int maxFreq = 0;
            for(int j=i;j<n;j++){
                hash[s.charAt(j) - 'A'] += 1;
                maxFreq = Math.max(maxFreq,hash[s.charAt(j) - 'A']);
                int changes = (j-i+1) - maxFreq;
                if (changes <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                }else {
                    break;
                }
            }
        }
        return maxLen;
    }
}
