package com.kitp13.food.blocks;

import com.kitp13.food.items.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;

public class PineapplePizzaBlock extends PizzaBlock{
    @Override
    public void afterEatenSlice(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.POISON,20*2,5));
    }

    @Override
    public ItemLike getSliceItem() {
        return ModItems.PINEAPPLE_PIZZA_SLICE.get();
    }
}
