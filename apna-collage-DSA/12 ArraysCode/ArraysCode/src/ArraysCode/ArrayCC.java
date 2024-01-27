package ArraysCode;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayCC {
    public static void main(String[] args) {
        int marks []= new int[5];
        Scanner scanner = new Scanner(System.in);
        int index = 0;
        while(index < marks.length){
            marks[index++] = scanner.nextInt();
        }
        System.out.println(Arrays.toString(marks));
    }
}
