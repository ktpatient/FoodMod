package com.kitp13.food.library.items;

import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class SeedItem extends ItemNameBlockItem {
    public SeedItem(RegistryObject<Block> block, Properties p_41580_) {
        super(block.get(), p_41580_);
    }
}