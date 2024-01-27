public class DiagonalSumCode {

    public static void main(String[] args) {
//        int matrix[][] = {
//                {1,2,3,4},
//                {5,6,7,8},
//                {9,10,11,12},
//                {13,14,15,16}};
        int matrix[][] = {
                {1,2,3},
                {5,6,7},
                {9,10,11}};
        System.out.println(diagonalSum3(matrix));
//        System.out.println(diagonalSum2(matrix));

    }


    // optimized code
    private static int diagonalSum2(int [][]matrix) {
        int sum = 0;

        for(int i=0;i<matrix.length;i++){
            // primary diagonal
            sum+=matrix[i][i];
            // secondary diagonal
            if(i != matrix.length-1-i)
                sum+=matrix[i][matrix.length-1-i];
        }
        return sum;
    }



    // brute force approach O(n2)
    private static int diagonalSum(int [][]matrix){
        int sum =0;
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(i==j){
                    sum+=matrix[i][j];
                }else if(i+j == (matrix.length-1)){
                    sum+=matrix[i][j];
                }
            }
        }
        return sum;
    }


    private static int diagonalSum3(int [][]matrix){

        int sum=0;
        for(int i=0;i<matrix.length;i++){
                   sum += matrix[i][i];
                   if(i != matrix.length-1-i)
                       sum += matrix[i][matrix.length-i-1];

        }
        return sum;
    }
}
