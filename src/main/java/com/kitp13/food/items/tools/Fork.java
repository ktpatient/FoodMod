package com.kitp13.food.items.tools;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Fork extends Item {
    public static final String COL_KEY = "col";

    public Fork(Properties p_41383_) {
        super(p_41383_);
    }
    public static void setCol(ItemStack stack, int col){
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(COL_KEY, col);
    }

    @Override
    public Component getName(ItemStack p_41458_) {
        return super.getName(p_41458_);
    }

    public static int getCol(ItemStack stack){
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(COL_KEY)) {
            return tag.getInt(COL_KEY);
        }
        return 0xFFFFFF;
    }

}
