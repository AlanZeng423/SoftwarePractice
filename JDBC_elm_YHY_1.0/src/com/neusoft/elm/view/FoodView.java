package com.neusoft.elm.view;

import com.neusoft.elm.po.Food;

import java.util.List;

public interface FoodView {
    public List<Food> showFoodList(int businessId);

    public void saveFood(int businessId);

    public void updateFood(int businessId);

    public void removeFood(int businessId);
}
