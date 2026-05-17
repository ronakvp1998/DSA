package ArraysCode;

public class SubArraySumCode {

    public static void main(String[] args) {
//        int num[] = {1,-2,6,-1,3};
        int num[] = {2,4,6,8,10};
        sumSub(num);
//        sumSub2(num);
    }

    private static void sumSub2(int[] num) {
        for(int i=0;i< num.length;i++){
            int start = i;
            for(int j =0;j< num.length;j++){
                int end = j;
                for(int k=start;k<=end;k++){
                    System.out.print(num[k] +" ");
                }
                System.out.println();
            }
        }
    }


    public static void sumSub(int num[]){
        int start =0 ,end=0;
        for(int i=0;i<num.length;i++){
            start = i;
            for(int j=0;j<num.length;j++){
                end = j;
                for(int k=start;k<=end;k++){
                    System.out.print(num[k] + " ");
                }
                System.out.println();
            }
        }
    }
}
