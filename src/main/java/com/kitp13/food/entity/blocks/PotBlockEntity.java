package com.kitp13.food.entity.blocks;

import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.items.ModItems;
import com.kitp13.food.library.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class PotBlockEntity extends BlockEntity implements IFluidTank {
    public static ResourceLocation getResourceLocation(String str) {
        return new ResourceLocation(str.substring(0, str.indexOf(":")), str.substring(str.indexOf(":") + 1));
    }

    protected FluidTank tank;
    public Fluid fluidActual;
    public int jumpCount = 0;
    public PotBlockEntity(BlockPos pos, BlockState state){
        super(ModBlocks.POT_TILE_ENTITY.get(), pos, state);
        fluidActual = ForgeRegistries.FLUIDS.getValue(getResourceLocation("food:milk_fluid"));
        tank = new FluidTank(FluidType.BUCKET_VOLUME, (fluid)->fluid.getFluid().isSame(fluidActual));
    }

    @Override
    public @NotNull FluidStack getFluid() {
        return tank.getFluid();
    }

    @Override
    public int getFluidAmount() {
        return tank.getFluidAmount();
    }

    @Override
    public int getCapacity() {
        return tank.getCapacity();
    }

    @Override
    public boolean isFluidValid(FluidStack fluidStack) {
        return fluidStack.getFluid().isSame(fluidActual);
    }

    @Override
    public int fill(FluidStack fluidStack, IFluidHandler.FluidAction fluidAction) {
        if (tank.getCapacity() - tank.getFluidAmount() != 0) {
            assert level != null;
            if ((tank.getCapacity() - tank.getFluidAmount()) > fluidStack.getAmount()) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                return (tank.getCapacity() - tank.getFluidAmount()) - fluidStack.getAmount();
            } else {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                return fluidStack.getAmount() - (tank.getCapacity() - tank.getFluidAmount());
            }
        }

        return 0;
    }

    @Override
    public @NotNull FluidStack drain(int i, IFluidHandler.FluidAction fluidAction) {
        int drained = i;
        if (tank.getFluid().getAmount() < drained) {
            drained = tank.getFluid().getAmount();
        }

        FluidStack stack = new FluidStack(tank.getFluid(), drained);
        if (fluidAction.execute() && drained > 0) {
            tank.getFluid().shrink(drained);
            assert level != null;
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        }
        return stack;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack fluidStack, IFluidHandler.FluidAction fluidAction) {
        if (fluidStack.isEmpty() || !fluidStack.isFluidEqual(tank.getFluid())) {
            return FluidStack.EMPTY;
        }

        return drain(fluidStack.getAmount(), fluidAction);
    }

    public FluidTank getTank() {
        return tank;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.tank.readFromNBT(tag.getCompound("Tank"));
        this.jumpCount = tag.getInt("JumpCount");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Tank", this.tank.writeToNBT(new CompoundTag()));
        tag.putInt("JumpCount", this.jumpCount);
    }
    public void incrementJumpCount(Level level, BlockPos pos) {
        level.playLocalSound(pos, SoundEvents.SLIME_DEATH, SoundSource.BLOCKS, 1.0f, 1.0f, true);
        jumpCount++;
        if (jumpCount >= 2) {
            recipeSuccess(level, pos);
            jumpCount = 0;
        }
        setChanged();
    }
    private void recipeSuccess(Level level, BlockPos pos){
        tank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
        ItemUtils.spawnItemAtBlock(level, pos, new ItemStack(ModItems.CHEESE_SLICE.get()));
    }
}
