package com.kitp13.food.blocks.crops;

import com.kitp13.food.items.ModItems;
import com.kitp13.food.library.blocks.crops.BaseCropBlock;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class TomatoCropBlock extends BaseCropBlock {
    public TomatoCropBlock(Properties properties){
        super(properties);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.TOMATO_SEEDS.get();
    }
}
