package VendingMachineProject;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private Map<String, Integer> productQuantity;
    private Map<String, Integer> productPrice;
    private Map<Integer, Integer> coinInventory;
    private int currentBalance;

    public VendingMachine(Map<String, Integer> initialProductQuantity, Map<String, Integer> initialProductPrice, Map<Integer, Integer> initialCoinInventory) {
        this.productQuantity = new HashMap<>(initialProductQuantity);
        this.productPrice = new HashMap<>(initialProductPrice);
        this.coinInventory = new HashMap<>(initialCoinInventory);
        this.currentBalance = 0;
    }

    public void displayProductQuantities() {
        System.out.println("Product Quantities:");
        for (Map.Entry<String, Integer> entry : productQuantity.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " units");
        }
    }

    public void displayProductPrices() {
        System.out.println("Product Prices:");
        for (Map.Entry<String, Integer> entry : productPrice.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + "p");
        }
    }

    public void updateProductQuantities(Map<String, Integer> newProductQuantities) {
        this.productQuantity.putAll(newProductQuantities);
    }

    public void updateProductPrices(Map<String, Integer> newProductPrices) {
        this.productPrice.putAll(newProductPrices);
    }

    public void insertCoin(int coin) {
        if (isValidCoin(coin)) {
            currentBalance += coin;
            coinInventory.put(coin, coinInventory.get(coin) + 1);
        } else {
            System.out.println("Invalid coin. Please use 1, 5, 10, 25, 50, or 100.");
        }
    }

    public void buyProduct(String product) {
        if (productQuantity.containsKey(product)) {
            int price = productPrice.get(product);
            if (currentBalance >= price && productQuantity.get(product) > 0) {
                System.out.println("Dispensing " + product);
                productQuantity.put(product, productQuantity.get(product) - 1);
                currentBalance -= price;
                calculateChange();
            } else {
                System.out.println("Insufficient funds or out of stock for " + product);
            }
        } else {
            System.out.println("Product not found: " + product);
        }
    }

    private void calculateChange() {
        System.out.println("Returning change: " + currentBalance + "p");
        for (int coin : new int[]{100, 50, 25, 10, 5, 1}) {
            int count = currentBalance / coin;
            if (count > 0 && coinInventory.containsKey(coin)) {
                System.out.println(count + "x" + coin + "p");
                coinInventory.put(coin, coinInventory.get(coin) - count);
                currentBalance -= count * coin;
            }
        }
        currentBalance = 0;
    }

    private boolean isValidCoin(int coin) {
        return coin == 1 || coin == 5 || coin == 10 || coin == 25 || coin == 50 || coin == 100;
    }


}

