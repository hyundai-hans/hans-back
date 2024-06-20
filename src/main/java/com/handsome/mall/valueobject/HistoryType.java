package com.handsome.mall.valueobject;

public enum HistoryType {
  BANNER("banner");

  private final String value;

  HistoryType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
