package ArraysDemo;

import java.util.Arrays;

public class ReverseListDemo {
    public static void main(String[] args) {
        int numbers[] = {2, 4, 6, 8, 10};
        System.out.println(Arrays.toString(numbers));
        reverse(numbers);
        System.out.println(Arrays.toString(numbers));

    }
    public static void reverse(int number[]) {
        int first = 0, last = number.length-1;
        while (first<last){
            // swap
            int temp = number[first];
            number[first] = number[last];
            number[last] = temp;
            first++;
            last--;
        }

    }
}