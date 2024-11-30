// code 10 optimized x^n => 2^10=1024 in log(n) time complexity
public class optimizedPrintXPowN {

    public static void main(String[] args) {
        int a = 2;
        int n = 10;
        System.out.println(optimizedPrintXPowN(a,n));
    }

    public static int optimizedPrintXPowN(int a, int n){  // O(log(n))
        // base case
        if(n == 0){
            return 1;
        }
        // divide the pow by 2
//        int halfPowSquar = optimizedPrintXPowN(a,n/2) * optimizedPrintXPowN(a,n/2);    -> O(n)
        int halfPower = optimizedPrintXPowN(a,n/2);
        int halfPowerSqr = halfPower * halfPower;
        // n is odd
        if(n %2 != 0){
            halfPowerSqr = a*halfPowerSqr;
        }
        return halfPowerSqr;
    }
}
