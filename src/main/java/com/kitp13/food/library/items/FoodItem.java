package com.kitp13.food.library.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class FoodItem extends Item {
    public FoodItem(Properties p_41383_, int nutrition, float saturation) {
        super(p_41383_.food(new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation).build()));
    }
}
