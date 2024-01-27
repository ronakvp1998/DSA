public class SpiralMatrixCode {


    public static void main(String[] args) {
        int matrix[][] = {
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,16}};

        spiralMatrix(matrix);
    }

    private static void spiralMatrix(int [][]matrix){
        int startRow = 0;
        int startColumn = 0;
        int endRow = matrix.length-1;
        int endCol = matrix[0].length-1;

        while (startRow <= endRow && startColumn <= endCol){
            //top
            // j for column i for row
            for(int j=startColumn;j<=endCol;j++){
                System.out.print(matrix[startRow][j] + " ");
            }

            //right
            for(int i=startRow+1;i<=endRow;i++){
                System.out.print(matrix[i][endCol] + " ");
            }

            //bottom
            for(int j=endCol-1;j>=startColumn;j--){
                if(startRow == endRow){
                    break;
                }
                System.out.print(matrix[endRow][j] + " ");
            }

            //left
            for(int i=endRow-1;i>=startRow+1;i--){
                if(startColumn == endCol){
                    break;
                }
                System.out.print(matrix[i][startColumn] + " ");
            }
            startColumn++;
            startRow++;
            endCol--;
            endRow--;
            System.out.println();

        }
    }

}
