package com.questions.practice.strings;

public class DemoString {

    public static boolean test (String s,String goal){

        char a = goal.charAt(0);
        int i = s.lastIndexOf(a);


        StringBuilder sb = new StringBuilder();
        sb.append(s.substring(i,s.length()));
        sb.append(s.substring(0,i));
        System.out.println(sb);
        return sb.toString().equals(goal);
    }

    public static void main(String[] args) {
        String s = "defdefdefabcabc", goal = "defdefabcabcdef";
        System.out.println(test(s,goal));
    }
}
