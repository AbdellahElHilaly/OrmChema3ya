package org.example.dao.Type;

public class Paragraph {
    private static String data;

    public Paragraph(String value) {
        this.data = value;
    }

    public Paragraph() {

    }
    public Paragraph set(String value) {
        this.data = value;
        return this;
    }

    @Override
    public String toString() {
        return data;
    }
}


