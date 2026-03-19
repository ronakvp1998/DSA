package com.questions.practice.strings;

public class DemoString {

    public static String rec (int n){
        String current = "1";

        // Iteratively build the sequence up to n
        for (int i = 2; i <= n; i++) {
            current = applyRle(current);
        }

        return current;
    }

    private static String applyRle(String s){
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for(int i=1;i<s.length();i++){
            if(s.charAt(i) == s.charAt(i-1)){
                count++;
            }else{
                sb.append(count).append(s.charAt(i-1));
                count=1;
            }
        }
        sb.append(count).append(s.charAt(s.length()-1));
        return sb.toString();
    }

    public static void main(String[] args) {

    }
}
