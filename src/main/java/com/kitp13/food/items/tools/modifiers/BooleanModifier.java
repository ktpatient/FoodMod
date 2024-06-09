package com.kitp13.food.items.tools.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BooleanModifier implements Modifiers{
    @Override
    public String getName() {
        return "";
    }

    @Override
    public void onMine(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        Modifiers.super.onMine(stack, level, state, pos, entity);
    }

    @Override
    public void onAttack(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Modifiers.super.onAttack(stack, target, attacker);
    }
}
