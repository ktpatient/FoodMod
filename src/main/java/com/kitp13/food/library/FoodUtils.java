package com.kitp13.food.library;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FoodUtils {
    public static void PlayEatingSound(Level level, BlockPos pos) {
        level.playLocalSound(pos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 1.0f, 1.0f, true);
    }
    public static void FeedPlayer(Player player, int nutrition, float saturation){
        player.getFoodData().eat(nutrition,saturation);
    }
}
