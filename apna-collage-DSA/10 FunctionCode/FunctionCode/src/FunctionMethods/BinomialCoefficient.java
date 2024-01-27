package FunctionMethods;

public class BinomialCoefficient {
    public static void main(String[] args) {

        binomial(5,2);
    }

    public static void binomial(int n,int r){

        int n_fact = factorial(n);
        int r_fact = factorial(r);
        int n_r_fact = factorial(n-r);
        System.out.println(n_fact/(r_fact*(n_r_fact)));
    }

    public static int factorial(int n){
        int f = 1;
        if(n == 0){
            return 1;
        }
        for(int i=1;i<=n;i++){
            f = f*i;
        }
        return f;
    }
}
