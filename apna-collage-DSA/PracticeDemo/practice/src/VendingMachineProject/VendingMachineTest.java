package VendingMachineProject;

import java.util.HashMap;
import java.util.Map;

public class VendingMachineTest {
    public static void main(String[] args) {
        // Test the updated VendingMachine class
        Map<String, Integer> initialProductQuantity = new HashMap<>();
        initialProductQuantity.put("Coke", 10);
        initialProductQuantity.put("Pepsi", 15);
        initialProductQuantity.put("Sprite", 12);

        Map<String, Integer> initialProductPrice = new HashMap<>();
        initialProductPrice.put("Coke", 25);
        initialProductPrice.put("Pepsi", 35);
        initialProductPrice.put("Sprite", 45);

        Map<Integer, Integer> initialCoinInventory = new HashMap<>();
        initialCoinInventory.put(1, 10);
        initialCoinInventory.put(5, 5);
        initialCoinInventory.put(10, 5);
        initialCoinInventory.put(25, 5);
        initialCoinInventory.put(50, 5);
        initialCoinInventory.put(100, 5);

        VendingMachine vendingMachine = new VendingMachine(initialProductQuantity, initialProductPrice, initialCoinInventory);

        // Display initial product quantities and prices
        vendingMachine.displayProductQuantities();
        vendingMachine.displayProductPrices();

        // Update product quantities and prices
        Map<String, Integer> newQuantities = new HashMap<>();
        newQuantities.put("Coke", 15);
        newQuantities.put("Pepsi", 20);
        vendingMachine.updateProductQuantities(newQuantities);

        Map<String, Integer> newPrices = new HashMap<>();
        newPrices.put("Coke", 30);
        newPrices.put("Pepsi", 40);
        vendingMachine.updateProductPrices(newPrices);

        // Display updated product quantities and prices
        vendingMachine.displayProductQuantities();
        vendingMachine.displayProductPrices();

        // Insert coins and buy products
        vendingMachine.insertCoin(50);
        vendingMachine.insertCoin(25);
        vendingMachine.buyProduct("Coke");

        // Display final product quantities and prices
        vendingMachine.displayProductQuantities();
        vendingMachine.displayProductPrices();
    }
}
