package com.behzadnazarbakhsh.finances.ui;

import com.behzadnazarbakhsh.finances.domain.*;
import com.behzadnazarbakhsh.finances.util.UnreachableCodeException;
import com.behzadnazarbakhsh.finances.values.Dollars;
import com.behzadnazarbakhsh.finances.values.SelfRenderable;
import com.behzadnazarbakhsh.finances.values.Year;

import javax.swing.table.AbstractTableModel;

public class StockMarketTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    public static final String[] COLUMN_TITLE = {"Year", "Starting Balance", "Cost Basis", "Sell Orders","Taxes", "Growth","Ending Balance"};
    public static final Class[] COLUMN_CLASSES = {Year.class, SelfRenderable.class, SelfRenderable.class, SelfRenderable.class, SelfRenderable.class,SelfRenderable.class, SelfRenderable.class};

    private StockMarketProjection projection;

    public StockMarketTableModel(StockMarketProjection projection){
        this.projection = projection;
    }

    public void setProjection(StockMarketProjection projection) {
        this.projection = projection;
        this.fireTableDataChanged();
    }

    public StockMarketProjection stockMarketProjection() {
        return projection;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_TITLE.length;
    }

    @Override
    public String getColumnName(int column){
        return COLUMN_TITLE[column];
    }

   @Override
   public Class<?> getColumnClass(int column){
        return COLUMN_CLASSES[column];
   }

    @Override
    public int getRowCount() {
        return projection.numberOfYears();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StockMarketYear currentYear = projection.getYearOffset(rowIndex);
        switch (columnIndex){
            case 0: return currentYear.year();
            case 1: return currentYear.startingBalance();
            case 2: return currentYear.startingCostBasis();
            case 3: return currentYear.totalSellOrders().flipSign();
            case 4: return currentYear.capitalGainTaxIncurred().flipSign();
            case 5: return currentYear.growth();
            case 6: return currentYear.endingBalance();
            default: throw new UnreachableCodeException();
        }
    }

    public Dollars startingBalance() {
        return projection.getYearOffset(0).startingBalance();
    }

    public Dollars startingCostBasis(){ return projection.getYearOffset(0).startingCostBasis();}

    public Dollars startingYearlySpending(){ return projection.getYearOffset(0).totalSellOrders();}
}
