package com.vn.model;

public class KeyValueStringIntegerModel {

    private String key;
    private Integer value;

    public KeyValueStringIntegerModel() {
    }

    public KeyValueStringIntegerModel(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
