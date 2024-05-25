package com.kitp13.food.items;

import com.kitp13.food.Main;
import com.kitp13.food.blocks.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static RegistryObject<Item> TOMATO_FRUIT = ITEMS.register("tomato",()-> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).build())));
    public static RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",()-> new ItemNameBlockItem(ModBlocks.TOMATO_CROP_BLOCK.get(), new Item.Properties()));
    public static RegistryObject<Item> CHEESE_SLICE = ITEMS.register("cheese", ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).build())));
    public static RegistryObject<Item> PIZZA_SLICE = ITEMS.register("pizza_slice", ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).build())));
    public static RegistryObject<Item> DOUGH = ITEMS.register("dough", ()->new Item(new Item.Properties()));
    public static RegistryObject<Item> PINEAPPLE_SEEDS = ITEMS.register("pineapple_seeds", ()->new ItemNameBlockItem(ModBlocks.PINEAPPLE_CROP_BLOCK.get(), new Item.Properties()));
    public static RegistryObject<Item> PINEAPPLE = ITEMS.register("pineapple", ()->new Item(new Item.Properties()));
}
