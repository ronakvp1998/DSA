package Backtracking;

import java.util.Arrays;

public class Demos {

    static int count = 0;
    public static void main(String[] args) {
//        int n = 5;
//        int arr[] = new int [n];
//        backtrackOnArrays(arr, 0);
//        System.out.println(Arrays.toString(arr));
//        findSubsets("abc","",0);
//        findPermu("abc","");

        int n = 4;
        char board[][] = new char[n][n];
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                board[i][j] = 'X';
            }
        }
        nQueens(board,0);
        System.out.println("Total counts " + count);
    }

    public static void nQueens(char[][] board, int row){
        if(row == board.length){
            printboard(board);
            count++;
            return;
        }
        for(int j=0;j<board.length;j++){
            if(isSafe(board,row,j)){
                board[row][j] = 'Q';
                nQueens(board,row+1);
                board[row][j] = 'X';
            }
        }
    }

    public static void printboard(char [][] board){
        System.out.println("------------------Chess board--------------------");
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean isSafe(char board[][], int row, int col){

        // top
        for(int i = row-1;i>=0;i--){
            if(board[i][col] == 'Q'){
                return false;
            }
        }
        // left
        for(int i=row-1,j=col-1;i>=0 && j>=0;i--,j-- ){
            if(board[i][j] == 'Q'){
                return false;
            }
        }
        // right
        for(int i=row-1,j=col+1;i>=0 && j<board.length;i--,j++){
            if(board[i][j] == 'Q'){
                return false;
            }
        }
        return true;
    }

    public static void findPermu(String s, String res){
        if(s.length() == 0){
            System.out.println(res);
            return;
        }
        for(int i=0;i<s.length();i++){
            char curr = s.charAt(i);
            String newStr = s.substring(0,i) + s.substring(i+1);
            findPermu(newStr,res+curr);
        }
    }

    public static void findSubsets(String str, String res, int i){
        if(i == str.length()){
            if(res.length() == 0){
                System.out.println("Null");
            }
            System.out.println(res);
            return;
        }
        findSubsets(str, str.charAt(i) + res, i+1);
        findSubsets(str,res,i+1);

    }


    public static void backtrackOnArrays(int arr[], int n){

        if(n == arr.length-1){
            System.out.println(Arrays.toString(arr));
            return;
        }
        arr[n] = n;
        backtrackOnArrays(arr,n+1);
        arr[n] = arr[n] - 2;
    }
}
