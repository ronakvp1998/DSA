package backtracking;

// code 5 Grid Ways
// count Total number of ways in which we can solve N Queens Problem
public class GridWays {

    public static void main(String[] args) {
        int n=3, m=3;
        System.out.println("Total number of ways= " + gridWays(0,0,n,m));

    }

    public static int gridWays(int i,int j, int n,int m){
        // base case
        if(i == n-1 && j == m-1){  // Condition for last cell
            System.out.println("---------------------Base Case 1---------------------");
            return 1;
        }else if(i == n || j == n){ // condition to check if the boundary of grid is crossed
            System.out.println("---------------------Base Case 2---------------------");
            return 0;
        }
        System.out.println("w1 i="+(i+1)+ ",j="+j+",n="+n+",m="+m);
        int w1 = gridWays(i+1, j, n, m);
        System.out.println("Backtrack w1=" +w1 + " -> w2 i="+(i+1)+ ",j="+j+",n="+n+",m="+m);
        int w2 = gridWays(i,j+1, n, m);
        System.out.println("backtrack w2=" + w2 +" -> w2 i="+i+ ",j="+(j+1)+",n="+n+",m="+m);
        return w1 + w2;
    }
}

//        w1 i=1,j=0,n=3,m=3
//        w1 i=2,j=0,n=3,m=3
//        w1 i=3,j=0,n=3,m=3
//            ---------------------Base Case 2---------------------
//        bactrack w1 -> w2 i=3,j=0,n=3,m=3
//        w1 i=3,j=1,n=3,m=3
//        ---------------------Base Case 2---------------------
//        bactrack w1 -> w2 i=3,j=1,n=3,m=3
//        ---------------------Base Case 1---------------------
//        bactrack w2 i=2,j=2,n=3,m=3
//        bactrack w2 i=2,j=1,n=3,m=3
//        bactrack w1 -> w2 i=2,j=0,n=3,m=3
//        w1 i=2,j=1,n=3,m=3
//        w1 i=3,j=1,n=3,m=3
//        ---------------------Base Case 2---------------------
//        bactrack w1 -> w2 i=3,j=1,n=3,m=3
//        ---------------------Base Case 1---------------------
//        bactrack w2 i=2,j=2,n=3,m=3
//        bactrack w1 -> w2 i=2,j=1,n=3,m=3
//        w1 i=2,j=2,n=3,m=3
//        ---------------------Base Case 1---------------------
//        bactrack w1 -> w2 i=2,j=2,n=3,m=3
//        ---------------------Base Case 2---------------------
//        bactrack w2 i=1,j=3,n=3,m=3
//        bactrack w2 i=1,j=2,n=3,m=3
//        bactrack w2 i=1,j=1,n=3,m=3
//        bactrack w1 -> w2 i=1,j=0,n=3,m=3
//        w1 i=1,j=1,n=3,m=3
//        w1 i=2,j=1,n=3,m=3
//        w1 i=3,j=1,n=3,m=3
//        ---------------------Base Case 2---------------------
//        bactrack w1 -> w2 i=3,j=1,n=3,m=3
//        ---------------------Base Case 1---------------------
//        bactrack w2 i=2,j=2,n=3,m=3
//        bactrack w1 -> w2 i=2,j=1,n=3,m=3
//        w1 i=2,j=2,n=3,m=3
//        ---------------------Base Case 1---------------------
//        bactrack w1 -> w2 i=2,j=2,n=3,m=3
//        ---------------------Base Case 2---------------------
//        bactrack w2 i=1,j=3,n=3,m=3
//        bactrack w2 i=1,j=2,n=3,m=3
//        bactrack w1 -> w2 i=1,j=1,n=3,m=3
//        w1 i=1,j=2,n=3,m=3
//        w1 i=2,j=2,n=3,m=3
//        ---------------------Base Case 1---------------------
//        bactrack w1 -> w2 i=2,j=2,n=3,m=3
//        ---------------------Base Case 2---------------------
//        bactrack w2 i=1,j=3,n=3,m=3
//        bactrack w1 -> w2 i=1,j=2,n=3,m=3
//        ---------------------Base Case 2---------------------
//        bactrack w2 i=0,j=3,n=3,m=3
//        bactrack w2 i=0,j=2,n=3,m=3
//        bactrack w2 i=0,j=1,n=3,m=3
//        Total number of ways= 6
