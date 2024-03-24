package com.example.ShopSaver;

public class ListItem {
    private String text;
    private boolean isActivo;

    public ListItem(String text, boolean isActivo) {
        this.text = text;
        this.isActivo = isActivo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getActivo() {
        return isActivo;
    }


    public void changeActivo() {
        isActivo = !isActivo;
    }
}