package ArraysCode;

import java.util.Arrays;

public class LinearSearchCode {
    public static void main(String[] args) {
        int num [] = {2,3,4,10,5,6,7};
        System.out.println(linearSearch(num,33));
        System.out.println(Arrays.toString(num));
    }
    public static int linearSearch(int num[] , int key){

        for(int i=0;i<num.length;i++){
            if(num[i] == key){
                return i;
            }
        }
        return -1;
    }
}
