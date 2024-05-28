package backtracking;

// code 5 Place N queens with attack condition
// N queens in N rows
// Print 1 Solution check if solution exists or not

import java.util.Arrays;

public class NQueensAllSolutionsWitAttackPrint1Solution {

    static int count = 0;
    public static void main(String[] args) {
        int n=4;
        char board[][] = new char[n][n];
        // initialize all with "X"
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                board[i][j] = 'X';
            }
        }
//        for(char[] i : board){
//            System.out.println(Arrays.toString(i));
//        }
        nQueens(board,0);
//        for(char[] i : board){
//            System.out.println(Arrays.toString(i));
//        }
        System.out.println("Total number of ways " + count);

        if(nQueens(board,0)){
            System.out.println("solution is possible");
            printBoard(board);
        }else{
            System.out.println("solution is not possible");
        }
    }

    public static boolean nQueens(char board[] [], int row){
        // base case
        if(row == board.length){
//            printBoard(board);
            count++;
            return true;
        }

        // column loop
        for(int j=0;j<board.length;j++){
            if(isSafe(board,row,j)){
                board[row][j] = 'Q';
                System.out.println("row=" + row + ",j=" + j + " (" + row + "," + j  + ")=Q");
                if(nQueens(board,row+1)) // function calls
                {
                    return true;
                }
                System.out.println("backtracking row=" + row + ",j=" + j + " (" + row + "," + j  + ")=X");
                // removing unwanted queens
                board[row][j] = 'X'; // backtracking steps
            }

        }
        return false;
    }

    public static boolean isSafe(char board[][], int row , int col){
        // check vertical up (same col, row dec)
        for(int i=row-1;i>=0;i--){
            if(board[i][col] == 'Q'){
                return false;
            }
        }

        // check diagonal left up (col dec, row dec)
        for(int i=row-1,j=col-1; i>=0 && j>=0; i--,j--){
            if(board[i][j] == 'Q'){
                return false;
            }
        }

        // check diagonal right up
        for(int i=row-1,j=col+1; i>=0 && j<board.length; i--,j++){
            if(board[i][j] == 'Q'){
                return false;
            }
        }

        return true;
    }

    public static void printBoard(char board[][]){
//        System.out.println(" ------------- Chess Board -------------------");
        for(int i=0;i< board.length;i++){
            for(int j=0;j< board.length;j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(" ------------- Chess Board -------------------");

    }
}

//
//        [X, X, X, X]
//        [X, X, X, X]
//        [X, X, X, X]
//        [X, X, X, X]
//        row=0,j=0 (0,0)=Q
//        row=1,j=2 (1,2)=Q
//        backtracking row=1,j=2 (1,2)=X
//        row=1,j=3 (1,3)=Q
//        row=2,j=1 (2,1)=Q
//        backtracking row=2,j=1 (2,1)=X
//        backtracking row=1,j=3 (1,3)=X
//        backtracking row=0,j=0 (0,0)=X
//        row=0,j=1 (0,1)=Q
//        row=1,j=3 (1,3)=Q
//        row=2,j=0 (2,0)=Q
//        row=3,j=2 (3,2)=Q
//        ------------- Chess Board -------------------
//        X Q X X
//        X X X Q
//        Q X X X
//        X X Q X
//        ------------- Chess Board -------------------
//        backtracking row=3,j=2 (3,2)=X
//        backtracking row=2,j=0 (2,0)=X
//        backtracking row=1,j=3 (1,3)=X
//        backtracking row=0,j=1 (0,1)=X
//        row=0,j=2 (0,2)=Q
//        row=1,j=0 (1,0)=Q
//        row=2,j=3 (2,3)=Q
//        row=3,j=1 (3,1)=Q
//        ------------- Chess Board -------------------
//        X X Q X
//        Q X X X
//        X X X Q
//        X Q X X
//        ------------- Chess Board -------------------
//        backtracking row=3,j=1 (3,1)=X
//        backtracking row=2,j=3 (2,3)=X
//        backtracking row=1,j=0 (1,0)=X
//        backtracking row=0,j=2 (0,2)=X
//        row=0,j=3 (0,3)=Q
//        row=1,j=0 (1,0)=Q
//        row=2,j=2 (2,2)=Q
//        backtracking row=2,j=2 (2,2)=X
//        backtracking row=1,j=0 (1,0)=X
//        row=1,j=1 (1,1)=Q
//        backtracking row=1,j=1 (1,1)=X
//        backtracking row=0,j=3 (0,3)=X
//        [X, X, X, X]
//        [X, X, X, X]
//        [X, X, X, X]
//        [X, X, X, X]
