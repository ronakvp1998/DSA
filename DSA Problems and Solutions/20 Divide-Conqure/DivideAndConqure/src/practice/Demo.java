package practice;

import java.util.Arrays;

public class Demo {

    public static void main(String[] args) {
        int arr[] = {4,3,7,8,2};
        System.out.println(Arrays.toString(arr));
        mergeSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));

    }

    public static void mergeSort(int arr[],int si,int ei){
        if(si >= ei){
            return;
        }

        int mid = si + (ei - si)/2;
        mergeSort(arr,si,mid);
        mergeSort(arr,mid+1,ei);

        merge(arr,si,mid,ei);
    }

    public static void merge(int arr[], int si, int mid, int ei){
        int temp [] = new int[ ei - si + 1 ];
        int i=si,j=mid+1,k=0;

        while(i<=mid && j<= ei){
            if(arr[i] < arr[j]){
                temp[k] = arr[i];
                i++;
            }else{
                temp[k] = arr[j];
                j++;
            }
            k++;
        }

        while(i<=mid){
            temp[k++] = arr[i++];
        }

        while(j<= ei){
            temp[k++] = arr[j++];
        }

        for(k=0,i=si;k< temp.length;i++,k++){
            arr[i] = temp[k];
        }
    }
}
