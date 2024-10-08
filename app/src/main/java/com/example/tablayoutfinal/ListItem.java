package com.example.tablayoutfinal;

public class ListItem {
    private String line1;
    private String line2;
    private String line3;
    private String line4;
    private String line5;

    public ListItem(String line1, String line2, String line3, String line4, String line5) {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
        this.line5 = line5;
    }

    // Add getters and setters as needed
    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getLine3() {
        return line3;
    }

    public String getLine4() {
        return line4;
    }

    public String getLine5() {
        return line5;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public void setLine5(String line5) {
        this.line5 = line5;
    }

    @Override
    public String toString() {
        return line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + line5;
    }
}