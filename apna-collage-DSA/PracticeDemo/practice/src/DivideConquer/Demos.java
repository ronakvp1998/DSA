package DivideConquer;

import java.util.Arrays;

public class Demos {

    public static void main(String[] args) {
        int[] arr = {1,2,31,41,5,16,7};
//        System.out.println(Arrays.toString(arr));
//        mergeSort(arr,0,arr.length-1);
//        System.out.println(Arrays.toString(arr));
//        System.out.println(rotatedSearchArr(arr,31,0,arr.length-1));
//        System.out.println(Arrays.toString(arr));
//        mergeSort1(arr,0,arr.length-1);
//        System.out.println(Arrays.toString(arr));
//        int arr2[] = {4,5,6,1,2,3};


    }

//    public static void quickSort(int arr[], int start, int end){
//        if(start>=end){
//            return;
//        }
//        int pidx = partition(arr,start,end);
//        quickSort(arr,start,pidx-1);
//        quickSort(arr,pidx+1,end);
//    }

    

    public static void mergeSort1(int arr[], int start, int end){
        if(start>= end){
            return;
        }

        int mid = start + (end-start)/2;
        mergeSort1(arr,start,mid);
        mergeSort1(arr,mid+1,end);
        merge1(arr,start,mid,end);
    }

    public static void merge1(int arr[], int start, int mid, int end){
        int temp[] = new int [end-start+1];
        int i = start;
        int j = mid+1;
        int k = 0;
        while (i<=mid && j<=end){
            if(arr[i]<arr[j]){
                temp[k] = arr[i];
                i++;
            }else {
                temp[k] = arr[j];
                j++;
            }
            k++;
        }
        while (i<=mid){
            temp[k++] = arr[i++];
        }
        while (j<=end){
            temp[k++] = arr[j++];
        }
        for(k=0,i=start;k<temp.length;i++,k++){
            arr[i] = temp[k];
        }
    }

    public static int rotatedSearchArr(int arr[], int target, int start, int end){

        if(start > end){
            return -1;
        }

        int mid = start + (end-start)/2;

        if(arr[mid] == target){
            return mid;
        }

        if(arr[start]<=arr[mid]){
            if(arr[start]<=target && target<=arr[mid]){
                return rotatedSearchArr(arr,target,start,mid-1);
            }else{
                return rotatedSearchArr(arr,target,mid+1,end);
            }
        }else{
            if(arr[mid] <= target && target <= arr[end]){
                return rotatedSearchArr(arr,target,mid+1,end);
            }else{
                return rotatedSearchArr(arr,target,start,mid-1);
            }
        }
    }


    public static void mergeSort(int []arr, int start, int end){
        if(start >= end){
            return;
        }
        int mid = start + (end-start)/2;
        mergeSort(arr,start,mid);
        mergeSort(arr,mid+1,end);
        merge(arr,start,mid,end);
    }

    public static void merge(int arr[], int start, int mid, int end){
        int temp[] = new int[end-start+1];
        int i = start;
        int j = mid+1;
        int k = 0;

        while (i<=mid && j<=end){
            if(arr[i]<arr[j]){
                temp[k] = arr[i];
                i++;
            }else{
                temp[k] = arr[j];
                j++;
            }
            k++;
        }

        while (i<=mid){
            temp[k++] = arr[i++];
        }

        while (j<=end){
            temp[k++] = arr[j++];
        }

        for(k=0,i=start;k<temp.length;i++,k++){
            arr[i] = temp[k];
        }
    }
}
