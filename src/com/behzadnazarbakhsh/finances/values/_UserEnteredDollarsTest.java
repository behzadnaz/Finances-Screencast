package com.behzadnazarbakhsh.finances.values;

import com.behzadnazarbakhsh.finances.ui.Resources;
import org.junit.*;
import static org.junit.Assert.*;

public class _UserEnteredDollarsTest {
    private UserEnteredDollars dollars1a = new UserEnteredDollars("1");
    UserEnteredDollars dollars2a = new UserEnteredDollars("2");
    private UserEnteredDollars dollarsWithSpaces = new UserEnteredDollars("  1  ");
    private UserEnteredDollars dollars2 = new UserEnteredDollars("1");
    private UserEnteredDollars invalid = new UserEnteredDollars("xxx");

    @Test
    public void parseNumbersAndDollarsAndNegativeSigns(){
       assertEquals("empty string", new ValidDollars(0), new UserEnteredDollars(""));
       assertEquals("just a number",new ValidDollars(42), new UserEnteredDollars("$42"));
       assertEquals("beginning dollar sign",new ValidDollars(42), new UserEnteredDollars("$42"));
       assertEquals("dollar sign only", new ValidDollars(0), new UserEnteredDollars("$"));
       assertEquals("decimals", new ValidDollars(42.13), new UserEnteredDollars("42.13"));
       assertEquals("one comma", new ValidDollars(1234), new UserEnteredDollars("1,234"));
       assertEquals("several commas",new ValidDollars(1234567), new UserEnteredDollars("12,34,567"));
       assertEquals("dysfunctional commas",new ValidDollars(17), new UserEnteredDollars(",,,1,,,,7"));
       assertEquals("negative dollars", new ValidDollars(-42), new UserEnteredDollars("-$42"));
       assertEquals("dollars negative", new ValidDollars(-42), new UserEnteredDollars("$-42"));
       assertEquals("negative number",new ValidDollars(-9.32), new UserEnteredDollars("-9.32"));
       assertEquals("negative and dollar sign only", new ValidDollars(0), new UserEnteredDollars("-$"));
       assertEquals("dollar and negative sign only", new ValidDollars(0), new UserEnteredDollars("$-"));
    }

    @Test
    public void parseParentheses(){
        assertEquals("open parenthesis only", new ValidDollars(0), new UserEnteredDollars("("));
        assertEquals("close parenthesis only", new InvalidDollars(), new UserEnteredDollars(")"));
        assertEquals("both parenthesis only", new ValidDollars(0), new UserEnteredDollars("()"));
        assertEquals("number in parentheses", new ValidDollars(-9), new UserEnteredDollars("(9)"));
        assertEquals("open parenthesis and number", new ValidDollars(-42), new UserEnteredDollars("(42"));
        assertEquals("close parenthesis and number", new ValidDollars(-42), new UserEnteredDollars("42)"));
    }

    @Test
    public void parseIllegals(){
        InvalidDollars invalid = new InvalidDollars();
        assertEquals(invalid, new UserEnteredDollars("x"));
        assertEquals(invalid, new UserEnteredDollars("40d"));
        assertEquals(invalid, new UserEnteredDollars("40f"));
        assertEquals(invalid, new UserEnteredDollars(")"));
        assertEquals(invalid, new UserEnteredDollars("NaN"));
    }

    @Test
    public void parseOutOfRangeAmount(){
        double tooLarge = Dollars.MAX_VALUE + 1;
        double tooSmall = Dollars.MIN_VALUE - 1;
        assertEquals("too large",invalid, new UserEnteredDollars("" + tooLarge));
        assertEquals("too small",invalid, new UserEnteredDollars("" + tooSmall));
    }

    @Test // This test handles special case where the core Java library hangs when
    // parsing a magic number
    public void parsingTheDoubleOfDeathDoesntHangMachine(){
        new UserEnteredDollars("2.2250738585072012e-308");
        //Should not hang -- if we reached this line,everything is ok.
    }

    @Test
    public void isValid(){
        assertTrue(dollars1a.isValid());
    }

    @Test
    public void getUserEnteredValue(){
        assertEquals(" Xy Z  ", new UserEnteredDollars(" Xy Z  ").getUserText());
    }

