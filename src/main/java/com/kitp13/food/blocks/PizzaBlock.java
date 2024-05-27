package com.kitp13.food.blocks;

import com.kitp13.food.entity.blocks.PizzaBlockTileEntity;
import com.kitp13.food.items.ModItems;
import com.kitp13.food.library.FoodUtils;
import com.kitp13.food.library.blocks.PlaceableFoodBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class PizzaBlock extends PlaceableFoodBlock {
    protected static final VoxelShape[] SHAPES = new VoxelShape[] {
            makeShape1(),
            makeShape2(),
            makeShape3(),
            makeShape4(),
            makeShape5(),
            makeShape6()
    };
    public static VoxelShape makeShape6(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.5, 1, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeShape5(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.5, 0.6875, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeShape4(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.5, 0.3125, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeShape3(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeShape2(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.6875, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeShape1(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.3125, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    public PizzaBlock(){
        super(BlockBehaviour.Properties.copy(Blocks.CAKE));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new PizzaBlockTileEntity(blockPos,blockState);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState p_60555_, @NotNull BlockGetter p_60556_, @NotNull BlockPos p_60557_, @NotNull CollisionContext p_60558_) {
        return SHAPES[getSize(p_60555_)-1];
    }

    @Override
    public ItemLike getSliceItem() {
        return ModItems.PIZZA_SLICE.get();
    }
    @Override
    public int getSliceNutritionValue() {
        return 6;
    }

    @Override
    public float getSliceSaturationValue() {
        return 2f;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand p_60507_, @NotNull BlockHitResult p_60508_) {
        decrementState(level, pos, state);
        FoodUtils.PlayEatingSound(level,pos);
        feedPlayer(player);
        return InteractionResult.SUCCESS;
    }
}
