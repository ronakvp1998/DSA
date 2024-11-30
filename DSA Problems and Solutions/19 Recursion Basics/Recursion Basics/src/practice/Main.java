package practice;

import java.util.Arrays;

public class Main{
    public static void main(String[] args) {
//        System.out.println(factorial(5));
//        printDesc(10);
//        printAsc(10);
//        System.out.println(factorial(5));
//        System.out.println(sumN(5));

//        System.out.println(fibonacci(5));
//        System.out.println(arrSorted(new int[]{1,12,3,4,5},0));
//        System.out.println(firstOcc(new int[]{8,3,6,9,5,10,2,5,3},5,0));
//        int arr [] = new int[]{8,3,6,19,5,10,19,5,3};
//        System.out.println(lastOcc(arr,19,0));

//        System.out.println(xN(2,10));
//        System.out.println(optimizedPrintXPowN(2,10));

//        System.out.println(tiling(4));
//        System.out.println(removeDuplicates1("appnacollege"));
        removeDuplicates("appnnacollege",0,new StringBuilder(),new boolean[26]);
    }

    public static void removeDuplicates(String str,int idx,
                                        StringBuilder newStr, boolean map[]){

        if(idx == str.length()){
            System.out.println(newStr);
            return;
        }

        char currChar = str.charAt(idx);
        if(map[currChar-'a'] == true){
            removeDuplicates(str,idx+1,newStr,map);
        }else{
            map[currChar-'a'] = true;
            removeDuplicates(str,idx+1,newStr.append(currChar),map);
        }
    }

    public static String removeDuplicates1(String str){
        int [] carr = new int[26];
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);
            if(carr[(int)(c-'a')] >= 1 ){
                continue;
            }
            sb.append(c);
            carr[c-'a']++;
        }
        System.out.println(Arrays.toString(carr));
        return sb.toString();
    }

    public static int tiling(int n){
        if(n == 0 || n == 1){
            return 1;
        }

        int fnm1 = tiling(n-1);
        int fnm2 = tiling(n-2);

        int total = fnm1 + fnm2;

        return total;

    }

    public static int optimizedPrintXPowN(int a,int n){
        if(n == 0){
            return 1;
        }

        int halfPower = optimizedPrintXPowN(a,n/2);
        int halfPowerSquare = halfPower*halfPower;
        if(n%2 != 0){
            halfPowerSquare = halfPowerSquare * a;
        }

        return halfPowerSquare;

    }

    public static int xN(int x,int n){
        if(n == 0){
            return 1;
        }

        return x * xN(x,n-1);
    }


    public static int lastOcc(int arr[], int key, int i){
        if(i == arr.length){
            return -1;
        }

        int foundInd = lastOcc(arr,key,i+1);

        if(foundInd == -1 && arr[i] == key){
            return i;
        }

        return foundInd;
    }

    public static int firstOcc(int arr[], int key, int i){

        if(i == arr.length){
            return -1;
        }

        if(arr[i] == key){
            return i;
        }

        return firstOcc(arr,key,i+1);

    }

    public static boolean arrSorted(int arr[], int i){
        if(i == arr.length-1){
            return true;
        }if(arr[i] > arr[i+1]){
            return false;
        }
        boolean res = arrSorted(arr,i+1);
        return res;

    }

    public static int fibonacci(int n){
        if(n==0 || n==1){
            return n;
        }

        int a = fibonacci(n-1);
        int b = fibonacci(n-2);
        return a+b;
    }

    public static int sumN(int n){
        if(n == 0){
            return 0;
        }

        int res = n + sumN(n-1);
        return res;
    }


    public static int factorial(int n){
        if(n == 0){
            return 1;
        }

        if(n < 0){
            return -1;
        }

        int res = n * factorial(n-1);
        return res;

    }

    public static void printAsc(int n){
        if(n==1){
            System.out.print(n + " ");
            return;
        }

        printAsc(n-1);
        System.out.print(n + " ");
    }

    public static void printDesc(int n){
        if(n == 0){
            return;
        }

        System.out.print(n + " ");
        printDesc(n-1);
    }
}
