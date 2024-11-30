package backtracking.practice;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String s = "abc";
        findPermutation(s,"");
    }

    public static void findPermutation(String s, String ans){

        if(s.length() == 0){
            System.out.println(ans);
            return;
        }

        for(int i=0;i<s.length();i++){
            char curr = s.charAt(i);
            String newString = s.substring(0,i) + s.substring(i+1);
            findPermutation(newString,ans + curr);
        }

    }
}
