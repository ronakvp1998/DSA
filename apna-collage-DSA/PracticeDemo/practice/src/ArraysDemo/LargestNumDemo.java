package ArraysDemo;

public class LargestNumDemo {

    public static void main(String[] args) {
        int num[] = {1, 2, 3, 44, 5, 6, 8};
        System.out.println(getLargest(num));
    }

    public static int getLargest(int num[]) {
        int largest = Integer.MIN_VALUE;
        for(int a : num){
            if(a > largest){
                largest = a;
            }
        }
        return largest;
    }
}