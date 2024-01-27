public class FabinocciSeries {

    public static void main(String[] args) {
        int n = 9;
        System.out.println(fabinonacciSeries(n));
    }


    public static int fabinonacciSeries(int n){
        if(n==0 || n==1){
            return n;
        }
        int fact1 = fabinonacciSeries(n-1);
        int fact2 = fabinonacciSeries(n-2);
        return fact1 + fact2;
    }



//    public static int fabinonacciSeries(int n){
//        if(n == 0 || n == 1) {
//            return n;
//        }
//
//        int fibn1 = fabinonacciSeries(n-1);
//        int fibn2 = fabinonacciSeries(n-2);
//
//        int fibn = fibn1 + fibn2;
//
//        return fibn;
//
//    }
}
