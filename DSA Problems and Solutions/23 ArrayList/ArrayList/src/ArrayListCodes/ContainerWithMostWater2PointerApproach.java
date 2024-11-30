package ArrayListCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// code 8-> container with most water 2 pointer approach
public class ContainerWithMostWater2PointerApproach {

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList( 1,8,6,2,5,4,8,3,7));
        System.out.println("Most water " + mostWater(arr));
    }

    public static int mostWater(List arr) {
        int lp=0;
        int rp = arr.size()-1;
        int maxWater = 0;
        int maxlp = 0;
        int maxrp = 0;
        while (lp<rp && lp<arr.size() && rp<arr.size()){
            int height = Math.min((int)arr.get(lp),(int)arr.get(rp));
            int width = rp-lp;
            int currWater = height * width;
            if(currWater>maxWater){
                maxWater = currWater;
                maxlp = lp;
                maxrp = rp;
            }
            if((int)arr.get(lp) < (int)arr.get(rp)){
                lp++;
            }else{
                rp++;
            }
        }
        System.out.println("maxlp " +maxlp + " maxrp " + maxrp );
        return maxWater;
    }
}
