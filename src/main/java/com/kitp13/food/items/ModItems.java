package com.kitp13.food.items;

import com.kitp13.food.Main;
import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.library.items.FoodItem;
import com.kitp13.food.library.items.SeedItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    // Foods
    public static RegistryObject<Item> TOMATO_FRUIT          = ITEMS.register("tomato",                () -> new FoodItem(new Item.Properties(),4,0.3f));
    public static RegistryObject<Item> CHEESE_SLICE          = ITEMS.register("cheese",                () -> new FoodItem(new Item.Properties(),4,0.3f));
    public static RegistryObject<Item> PIZZA_SLICE           = ITEMS.register("pizza_slice",           () -> new FoodItem(new Item.Properties(),4,0.3f));
    public static RegistryObject<Item> PINEAPPLE_PIZZA_SLICE = ITEMS.register("pineapple_pizza_slice", () -> new FoodItem(new Item.Properties(),4,0.3f));
    public static RegistryObject<Item> PINEAPPLE             = ITEMS.register("pineapple",             () -> new FoodItem(new Item.Properties(),4,0.3f));
    public static RegistryObject<Item> CAKE_BITE             = ITEMS.register("cake_bite",             () -> new FoodItem(new Item.Properties(),4,0.3f));
    public static RegistryObject<Item> STRAWBERRY            = ITEMS.register("strawberry",            () -> new FoodItem(new Item.Properties(),4,0.3f));


    // Seeds
    public static RegistryObject<Item> TOMATO_SEEDS    = ITEMS.register("tomato_seeds",    () -> new SeedItem(ModBlocks.TOMATO_CROP_BLOCK,    new Item.Properties()));
    public static RegistryObject<Item> PINEAPPLE_SEEDS = ITEMS.register("pineapple_seeds", () -> new SeedItem(ModBlocks.PINEAPPLE_CROP_BLOCK, new Item.Properties()));

    //Miscellaneous Intermediate Items
    public static RegistryObject<Item> DOUGH = ITEMS.register("dough", ()->new Item(new Item.Properties()));

}
