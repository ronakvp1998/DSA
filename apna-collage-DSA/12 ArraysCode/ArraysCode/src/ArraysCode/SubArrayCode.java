package ArraysCode;

public class SubArrayCode {

    public static void main(String[] args) {
        int num[] = {2,4,6,8,10};
//        int num[] = {1,-2,6,-1,3};
        subArray(num);
    }

    public static void subArray(int[] num){
        int start=0,end=0,max = Integer.MIN_VALUE,sum=0;
        for(int i=0;i<num.length;i++){
            start = i;
            for(int j=i;j<num.length;j++){
                end = j;
                sum = 0;
                for(int k=start;k <= end;k++){
                    System.out.print(num[k] + " ");
                    sum = sum + num[k];
                }
                System.out.print("       -> sum " + sum + " ");
                if(sum > max){
                    max = sum;
                }
                System.out.println();
            }
        }
        System.out.println("max " + max);
    }
}
