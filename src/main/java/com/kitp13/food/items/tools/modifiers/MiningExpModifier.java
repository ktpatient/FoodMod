package com.kitp13.food.items.tools.modifiers;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class MiningExpModifier extends BooleanModifier {
    public static final String NAME = "MiningExp";

    @Override
    public String getName() {
        return NAME;
    }
    @Override
    public MutableComponent tooltip(ItemStack stack) {
        return Component.literal(NAME);
    }

    @Override
    public MutableComponent shiftTooltip(ItemStack stack) {
        return Component.empty().append(this.tooltip(stack)).append(Component.literal(" Shift Description").withStyle(ChatFormatting.AQUA));
    }

    @Override
    public void onMine(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity, Random random) {
        if (random.nextInt(10)>5){
            ExperienceOrb.award((ServerLevel) level,pos.getCenter(),5);
        }
        super.onMine(stack, level, state, pos, entity, random);
    }
}
