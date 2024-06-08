package com.kitp13.food.items.tools.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Modifiers {
    public String getName(){
        return "NoNameProvided";
    }
    public MutableComponent tooltip(ItemStack stack){
        return Component.literal("Empty");
    }

    public void onMine(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
    }
}
