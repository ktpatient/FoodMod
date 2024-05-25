package com.kitp13.food.blocks;

import com.kitp13.food.Main;
import com.kitp13.food.entity.blocks.PizzaBlockTileEntity;
import com.kitp13.food.items.ModItems;
import com.kitp13.food.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PizzaBlock extends Block implements EntityBlock {
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1,6);

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
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return (BlockEntity) new PizzaBlockTileEntity(blockPos,blockState);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPES[((Integer)p_60555_.getValue((Property)SIZE)).intValue()-1];
        //return super.getShape(p_60555_, p_60556_, p_60557_, p_60558_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(SIZE);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }
    public void setSize(BlockState state, int size){
        if (size>=0 && size <6) {
            state.setValue(SIZE, size);
        }
    }
    public int getSize(BlockState state){
        return ((Integer)state.getValue((Property)SIZE)).intValue();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (getSize(state)-1>0){
            level.setBlock(pos, state.setValue(SIZE, getSize(state)-1),0);
        } else {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
        }
        level.playLocalSound(pos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 1.0f, 1.0f, true);
        player.getFoodData().eat(6,2f);
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        return this.defaultBlockState().setValue(SIZE, 6);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        for (int i = 0; i<getSize(state); i++){
            ItemUtils.spawnItemOnGround(level, pos, new ItemStack(ModItems.PIZZA_SLICE.get()));
        }
        return super.onDestroyedByPlayer(state,level,pos,player,willHarvest,fluid);
    }
}
