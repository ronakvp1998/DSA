import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int arr[] = {6,3,9,5,2,8,-2};
        quickSort(arr,0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int arr[], int start, int end){
        // Base case
        if(start >= end){
            return;
        }
        // find the pivot -> last element
        // this funcation will give the index of pivot
        // and left of pivot will have less value and right of pivot will have more value compare to pivot
        int pidx =  partition(arr,start,end);
        quickSort(arr,start,pidx-1); // left part less than pivot
        quickSort(arr,pidx+1,end);  //right part greater than pivot


    }

    public static int partition(int[] arr, int start, int end){
        int pivot = arr[end];
        int i = start-1;  // to make place for elements smaller than pivot element
        for(int j=start ;j<end;j++){ // this loop will go till index less than pivot index
            if(arr[j] <= pivot){
                i++;
                // swap
                int temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }
        i++;
        // bring pivot to its place
        // swap
        int temp = pivot;
        arr[end] = arr[i];
        arr[i] = temp;
        // return the index of the pivot
        return i;

    }
}
