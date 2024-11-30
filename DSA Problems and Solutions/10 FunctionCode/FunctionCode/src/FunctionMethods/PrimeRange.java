package FunctionMethods;

public class PrimeRange {

    public static void main(String[] args) {
        primesInRange(100);
    }

    public static void primesInRange(int n){
        for(int i=2;i<=n;i++){
            if(isPrime(i)){ //true
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public static boolean isPrime(int n){
        boolean isPrime = true;

        if(n==2){
            return true;
        }

        for(int i=2;i<Math.sqrt(n);i++){
            if(n%i == 0){
                isPrime =  false;
                break;
            }
        }
        return isPrime;
    }
}
