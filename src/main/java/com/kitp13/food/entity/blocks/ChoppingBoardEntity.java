package com.kitp13.food.entity.blocks;

import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.library.entity.block.SyncableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ChoppingBoardEntity extends BlockEntity implements SyncableBlockEntity {
    public final ItemStackHandler itemStackHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            sync();
        }
    };

    public static final int SLOT_INPUT = 0;

    public ChoppingBoardEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlocks.CHOPPING_BOARD_ENTITY.get(), p_155229_, p_155230_);
    }

    public ItemStack getRenderStack() {
        return itemStackHandler.getStackInSlot(SLOT_INPUT);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        itemStackHandler.deserializeNBT(tag.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(tag);
    }
    @Override
    public void sync() {
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            setChanged();
        }
    }

    @Override
    public void readSyncTag(CompoundTag tag) {
        itemStackHandler.deserializeNBT(tag.getCompound("inventory"));
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("inventory", itemStackHandler.serializeNBT());
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return writeSyncTag();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        readSyncTag(Objects.requireNonNull(pkt.getTag()));
    }
}
