package practice;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

    }

    private static List<String> allSubsets(String str){
        List<String> res = new ArrayList<>();
        generateSubsets(str,0,"",res);
        return res;
    }

    private static void generateSubsets(String str,int start,String current,List<String>res){
        res.add(current);
        for(int i=start;i<str.length();i++){
            generateSubsets(str,i+1,current+str.charAt(i),res);
        }
    }

    private static List<String> allSub(String str){
        List<String> res = new ArrayList<>();
        generateSubseq(str,0,"",res);
        return res;
    }

    private static void generateSubseq(String str,int index,String current,List<String>res){
        if(index == str.length()){
            res.add(current);
            return;
        }
        generateSubseq(str,index+1,current+str.charAt(index),res);
        generateSubseq(str,index+1,current,res);
    }

}