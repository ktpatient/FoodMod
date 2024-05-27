package com.kitp13.food.blocks.crops;

import com.kitp13.food.items.ModItems;
import com.kitp13.food.library.blocks.crops.BaseCropBlock;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class PineappleCropBlock extends BaseCropBlock {
    public PineappleCropBlock(Properties properties) {
        super(properties);
    }
    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.PINEAPPLE_SEEDS.get();
    }
}
