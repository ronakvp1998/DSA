package com.questions.practice2;

public class Test {
    public static void main(String[] args) {
        String str = "aababba";
        int k = 2;
        System.out.println(numSubstr(str,k));
    }

    public static int numSubstr(String str,int k){
        int maxLen = 0,n=str.length();
        for(int i=0;i<n;i++){
            int map[] = new int [26];
            int maxFreq = 0;
            for(int j=i;j<n;j++){
                map[str.charAt(j) - 'a']++;
                maxFreq = Math.max(maxFreq, map[str.charAt(j) - 'a']);
                int changes = (j-i+1) - maxFreq;
                if(changes <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }
}
