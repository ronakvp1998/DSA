package functions;

public class CheckPrime {

    public static void main(String[] args) {
        System.out.println(isPrime(2));
    }

    public static boolean isPrime(int n){
        boolean isPrime = true;
        if(n == 2){
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
}
