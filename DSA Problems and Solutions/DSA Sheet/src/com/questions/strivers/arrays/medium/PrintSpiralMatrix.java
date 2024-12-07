package com.questions.strivers.arrays.medium;

import java.util.ArrayList;
import java.util.List;

public class PrintSpiralMatrix {
    public static void main(String[] args) {
        int arr[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        List<Integer> res = printSpiral(arr);
        System.out.println(res);
    }

    public static List<Integer> printSpiral(int arr[][]){

        int rowStart=0, rowEnd=arr.length-1, colStart=0,colEnd = arr[0].length-1;
        List<Integer> res = new ArrayList<>();
        while (rowStart <= rowEnd && colStart <= colEnd){
            // top
            for(int i=colStart;i<=colEnd;i++){
                res.add(arr[rowStart][i]);
            }
            //right
            for(int i=rowStart+1;i<=rowEnd;i++){
                res.add(arr[i][colEnd]);
            }

            // bottom
            for(int i=colEnd-1;i>=colStart;i--){
                if (rowStart == rowEnd){
                    break;
                }
                res.add(arr[rowEnd][i]);
            }

            //left
            for(int i=rowEnd-1;i>=rowStart;i--){
                if(colStart == colEnd){
                    break;
                }
                res.add(arr[i][colStart]);
            }

            rowStart++;
            rowEnd--;
            colStart++;
            colEnd--;
        }
        return res;
    }

}
