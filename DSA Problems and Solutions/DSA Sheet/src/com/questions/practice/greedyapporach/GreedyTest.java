package com.questions.practice.greedyapporach;

public class GreedyTest {

    /**
     PROBLEM STATEMENT: 860. Lemonade Change (Easy)
     * ==================================================================================================
     * At a lemonade stand, each lemonade costs $5. Customers are standing in a queue to buy from you
     * and order one at a time (in the order specified by bills). Each customer will only buy one
     * lemonade and pay with either a $5, $10, or $20 bill. You must provide the correct change to
     * each customer so that the net transaction is that the customer pays $5.
     * * Note that you do not have any change in hand at first.
     * * Given an integer array bills where bills[i] is the bill the ith customer pays, return true
     * if you can provide every customer with the correct change, or false otherwise.
     *
     * Example 1:
     * Input: bills = [5,5,5,10,20]
     * Output: true
     *
     * Example 2:
     * Input: bills = [5,5,10,10,20]
     * Output: false
     * */
    public static void main(String[] args) {
        int bills[] = {5,5,5,10,20};
        int five=0,ten=0,tw=0;

        for(int i=0;i<bills.length;i++){
            int coins = bills[i];
            if(coins == 5){
                five++;
            } else if (coins == 10) {
                if(five == 0){
                    System.out.println(false);
                    break;
                }
                five--;
                ten++;
            } else if (coins == 20) {
                if(ten > 0 && five > 0){
                    tw++;
                    ten--;
                    five--;
                } else if (five >= 3) {
                    five-=3;
                }else{
                    System.out.println(false);
                    break;
                }
            }
        }
        System.out.println(true);
    }

}
