package com.kitp13.food.items.tools.modifiers;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class VampiricModifier implements Modifiers{
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
        return Component.empty().append(this.tooltip(stack)).append(Component.literal(" Shift Description").withStyle(ChatFormatting.AQUA));
    }

    @Override
    public void onAttack(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        attacker.heal(1.0f);
    }
}
