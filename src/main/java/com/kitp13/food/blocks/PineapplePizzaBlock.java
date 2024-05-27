package com.kitp13.food.blocks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class PineapplePizzaBlock extends PizzaBlock{
    @Override
    public void afterEatenSlice(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.POISON,20*2,5));
    }
}
