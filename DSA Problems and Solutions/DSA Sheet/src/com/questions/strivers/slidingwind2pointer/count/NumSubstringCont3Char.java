package com.questions.strivers.slidingwind2pointer.count;

/* L7   Number of Substrings Containing All Three Characters
        Given a string s consisting only of characters a, b and c.
        Return the number of substrings containing at least one occurrence of all these characters a, b and c.
        Example 1: Input: s = "abcabc" Output: 10
        Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again).
        Example 2: Input: s = "aaacb" Output: 3
        Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb".
        Example 3: Input: s = "abc" Output: 1
*/
import java.util.Arrays;

public class NumSubstringCont3Char {
    public static void main(String[] args) {
        String s = "bbacba";
        System.out.println(numSubStringCont2(s));
    }

    // approach3
    public static int numSubStringCont3(String s){
        int lastSeen[] = new int [3];
        int n = s.length();
        Arrays.fill(lastSeen,-1);
        int count=0;
        for(int i=0;i<n;i++){
            lastSeen[s.charAt(i) - 'a'] = i;
            if(lastSeen[0] != -1 && lastSeen[1] != -1 && lastSeen[2] != -2){
                count = count + (1+Math.min(Math.min(lastSeen[0],lastSeen[1]),lastSeen[2]));
            }
        }
        return count;
    }

    // approach 2 bruteforce approach
    public static int numSubStringCont2(String s){
        int count=0,n=s.length();
        for(int i=0;i<n;i++){
            int arr[] = new int[3];
            for(int j=i;j<n;j++){
                arr[s.charAt(j) - 'a'] = 1;
                if(arr[0] + arr[1] + arr[2] == 3){
                    count = count + (n-j);
                    break;
                }
            }
        }
        return count;
    }

    // approach 1 bruteforce approach
    public static int numSubStringCont1(String s){
        int count=0;
        for(int i=0;i<s.length();i++){
            int arr[] = new int[3];
            for(int j=i;j<s.length();j++){
                arr[s.charAt(j)-'a'] = 1;
                if(arr[0] + arr[1] + arr[2] == 3){
                    count = count + 1;
                }
            }
        }
        return count;
    }
}
