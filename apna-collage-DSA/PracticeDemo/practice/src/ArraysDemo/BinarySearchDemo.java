package ArraysDemo;

public class BinarySearchDemo {

    public static void main(String[] args) {
        int number[] = {2, 4, 6, 8, 10, 12, 14};
//        int key = 10;
        int key = 2;

        System.out.println("index for key is " + binarySearch(number, key));
    }

    public static int binarySearch(int number[], int key) {
        int start = 0, end = number.length-1;
        while (start <=end){
            int mid = start + (end - start )/2;
            if(number[mid] > key){ // left
                end = mid-1;
            }if(number[mid] < key){ // right
                start = mid + 1;
            }if(number[mid] == key){
                return mid;
            }
        }
        return -1;
    }
}