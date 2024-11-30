import java.util.Scanner;

public class Create2dArrays {

    public static void main(String[] args) {
        d2dArrayCreation();
    }

    private static boolean search(int matrix[][],int key){
        int n=matrix.length ,m=matrix[0].length;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(matrix[i][j] == key){
                    System.out.println("key found at " + i + " " +j);
                    return true;
                }
            }
        }
        System.out.println("key not found");
        return false;
    }

    private static void d2dArrayCreation() {
        int matrix[][] =new int[3][3];
        int n=matrix.length ,m=matrix[0].length;
        Scanner scanner = new Scanner(System.in);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                matrix[i][j] = scanner.nextInt();
            }
        }

        //output
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        search(matrix,5);
    }
}
