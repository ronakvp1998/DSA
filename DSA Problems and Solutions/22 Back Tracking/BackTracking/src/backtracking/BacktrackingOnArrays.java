package backtracking;
// code 1 BackTracking on Array
public class BacktrackingOnArrays {
    public static void main(String[] args) {
        // create an empty array
        int arr[] = new int[5];
        changeArr(arr,0,1);
        printArr(arr);
    }

    // print the array
    public static void printArr(int[] arr){
        for(int a : arr){
            System.out.print(a + " ");
        }
        System.out.println();
    }

    // recursion
    public static void changeArr(int arr[],int i,int val){
        // base case
        if(i == arr.length){
            printArr(arr);
            return;
        }

        arr[i] = val;
        changeArr(arr,i+1,val+1);
        // backtracking step
        arr[i] = arr[i] - 2;
    }
}
