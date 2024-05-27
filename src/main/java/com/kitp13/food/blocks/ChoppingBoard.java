package com.kitp13.food.blocks;

import com.kitp13.food.entity.blocks.ChoppingBoardEntity;
import com.kitp13.food.library.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class ChoppingBoard extends BaseEntityBlock {
    public static final VoxelShape SHAPE = makeShape();
    protected ChoppingBoard(Properties p_49224_) {
        super(p_49224_);
    }
    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.125, 0.875), BooleanOp.OR);
        return shape;
    }


    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return (BlockEntity)new ChoppingBoardEntity(blockPos,blockState);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState p_60555_, @NotNull BlockGetter p_60556_, @NotNull BlockPos p_60557_, @NotNull CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        ChoppingBoardEntity entity = (ChoppingBoardEntity) level.getBlockEntity(pos);
        assert entity != null;
        // Main.LOGGER.info(entity.itemStackHandler.getStackInSlot(ChoppingBoardEntity.SLOT_INPUT));
        if (ItemUtils.emptyOrNull(entity.itemStackHandler, ChoppingBoardEntity.SLOT_INPUT)){
            entity.itemStackHandler.insertItem(ChoppingBoardEntity.SLOT_INPUT,player.getItemInHand(hand).split(1), false);
            player.getItemInHand(hand).shrink(1);
            entity.setChanged();
            level.sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), 2);
        } else if (ItemUtils.emptyOrNull(player,hand)){
            player.addItem(entity.itemStackHandler.extractItem(ChoppingBoardEntity.SLOT_INPUT,1,false));
            entity.setChanged();
            level.sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), 2);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        ItemUtils.spawnItemAtBlock(level,pos,((ChoppingBoardEntity) Objects.requireNonNull(level.getBlockEntity(pos))).itemStackHandler.getStackInSlot(ChoppingBoardEntity.SLOT_INPUT));
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
