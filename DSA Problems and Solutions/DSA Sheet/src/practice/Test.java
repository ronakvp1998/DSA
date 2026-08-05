package practice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Test {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(11);
        stack.push(2);
        stack.push(222);
        stack.push(5);
        stack.push(55);
        sortStack(stack);
    }


    private static void backtrack(int n,StringBuilder sb,List<String> res){
        if(sb.length() == n){
            res.add(sb.toString());
            return;
        }
        sb.append('0');
        backtrack(n,sb,res);
        sb.deleteCharAt(sb.length()-1);

        sb.append('1');
        backtrack(n,sb,res);
        sb.deleteCharAt(sb.length()-1);
    }

    public static void reverseOptimal(Stack<Integer> stack) {
        if(stack.isEmpty()){
            return;
        }
        int topEle = stack.pop();
        reverseOptimal(stack);
        insertBottom(stack,topEle);
    }

    private static void insertBottom(Stack<Integer> stack,int val){
        if(stack.isEmpty()){
            stack.push(val);
            return;
        }
        int top = stack.pop();
        insertBottom(stack,val);;
        stack.push(top);
    }

    private static void reverStack(Stack<Integer>stack){
        if(stack.isEmpty()){
            return;
        }
        int a = stack.pop();
        reverStack(stack);
        stack.push(a);
    }

    private static void sortStack(Stack<Integer> stack){
        if(stack.size() == 1){
            return;
        }
        int a = stack.pop();
        sortStack(stack);
        List<Integer> temp = new ArrayList<>();
        while (stack.peek() < a){
            temp.add(stack.pop());
        }
        stack.push(a);
        for(int i : temp){
            stack.push(i);
        }
    }

    private static List<Long> factNum(long n){
        ArrayList<Long> list = new ArrayList<>();
        fact(n,list,1,2);
        return list;
    }

    private static void fact(long n,ArrayList<Long> list,long res,long i){
        if(res > n){
            return;
        }
        list.add(res);
        fact(n,list,res*i,i+1);
    }

    public static int fib(int n) {
        if(n == 0 || n == 1){
            return 1;
        }
        return fib(n-1) + fib(n-2);
    }

    public static boolean isPalindrome(String s) {
        boolean res = true;
        int left=0,right=s.length()-1;
        while (left < right){
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))){
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))){
                right--;
            }
            if(!Character.toString(s.charAt(left)).equalsIgnoreCase(Character.toString(s.charAt(right)))){
                return false;
            }else{
                left++;
                right--;
            }
        }
        return res;
    }

    private static void reverseArray(int arr[],int a,int b){
        if(a >= b){
            return;
        }
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        reverseArray(arr,a+1,b-1);
    }

    private static int factorical(int n){
        if(n == 0 || n == 1){
            return 1;
        }
        return n * factorical(n-1);
    }

    private static int sumN(int n){
        if(n == 0){
            return 0;
        }
        return n + sumN(n-1);
    }


    private static void print1ton(int n){
        if(n == 0){
            return;
        }
        print1ton(n-1);
        System.out.println(n);

    }
}