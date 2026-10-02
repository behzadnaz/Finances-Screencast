package com.behzadnazarbakhsh.finances.values;

import com.behzadnazarbakhsh.finances.ui.RenderTarget;
import com.behzadnazarbakhsh.finances.ui.Resources;
import com.behzadnazarbakhsh.finances.util.Require;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class ValidDollars extends Dollars{

    private double amount;

    public ValidDollars(double rangeLimitedAmount){
        Require.that(inRange(rangeLimitedAmount), "dollar rangeLimitedAmount [ " + rangeLimitedAmount +" ] outside valid range!");
        this.amount = rangeLimitedAmount;
    }

    public boolean isValid(){
        return true;
    }

    @Override
    protected double toCoreDataType(){
        return amount;
    }


    public Dollars plus(Dollars operand) {
        if(!operand.isValid()) return new InvalidDollars();
        //if(operand instanceof UserEnteredDollars) return operand.plus(this);
        return create(this.amount + operand.toCoreDataType());
    }

    public Dollars minus(Dollars operand) {
        if(!operand.isValid()) return new InvalidDollars();
        return create(this.amount - operand.toCoreDataType());
    }

    public Dollars subtractToZero(Dollars operand) {
        if(!operand.isValid()) return new InvalidDollars();
        return create(Math. max(0, this.amount - operand.toCoreDataType()));
    }

    public Dollars percentage(double percentage){
        return create(amount * percentage / 100.0);
    }

    public Dollars min(Dollars operand) {
        if(!operand.isValid()) return new InvalidDollars();
        return create(Math.min(this.amount, operand.toCoreDataType()));
    }

    private boolean isNegative() {
        return amount < 0;
    }

    //FixMe why?
    private long roundOfPennies(){
        return roundOfPennies(this.amount);
    }

    private long roundOfPennies(double amount) {
        return Math.round(amount);
    }

    public void render(Resources resources, RenderTarget target){
        target.setIcon(null, null);
        target.setForeGroundColor(Color.BLACK);
        target.setText(this.toString());
        if (amount < 0) target.setForeGroundColor(Color.RED);
    }

    @Override
    public String toString(){
        if(isNegative()){
            return "(" + toAbsoluteValueString() + ")";
        }
        else {
            return toAbsoluteValueString();
        }
    }

    private String toAbsoluteValueString() {
        long roundedAmount = roundOfPennies(toCoreDataType());
        roundedAmount = Math.abs(roundedAmount);
        return "$" + NumberFormat.getInstance(Locale.US).format(roundedAmount);
    }

    @Override
    public int hashCode() {
        return (int)roundOfPennies();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        Dollars that = (Dollars) obj;
        if (!that.isValid()) return false;
        return roundOfPennies(this.toCoreDataType()) == roundOfPennies(that.toCoreDataType());
    }

}
