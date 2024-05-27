package com.kitp13.food.library;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;

public class ItemUtils {
    public static void spawnItemAtBlock(Level world, BlockPos pos, ItemStack stack) {
        if (!world.isClientSide()) {
            ItemEntity itemEntity = new ItemEntity((ServerLevel) world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, stack);
            world.addFreshEntity(itemEntity);
        }
    }
    public static boolean emptyOrNull(Player player, InteractionHand hand){
        return player.getItemInHand(hand).getItem() == Items.AIR;
    }
    public static boolean emptyOrNull(ItemStackHandler handler, int slot){
        return handler.getStackInSlot(slot).getItem().equals(Items.AIR);
    }
}
