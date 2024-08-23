package SheetQuestions.arrays;

// code 2 :Array Reverse

import java.util.Arrays;
import java.util.Stack;

public class ArrayReverse {

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5,6,7};
        System.out.println(Arrays.toString(arr));

//        arrayReverse(arr,arr.length);
//        arrayReverseInPlace(arr,0,arr.length-1);

//        reverseArrayRecursion(arr,0,arr.length-1);
//        System.out.println(Arrays.toString(arr));

        reverseArrayUsingStack(arr);
        System.out.println(Arrays.toString(arr));

    }

//    5. Array Reverse Stack (Non In-place):
    public static void reverseArrayUsingStack(int arr[]){
        Stack<Integer> stack = new Stack<>();
        for(int a : arr){
            stack.push(a);
        }

        for(int i=0;i<arr.length;i++){
            arr[i] = stack.pop();
        }
    }


    //    4. Array Reverse Recursion (In-place or Non In-place):
//    Time Complexity: O(n).
//    Auxiliary Space Complexity: O(n)

    public static void reverseArrayRecursion(int arr[], int start,int end){
        if(start>=end){
            return;
        }
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
        reverseArrayRecursion(arr,start+1,end-1);
    }

    //    2. Array Reverse Using a Loop (In-place):
    //    Time Complexity: O(n)
    //    Auxiliary Space Complexity: O(1)
    public static void arrayReverseInPlace(int arr[], int start, int end){
        int temp ;
        while(start<end){
            temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }

        System.out.println(Arrays.toString(arr));
    }


//    1. Array Reverse Using an Extra Array (Non In-place):
//Time Complexity: O(n)
//Space Complexity: O(n)
    public static void arrayReverse(int arr[] ,int n){
        System.out.println("array " + Arrays.toString(arr));
        int [] reverseArr = new int[n];
        for(int i=0;i<n;i++){
            reverseArr[i] = arr[n-i-1];
        }

        System.out.println("reversed array " + Arrays.toString(reverseArr));
    }


}
