package com.kitp13.food.blocks.crops;

import com.kitp13.food.items.ModItems;
import net.minecraft.world.level.ItemLike;

public class PineappleCropBlock extends BaseCropBlock{
    public PineappleCropBlock(Properties properties) {
        super(properties);
    }
    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.PINEAPPLE_SEEDS.get();
    }
}
