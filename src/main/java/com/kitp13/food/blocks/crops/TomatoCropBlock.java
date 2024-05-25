package com.kitp13.food.blocks.crops;

import com.kitp13.food.items.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.properties.Property;

public class TomatoCropBlock extends BaseCropBlock{
    public TomatoCropBlock(Properties properties){
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.TOMATO_SEEDS.get();
    }
}
