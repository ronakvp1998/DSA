package FunctionMethod;

public class BinomialCoefficientDemo {

    public static void main(String[] args) {
        binomialFunc(5,2);
    }

    public static void binomialFunc(int n,int r) {
        int nFact = factorial(n);
        int rFact = factorial(r);
        System.out.println(nFact/(rFact*factorial(n-r)));
    }

    public static int factorial(int a) {
        if(a == 0){
            return 1;
        }
        int fact = a * factorial(a-1);
        return fact;

    }
}
