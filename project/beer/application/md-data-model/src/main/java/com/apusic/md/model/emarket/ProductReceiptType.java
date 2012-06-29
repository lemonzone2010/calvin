package com.apusic.md.model.emarket;

public enum ProductReceiptType {
  IN_RECEIPT("提供"),OUT_OF_RECEIPT("不提供");
  private String type;
  private ProductReceiptType(String type){
      this.type=type;
  }
  public String getType(){
      return type;
  }
}
