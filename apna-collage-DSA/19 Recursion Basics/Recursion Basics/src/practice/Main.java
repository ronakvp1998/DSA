package practice;

public class Main {
    public static void main(String[] args) {

        int n = 2;
        int p = 10;

        System.out.println(pow(n,p));
    }

    public static int pow(int n, int p){
        if(p == 1){
            return n;
        }if(n==0){
            return 1;
        }

        int halfPower = pow(n,p/2);

        int res = halfPower * halfPower;
        if(p%2 != 0){
            res = res * n;
        }
        return res;
    }
}
