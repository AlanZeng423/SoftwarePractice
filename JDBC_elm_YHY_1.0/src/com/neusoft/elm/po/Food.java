package com.neusoft.elm.po;

public class Food {
    private int foodId;
    private String foodName;
    private String foodExplain;
    private double foodPrice;
    private int businessId;

    public int getFoodId() {
        return foodId;
    }
    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodExplain() {
        return foodExplain;
    }
    public void setFoodExplain(String foodExplain) {
        this.foodExplain = foodExplain;
    }

    public double getFoodPrice() {
        return foodPrice;
    }
    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getBusinessId() {
        return businessId;
    }
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    //重写toString函数，因为有些时候会出现直接输出food的情况
    public String toString() {
        return "Food{" +
                "食品编号=" + foodId +
                ", 食品名称='" + foodName + '\'' +
                ", 食品描述='" + foodExplain + '\'' +
                ", 食品价格=" + foodPrice +
                ", 所属商家编号=" + businessId +
                '}';
    }
}
