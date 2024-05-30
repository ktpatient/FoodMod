package com.kitp13.food.entity.blocks;

import com.kitp13.food.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BerryBushEntity extends BlockEntity {
    public BerryBushEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlocks.BERRY_BUSH_ENTITY.get(), p_155229_, p_155230_);
    }
}
