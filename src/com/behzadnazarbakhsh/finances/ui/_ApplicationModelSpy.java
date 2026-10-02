package com.behzadnazarbakhsh.finances.ui;

import com.behzadnazarbakhsh.finances.values.UserEnteredDollars;

import java.io.File;
import java.io.IOException;

public class _ApplicationModelSpy extends ApplicationModel{
    public UserEnteredDollars setStartingBalanceCalledWith;
    public UserEnteredDollars setCostBasisCalledWith;
    public UserEnteredDollars setYearlySpendingCalledWith;
    public File saveAsCalledWith;

    @Override
    public void setStartingBalance(UserEnteredDollars startingBalance){
        setStartingBalanceCalledWith = startingBalance;
    }

    @Override
    public void setStartingCostBasis(UserEnteredDollars startingCostBasis){
        setCostBasisCalledWith = startingCostBasis;
    }

    @Override
    public void setYearlySpending(UserEnteredDollars yearlySpending){
        setYearlySpendingCalledWith = yearlySpending;
    }

    @Override
    public void save(File saveFile) throws IOException {
        saveAsCalledWith = saveFile;
    }
}
