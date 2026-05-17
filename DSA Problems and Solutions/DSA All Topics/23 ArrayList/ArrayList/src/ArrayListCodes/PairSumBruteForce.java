package ArrayListCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// code 9- pair sum brute force approach O(n^2)
public class PairSumBruteForce {

    public static void main(String[] args) {

        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        System.out.println("Most water " + pairSum(arr,5));
    }

    public static String pairSum(List<Integer> arr,int target){

        for(int i=0;i<arr.size()-1;i++){
            for(int j=i+1;j<arr.size();j++){
                int sum = arr.get(i) + arr.get(j);
                if(sum == target){
                    System.out.println(sum);
                    return "(" + i +"," + j + ")";
                }
            }
        }
        return "not found";
    }
}
