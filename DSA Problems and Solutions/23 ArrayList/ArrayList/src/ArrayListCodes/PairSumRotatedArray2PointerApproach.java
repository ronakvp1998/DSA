package ArrayListCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// code 11- pair sum for rotated array using 2 pointer approach
public class PairSumRotatedArray2PointerApproach {


    public static void main(String[] args) {

        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(11,15,6,8,9,10));
        System.out.println(arr);
        System.out.println("Pair sum " + pairSum(arr,16));
    }

    public static String pairSum(List<Integer> arr, int target){

        int pivot = -1;
        for(int i=0;i<arr.size();i++){
            if(arr.get(i) > arr.get(i+1)){ // breaking point
                pivot = i;
                break;
            }
        }

        int lp = pivot+1;
        int rp = pivot;
        while (lp!=rp){
            int sum = arr.get(lp) + arr.get(rp);
//            System.out.println(sum);
            if(sum == target){
                return "lp " + lp + " ,rp " + rp;
            }else if(sum<target){
                lp = (lp+1)%arr.size();
            } else if (sum>target) {
                rp = ((arr.size()) + rp -1)%arr.size();
            }
        }
        return "not found";
    }
}
