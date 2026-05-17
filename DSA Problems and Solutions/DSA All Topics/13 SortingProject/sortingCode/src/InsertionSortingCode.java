import java.util.Arrays;

public class InsertionSortingCode {

    public static void insertionCode(int arr[]){
        for(int i=1;i< arr.length;i++){
            int curr = arr[i];
            int prev = i-1;

            //finding out the correct position to insert
            while(prev>=0 && arr[prev] > curr){
                arr[prev+1] = arr[prev];
                prev--;
            }

            //insertion
            arr[prev+1] = curr;
        }

    }

    public static void main(String[] args) {
        int arr [] ={5,4,1,3,2};
        System.out.println(Arrays.toString(arr));
        insertionCode(arr);
        System.out.println(Arrays.toString(arr));
    }
}
