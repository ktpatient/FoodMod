package com.kitp13.food.entity.blocks;

import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.library.entity.block.SyncableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PedestalBlockEntity extends BlockEntity implements SyncableBlockEntity {
    private ItemStack requiredItem = new ItemStack(Items.DIAMOND, 3); // Default requirement
    private ItemStack rewardItem = new ItemStack(Items.EMERALD); // Default reward
    private boolean itemsSubmitted = false; // Track if items have been submitted

    public PedestalBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlocks.PEDESTAL_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }

    public ItemStack getRenderStack() {
        return getRequiredItem();
    }

    public void tick() {
        if (this.level == null || this.level.isClientSide) return;

        if (requiredItem.isEmpty() || rewardItem.isEmpty() || itemsSubmitted) return;

        Item requiredItem = this.requiredItem.getItem();
        int requiredCount = this.requiredItem.getCount();

        int totalFound = 0;
        List<ItemEntity> itemsToConsume = new ArrayList<>();
        AABB aabb = new AABB(worldPosition).inflate(2.0);
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, aabb);

        for (ItemEntity itemEntity : itemEntities) {
            ItemStack stack = itemEntity.getItem();

            if (stack.getItem() == requiredItem) {
                totalFound += stack.getCount();
                itemsToConsume.add(itemEntity);

                if (totalFound >= requiredCount) {
                    break;
                }
            }
        }

        if (totalFound >= requiredCount) {
            int remaining = requiredCount;

            for (ItemEntity itemEntity : itemsToConsume) {
                ItemStack stack = itemEntity.getItem();
                int toRemove = Math.min(stack.getCount(), remaining);

                stack.shrink(toRemove);
                remaining -= toRemove;

                if (stack.isEmpty()) {
                    itemEntity.remove(Entity.RemovalReason.DISCARDED);
                }

                if (remaining <= 0) {
                    break;
                }
            }

            itemsSubmitted = true;
            triggerReward();
            setChanged();
            sync();
        }
    }

    private void triggerReward() {
        if (rewardItem != null && rewardItem.getCount() > 0) {
            ItemStack rewardStack = rewardItem.copy();
            ItemEntity rewardEntity = new ItemEntity((ServerLevel) level, worldPosition.getX() + 0.5, worldPosition.getY() + 1.0, worldPosition.getZ() + 0.5, rewardStack);
            level.addFreshEntity(rewardEntity);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        requiredItem = ItemStack.of(tag.getCompound("RequiredItem"));
        rewardItem = ItemStack.of(tag.getCompound("RewardItem"));
        itemsSubmitted = tag.getBoolean("ItemsSubmitted");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("RequiredItem", requiredItem.save(new CompoundTag()));
        tag.put("RewardItem", rewardItem.save(new CompoundTag()));
        tag.putBoolean("ItemsSubmitted", itemsSubmitted);
    }

    public ItemStack getRequiredItem() {
        return requiredItem;
    }

    public void setRequiredItem(ItemStack requiredItem) {
        this.requiredItem = requiredItem;
        setChanged();
    }

    public ItemStack getRewardItem() {
        return rewardItem;
    }

    public void setRewardItem(ItemStack rewardItem) {
        this.rewardItem = rewardItem;
        setChanged();
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
        requiredItem = ItemStack.of(tag.getCompound("RequiredItem"));
        rewardItem = ItemStack.of(tag.getCompound("RewardItem"));
        itemsSubmitted = tag.getBoolean("ItemsSubmitted");
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("RequiredItem", requiredItem.save(new CompoundTag()));
        tag.put("RewardItem", rewardItem.save(new CompoundTag()));
        tag.putBoolean("ItemsSubmitted", itemsSubmitted);
        return tag;
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return writeSyncTag();
    }
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        readSyncTag(Objects.requireNonNull(pkt.getTag()));
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    public boolean areItemsSubmitted() {
        return itemsSubmitted;
    }
    public void setItemsSubmitted(boolean submitted) {
        this.itemsSubmitted = submitted;
        setChanged();
        sync();
    }
}
