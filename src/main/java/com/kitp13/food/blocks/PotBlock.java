package com.kitp13.food.blocks;

import com.kitp13.food.Main;
import com.kitp13.food.entity.blocks.PotBlockEntity;
import com.kitp13.food.fluid.ModFluids;
import com.kitp13.food.items.ModItems;
import com.kitp13.food.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import static com.kitp13.food.entity.blocks.PotBlockEntity.getResourceLocation;

public class PotBlock extends Block implements EntityBlock {
    public PotBlock(){
        super(BlockBehaviour.Properties.copy(Blocks.CAKE).sound(SoundType.METAL));
    }
    public static final VoxelShape SHAPE = makeShape();



    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0, 1, 0.375, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.125, 0.125, 0.375, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0.125, 0.125, 1, 0.375, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.875, 1, 0.375, 1), BooleanOp.OR);
        return shape;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        //if (!level.isClientSide){
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof PotBlockEntity blockEntity) {
                FluidTank tank = blockEntity.getTank();
                if (!player.isCrouching()){
                    ItemStack stack = player.getItemInHand(hand);
                    if (stack.equals(Items.BUCKET.getDefaultInstance(), false)) {
                        if (blockEntity.getFluidAmount() >= 1000){
                            blockEntity.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                            player.setItemInHand(hand, Items.MILK_BUCKET.getDefaultInstance());
                            player.getInventory().setChanged();
                            level.sendBlockUpdated(blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity.getBlockState(), 2);
                            return InteractionResult.SUCCESS;

                        }
                    }
                    if (stack.equals(Items.MILK_BUCKET.getDefaultInstance(), false)){
                        int filled = tank.fill(new FluidStack(ForgeRegistries.FLUIDS.getValue(getResourceLocation("food:milk_fluid")),1000), IFluidHandler.FluidAction.EXECUTE);
                        if (filled!=0){
                            player.setItemInHand(hand, Items.BUCKET.getDefaultInstance());
                            player.getInventory().setChanged();
                            level.sendBlockUpdated(blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity.getBlockState(), 2);
                            return InteractionResult.SUCCESS;
                        }

                    }
                    else if (!stack.isEmpty() && FluidUtil.interactWithFluidHandler(player, hand, tank)){
                        player.getInventory().setChanged();
                        level.sendBlockUpdated(blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity.getBlockState(), 2);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        //}
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PotBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return EntityBlock.super.getTicker(p_153212_, p_153213_, p_153214_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel p_221121_, T p_221122_) {
        return EntityBlock.super.getListener(p_221121_, p_221122_);
    }
    @Override
    public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float p_152430_) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof PotBlockEntity blockEntity){
                if (blockEntity.getFluidAmount()>=1000){
                    blockEntity.incrementJumpCount(level,blockPos,entity);
                }
            }

    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        ItemUtils.spawnItemOnGround(level,pos,new ItemStack(ModBlocks.POT_BLOCK_ITEM.get()));
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
