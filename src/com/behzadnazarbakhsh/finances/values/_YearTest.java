package com.behzadnazarbakhsh.finances.values;

import static org.junit.Assert.*;
import org.junit.*;


public class _YearTest {

    @Test
    public void nextYear(){
        Year thisYear = new Year(2026);
        assertEquals(new Year(2027), thisYear.nextYear());
    }
    @Test
    public void numberOfYearsInclusive(){
        Year thisYear = new Year(2026);
        assertEquals(25, thisYear.numberOfYearInclusive(new Year(2050)));
    }
    @Test
    public void valueObject(){
        Year year1a = new Year(2026);
        Year year1b = new Year(2026);
        Year year2 = new Year(2027);

        assertEquals("2026", year1a.toString());
        assertTrue("years with same value should be equal", year1a.equals(year1b));
        assertFalse("years with different values should not be equal", year1a.equals(year2) );
        assertTrue("years with same value should have same hashcode", year1a.hashCode() == year1b.hashCode());

    }
}
