package com.kitp13.food.items.tools.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TestModifier extends Modifiers {
    public static final String NAME = "TestModifier";
    @Override
    public String getName(){
        return NAME;
    }
    private final int level;

    public TestModifier(int level) {
        this.level = level;
    }

    @Override
    public MutableComponent tooltip(ItemStack stack) {
        return Component.literal("TestModifier Level " + level);
    }

    @Override
    public void onMine(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.POISON, 10, this.level - 1));
    }

    public int getLevel() {
        return level;
    }
}
