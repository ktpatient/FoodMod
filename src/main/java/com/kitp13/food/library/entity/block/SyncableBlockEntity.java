package com.kitp13.food.library.entity.block;

import net.minecraft.nbt.CompoundTag;

public interface SyncableBlockEntity {
    void sync();
    void readSyncTag(CompoundTag tag);
    CompoundTag writeSyncTag();
}
