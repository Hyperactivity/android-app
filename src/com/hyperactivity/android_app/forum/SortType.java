package com.hyperactivity.android_app.forum;

public enum SortType {
    STANDARD(0),
    DATE(1),
    THUMBS_UP(2);

    private SortType(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }
}
