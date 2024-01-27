import java.util.Arrays;

public class BubbleSortingCode {

    public static void main(String[] args) {
        int[] arr =  {5,4,1,3,2};
        System.out.println(Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void bubbleSort(int arr[]){

        for(int turn =0;turn< arr.length-1;turn++){
            int swap = 0;
            for(int j=0;j<arr.length-1-turn;j++){
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    swap++;
                }
            }
            if (swap == 0){
                break;
            }
        }
    }
}
