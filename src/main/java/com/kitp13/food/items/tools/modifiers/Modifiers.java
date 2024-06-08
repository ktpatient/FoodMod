package com.kitp13.food.items.tools.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface Modifiers {
    String NAME = "noName";
    String getName();

    default MutableComponent tooltip(ItemStack stack){
        return Component.literal("");
    }
    default MutableComponent shiftTooltip(ItemStack stack){
        return Component.literal("");
    }


    default void onMine(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity){
        return;
    }
    default void onAttack(ItemStack stack, LivingEntity target, LivingEntity attacker){
        return;
    }
}
