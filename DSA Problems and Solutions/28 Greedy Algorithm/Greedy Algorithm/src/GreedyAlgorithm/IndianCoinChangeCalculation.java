package GreedyAlgorithm;

// code 5 :- Indian coin change denomination

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class IndianCoinChangeCalculation {

    public static void main(String[] args) {
        Integer coins[] = { 1,2,5,10,20,50,100,500,2000};

        // sorting the Number system in descending order
        Arrays.sort(coins, Comparator.reverseOrder());

        int countOfCoins = 0;
        int amount = 5090;
        ArrayList<Integer> ans = new ArrayList<>();

        for(int i=0;i<coins.length;i++){
            if(coins[i] <= amount){
                while (coins[i] <= amount){
                    countOfCoins++;
                    ans.add(coins[i]);
                    amount -= coins[i];
                }
            }
        }

        System.out.println("Total (min) coins used = " + countOfCoins);

        for(int i=0;i<ans.size();i++){
            System.out.println(ans.get(i) + " ");
        }

        System.out.println();
    }
}
