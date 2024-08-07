package com.kitp13.food.items.tools.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class TestModifier extends LeveledModifier {
    public static final String NAME = "TestModifier";

    public TestModifier(int level) {
        super(level);
    }
    public TestModifier() {
        super(0);
    }


    @Override
    public String getName(){
        return NAME;
    }

    @Override
    public MutableComponent tooltip(ItemStack stack) {
        return Component.literal("TestModifier Level " + this.getLevel());
    }

    @Override
    public void onMine(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity, Random random) {
        entity.addEffect(new MobEffectInstance(MobEffects.POISON, 10, this.getLevel() - 1));
    }

    @Override
    public ItemLike applyMaterial() {
        return Items.DIAMOND;
    }
}
