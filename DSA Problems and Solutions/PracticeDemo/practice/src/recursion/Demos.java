package recursion;

import java.util.Arrays;

public class Demos {

    public static void main(String[] args) {
//        print1ToN(10);
//        printNTo1(10);
//        System.out.println(factorial(4));
//        int arr[] = {1,2,3,4,5,6,3};
//        System.out.println(checkArry(arr,arr.length-1,0));
//        System.out.println(firOccInArray(arr,55,0));
//        System.out.println(lasOccInArray(arr,4,0));
//        System.out.println(xPowerN(2,10));
//        System.out.println(xPowerN2(2,10));
//        System.out.println(titlingProblem(3));
//        System.out.println(titlingProblem(4));
//        System.out.println(removeDuplicate("appnnazgdfcollege","",0));
//        System.out.println(removeduplicate2("appnnazgdfcoollegeb"));
//        binaryString(4,0,"");
//        int n = 26;
//        System.out.println(fibonacci(n));
//        System.out.println(checkSorted(new int[]{1,2,23,4,5},0));

    }

    public static boolean checkSorted(int arr[], int i){

        if(i == arr.length-1){
            return true;
        }

        if(arr[i] > arr[i+1]){
            return false;
        }

        return checkSorted(arr,i+1);
    }

    public static int fibonacci(int n){
        if(n == 0 || n ==1){
            return n;
        }

        return fibonacci(n-1) + fibonacci(n-2);

    }

    public static void binaryString(int n, int lastPlace, String res){
        if(res.length() == n){
            System.out.println(res);
            return;
        }

        binaryString(n,0,res + 0);
        if(lastPlace == 0){
            binaryString(n,1,res + 1);
        }
    }

    public static String removeduplicate2(String s){
        int arr[] = new int[26];
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(arr[(int)(c - 'a')] >= 1){
                continue;
            }
            sb.append(c);
            arr[(int)(c - 'a')] += 1;
        }
        return sb.toString();
    }

    public static String removeDuplicate(String s, String res,int i ){
        if(s.length() == i){
            return res;
        }

        if(!res.contains(s.charAt(i)+"")){
            res = res + s.charAt(i);
        }
        return removeDuplicate(s,res,i+1);
    }

    public static int titlingProblem(int n){
        if(n == 0 || n == 1){
            return 1;
        }
        int fn1 = titlingProblem(n-1);
        int fn2 = titlingProblem(n-2);
        int totalWays = fn1 + fn2 ;
        return totalWays;
    }

    public static int xPowerN2(int x, int n){
        if(n == 1){
            return x;
        }

        int halfPower = xPowerN2(x,n/2);
        int halfPowerSquare = halfPower * halfPower;

        if(n%2 != 0){
            halfPowerSquare = x * halfPowerSquare;
        }
        return halfPowerSquare;
    }

    public static int xPowerN(int x, int n){

        if(n == 1){
            return x;
        }

        return  x * xPowerN(x,n-1);
    }

    public static int lasOccInArray(int arr[], int key, int i){
        if(i == arr.length){
            return -1;
        }
        int found = lasOccInArray(arr,key,i+1);
        if(arr[i] == key && found == -1){
            return i;
        }
        return found;
    }

    public static int firOccInArray(int arr[],int key, int i){
        if(i == arr.length){
            return -1;
        }
        if(arr[i] == key){
            return i;
        }
        return firOccInArray(arr,key,i+1);
    }


    public static boolean checkArry(int [] arr,int n,int idx){
        if(idx == n){
            return true;
        }

        if(arr[idx] > arr[idx+1]){
            return false;
        }

        return checkArry(arr,n,idx+1);
    }

    public static int factorial(int n){
        if(n ==0 || n == 1){
            return 1;
        }

        int fact = n * factorial(n-1);
        return fact;
    }

    public static void printNTo1(int n){
        if(n == 0){
            return;
        }

        System.out.print(n + " ");
        printNTo1(n-1);
    }


    public static void print1ToN(int n){
        if(n == 0){
            return;
        }

        print1ToN(n-1);
        System.out.print(n + " ");

    }

}
