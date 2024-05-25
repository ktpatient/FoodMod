package com.kitp13.food.entity.blocks;

import com.kitp13.food.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PizzaBlockTileEntity extends BlockEntity {
    public PizzaBlockTileEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlocks.PIZZA_TILE_ENTITY.get(), p_155229_, p_155230_);
    }
}
