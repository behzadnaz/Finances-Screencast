package com.behzadnazarbakhsh.finances.values;

import com.behzadnazarbakhsh.finances.ui.Resources;
import com.behzadnazarbakhsh.finances.util.UnreachableCodeException;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class _InvalidDollarsTest {
    private InvalidDollars invalid1a;
    private InvalidDollars invalid1b;
    private ValidDollars valid;

    @Before
    public void setup(){
        invalid1a = new InvalidDollars();
        invalid1b = new InvalidDollars();
        valid = new ValidDollars(20);
    }

    @Test
    public void isValid(){
        assertFalse(invalid1a.isValid());
    }

    @Test (expected = UnreachableCodeException.class)
    public void toCoreDataType_IsAnError(){
        invalid1a.toCoreDataType();
    }

    @Test
    public void plus(){
        assertEquals(new InvalidDollars(), invalid1a.plus(invalid1b));
        assertEquals(new InvalidDollars(), invalid1a.plus(valid));
        assertEquals(new InvalidDollars(), valid.plus(invalid1b));
    }

    @Test
    public void minus(){
        assertEquals(new InvalidDollars(), invalid1a.minus(invalid1b));
        assertEquals(new InvalidDollars(), invalid1a.minus(valid));
        assertEquals(new InvalidDollars(), valid.minus(invalid1b));
    }

    @Test
    public void subtractToZero(){
        assertEquals(new InvalidDollars(), invalid1a.subtractToZero(invalid1b));
        assertEquals(new InvalidDollars(), invalid1a.subtractToZero(valid));
        assertEquals(new InvalidDollars(), valid.subtractToZero(invalid1b));
    }

    @Test
    public void percentage(){
        assertEquals(new InvalidDollars(), invalid1a.percentage(10));
    }

    @Test
    public void min(){
        assertEquals(new InvalidDollars(), invalid1a.min(invalid1b));
        assertEquals(new InvalidDollars(), invalid1a.min(valid));
        assertEquals(new InvalidDollars(), valid.min(invalid1b));
    }

    @Test
    public void flipSign(){
        assertEquals(new InvalidDollars(), invalid1a.flipSign());
    }

    @Test
    public void renderItself(){
        RenderTargetStub target = new RenderTargetStub();
        invalid1a.render(new Resources(), target);

        ImageIcon expectedIcon = new Resources().invalidDollarIcon();
        ImageIcon actualIcon = (ImageIcon) target.icon;

        assertEquals("image", expectedIcon.getImage(), actualIcon.getImage());
        assertEquals("icon description", "Invalid dollar amount",actualIcon.getDescription());
        assertEquals("tooltip message", "Invalid dollar amount", target.tooltipText);
    }

    @Test
    public void renderingShouldResetLabelToDefaultState(){
        RenderTargetStub target = new RenderTargetStub();
        target.text = "foodle";

        invalid1a.render(new Resources(), target);
        assertNull("should have no text", target.text);
    }

    @Test
    public void valueObject(){
        assertEquals("$???", invalid1a.toString());
        assertTrue("invalid dollars should always equal", invalid1a.equals(invalid1b));
        assertFalse("invalid dollars shouldn't equal valid dollars", invalid1a.equals(valid));
        assertFalse("invalid dollars shouldn't equal valid user-entered dollars", invalid1a.equals(new UserEnteredDollars("10")));
        assertTrue("invalid dollars should equal invalid user-entered dollars", invalid1a.equals(new UserEnteredDollars("XXX")));
        assertFalse("shouldn't be below up when comparing to null", invalid1a.equals(null));
        assertTrue("equal dollars should have same hash code", invalid1a.hashCode() == invalid1b.hashCode());
    }

}
