package backtracking;

// code 4 Place N queens without attack condition but only 1 queen in each row
// N Queens in N Rows

import java.util.Arrays;

public class NQueensAllSolutionsWithoutAttack {

    public static void main(String[] args) {
        int n=2;
        char board[][] = new char[n][n];
        // initialize all with "X"
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                board[i][j] = 'X';
            }
        }
        for(char[] i : board){
            System.out.println(Arrays.toString(i));
        }
        nQueens(board,0);
        for(char[] i : board){
            System.out.println(Arrays.toString(i));
        }
    }

    public static void nQueens(char board[] [], int row){
        // base case
        if(row == board.length){
            printBoard(board);
            return;
        }

        // column loop
        for(int j=0;j<board.length;j++){
            board[row][j] = 'Q';
            System.out.println("row=" + row + ",j=" + j + " (" + row + "," + j  + ")=Q");
            nQueens(board,row+1); // function calls
            System.out.println("backtracking row=" + row + ",j=" + j + " (" + row + "," + j  + ")=X");
            // removing unwanted queens
            board[row][j] = 'X'; // backtracking steps
        }
    }

    public static void printBoard(char board[][]){
        System.out.println(" ------------- Chess Board -------------------");
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
//        [X, X]
//        [X, X]
//        row=0,j=0 (0,0)=Q
//        row=1,j=0 (1,0)=Q
//        ------------- Chess Board -------------------
//        Q X
//        Q X

//        ------------- Chess Board -------------------
//        backtracking row=1,j=0 (1,0)=X
//        row=1,j=1 (1,1)=Q
//        ------------- Chess Board -------------------
//        Q X
//        X Q

//        ------------- Chess Board -------------------
//        backtracking row=1,j=1 (1,1)=X
//        backtracking row=0,j=0 (0,0)=X
//        row=0,j=1 (0,1)=Q
//        row=1,j=0 (1,0)=Q
//        ------------- Chess Board -------------------
//        X Q
//        Q X

//        ------------- Chess Board -------------------
//        backtracking row=1,j=0 (1,0)=X
//        row=1,j=1 (1,1)=Q
//        ------------- Chess Board -------------------
//        X Q
//        X Q

//        ------------- Chess Board -------------------
//        backtracking row=1,j=1 (1,1)=X
//        backtracking row=0,j=1 (0,1)=X
//        [X, X]
//        [X, X]

