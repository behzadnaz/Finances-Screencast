package com.behzadnazarbakhsh.finances.values;

import com.behzadnazarbakhsh.finances.ui.RenderTarget;
import com.behzadnazarbakhsh.finances.ui.Resources;

public class UserEnteredDollars extends Dollars{
    private String userText;
    private  Dollars backingDollars;

    public UserEnteredDollars(String userText) {
        this.userText =userText;
        this.backingDollars = this.parse(userText);
    }

    private Dollars parse(String text){

        if(text.equals(")")) return new InvalidDollars();
        if(text.endsWith("d")) return new InvalidDollars();
        if(text.contains("e")) return new InvalidDollars();
        if(text.endsWith("f")) return new InvalidDollars();
        if(text.equals("NaN")) return new InvalidDollars();

        boolean paranthesis = false;
        if (text.startsWith("(")) {text = text.substring(1); paranthesis =true;}
        if (text.endsWith(")")) {text = text.substring(0, text.length()-1); paranthesis =true;}
        if (paranthesis) text = "-" + text;

        if (text.startsWith("$")) text = text.substring(1);
        if (text.startsWith("-$")) text = "-" + text.substring(2);
        if (text.isEmpty()) return new ValidDollars(0);
        if (text.equals("-")) return new ValidDollars(0);
        if (text.equals("-$")) return new ValidDollars(0);
        text =text.replace(",", "");

        try {
            return Dollars.create(Double.parseDouble(text));
        }
        catch(NumberFormatException e){
            return new InvalidDollars();
        }
    }

    @Override
    public boolean isValid() {
        return backingDollars.isValid();
    }

    @Override
    protected double toCoreDataType() {
        return backingDollars.toCoreDataType();
    }

    @Override
    public Dollars plus(Dollars operand) {
       return backingDollars.plus(operand);
    }

    @Override
    public Dollars minus(Dollars operand) {
        return backingDollars.minus(operand);
    }

    @Override
    public Dollars subtractToZero(Dollars operand) {
        return backingDollars.subtractToZero(operand);
    }

    @Override
    public Dollars percentage(double percentage) {
        return backingDollars.percentage(percentage);
    }

    @Override
    public Dollars min(Dollars operand) {
        return backingDollars.min(operand);
    }

    @Override
    public String toString(){
        return backingDollars.toString();
    }

    public String getUserText() {
        return userText;
    }

    @Override
    public boolean equals(Object obj){
            Dollars that = (Dollars) obj;
            return this.backingDollars.equals(that);
    }
    @Override
    public int hashCode(){
        return backingDollars.hashCode();
    }

    @Override
    public void render(Resources resources, RenderTarget target) {
        backingDollars.render(resources, target);
    }

}
