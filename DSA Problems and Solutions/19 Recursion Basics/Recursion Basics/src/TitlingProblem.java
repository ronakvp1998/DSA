public class TitlingProblem {

    public static void main(String[] args) {
        System.out.println(titlingProblem(3)); // output 3
        System.out.println(titlingProblem(4)); // output 5

    }

    public static int titlingProblem(int n){ // 2 x n (floor size)
        // base case
        if(n == 0 || n == 1){
            return 1;
        }
        // kaam
        // vertical choice
        int fnm1 = titlingProblem(n-1);
        // horizontal choice
        int fnm2 = titlingProblem(n-2);
        int totWays = fnm1 + fnm2;
        return totWays;
    }
}
