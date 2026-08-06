package practice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Test {

    public static void main(String[] args) {
        int arr[] = {1,2,3};
        List<List<Integer>> res = new ArrayList<>();
        generateOneSumk(0,arr,new ArrayList<>(),res,0,3);
        for(List<Integer> i : res){
            System.out.println(i);
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        comb2(0,candidates,target,new ArrayList<>(),res);
        return res;
    }

    private static void comb2(int start,int[] cand,int target,List<Integer>temp,List<List<Integer>> res){

        if(target == 0){
            res.add(new ArrayList<>(temp));
            return;
        }
        for(int i=start;i<cand.length;i++){
            if(i > start && cand[i] == cand[i-1]){
                continue;
            }
            if(cand[i] > target){
                break;
            }
            temp.add(cand[i]);
            comb2(i+1,cand,target-cand[i],temp,res);
            temp.remove(temp.size()-1);
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<List<Integer>> res = new ArrayList<>();
        comb(0,candidates,target,new ArrayList<>(),res);
        return res;
    }

    private static void comb(int index,int[] cand,int target,List<Integer>temp,List<List<Integer>> res){
        if(index >= cand.length){
            return;
        }
        if(target == 0){
            res.add(new ArrayList<>(temp));
            return;
        }
        if(cand[index] <= target){
            temp.add(cand[index]);
            comb(index,cand,target-cand[index],temp,res);
            temp.remove(temp.size()-1);
        }
        comb(index+1,cand,target,temp,res);
    }


    private static int countSubseq(int index,int []arr,int sum,int k){
        if(index == arr.length){
            return (sum == k) ? 1 : 0;
        }
        int pick = countSubseq(index+1,arr,sum+arr[index],k);
        int notPick = countSubseq(index+1,arr,sum,k);
        return pick+notPick;
    }

    private static boolean generateOneSumk(int index,int[] arr,
                                        List<Integer> ds,List<List<Integer>>ans,int sum,int k){
        if(index == arr.length){
            if(sum == k){
                ans.add(new ArrayList<>(ds));
                return true;
            }
            return false;
        }
        ds.add(arr[index]);
        if(generateOneSumk(index+1,arr,ds,ans,sum+arr[index],k)){
            return true;
        }
        ds.remove(ds.size()-1);
        if(generateOneSumk(index+1,arr,ds,ans,sum,k)){
            return true;
        }
        return false;
    }

    // generate all subsequne with sum k
    private static void generateAllSumk(int index,int[] arr,
                                        List<Integer> ds,List<List<Integer>>ans,int sum,int k){
        if(index == arr.length){
            if(sum == k){
                ans.add(new ArrayList<>(ds));
            }
            return;
        }
        ds.add(arr[index]);
        generateAllSumk(index+1,arr,ds,ans,sum+arr[index],k);
        ds.remove(ds.size()-1);

        generateAllSumk(index+1,arr,ds,ans,sum,k);
    }


    private static void generateAll(int index,int[] arr,List<Integer> ds,List<List<Integer>>ans){
        if(index == arr.length){
            ans.add(new ArrayList<>(ds));
            return;
        }
        ds.add(arr[index]);
        generateAll(index+1,arr,ds,ans);
        ds.remove(ds.size()-1);

        generateAll(index+1,arr,ds,ans);
    }

    private static void generateBinaryString(int n,StringBuilder sb,List<String> res){
        if(sb.length() == n){
            res.add(sb.toString());
            return;
        }
        sb.append('0');
        generateBinaryString(n,sb,res);
        sb.deleteCharAt(sb.length()-1);

        sb.append('1');
        generateBinaryString(n,sb,res);
        sb.deleteCharAt(sb.length()-1);
    }
}