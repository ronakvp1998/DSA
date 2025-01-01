package com.questions.strivers.basics.recursion;

public class Palindrome {
    public static void main(String[] args) {
        String str = "madam";
        System.out.println(palindrome(0,str,str.length()));
    }

    public static boolean palindrome(int index, String str,int n){
        if(index >= n/2) return true;
        if(str.charAt(index) != str.charAt(n-index-1)) return false;
        return palindrome(index+1,str,n);
    }

}
