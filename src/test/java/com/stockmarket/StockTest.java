package com.stockmarket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    private Stock stockCDR;
    private Stock stockCPS;
    private Stock stockCDR_clone;

    @BeforeEach
    void setUp() {
        stockCDR = new Stock("CDR", "CD Projekt", 300.0);
        stockCPS = new Stock("CPS", "Cyfrowy Polsat", 30.0);
        stockCDR_clone = new Stock("CDR", "CD Projekt SA", 350.0);
    }

    @Test
    @DisplayName("Test konstruktora i getterów klasy Stock")
    void testStockCreationAndGetters() {
        assertEquals("CDR", stockCDR.getSymbol());
        assertEquals("CD Projekt", stockCDR.getName());
        assertEquals(300.0, stockCDR.getInitialPrice());

        assertEquals("CPS", stockCPS.getSymbol());
        assertEquals("Cyfrowy Polsat", stockCPS.getName());
        assertEquals(30.0, stockCPS.getInitialPrice());
    }

    @Test
    @DisplayName("Test metody equals() - porównanie na podstawie symbolu")
    void testEquals() {
        assertTrue(stockCDR.equals(stockCDR), "Akcja powinna być równa sama sobie");
        assertTrue(stockCDR.equals(stockCDR_clone), "Akcje z tym samym symbolem powinny być równe");
        assertTrue(stockCDR_clone.equals(stockCDR), "Porównanie powinno być symetryczne");

        assertFalse(stockCDR.equals(stockCPS), "Akcje z różnymi symbolami nie powinny być równe");
        assertFalse(stockCPS.equals(stockCDR), "Porównanie powinno być symetryczne");

        assertFalse(stockCDR.equals(null), "Porównanie z null powinno zwrócić false");
        assertFalse(stockCDR.equals(new Object()), "Porównanie z obiektem innej klasy powinno zwrócić false");
    }

    @Test
    @DisplayName("Test metody hashCode() - zgodność z equals()")
    void testHashCode() {
        assertEquals(stockCDR.hashCode(), stockCDR_clone.hashCode(), "Równe akcje (ten sam symbol) muszą mieć ten sam hashCode");

        assertNotEquals(stockCDR.hashCode(), stockCPS.hashCode(), "Akcje o różnych symbolach powinny mieć różny hashCode");
    }
}