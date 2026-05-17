import java.util.Arrays;

public class SelectionSortCode {

    public static void main(String[] args) {
        int arr [] ={5,4,1,3,2};
        System.out.println(Arrays.toString(arr));
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));

    }

    private static void selectionSort(int[] arr) {

        for(int i=0;i< arr.length-1;i++){
            int minPos = i;
            for(int j= i+1;j< arr.length;j++){
                if(arr[minPos] > arr[j]){
                    minPos = j;
                }
            }
            //swap
            int temp = arr[minPos];
            arr[minPos] = arr[i];
            arr[i] = temp;
        }
    }
}
