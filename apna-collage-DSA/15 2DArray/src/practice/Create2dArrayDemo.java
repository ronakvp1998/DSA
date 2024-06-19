package practice;

import java.util.Arrays;
import java.util.Scanner;

public class Create2dArrayDemo {
    public static void main(String[] args) {
//        d2dArray();
//        spiralMatrix();
//        diagonalSum();

//        searchBottomLeft(5);
        searchTopRight(39);

    }

    public static boolean searchTopRight(int key){
        int matrix[][] = {
                {10,20,30,40},
                {15,25,35,45},
                {27,28,37,48},
                {32,33,39,50}
        };

        int row=0,col = matrix[0].length-1;
        while (row < matrix.length && col >=0){
            if(matrix[row][col] == key){
                System.out.println("found key at (" + row + "," + col + ")");
                return true;
            }else if(key < matrix[row][col]){
                col--;
            }else{
                row++;
            }
        }

        System.out.println("Key not found");
        return false;
    }

    public static boolean searchBottomLeft(int key){

        int matrix[][] = {
                {10,20,30,40},
                {15,25,35,45},
                {27,28,37,48},
                {32,22,29,50}
        };

        int row = matrix.length-1, col=0;
        while (row>=0 && col< matrix[0].length){
            if(matrix[row][col] == key){
                System.out.println("found key at (" + row + "," + col + ")");
                return true;
            }else if(key < matrix[row][col]){
                row--;
            }else {
                col++;
            }
        }

        System.out.println("Key not found");
        return false;
    }


    public static void diagonalSum(){
        int matrix[][] = {
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };

        int sum = 0;
        for(int i=0;i<matrix.length;i++){
            sum += matrix[i][i];
            if(i != matrix.length-1-i)
            sum += matrix[i][matrix.length-1-i];
        }

        System.out.println("Sum: " + sum);

//        int leftSum = 0;
//        for(int i=0,j=0;i<matrix.length;i++,j++){
//            leftSum += matrix[i][j];
//        }
//
//        int rightSum = 0;
//        for(int sr=0,sc=matrix[0].length-1;sr<matrix.length; sr++,sc--){
//            rightSum += matrix[sr][sc];
//        }
//
//        System.out.println("Sum: " + (leftSum+rightSum));
    }

    public static void spiralMatrix(){
        int matrix[][] = {
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12}
//                {13,14,15,16}
        };

        int sr=0,sc=0,er=matrix.length-1,ec=matrix[0].length-1;

        while(sr<=er && sc<=ec){
            // top
            for(int i=sc;i<=ec;i++){
                System.out.print(matrix[sr][i] + " ");
            }

            // right
            for(int i=sr+1;i<=er;i++){
                System.out.print(matrix[i][ec] + " ");
            }

            // bottom
            for(int i=ec-1;i>=sc;i--){
                if(sr == er){
                    break;
                }
                System.out.print(matrix[er][i] + " ");
            }

            // left
            for(int i=er-1;i>=sr+1;i--){
                if(sc == ec){
                    break;
                }
                System.out.print(matrix[i][sc] + " ");
            }

            sr++;
            er--;
            sc++;
            ec--;

        }

    }

    private static void d2dArray(){
        int matrix[][] = new int[3][3];
        int n = matrix.length, m = matrix[0].length;
        Scanner scanner = new Scanner(System.in);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                matrix[i][j] = scanner.nextInt();
            }
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        search(matrix,5);

    }

    private static void search(int matrix[][], int key){

        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j] == key){
                    System.out.println("key=" + key +" found at i=" + i + " ,j=" + j);
                    break;
                }
            }
        }
        System.out.println("Key not found");
    }
}
