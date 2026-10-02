package com.behzadnazarbakhsh.finances.values;

import com.behzadnazarbakhsh.finances.util.Require;

import java.util.Objects;

public class GrowthRate {
    private double rateAsPercentage;

    public GrowthRate(int rateAsPercentage) {
        Require.that(rateAsPercentage > 0, "tax rate must be positive (and not zero); was " + rateAsPercentage);
        this.rateAsPercentage = rateAsPercentage;
    }
    public Dollars growthFor(Dollars amount) {
        return amount.percentage(rateAsPercentage);
    }
    @Override
    public String toString(){
        return rateAsPercentage + "%";
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GrowthRate that = (GrowthRate) o;
        return Double.compare(rateAsPercentage, that.rateAsPercentage) == 0;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(rateAsPercentage);
    }
}
