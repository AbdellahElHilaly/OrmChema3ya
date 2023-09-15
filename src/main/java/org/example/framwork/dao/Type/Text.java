package org.example.framwork.dao.Type;


public class Text {
    public String data;

    public Text(String value) {
        this.data = value;
    }

    public Text set(String value) {
        this.data = value;
        return this;
    }

    public Text() {

    }
    @Override
    public String toString() {
        return data;
    }
}
