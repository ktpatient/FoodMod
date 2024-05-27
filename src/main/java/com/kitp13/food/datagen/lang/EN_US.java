package com.kitp13.food.datagen.lang;

import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class EN_US extends LanguageProvider {
    public EN_US(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModItems.CHEESE_SLICE, "Cheese Slice");
        add(ModItems.PIZZA_SLICE, "Pizza Slice");
        add(ModItems.PINEAPPLE_PIZZA_SLICE, "Pineapple Pizza Slice");
        add(ModItems.DOUGH, "Dough");
        add(ModItems.TOMATO_FRUIT, "Tomato");
        add(ModItems.TOMATO_SEEDS, "Tomato Seeds");
        add(ModItems.PINEAPPLE, "Pineapple");
        add(ModItems.PINEAPPLE_SEEDS, "Pineapple Seeds");

        addBlock(ModBlocks.PIZZA_BLOCK_ITEM, "Pizza");
        addBlock(ModBlocks.PINEAPPLE_PIZZA_BLOCK_ITEM, "Pineapple Pizza");
        addBlock(ModBlocks.POT_BLOCK_ITEM, "Curding Pot");

    }
    private void addBlock(RegistryObject<BlockItem> item, String translation){
        add(item.get(),translation);
    }
    private void add(RegistryObject<Item> item, String translation){
        add(item.get(), translation);
    }
}
