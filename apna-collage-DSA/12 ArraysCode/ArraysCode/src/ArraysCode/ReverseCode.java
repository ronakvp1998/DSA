package ArraysCode;

import java.util.Arrays;

public class ReverseCode {
    public static void main(String[] args) {
        int numbers[] = {2,4,6,8,10};
        System.out.println(Arrays.toString(numbers));
        reverse(numbers);
        System.out.println(Arrays.toString(numbers));

    }
    public static void reverse(int number[]){
        int first = 0,last= number.length-1;
        while(first<last){
            //swap
            int temp = number[last];
            number[last] = number[first];
            number[first] = temp;
            first++;
            last--;
        }
    }
}
