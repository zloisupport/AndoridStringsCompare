package com.mrikso.stringscompare;

public class StringItem
{
    private String nameString;
    private String valueString;
    private boolean isFormatted;
    private boolean isContainsFormatted;
    
    public StringItem() {
    }
    
    public StringItem(final String name, final String value, final boolean containsFormatted) {
        this.nameString = name;
        this.valueString = value;
        this.isContainsFormatted = containsFormatted;
    }
    
    public StringItem(final String name, final String value, final boolean containsFormatted, final boolean formatted) {
        this.nameString = name;
        this.valueString = value;
        this.isFormatted = formatted;
        this.isContainsFormatted = containsFormatted;
    }
    
    public boolean isFormatted() {
        return this.isFormatted;
    }
    
    public String getNameString() {
        return this.nameString;
    }
    
    public String getValueString() {
        return this.valueString;
    }
    
    public void setFormatted(final boolean formatted) {
        this.isFormatted = formatted;
    }
    
    public void setNameString(final String nameString) {
        this.nameString = nameString;
    }
    
    public void setValueString(final String valueString) {
        this.valueString = valueString;
    }
    
    public boolean isContainsFormatted() {
        return this.isContainsFormatted;
    }
    
    public void setContainsFormatted(final boolean containsFormatted) {
        this.isContainsFormatted = containsFormatted;
    }
}
