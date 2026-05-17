// code 7 WAP to find the first occurance of a value in an array

public class FirstOccuInArray {
    public static void main(String[] args) {
        int[] arr = {8,3,15,9,15,10,2,15,5};
        System.out.println(firstOccurance(arr,5,0));
    }

    public static int firstOccurance(int arr[], int key,int i){
        if(i == arr.length){
            return -1;
        }
        if(arr[i] == key){
            return i;
        }
        return firstOccurance(arr,key,i+1);

    }
}
