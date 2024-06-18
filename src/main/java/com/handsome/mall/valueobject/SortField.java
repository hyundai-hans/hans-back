package com.handsome.mall.valueobject;

public enum SortField {
    created_at("createdAt"),
    post_likes("postLikes.size");

    private final String field;

    SortField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
