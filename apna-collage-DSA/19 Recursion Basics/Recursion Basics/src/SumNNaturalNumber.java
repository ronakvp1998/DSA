public class SumNNaturalNumber {

    public static void main(String[] args) {
        int n = 5;
        System.out.println(sumNaturalNum(n));
    }

    public static int sumNaturalNum(int n){
        if(n == 1){
            return 1;
        }
        return n+sumNaturalNum(n-1);
    }

//    public static int sumNaturalNum(int n){
//        if(n == 1){
//            return 1;
//        }
//        return n+sumNaturalNum(n-1);
//    }
}
