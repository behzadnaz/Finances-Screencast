package com.behzadnazarbakhsh.finances.values;

import static org.junit.Assert.*;

import com.behzadnazarbakhsh.finances.ui.Resources;
import com.behzadnazarbakhsh.finances.util.RequireException;
import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class _ValidDollarsTest {

    private Dollars twentyDollars = new ValidDollars(20);
    private Dollars minusTwentyDollars = new ValidDollars(-20);

    private Dollars oneDollar = new ValidDollars(1);
    private Dollars minusOneDollar = new ValidDollars(-1);
    private Dollars zeroDollars = new ValidDollars(0);

    private Dollars MAX_VALID = new ValidDollars(Dollars.MAX_VALUE);
    private Dollars MIN_VALID = new ValidDollars(Dollars.MIN_VALUE);

    @Test
    public void cannotConstructDollarsOutsideValidRange(){
        try {
            new ValidDollars(Dollars.MAX_VALUE + 1);
            fail("expected overflow!");
        }catch (RequireException e){
         // expected
        }
        try {
            new ValidDollars(Dollars.MIN_VALUE - 1);
            fail("expected underflow!");
        }
        catch (RequireException e){
            // expected
        }
        try {
            new ValidDollars(Double.NaN);
            fail("expected NaN failure");

        }catch (RequireException e){
            // expected
        }
    }

    @Test
    public void isInvalid(){
        assertTrue(new ValidDollars(42).isValid());
    }

    @Test
    //Resist the temptation to make this public! That is a design smell.
    public void toCoreDataType(){
        //FixMe: Why (ValidDollars) must be used when I use the factory pattern i ValidDollar class
        assertEquals(12.34567891 , ((ValidDollars)new ValidDollars(12.34567891)).toCoreDataType(), 0);
    }

    @Test
    public void addition(){
        assertEquals( "addition", new ValidDollars(40), new ValidDollars(10).plus(new ValidDollars(30)));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.plus(oneDollar));
        assertEquals("underflow", new InvalidDollars(), MIN_VALID.plus(minusOneDollar));
    }

    @Test
    public void subtraction(){
        assertEquals("positive result", twentyDollars, new ValidDollars(50).minus(new ValidDollars(30)));
        assertEquals("negative result", new ValidDollars(-60), new ValidDollars(40).minus(new ValidDollars(100)));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.minus(minusOneDollar));
        assertEquals("underflow", new InvalidDollars(), MIN_VALID.minus(oneDollar));
    }

    @Test
    public void subtractToZero(){
        assertEquals("positive result", twentyDollars, new ValidDollars(50).subtractToZero(new ValidDollars(30)));
        assertEquals("no negative result--return zero instead", zeroDollars, new ValidDollars(40).subtractToZero(new ValidDollars(100)));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.subtractToZero(minusOneDollar));
    }

    @Test
    public void flipSign(){
      assertEquals("zero is unchanged", zeroDollars, new ValidDollars(0).flipSign());
      assertEquals("positive to negative", minusTwentyDollars, twentyDollars.flipSign());
        assertEquals("negative to positive", twentyDollars, minusTwentyDollars.flipSign());
    }

    @Test
    public void percentage(){
        assertEquals(twentyDollars, new ValidDollars(100).percentage(20));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.percentage(200));
    }

    @Test
    public void min(){
        Dollars value1 = twentyDollars;
        Dollars value2 = new ValidDollars(30);
        assertEquals("value 1", twentyDollars, Dollars.min(value1,value2));
        assertEquals("value 2", twentyDollars, Dollars.min(value2,value1));
    }

    @Test
    public void renderItSelf(){
        RenderTargetStub target = new RenderTargetStub();
        twentyDollars.render(new Resources(), target);
        assertEquals("label text should be toString() value", twentyDollars.toString(),target.text);
    }

    @Test
    public void renderNegativeValuesInRed(){
        RenderTargetStub target = new RenderTargetStub();
        minusTwentyDollars.render(new Resources(), target);
        assertEquals("red when negative", Color.RED, target.foregroundcolor);
    }

    @Test
    public void renderZeroAndPositiveInBlack(){
        RenderTargetStub target = new RenderTargetStub();
        zeroDollars.render(new Resources(), target);
        assertEquals("black when zero", Color.BLACK, target.foregroundcolor);

        target = new RenderTargetStub();
        twentyDollars.render(new Resources(), target);
        assertEquals("black when positive", Color.BLACK, target.foregroundcolor);
    }

    @Test
    public void renderingShouldResetLabelToDefaultState(){
        RenderTargetStub target = new RenderTargetStub();
        target.icon = new ImageIcon();
        target.tooltipText = "bogus tooltip"; //target.setToolTipText("bogus tooltip");
        target.foregroundcolor = Color.BLACK; //target.setForeGroundColor(Color.BLACK);

        twentyDollars.render(new Resources(), target);
        assertNull("Should not have icon",target.icon);
        assertNull("Should not have tooltip",target.tooltipText);
        assertEquals("foreground color", Color.BLACK, target.foregroundcolor);
    }

    @Test
    public void equalsIgnoresPennies(){
        assertTrue("should round down",new ValidDollars(10).equals(new ValidDollars(10.10)));
        assertTrue("should round up", new ValidDollars(10).equals(new ValidDollars(9.90)));
        assertTrue("should round up when we have exactly 50 cents",new ValidDollars(11).equals(new ValidDollars(10.50)));
    }

    @Test
    public void hashcodeIgnoresPenniesToo(){
        assertTrue("should round down", new ValidDollars(10).hashCode() == new ValidDollars(10.10).hashCode());
        assertTrue("should round up", new ValidDollars(10).hashCode() == new ValidDollars(9.90).hashCode());
        assertTrue("should round up when we have exactly 50 cents",new ValidDollars(11).hashCode() == new ValidDollars(10.50).hashCode());
    }
    @Test
    public void toStringIgnorePennies(){
        assertEquals("should round down", "$10", new ValidDollars(10.10).toString());
        assertEquals("should round up", "$10", new ValidDollars(9.90).toString());
        assertEquals("should round up when we have exactly 50 cents", "$11", new ValidDollars(10.50).toString());
    }

    @Test
    public void toStringFormatsLongNumberWithCommas(){
        assertEquals("$1,234", new ValidDollars(1234).toString());
        assertEquals("$12,345,678", new ValidDollars(12345678).toString());
        assertEquals("$123,456,789", new ValidDollars(123456789 ).toString());
    }

    @Test
    public void toStringFormatNegativeNumberWithParentheses(){
        assertEquals("($20)", minusTwentyDollars.toString());
    }

    @Test
    public void toStringFormatsWithUsaStyleEvenWhenThatIsntTheDefault(){
        try{
            Locale.setDefault(Locale.FRANCE);
            assertEquals("$1,234", new ValidDollars(1234).toString());
        }
        finally {
            Locale.setDefault(Locale.US);
        }
    }

    @Test
    public void valueObject(){
        Dollars dollars1a = new ValidDollars(10);
        Dollars dollars2a = new ValidDollars(10);
        Dollars dollars2 = twentyDollars;

        assertEquals("$10", dollars1a.toString());
        assertTrue("dollars with same amounts should be equal", dollars1a.equals(dollars2a));
        assertFalse("dollars with different amounts should not be equal", dollars1a.equals(dollars2));
        assertFalse("valid dollars are not equal to invalid dollars", dollars1a.equals(new InvalidDollars()));
        assertTrue("equal dollars should have the same hashcode", dollars1a.hashCode() == dollars2a.hashCode());
        assertFalse("shouldn't be below up when comparing to null", dollars1a.equals(null));
        assertTrue("valid dollar should be comparable to user-entered dollars when equal", dollars1a.equals(new UserEnteredDollars("10")));
        assertFalse("valid dollar should be comparable to user-entered dollars when unequal", dollars1a.equals(new UserEnteredDollars("20")));

    }
}
