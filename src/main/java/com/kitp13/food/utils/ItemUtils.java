package com.kitp13.food.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemUtils {
    public static void spawnItemOnGround(Level world, BlockPos pos, ItemStack stack) {
        if (!world.isClientSide()) {
            ItemEntity itemEntity = new ItemEntity((ServerLevel) world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack);
            world.addFreshEntity(itemEntity);
        }
    }
}
