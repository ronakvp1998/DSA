package com.questions.practice.slidingwindow.old;

public class LongestRepeatCharReplace {
    public static void main(String[] args) {
        String s = "AABABBA";
        int k = 2;
        System.out.println(longestRepeatCharReplace2(s,k));
    }

    public static int longestRepeatCharReplace2(String s, int k){
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
                l = l+1;
            }
            if((r-l+1) - maxFreq <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r++;
        }
        return maxLen;
    }

    public static int longestRepeatCharReplace1(String s, int k){
        int maxLen = 0,n=s.length();
        for(int i=0;i<n;i++){
            int hash[] = new int[26];
            int maxFreq = 0;
            for(int j=i;j<n;j++){
                hash[s.charAt(j) - 'A']++;
                maxFreq = Math.max(maxFreq,hash[s.charAt(j) - 'A']);
                int changes = (j-i+1) - maxFreq;
                if(changes <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                }else {
                    break;
                }
            }
        }
        return maxLen;
    }
}
