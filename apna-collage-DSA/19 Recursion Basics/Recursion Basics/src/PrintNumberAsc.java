public class PrintNumberAsc {

    public static void main(String[] args) {
        int n = 10;
//        printNumAsc(1,n);
//        printIncreasing(n);
        printIncreasing2(n);
    }

    public static void printIncreasing2(int n){
        if(n==1){
            System.out.println(n + " ");
            return;
        }
        printIncreasing2(n-1);
        System.out.println(n + " ");
    }

    public static void printIncreasing(int n){
        if(n == 1){
            System.out.print(n + " ");
            return;
        }
        printIncreasing(n-1);
        System.out.print(n + " ");
    }


    public static void printNumAsc(int i,int n){
        if(i == n){
            System.out.print(n);
            return;
        }
        System.out.print(i + " ");
        printNumAsc(i+1,n);
    }
}
