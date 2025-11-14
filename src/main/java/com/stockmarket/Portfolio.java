package com.stockmarket;

public class Portfolio {

    private static final int MAX_HOLDINGS = 10;

    private final double cash;
    private final StockHolding[] holdings;
    private int holdingsCount;

    private static class StockHolding {
        Stock stock;
        int quantity;

        StockHolding(Stock stock, int quantity) {
            this.stock = stock;
            this.quantity = quantity;
        }
    }

    public Portfolio(double initialCash) {
        this.cash = initialCash;
        this.holdings = new StockHolding[MAX_HOLDINGS];
        this.holdingsCount = 0;
    }

    public void addStock(Stock stock, int quantity) {
        if (stock == null || quantity <= 0) {
            return;
        }

        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i].stock.equals(stock)) {
                holdings[i].quantity += quantity;
                return;
            }
        }

        if (holdingsCount < MAX_HOLDINGS) {
            holdings[holdingsCount] = new StockHolding(stock, quantity);
            holdingsCount++;
        }
    }

    public double calculateStockValue() {
        double totalValue = 0.0;
        for (int i = 0; i < holdingsCount; i++) {
            StockHolding holding = holdings[i];
            totalValue += holding.quantity * holding.stock.getInitialPrice();
        }
        return totalValue;
    }

    public double calculateTotalValue() {
        return cash + calculateStockValue();
    }

    public double getCash() {
        return cash;
    }

    public int getHoldingsCount() {
        return holdingsCount;
    }

    public int getStockQuantity(Stock stock) {
        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i].stock.equals(stock)) {
                return holdings[i].quantity;
            }
        }
        return 0;
    }
}