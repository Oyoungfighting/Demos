package com.example.intentdemo;

/**
 * @author OyoungZh
 * @brief description
 * @date 2023-04-03
 */
public class Item {
    private int resourceId;
    private String text;

    public Item(int resourceId, String text) {
        this.resourceId = resourceId;
        this.text = text;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
