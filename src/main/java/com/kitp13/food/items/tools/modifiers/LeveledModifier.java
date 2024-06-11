package com.kitp13.food.items.tools.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class LeveledModifier implements Modifiers{

    public LeveledModifier(int level) {
        this.level = level;
    }

    private int level;

    public int getLevel(){
        return level;
    }
    public Modifiers setLevel(int value){
        this.level = value;
        return this;
    }
    @Override
    public String getName() {
        return "";
    }

    @Override
    public void onMine(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity, Random random) {
        Modifiers.super.onMine(stack, level, state, pos, entity, random);
    }

    @Override
    public void onAttack(ItemStack stack, LivingEntity target, LivingEntity attacker, Random random) {
        Modifiers.super.onAttack(stack, target, attacker, random);
    }
}
