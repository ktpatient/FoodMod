package com.kitp13.food.blocks;

import com.kitp13.food.entity.blocks.PotBlockEntity;
import com.kitp13.food.shapes.BlockVoxelShapes;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.kitp13.food.entity.blocks.PotBlockEntity.getResourceLocation;

@SuppressWarnings("deprecation")
public class PotBlock extends Block implements EntityBlock {
    public PotBlock(){
        super(BlockBehaviour.Properties.copy(Blocks.CAKE).sound(SoundType.METAL));
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_60550_) {
        return RenderShape.MODEL;
    }
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState p_60555_, @NotNull BlockGetter p_60556_, @NotNull BlockPos p_60557_, @NotNull CollisionContext p_60558_) {
        return BlockVoxelShapes.POT_BLOCK_SHAPE;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState p_60503_, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult p_60508_) {
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
                        int filled = tank.fill(new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(getResourceLocation("food:milk_fluid"))),1000), IFluidHandler.FluidAction.EXECUTE);
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
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new PotBlockEntity(blockPos, blockState);
    }

    @Override
    public void fallOn(Level level, @NotNull BlockState blockState, @NotNull BlockPos blockPos, @NotNull Entity entity, float p_152430_) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof PotBlockEntity blockEntity){
                if (blockEntity.getFluidAmount()>=1000){
                    blockEntity.incrementJumpCount(level,blockPos);
                }
            }

    }
}
