// code 13 friends Pairing

public class FriendsPairing {

    public static void main(String[] args) {
        System.out.println(friendsPairing(3));
    }

    public static int friendsPairing(int n){

        // Base Case
        if(n == 1 || n == 2){
            return n;
        }

        // single choice
        int fnm1 = friendsPairing(n-1);

        // pair
        int fnm2 = friendsPairing(n-2);
        int pairWays = (n-1) * fnm2;

        // Total Ways
        int totalWays = fnm1 + pairWays;

        // shotcut
        // return friendsPairing(n-1) + (n-1) * friendsPairing(n-2);

        return totalWays;
    }

}
