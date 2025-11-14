package com.stockmarket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {

    private Portfolio portfolio;
    private final Stock stockCDR = new Stock("CDR", "CD Projekt", 100.0);
    private final Stock stockCPS = new Stock("CPS", "Cyfrowy Polsat", 20.0);
    private final Stock stockALE = new Stock("ALE", "Allegro", 50.0);

    @BeforeEach
    void setUp() {
        portfolio = new Portfolio(10000.0);
    }

    @Test
    @DisplayName("Test pustego portfela")
    void testEmptyPortfolio() {
        assertEquals(10000.0, portfolio.getCash());
        assertEquals(0, portfolio.getHoldingsCount());
        assertEquals(0.0, portfolio.calculateStockValue());
        assertEquals(10000.0, portfolio.calculateTotalValue());
        assertEquals(0, portfolio.getStockQuantity(stockCDR));
    }

    @Test
    @DisplayName("Test dodawania pierwszej akcji")
    void testAddingFirstStock() {
        portfolio.addStock(stockCDR, 10);

        assertEquals(10000.0, portfolio.getCash(), "Gotówka nie powinna się zmienić na tym etapie");
        assertEquals(1, portfolio.getHoldingsCount());
        assertEquals(10, portfolio.getStockQuantity(stockCDR));
        assertEquals(0, portfolio.getStockQuantity(stockCPS), "Inna akcja nie powinna być w portfelu");
        assertEquals(1000.0, portfolio.calculateStockValue());
        assertEquals(11000.0, portfolio.calculateTotalValue());
    }

    @Test
    @DisplayName("Test dodawania tej samej akcji wielokrotnie (sumowanie)")
    void testAddingSameStockMultipleTimes() {
        portfolio.addStock(stockCDR, 10);
        portfolio.addStock(stockCDR, 5);

        assertEquals(1, portfolio.getHoldingsCount(), "Liczba pozycji nie powinna wzrosnąć");
        assertEquals(15, portfolio.getStockQuantity(stockCDR), "Ilości powinny się zsumować");
        assertEquals(1500.0, portfolio.calculateStockValue());
        assertEquals(11500.0, portfolio.calculateTotalValue());
    }

    @Test
    @DisplayName("Test dodawania różnych akcji")
    void testAddingDifferentStocks() {
        portfolio.addStock(stockCDR, 10);
        portfolio.addStock(stockCPS, 50);

        assertEquals(2, portfolio.getHoldingsCount(), "Powinny być dwie różne pozycje");
        assertEquals(10, portfolio.getStockQuantity(stockCDR));
        assertEquals(50, portfolio.getStockQuantity(stockCPS));
        assertEquals(2000.0, portfolio.calculateStockValue(), "Wartość akcji = 1000 + 1000");
        assertEquals(12000.0, portfolio.calculateTotalValue(), "Wartość całkowita = 10000 + 2000");
    }

    @Test
    @DisplayName("Test obliczeń wartości dla złożonego portfela")
    void testComplexValueCalculation() {
        portfolio.addStock(stockCDR, 5);
        portfolio.addStock(stockCPS, 20);
        portfolio.addStock(stockALE, 10);
        portfolio.addStock(stockCDR, 3);

        assertEquals(3, portfolio.getHoldingsCount());
        assertEquals(8, portfolio.getStockQuantity(stockCDR));
        assertEquals(20, portfolio.getStockQuantity(stockCPS));
        assertEquals(10, portfolio.getStockQuantity(stockALE));

        assertEquals(1700.0, portfolio.calculateStockValue());
        assertEquals(11700.0, portfolio.calculateTotalValue());
    }

    @Test
    @DisplayName("Test brzegowy - dodawanie akcji do pełnego portfela")
    void testAddingStockToFullPortfolio() {
        for (int i = 0; i < 10; i++) {
            Stock s = new Stock("S" + i, "Stock " + i, 10.0);
            portfolio.addStock(s, 1);
        }

        assertEquals(10, portfolio.getHoldingsCount(), "Portfel powinien być pełny");

        portfolio.addStock(stockCDR, 5);

        assertEquals(10, portfolio.getHoldingsCount(), "Liczba pozycji nie powinna wzrosnąć powyżej 10");
        assertEquals(0, portfolio.getStockQuantity(stockCDR), "11-ta akcja nie powinna zostać dodana");

        Stock s1 = new Stock("S1", "Stock 1", 10.0);
        portfolio.addStock(s1, 9);
        assertEquals(10, portfolio.getHoldingsCount());
        assertEquals(10, portfolio.getStockQuantity(s1), "Ilość istniejącej akcji powinna się zaktualizować");
    }

    @Test
    @DisplayName("Test dodawania akcji z niepoprawną ilością lub null")
    void testAddingInvalidStock() {
        portfolio.addStock(stockCDR, 0);
        assertEquals(0, portfolio.getHoldingsCount(), "Akcje z ilością 0 nie powinny być dodawane");

        portfolio.addStock(stockCDR, -5);
        assertEquals(0, portfolio.getHoldingsCount(), "Akcje z ilością ujemną nie powinny być dodawane");

        portfolio.addStock(null, 10);
        assertEquals(0, portfolio.getHoldingsCount(), "Akcje null nie powinny być dodawane");
    }
}