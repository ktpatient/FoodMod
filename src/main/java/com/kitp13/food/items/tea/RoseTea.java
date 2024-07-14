package com.kitp13.food.items.tea;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class RoseTea extends Item {
    public RoseTea(Properties p_41383_) {
        super(p_41383_.food(new FoodProperties.Builder().saturationMod(0).nutrition(0).build()));
    }
    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level level, LivingEntity entity) {

        if (!level.isClientSide){
            ((Player)entity).getFoodData().setSaturation(40);
        }

        return super.finishUsingItem(p_41409_, level, entity);
    }

    @Override
    public boolean isEdible() {
        return true;
    }
    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }
}
