package ArraysCode;

public class LargestNum {
    public static void main(String[] args) {
        int num[] = {1,2,3,4,5,6,8};
        System.out.println(getLargest(num));
    }
    public static int getLargest(int num[]){
        int largest = Integer.MIN_VALUE;
        for(int i=0;i< num.length;i++){
            if(num[i] > largest){
                largest = num[i];
            }
        }
        return largest;
    }
}
