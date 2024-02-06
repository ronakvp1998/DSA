package ArraysDemo;

public class SubArrayDemo {

    public static void main(String[] args) {
        int num[] = {2, 4, 6, 8, 10};
        subArray(num);
    }

    public static void subArray(int[] num) {
        int max = Integer.MIN_VALUE,sum = 0;
        for(int i=0;i<num.length;i++){
            for(int j=i;j<num.length;j++){
                int start = i;
                int end = j;
                sum = 0;
                for(int k = start;k<=end;k++){
                    System.out.print(num[k] + " ");
                    sum = sum + num[k];
                }
                System.out.println();
                if(sum>max){
                    max = sum;
                }
            }
            System.out.println();

        }
        System.out.println("max " + max);
    }
}