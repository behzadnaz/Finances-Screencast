package com.behzadnazarbakhsh.finances.values;

public class Year {
    private final int year;

    public Year(int year){
        this.year = year;
    }
    public Year nextYear() {
    return new Year(year + 1);
    }

    public int numberOfYearInclusive(Year endingYear){
        return endingYear.year - this.year + 1;
    }

    @Override
    public String toString() {
        return "" + year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Year other = (Year) obj;
        return year == other.year;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(year);
    }
}
