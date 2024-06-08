package com.kitp13.food.items.tools.modifiers;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BrittleModifier implements Modifiers{
    public static final String NAME = "Brittle";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public MutableComponent tooltip(ItemStack stack) {
        return Component.literal("Brittle").withStyle(ChatFormatting.GREEN);
    }

    @Override
    public MutableComponent shiftTooltip(ItemStack stack) {
        return Component.literal("Takes extra damage than normal, when breaking blocks").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#4085f5")));
    }

    @Override
    public void onMine(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        stack.hurtAndBreak(3,entity, (e) -> e.broadcastBreakEvent(e.getUsedItemHand()));
    }
}
