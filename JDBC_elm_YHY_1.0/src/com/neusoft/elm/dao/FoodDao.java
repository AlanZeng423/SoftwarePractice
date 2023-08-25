package com.neusoft.elm.dao;

import com.neusoft.elm.po.Food;

import java.util.List;

public interface FoodDao {
    List<Food> listFoodByBusinessId(int businessId);

    int saveFood(Food food);

    Food getFoodById(int foodId);

    int updateFood(Food food);

    int removeFood(int foodId);
}
