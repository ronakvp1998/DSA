package practice;

import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int arr[] = {1,9,3,2,10,13,8};
//        bubbleSort(arr);
//        selectionSort(arr);
//        insertionSort(arr);
        countingSort(arr);
    }

    public static void countingSort(int arr[]){
        int largest = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            largest = Math.max(largest,arr[i]);
        }

        // create count arr
        int count[] = new int[largest+1];
        for(int i=0;i<arr.length;i++){
            int a = arr[i];
            count[a] += 1;
        }

        int j=0;
        for(int i=0;i<count.length;i++){
            while(count[i] > 0){
                arr[j] = i;
                j++;
                count[i]--;
            }
        }

        System.out.println(Arrays.toString(arr));
    }

    public static void insertionSort(int arr[]){

        for(int i=1;i<arr.length;i++){

            int curr = arr[i], prev = i-1;

            while(prev >= 0 && curr < arr[prev]){
                arr[prev+1] = arr[prev];
                prev--;
            }

            arr[prev+1] = curr;
            System.out.println(Arrays.toString(arr));
        }
    }

    public static void selectionSort(int arr[]){

        for(int i=0;i< arr.length;i++){
            int min = i;
            for(int j=i;j< arr.length;j++){
                if(arr[j] < arr[min]){
                    min = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
            System.out.println(Arrays.toString(arr));
        }
    }

    public static void bubbleSort(int arr[]){

        for(int i=0;i< arr.length-1;i++){
            Boolean flage = false;
            for(int j=0;j< arr.length-1-i;j++){
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    flage = true;
                }
            }
            if(flage == false){
                break;
            }
            System.out.println(Arrays.toString(arr));
        }
    }

}
