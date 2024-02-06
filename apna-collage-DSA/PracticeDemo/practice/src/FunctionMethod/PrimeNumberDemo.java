package FunctionMethod;

public class PrimeNumberDemo {

    public static void main(String[] args) {
//        System.out.println(isPrime(1507));
        primeRange(100);
    }

    public static boolean isPrime(int n){
        boolean isPrime = true;
        if(n==2){
            return true;
        }
        for(int i=2;i<Math.sqrt(n);i++){
            if(n%i == 0){
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }

    public static void primeRange(int n){
        for(int i=2;i<=n;i++){
            if(isPrime(i)){
                System.out.print(i + " ");
            }
        }
    }
}
