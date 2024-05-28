package ArrayListCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// code 10- pair sum 2 pointer approach O(n)
public class PairSum2PointerApproach {

    public static void main(String[] args) {

        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        System.out.println(arr);
        System.out.println("Pair sum " + pairSum(arr,6));
    }

    public static String pairSum(List<Integer> arr,int target){

        int lp = 0,rp = arr.size()-1;
        while (lp<rp){
            int sum = arr.get(lp) + arr.get(rp);
//            System.out.println(sum);
            if(sum == target){
                return "lp " + lp + " ,rp " + rp;
            }else if(sum<target){
                lp++;
            } else if (sum>target) {
                rp--;
            }
        }
        return "not found";
    }
}
