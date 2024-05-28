// code 3 print factorial of a number

public class PrintFactorial {

    public static void main(String[] args) {
        int n = 5;
//        System.out.println(factorial(n));
        System.out.println(factorial2(n));
    }

    private static int factorial2(int n){
        if(n == 0){
            return 1;
        }if(n<0){
            return -1;
        }
        int fact = n * factorial2(n-1);
        return fact;
    }

    public static int factorial(int n){
        if(n < 0){
            return -1;
        }
        if( n == 0){
            return 1;
        }
        int fact = n * factorial(n-1);
        return fact;
    }
}