    @Test
    public void toCoreDataType(){
        assertEquals(1.234, new UserEnteredDollars("1.234").toCoreDataType(),0 );
    }


    @Test
    public void plus(){
        assertEquals("should be able to add two user-entered dollars",new ValidDollars(2), dollars1a.plus(dollars2));
        assertEquals("should be able to add user-entered dollars to valid dollars",new ValidDollars(4), dollars1a.plus(new ValidDollars(3)));
        assertEquals("should be able to add user-entered dollars to invalid dollars",new InvalidDollars(), dollars1a.plus(new InvalidDollars()));
        assertEquals("should be able to add valid dollars to user-entered dollars",new ValidDollars(5), new ValidDollars(4).plus(dollars1a));
        assertEquals("should be able to add invalid dollars to user-entered dollars",new InvalidDollars(), new InvalidDollars().plus(dollars1a));
    }

    @Test
    public void minus(){
        assertEquals("should be able to minus two user-entered dollars",new ValidDollars(0), dollars1a.minus(dollars2));
        assertEquals("should be able to minus user-entered dollars to valid dollars",new ValidDollars(-2), dollars1a.minus(new ValidDollars(3)));
        assertEquals("should be able to minus valid dollars to user-entered dollars",new ValidDollars(3), new ValidDollars(4).minus(dollars1a));
        assertEquals("should be able to minus invalid dollars to user-entered dollars",new InvalidDollars(), new InvalidDollars().min(dollars1a));

    }

    @Test
    public void subtractToZero(){
        assertEquals("should be able to subtract-to-zero two user-entered dollars",new ValidDollars(0), dollars1a.subtractToZero(dollars2));
        assertEquals("should be able to subtract-to-zero user-entered dollars to valid dollars",new ValidDollars(0), dollars1a.subtractToZero(new ValidDollars(3)));
        assertEquals("should be able to subtract-to-zero valid dollars to user-entered dollars",new ValidDollars(3), new ValidDollars(4).subtractToZero(dollars1a));
        assertEquals("should be able to subtract-to-zero invalid dollars to user-entered dollars",new InvalidDollars(), new InvalidDollars().subtractToZero(dollars1a));

    }

    @Test
    public void percentage(){
        assertEquals("should be able to percentage user-entered dollars",new ValidDollars(5), new UserEnteredDollars("50").percentage(10));
    }

    @Test
    public void min(){
        assertEquals("should be able to min two user-entered dollars",new ValidDollars(1), dollars1a.min(dollars2a));
        assertEquals("should be able to min user-entered dollars to valid dollars",new ValidDollars(1), dollars1a.min(new ValidDollars(3)));
        assertEquals("should be able to min valid dollars to user-entered dollars",new ValidDollars(1), new ValidDollars(4).min(dollars1a));
        assertEquals("should be able to min invalid dollars to user-entered dollars",new InvalidDollars(), new InvalidDollars().min(dollars1a));

    }

    @Test
    public void renderItSelf(){
        RenderTargetStub target = new RenderTargetStub();
        dollars1a.render(new Resources(), target);
        assertEquals("label text should be toString() value", dollars1a.toString(),target.text);
    }

    @Test
    public void valueObject(){
        assertEquals("$1", dollars1a.toString());

        assertTrue("dollars with the same string should be equal", dollars1a.equals(dollars2));
        assertTrue("dollars with the different string but same value should be equal", dollars1a.equals(dollarsWithSpaces));

        assertTrue("user-entered dollars should be comparable to valid dollars when equal", dollars1a.equals(new ValidDollars(1)));
        assertFalse("user-entered dollars should be unequal to valid dollars when unequal", dollars1a.equals(new ValidDollars(2)));

        assertFalse("user-entered dollars should be comparable to invalid dollars when valid", dollars1a.equals(new InvalidDollars()));
        assertTrue("user-entered dollars should be comparable to invalid dollars when invalid", invalid.equals(new InvalidDollars()));

        assertTrue("equal dollars should have same hash code even if string is different", dollars1a.hashCode() == dollarsWithSpaces.hashCode());
        assertFalse("should not blow when comparing to null", dollars1a.equals(null));
    }
}
