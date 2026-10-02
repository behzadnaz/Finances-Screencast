package com.behzadnazarbakhsh.finances.domain;

import com.behzadnazarbakhsh.finances.values.*;

public class StockMarketYear {
    private Year year;
    private Dollars costBasis;
    private GrowthRate growthRate;
    private Dollars startingBalance;
    private Dollars totalSellOrders;
    private TaxRate capitalGainsTaxRate;

    public StockMarketYear(Year year, Dollars startingBalance, Dollars costBasis, GrowthRate growthRate, TaxRate capitalGainsTaxRate) {
        this.year = year;
        this.startingBalance = startingBalance;
        this.costBasis = costBasis;
        this.growthRate = growthRate;
        this.capitalGainsTaxRate = capitalGainsTaxRate;
        this.totalSellOrders = new ValidDollars(0);
    }
    public Year year() {
        return year;
    }
    //The total value of the investment at the beginning of the period, including principal and  accumulated gain
    public Dollars startingBalance() {
        return startingBalance;
    }
    //The original amount of money invested at the beginning of the period, excluding any gain or loses
    public Dollars startingCostBasis() {
        return costBasis;
    }
    private Dollars startingCapitalGains() {
        return startingBalance.minus(costBasis);
    }
    public GrowthRate growthRate() {
        return growthRate;
    }
    public TaxRate capitalGainsTaxRate() {
        return capitalGainsTaxRate;
    }
    public void sell(Dollars amount) {
        this.totalSellOrders = totalSellOrders.plus(amount);
    }
    //The portion of the capital gains that has been withdrawn or realized during the period
    private Dollars capitalGainWithdrawn() {
        return Dollars.min(startingCapitalGains(), totalSellOrders());
    }
    //The amount of tax owed or paid on realized capital gains
    public Dollars capitalGainTaxIncurred() {
        return capitalGainsTaxRate.compoundTaxFor(capitalGainWithdrawn());
    }
    public Dollars totalSellOrders() {
        return totalSellOrders;
    }
    public Dollars totalSold() {
        return totalSellOrders().plus(capitalGainTaxIncurred());
    }
    public Dollars growth() {
        return growthRate.growthFor(startingBalance.minus(totalSold()));
    }
    //The total value of the investment at the end of the period, equal to ending principal plus ending capital gains
    public Dollars endingBalance() {
        return startingBalance.minus(totalSold()).plus(growth());
    }
    //The amount of original invested capital remaining at the end of the period
    public Dollars endingCostBasis() {
        Dollars purchasesSold = totalSold().subtractToZero(startingCapitalGains());
        return startingCostBasis().minus(purchasesSold);
    }
    public StockMarketYear nextYear() {
        return new StockMarketYear(year.nextYear(),this.endingBalance(),this.endingCostBasis(),this.growthRate(), this.capitalGainsTaxRate());
    }
}
