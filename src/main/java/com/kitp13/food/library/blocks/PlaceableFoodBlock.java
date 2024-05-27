package com.kitp13.food.library.blocks;

import com.kitp13.food.library.FoodUtils;
import com.kitp13.food.library.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public abstract class PlaceableFoodBlock extends Block implements EntityBlock {
    public static final int MAX_SIZE = 6;
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1,MAX_SIZE);

    public PlaceableFoodBlock(Properties p_49795_) {
        super(p_49795_);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(SIZE);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    public void setSize(BlockState state, int size){
        if (size>=0 && size <MAX_SIZE) {
            state.setValue(SIZE, size);
        }
    }

    public int getSize(BlockState state){
        return state.getValue(SIZE);
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext p_49820_) {
        return this.defaultBlockState().setValue(SIZE, MAX_SIZE);
    }

    public void decrementState(Level level, BlockPos pos, BlockState state){
        if (getSize(state)-1>0){
            level.setBlock(pos, state.setValue(SIZE, getSize(state)-1),0);
        } else {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
        }
    }

    public void feedPlayer(Player player) {
        FoodUtils.FeedPlayer(player, getSliceNutritionValue(), getSliceSaturationValue());
    }

    public float getSliceSaturationValue(){
        return 1;
    }

    public int getSliceNutritionValue(){
        return 1;
    }
    public ItemLike getSliceItem(){
        return Items.STONE;
    }
    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        for (int i = 0; i<getSize(state); i++){
            ItemUtils.spawnItemAtBlock(level, pos, new ItemStack(getSliceItem()));
        }
        return super.onDestroyedByPlayer(state,level,pos,player,willHarvest,fluid);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand p_60507_, @NotNull BlockHitResult p_60508_) {
        decrementState(level, pos, state);
        FoodUtils.PlayEatingSound(level,pos);
        feedPlayer(player);
        afterEatenSlice(player);
        return InteractionResult.SUCCESS;
    }
    public void afterEatenSlice(Player player){
        return;
    }
}
