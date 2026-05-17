import java.util.Arrays;

public class DivideConqure {

    public static void main(String[] args) {
        int arr[] = {6,3,9,5,2,8,-2};
        mergeSort(arr,0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public  static void mergeSort(int[] arr, int start, int end){
        // Base case
        if(start >= end){
            return;
        }
        // get the middle index
        int mid = start + (end-start) / 2;
        // get the left and right part
        mergeSort(arr,start,mid);  // left part
        mergeSort(arr,mid+1,end);  // right part

        // merge
        merge(arr,start,mid,end);

    }

    public static void merge(int[] arr, int start, int mid, int end){

        // create a temp array to merge the left and right part
        int temp[] = new int[end - start + 1];
        int i = start; // iterator for left part
        int j = mid+1; // iterator for right part
        int k = 0;// iterator for temp array

        while(i <= mid && j<=end){
            if(arr[i] < arr[j]){
                temp[k] = arr[i];
                i++; // increase left index
            }else{
                temp[k] = arr[j];
                j++; // increase right index
            }
            k++; // increase temp array index

        }

        // what if left or right array elements are still pending
        // left part
        while(i <= mid){
            temp[k++] = arr[i++];
        }

        //right part
        while(j <= end){
            temp[k++] = arr[j++];
        }

        // copy temp to my original array
        for(k=0,i=start;k<temp.length;i++,k++){
            arr[i] = temp[k];
        }


    }
}
