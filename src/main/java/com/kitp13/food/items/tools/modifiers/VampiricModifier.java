package com.kitp13.food.items.tools.modifiers;

import com.kitp13.food.library.chat.Components;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class VampiricModifier extends BooleanModifier{
    public static final String NAME = "Vampiric";
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
        return Components.ColoredPart(this.tooltip(stack)," Shift Description", ChatFormatting.AQUA);
    }

    @Override
    public void onAttack(ItemStack stack, LivingEntity target, LivingEntity attacker, Random random) {
        attacker.heal(1.0f);
    }
}
