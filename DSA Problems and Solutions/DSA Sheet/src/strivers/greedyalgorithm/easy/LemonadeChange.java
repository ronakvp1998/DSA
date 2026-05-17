package com.questions.strivers.greedyalgorithm.easy;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 860. Lemonade Change (Easy)
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
 * * ==================================================================================================
 * APPROACH: GREEDY ALGORITHM
 * ==================================================================================================
 * The core logic relies on a Greedy approach.
 * Since the bills are only $5, $10, and $20, they are multiples of each other.
 * A $5 bill is strictly more versatile than a $10 bill because a $5 bill can be used to give
 * change for both a $10 purchase and a $20 purchase. A $10 bill can ONLY be used to give
 * change for a $20 purchase.
 * * Therefore, when giving change for a $20 bill, we should ALWAYS be greedy and try to get rid
 * of our $10 bills first, holding onto our precious $5 bills for as long as possible.
 * ==================================================================================================
 */

public class LemonadeChange {

    public static void main(String[] args) {
        // Test Case 1: Expected true
        int[] bills1 = {5, 5, 5, 10, 20};
        System.out.println("Test Case 1 (Expected: true)  -> Result: " + lemonadeChange(bills1));

        // Test Case 2: Expected false (Not enough $5 bills for the last $20)
        int[] bills2 = {5, 5, 10, 10, 20};
        System.out.println("Test Case 2 (Expected: false) -> Result: " + lemonadeChange(bills2));

        // Test Case 3: Expected true (Giving three $5 bills as change for a $20)
        int[] bills3 = {5, 5, 5, 5, 20};
        System.out.println("Test Case 3 (Expected: true)  -> Result: " + lemonadeChange(bills3));
    }

    /**
     * Determines if we can provide correct change to every customer.
     * * @param bills Array of bills customers pay with ($5, $10, or $20)
     * @return true if all customers get correct change, false otherwise
     */
    public static boolean lemonadeChange(int[] bills) {
        // Track the count of $5 and $10 bills we currently hold in our cash register.
        // We do not need to track $20 bills because we never use a $20 bill as change.
        int fiveDollarBills = 0;
        int tenDollarBills = 0;

        // Iterate through each customer's bill in the queue
        for (int bill : bills) {

            // Case 1: Customer gives a $5 bill
            if (bill == 5) {
                // No change needed. Just put the $5 in our register.
                fiveDollarBills++;
            }

            // Case 2: Customer gives a $10 bill
            else if (bill == 10) {
                // We MUST give one $5 bill as change.
                if (fiveDollarBills == 0) {
                    // Edge Case / Failure: We don't have a $5 bill to give as change.
                    return false;
                }
                // Take the $5 from our register and put the $10 in.
                fiveDollarBills--;
                tenDollarBills++;
            }

            // Case 3: Customer gives a $20 bill
            else {
                // We MUST give $15 in change.
                // GREEDY CHOICE: Always prefer giving one $10 and one $5 over three $5s.
                // Why? Because $5 bills are more valuable for future transactions.
                if (tenDollarBills > 0 && fiveDollarBills > 0) {
                    // Hand out one $10 and one $5
                    tenDollarBills--;
                    fiveDollarBills--;
                }
                // Fallback: If we don't have a $10 bill, we must give three $5 bills.
                else if (fiveDollarBills >= 3) {
                    // Hand out three $5 bills
                    fiveDollarBills -= 3;
                }
                // Failure: We cannot make $15 in change with our current register.
                else {
                    return false;
                }
            }
        }

        // If we successfully process all customers without returning false, we succeeded!
        return true;
    }
}