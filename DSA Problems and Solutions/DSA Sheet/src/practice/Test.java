package practice;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        String  s = "LVIII"     ;
        System.out.println(romanToInt(s));
    }

    public static int romanToInt(String s) {
        int total = 0,prev = 0;
        for(int i=s.length()-1;i>=0;i--){
            int current = 0;
            switch (s.charAt(i)){
                case 'I': current = 1; break;
                case 'V': current = 5; break;
                case 'X': current = 10; break;
                case 'L': current = 50; break;
                case 'C': current = 100; break;
                case 'D': current = 500; break;
                case 'M': current = 1000; break;
            }
            if(current < prev){
                total -= current;
            }else {
                total += current;
            }
            prev = current;
        }
        return total;

    }

    public static int maxDepth(String s) {
        int maxDept = 0;
        int currentDept = 0;
        for (Character c : s.toCharArray()){
            if(c == '('){
                currentDept++;
            } else if (c == ')') {
                currentDept--;
            }
            maxDept = Math.max(maxDept,currentDept);
        }
        return maxDept;
    }

    public static String frequencySort(String s) {
        StringBuilder sb = new StringBuilder();
        HashMap<Character,Integer> freq = new HashMap<>();
        for(int i=0;i<s.length();i++){
            freq.put(s.charAt(i),freq.getOrDefault(s.charAt(i),0) + 1);
        }
        freq.entrySet().stream().sorted((a,b) -> b.getValue() - a.getValue()).forEach(i -> {
            for(int j=0;j<i.getValue();j++){
                sb.append(i.getKey());
            }
        });

        return sb.toString();
    }
}
