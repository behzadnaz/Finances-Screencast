package com.behzadnazarbakhsh.finances.domain;

import static org.junit.Assert.*;

import com.behzadnazarbakhsh.finances.values.*;
import org.junit.*;

public class _StockMarketProjectionTest {

    private static final Year STARTING_YEAR = new Year(2026) ;
    private static final Year ENDING_YEAR = new Year(2050);
    private static final Dollars STARTING_BALANCE = new ValidDollars(10000);
    private static final Dollars COST_BASIS = new ValidDollars(7000);
    private static final GrowthRate GROWTH_RATE = new GrowthRate(10);
    private static final TaxRate CAPITAL_GAINS_TAX_RATE = new TaxRate(25);
    private StockMarketYear firstYear;

    @Before
    public void setup(){
        firstYear = new StockMarketYear(STARTING_YEAR,STARTING_BALANCE, COST_BASIS, GROWTH_RATE,CAPITAL_GAINS_TAX_RATE);
    }

    @Test
    public void stockMarketContainsMultipleYears(){
        StockMarketProjection account = new StockMarketProjection(firstYear, ENDING_YEAR, new ValidDollars(0));
        assertEquals("# of years", 25, account.numberOfYears());
        assertEquals(STARTING_BALANCE, account.getYearOffset(0).startingBalance());
        assertEquals(new ValidDollars(11000), account.getYearOffset(1).startingBalance());
        assertEquals(new ValidDollars(12100), account.getYearOffset(2).startingBalance());
        assertEquals(new Year(2050), account.getYearOffset(24).year());
    }
    @Test
    public void stockMarketWithdrawsAsStandardAmountEveryYear(){
       StockMarketProjection account = new StockMarketProjection(firstYear, ENDING_YEAR, new ValidDollars(10));
       assertEquals("Year 0", new ValidDollars(10),account.getYearOffset(0).totalSellOrders());
       assertEquals("Year 1", new ValidDollars(10),account.getYearOffset(1).totalSellOrders());
       assertEquals("Year 24", new ValidDollars(10),account.getYearOffset(24).totalSellOrders());
    }
    @Test
    public void noCumulativeRoundingErrorInInterestCalculation(){
        StockMarketProjection account = new StockMarketProjection(firstYear, ENDING_YEAR, new ValidDollars(0));
        assertEquals(new ValidDollars(108347), account.getYearOffset(24).endingBalance());
    }
    @Test
    public void capitalGainsTaxCalculationWorksTheSomeWayAsSpreadsheet(){
        StockMarketProjection account = new StockMarketProjection(firstYear, ENDING_YEAR, new ValidDollars(695));
        assertEquals(new ValidDollars(8099), account.getYearOffset(24).endingBalance());
    }
}
