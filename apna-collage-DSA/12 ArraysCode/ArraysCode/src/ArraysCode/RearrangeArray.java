package ArraysCode;

import java.util.Arrays;

public class RearrangeArray {

//    Two Pointer Approach: The idea is to solve this problem with constant space and linear time is by using a two-pointer or two-variable approach where we simply take two variables like left and right which hold the 0 and N-1 indexes. Just need to check that :
//
//    Check If the left and right pointer elements are negative then simply increment the left pointer.
//    Otherwise, if the left element is positive and the right element is negative then simply swap the elements, and simultaneously increment and decrement the left and right pointers.
//            Else if the left element is positive and the right element is also positive then simply decrement the right pointer.
//    Repeat the above 3 steps until the left pointer ? right pointer.
    public static void rearrangeArray(int[] arr,int left,int right){
        // Loop to iterate over the
        // array from left to the right
        while(left <= right){
            // Condition to check if the left and the right elements are negative
            if(arr[left]<0 && arr[right]<0){
                left++;
            }

            // Condition to check if the left // pointer element is positive and // the right pointer element is negative
            else if (arr[left]>0 && arr[right]<0) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }

            // Condition to check if both the // elements are positive
            else if(arr[left] > 0 && arr[right] > 0){
                right--;
            }else{
                left++;
                right--;
            }
        }

    }
    public static void main(String[] args) {
        int[] inputArray = {1, -2, 3, -4, 5, -6, 7, -8};

        System.out.println("Original array: " + Arrays.toString(inputArray));

        rearrangeArray(inputArray,0,inputArray.length-1);

        System.out.println("Rearranged array: " + Arrays.toString(inputArray));

    }


}
