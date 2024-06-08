package com.kitp13.food.items.tools.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface Modifiers {
    public static final String NAME = "noName";
    public String getName();

    public MutableComponent tooltip(ItemStack stack);
    public MutableComponent shiftTooltip(ItemStack stack);


    public void onMine(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity);
}
