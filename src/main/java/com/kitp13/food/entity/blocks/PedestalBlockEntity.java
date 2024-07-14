package com.kitp13.food.entity.blocks;

import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.library.entity.block.SyncableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

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

        AABB aabb = new AABB(this.worldPosition).inflate(1.0);
        List<ItemEntity> items = this.level.getEntitiesOfClass(ItemEntity.class, aabb, item -> ItemStack.isSameItemSameTags(item.getItem(), requiredItem));

        if (items.size() >= requiredItem.getCount()) {
            for (int i = 0; i < requiredItem.getCount(); i++) {
                items.get(i).discard();
            }

            Player player = this.level.getNearestPlayer(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 10, false);
            if (player != null && !rewardItem.isEmpty()) {
                setItemsSubmitted(true);
                ItemStack reward = rewardItem.copy();
                if (!player.getInventory().add(reward)) {
                    ItemEntity itemEntity = new ItemEntity(this.level, player.getX(), player.getY(), player.getZ(), reward);
                    this.level.addFreshEntity(itemEntity);
                }
            }
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
    public CompoundTag getUpdateTag() {
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
    }
}
