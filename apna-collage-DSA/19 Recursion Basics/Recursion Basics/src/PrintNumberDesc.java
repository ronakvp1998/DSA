public class PrintNumberDesc {

    public static void main(String[] args) {
        // print num from n to 1 descending using recursion
        int n = 10;
        printNumberDesc(n);
    }

    public static void printNumberDesc(int n){
//        // 1 defining the base case
        if(n==1){
            System.out.print(1);
            return;
        }
//        // 2 defining the operation to be perform
        System.out.print(n + " ");
//        // 3 calling the inner function
        printNumberDesc(n-1);


    }

//    public static void printNumberDesc(int n){
//        // 1 defining the base case
//        if(n == 1){
//            System.out.println(1);
//            return ;
//        }
//        // 2 defining the operation to be perform
//        System.out.print(n + " ");
//        // 3 calling the inner function
//        printNumberDesc(n-1);
//
//    }

}
