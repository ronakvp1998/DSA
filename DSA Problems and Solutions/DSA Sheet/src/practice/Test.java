package practice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

    public static void convertOptimal(int[] arr) {
        if(arr == null || arr.length <= 1){
            return;
        }
        int n = arr.length;
        for(int i = (n-2)/2;i>=0;i--){
            maxHeapifyIterative(arr,n,i);
        }
    }

    private static void maxHeapifyIterative(int arr[],int n,int i){
        while (true){
            int largest = i;
            int left = 2*i+1;
            int right = 2*i+2;
            if(left < n && arr[left] > arr[largest]){
                largest = left;
            }
            if(right < n && arr[right] > arr[largest]){
                largest = right;
            }
            if(largest != i){
                swap(arr,i,largest);
                i = largest;
            }else{
                break;
            }
        }
    }

    private static void swap(int arr[],int a,int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static boolean isMaxHeapOptimal(int[] arr) {
        if(arr == null || arr.length <= 1){
            return true;
        }
        int n = arr.length;
        for(int i=0;i<=(n-2)/2;i++){
            int leftChild = 2*i+1;
            int rightChild = 2*i+2;
            if(leftChild < n && arr[i] < arr[leftChild]){
                return false;
            }
            if(rightChild < n && arr[i] < arr[rightChild]){
                return false;
            }
        }
        return true;
    }

    public static boolean isMinHeapOptimal(int[] arr) {
        if(arr == null || arr.length <= 1){
            return true;
        }
        int n = arr.length;
        for(int i=0;i<=(n-2)/2;i++){
            int leftChild = 2*i+1;
            int rightChild = 2*i+2;
            if(leftChild < n && arr[i] > arr[leftChild]){
                return false;
            }
            if(rightChild < n && arr[i] > arr[rightChild]){
                return false;
            }
        }
        return true;
    }

    static class MaxHeap{
        int size ;
        final int capacity;
        final int[] heap;

        public MaxHeap(int capacity){
            this.size = 0;
            this.capacity = capacity;
            this.heap = new int[capacity];
        }

        public int left(int i) {
            return 2*i+1;
        }
        public int right(int i){
            return 2*i+2;
        }
        public int parent(int i){
            return (i-1)/2;
        }
        public void insert(int data){
            if(size == capacity){
                System.out.println("Heap is full");
                return;
            }
            int i = size;
            heap[i] = data;
            size++;
            while (i != 0 && heap[i] > heap[parent(i)]){
                swap(i,parent(i));
                i = parent(i);
            }
        }

        private int extractMaxHeap(){
            if(size == 0){
                return -1;
            }
            if(size == 1){
                return heap[--size];
            }
            int root = heap[0];
            swap(0,size-1);
            size--;
            heapifyMaxHeap(0);
            return root;
        }

        private void heapifyMaxHeap(int i){
            while (true){
                int largest = i;
                int l = left(i);
                int r = right(i);
                if(l < size && heap[l] > heap[largest]){
                    largest = l;
                }
                if(r < size && heap[r] > heap[largest]){
                    largest = r;
                }
                if(i != largest){
                    swap(i,largest);
                    i = largest;
                }else{
                    break;
                }
            }
        }


        private int peek(){
            if(size == 0){
                return -1;
            }
            return heap[0];
        }

        private void swap(int a,int b){
            int temp = heap[a];
            heap[a] = heap[b];
            heap[b] = temp;
        }
    }

}
