package SheetQuestions.arrays;

//code 1 : Maximum and minimum of an array using minimum number of comparisons

import java.util.Arrays;

class Pair{
    int min;
    int max;
}
public class MinMaxArray {
    public static void main(String[] args)
    {
        int[] A = { 4, 9, 6, 5, 2, 3 };
        int N = A.length;

        // 1 normal traversal approach
//        System.out.println("Minimum element is: "
//                + setmini(A, N));
//        System.out.println("Maximum element is: "
//                + setmaxi(A, N));

        // 2 using sorting method
        // number of comparisons is O(n log n)
//        Pair p = getMinMax(A,N);
//        System.out.println("min " + p.min);
//        System.out.println("max " + p.max);

//        3 using linear search
//        in this method, the total number of comparisons is 1 + 2*(n-2) in the worst case
//        and 1 + (n-2) in the best case.
//        Pair p = getMinMaxLinear(A,N);
//        System.out.println("min " + p.min);
//        System.out.println("max " + p.max);

//        4 using the Tournament Method:
//        T(n) = T(floor(n/2)) + T(ceil(n/2)) + 2
//        T(2) = 1
//        T(1) = 0
//        If n is a power of 2, then we can write T(n) as:
//        T(n) = 2T(n/2) + 2
//        T(n) = 3n/2 -2
//        approach does 3n/2 -2 comparisons if n is a power of 2.
//        And it does more than 3n/2 -2 comparisons if n is not a power of 2.

        Pair p =getMinMaxTou(A,0,A.length-1);
        System.out.println("min " + p.min);
        System.out.println("max " + p.max);
    }

    public static Pair getMinMaxTou(int arr[] ,int low, int high){
        Pair minmax = new Pair();
        Pair mml = new Pair();
        Pair mmr = new Pair();
        int mid;

        if(low == high){
            minmax.max = arr[low];
            minmax.min = arr[low];
            return minmax;
        }

        if(high == low+1){
            if(arr[low] > arr[high]){
                minmax.max = arr[low];
                minmax.min = arr[high];
            }else{
                minmax.max = arr[high];
                minmax.min = arr[low];
            }
            return minmax;
        }

        mid = (low + high)/2;
        mml = getMinMaxTou(arr,low,mid);
        mmr = getMinMaxTou(arr,mid+1,high);

        if(mml.min > mmr.min){
            minmax.min = mmr.min;
        }else{
            minmax.max = mml.min;
        }

        if(mml.max > mmr.max){
            minmax.max = mml.max;
        }else{
            minmax.max = mmr.max;
        }

        return minmax;
    }

    public static Pair getMinMaxLinear(int arr[], int n){
        Pair p = new Pair();
        int i ;

        if(n == 1){
            p.max = arr[0];
            p.min = arr[0];
            return p;
        }


            if(arr[0] > arr[1]){
                p.max = arr[0];
                p.min = arr[1];
            }else{
                p.max = arr[1];
                p.min = arr[0];
            }


        for(i=2;i<arr.length;i++){
            if(arr[i] > p.max){
                p.max = arr[i];
            } else if (arr[i] < p.min) {
                p.min = arr[i];
            }
        }

        return p;
    }




    public static Pair getMinMax(int arr[],int n){
        Pair minMax = new Pair();
        Arrays.sort(arr);
        minMax.max = arr[arr.length-1];
        minMax.max = arr[0];
        return minMax;
    }

    public static int setmini(int arr[], int n){
        int min = Integer.MAX_VALUE;
        for(int i=0;i<arr.length;i++){
            if(arr[i] < min){
                min = arr[i];
            }
        }
        return min;
    }

    public static int setmaxi(int arr[], int n){
        int max = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }
}
