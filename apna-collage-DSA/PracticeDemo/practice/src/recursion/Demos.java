package recursion;

import java.util.Arrays;

public class Demos {

    public static void main(String[] args) {
//        printNumberDesc(10);
//        System.out.println(factorial(4));
//        System.out.println(sumNNaturalNumber(5));
//       fibonacciSeries(10);
//        System.out.println(checkArraySorted(new int[]{1,2,3,44,15},0));
//        System.out.println(lastOccurance(new int[]{1,22,3,4,5},5,0));
//        System.out.println(printxN(2,10));
//        System.out.println(tilingProblem(4));
//        boolean map[] = new boolean[26];
//        removeDuplicate("appnnacollege",new StringBuilder(""),0,map);
//        binaryString(3,0,"");
        int arr[] = {1,5,4,2,6};
        System.out.println(Arrays.toString(arr));
        mergesort(arr,0,arr.length);
        System.out.println(Arrays.toString(arr));

    }

    public static void mergesort(int arr[],int start,int end){
        if(start>= end){
            return;
        }
        int mid = start + (end-start)/2;
        mergesort(arr,start,mid);
        mergesort(arr,mid+1,end);
        merge(arr,start,mid,end);
    }

    public static void merge(int arr[], int start, int mid, int end){
        int temp[] = new int[end - start + 1];
        int i =start;
        int j = mid+1;
        int k =0;

        while (i<=mid && j<=end){
             if(arr[i] < arr[j]){
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

    public static void binaryString(int n, int lp, String str){
        if(n == 0){
            System.out.println(str);
            return;
        }
        binaryString(n-1,0,str+"0");
        if(lp == 0){
            binaryString(n-1,1,str+"1");
        }
    }

    public static void removeDuplicate(String str, StringBuilder newStr, int idx, boolean map[]){
        if(idx == str.length()){
            System.out.println(newStr);
            return;
        }
        char currChar = str.charAt(idx);
        if(map[currChar - 'a'] == true){
            removeDuplicate(str,newStr,idx+1,map);
        }else{
            map[currChar-'a'] = true;
            removeDuplicate(str,newStr.append(currChar),idx+1,map);
        }
    }

    public static int tilingProblem(int n){
        if(n == 0 || n == 1){
            return 1;
        }
        int fn1 = tilingProblem(n-1);
        int fn2 = tilingProblem(n-2);
        int totalways = fn1 + fn2;
        return totalways;
    }

    public static int printxN(int a, int n){
        if(n == 0){
            return 1;
        }
        int hp = printxN(a,n/2);
        int hps = hp * hp;
        if(n%2 != 0){
            hps = a * hps;
        }
        return hps;
    }

    public static int lastOccurance(int arr[], int key ,int i){
        if(i == arr.length){
            return -1;
        }
        int isFound = lastOccurance(arr,key,i+1);
        if(isFound == -1 && arr[i] == key){
            return i;
        }
        return isFound;
    }

    public static boolean checkArraySorted(int arr[],int i){
        if(i == arr.length-1){
            return true;
        }
        if(arr[i] > arr[i+1]){
            return false;
        }
        return checkArraySorted(arr,i+1);
    }

    public static void fibonacciSeries(int n){
        for(int i=0;i<=n;i++){
            System.out.print(fibonacci(i) + " ");
        }
        System.out.println();
    }

    public static int fibonacci(int n){
        if(n == 0 || n == 1){
            return n;
        }
        int fn1 = fibonacci(n-1);
        int fn2 = fibonacci(n-2);
        int res = fn1 + fn2 ;
        return res;
    }

    public static int sumNNaturalNumber(int n){
        if(n == 0 || n== 1){
            return n;
        }

        int sum = n + sumNNaturalNumber(n-1);
        return sum;
    }

    public static int factorial(int n){
        if(n == 0){
            return 1;
        }

        int fact = n * factorial(n-1);
        return fact;
    }

    public static void printNumberDesc(int n){
        if(n == 0){
            System.out.println();
            return;
        }
        System.out.print(n + " ");
        printNumberDesc(n-1);
        System.out.print(n-2 + " ");

    }
}
