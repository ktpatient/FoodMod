package com.kitp13.food.items.tea;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class EffectTea extends Item {
    public EffectTea(Properties properties) {
        super(properties.food(new FoodProperties.Builder().saturationMod(0f).nutrition(0).build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level level, LivingEntity entity) {

        if (!level.isClientSide){
            entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,200,20));
        }

        return super.finishUsingItem(p_41409_, level, entity);
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }

    @Override
    public boolean isEdible() {
        return true;
    }

}
