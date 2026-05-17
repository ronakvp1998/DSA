package ArrayListCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// code 8 Container with most water Brute Force Approach
public class ContainerWithMostWater {

    public static void main(String[] args) {

       ArrayList<Integer>arr = new ArrayList<>(Arrays.asList( 1,8,6,2,5,4,8,3,7));
        System.out.println("Most water " + mostWater(arr));
    }

    public static int mostWater(List arr){

        int maxWater = 0;
        int maxI = 0;
        int maxJ = 0;
        for(int i=0;i<arr.size()-1;i++){
            for(int j=i+1;j<arr.size();j++){
                int width = j-i;
                int height = Math.min((int)arr.get(i),(int)arr.get(j));
                if(maxWater<(width*height)) {
                    maxWater = width*height;
                    maxI = i;
                    maxJ = j;
                }
            }
        }
        System.out.println("i=" + maxI + " j="+maxJ );
        return maxWater;
    }
}
