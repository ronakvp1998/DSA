public class printXPowN {

    public static void main(String[] args) {
        System.out.println(printXPowN(2,10));
    }

    public static int printXPowN(int x,int n){
        if(n == 1){
            return x;
        }
        return x * printXPowN(x,n-1);
    }
}
