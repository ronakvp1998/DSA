package practice;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    private static final String[] KEYPAD = {
            "",     // 0
            "",     // 1
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
    };

    public List<String> letterCombinationsForLoop(String digits) {
        List<String> res = new ArrayList<>();
        if(digits == null || digits.isEmpty()){
            return res;
        }
        solveForLoop(0,digits,new StringBuilder(),res);
        return res;
    }

    private void solveForLoop(int index,String digits,StringBuilder current,List<String> res){
        if(index == digits.length()){
            res.add(current.toString());
            return;
        }
        String letter = KEYPAD[digits.charAt(index) - '0'];;
        for(int i=0;i<letter.length();i++){
            current.append(letter.charAt(i));
            solveForLoop(index+1,digits,current,res);
            current.deleteCharAt(current.length()-1);
        }
    }

}
