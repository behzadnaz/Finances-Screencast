package com.behzadnazarbakhsh.finances.ui;

import com.behzadnazarbakhsh.finances.domain.StockMarketProjection;
import com.behzadnazarbakhsh.finances.domain.StockMarketYear;
import com.behzadnazarbakhsh.finances.persistence.UserConfiguration;
import com.behzadnazarbakhsh.finances.values.UserEnteredDollars;
import com.behzadnazarbakhsh.finances.values.ValidDollars;
import org.junit.*;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class _ApplicationModelTest {

    public ApplicationModel model;

    @Before
    public void setup(){
        UserConfiguration.STUB_OUT_FILE_SYSTEM_FOR_TESTING = true;
        model = new ApplicationModel();
    }

    @After
    public void teardown(){
        UserConfiguration.STUB_OUT_FILE_SYSTEM_FOR_TESTING =false;
    }

    @Test
    public void shouldStartWithDefaultStockMarket(){
        StockMarketProjection projection = model.stockMarketTableModel().stockMarketProjection();

        StockMarketYear startingYear = projection.getYearOffset(0);
        assertEquals(ApplicationModel.DEFAULT_STARTING_YEAR, startingYear.year());
        assertEquals(ApplicationModel.DEFAULT_STARTING_BALANCE, startingYear.startingBalance());
        assertEquals(ApplicationModel.DEFAULT_STARTING_COST_BASIS, startingYear.startingCostBasis());
        assertEquals(ApplicationModel.DEFAULT_GROWTH_RATE, startingYear.growthRate());
        assertEquals(ApplicationModel.DEFAULT_CAPITAL_GAINS_TAX_RATE, startingYear.capitalGainsTaxRate());
        assertEquals(ApplicationModel.DEFAULT_YEARLY_SPENDING, startingYear.totalSellOrders());

        assertEquals(25, projection.numberOfYears());
    }

    @Test
    public void shouldOnlyHaveOneInstanceOfStockTableModel(){
        assertTrue("should be same instance", model.stockMarketTableModel() == model.stockMarketTableModel());
    }

    @Test
    public void changingStartingBalanceShouldChangeStockMarketTableModel(){
        model.setStartingBalance(new UserEnteredDollars("123"));
        assertEquals( new ValidDollars(123), model.stockMarketTableModel().startingBalance());
    }

    @Test
    public void changingCostBasisShouldChangeStockMarketTableModel(){
        model.setStartingCostBasis(new UserEnteredDollars("50"));
       assertEquals(new ValidDollars(50), model.stockMarketTableModel().startingCostBasis());
    }

    @Test
    public void changingYearlySpendingShouldChangeStockMarketTableModel(){
        model.setYearlySpending(new UserEnteredDollars("200"));
        assertEquals(new ValidDollars(200), model.stockMarketTableModel().startingYearlySpending());
    }

    @Test
    @Ignore
    public void nameOfSaveFIle() throws IOException{
        assertNull("should not have save file if save not called", model.lastSavePathOrNullIfNeverSaved());
        File expectedFile = new File("foo");
        model.save(expectedFile);
        assertEquals("should have file after save called", expectedFile, model.lastSavePathOrNullIfNeverSaved());
    }

    @Test
    public void save() throws IOException{
       // model.save(new File("foo"));
        //assertTrue("file should have been saved", model.fileHasEverBeenSaved());
    }
}
