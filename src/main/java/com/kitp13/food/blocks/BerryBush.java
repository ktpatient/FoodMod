package com.kitp13.food.blocks;

import com.kitp13.food.items.ModItems;
import com.kitp13.food.library.ItemUtils;
import com.kitp13.food.shapes.BlockVoxelShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BerryBush extends Block implements EntityBlock {
    public static final int MAX_SIZE = 4;
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1,MAX_SIZE);
    public BerryBush(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(SIZE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext p_49820_) {
        return this.defaultBlockState().setValue(SIZE,MAX_SIZE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level p_153212_, @NotNull BlockState p_153213_, @NotNull BlockEntityType<T> p_153214_) {
        return EntityBlock.super.getTicker(p_153212_, p_153213_, p_153214_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(@NotNull ServerLevel p_221121_, @NotNull T p_221122_) {
        return EntityBlock.super.getListener(p_221121_, p_221122_);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState p_60555_, @NotNull BlockGetter p_60556_, @NotNull BlockPos p_60557_, @NotNull CollisionContext p_60558_) {
        return BlockVoxelShapes.BERRY_BUSH_SHAPE;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult p_60508_) {
        if (getSize(state) == MAX_SIZE){
            ItemUtils.spawnItemAtBlock(level, pos, new ItemStack(getBerryItem()));
            decrementState(level,pos,state);
        } else if (player.getItemInHand(hand).getItem().equals(ModItems.TOMATO_FRUIT.get())){
            level.setBlock(pos, state.setValue(SIZE, MAX_SIZE),0);
        }
        return InteractionResult.SUCCESS;
    }
    public int getSize(BlockState state){
        return state.getValue(SIZE);
    }

    public void decrementState(Level level, BlockPos pos, BlockState state){
        if (getSize(state)-1>0){
            level.setBlock(pos, state.setValue(SIZE, getSize(state)-1),0);
        } else {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
        }
    }

    public ItemLike getBerryItem() {
        return ModItems.CAKE_BITE.get();
    }
}
