package com.kitp13.food.entity.blocks;

import net.minecraft.nbt.CompoundTag;

public interface SyncableBlockEntity {
    void sync();
    void readSyncTag(CompoundTag tag);
    CompoundTag writeSyncTag();
}
