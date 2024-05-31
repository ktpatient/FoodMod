package com.kitp13.food.items;

import com.kitp13.food.Main;
import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.library.items.FoodItem;
import com.kitp13.food.library.items.SeedItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    // Foods
    public static RegistryObject<Item> TOMATO_FRUIT;
    public static RegistryObject<Item> CHEESE_SLICE;
    public static RegistryObject<Item> PIZZA_SLICE;
    public static RegistryObject<Item> PINEAPPLE_PIZZA_SLICE;
    public static RegistryObject<Item> PINEAPPLE;
    public static RegistryObject<Item> CAKE_BITE;
    public static RegistryObject<Item> STRAWBERRY;

    // Seeds
    public static RegistryObject<Item> TOMATO_SEEDS;
    public static RegistryObject<Item> PINEAPPLE_SEEDS;

    //Miscellaneous Intermediate Items
    public static RegistryObject<Item> DOUGH;

    public static void register(IEventBus bus){
        TOMATO_FRUIT          = genericFood("tomato");
        CHEESE_SLICE          = genericFood("cheese");
        PIZZA_SLICE           = genericFood("pizza_slice");
        PINEAPPLE_PIZZA_SLICE = genericFood("pineapple_pizza_slice");
        PINEAPPLE             = genericFood("pineapple");
        CAKE_BITE             = genericFood("cake_bite");
        STRAWBERRY            = genericFood("strawberry");
        TOMATO_SEEDS    = ITEMS.register("tomato_seeds",    () -> new SeedItem(ModBlocks.TOMATO_CROP_BLOCK,    new Item.Properties()));
        PINEAPPLE_SEEDS = ITEMS.register("pineapple_seeds", () -> new SeedItem(ModBlocks.PINEAPPLE_CROP_BLOCK, new Item.Properties()));
        DOUGH = ITEMS.register("dough", ()->new Item(new Item.Properties()));
        genericFood("bag_of_chips");
        genericFood("bannana");
        genericFood("blueberries");
        genericFood("bread_slice");
        genericFood("butter");
        genericFood("chili_pepper_green");
        genericFood("chili_pepper_red");
        genericFood("donut");
        genericFood("doughnut_chocolate");
        genericFood("doughnut_pink");
        genericFood("doughnut_pink_sprinkles");
        genericFood("egg");
        genericFood("french_fries");
        genericFood("garlic");
        genericFood("green_apple");
        genericFood("hotdog");
        genericFood("ice_cream");
        genericFood("lasagna");
        genericFood("lemon");
        genericFood("mango");
        genericFood("meatball");
        genericFood("onion");
        genericFood("orange");
        genericFood("pear");
        genericFood("pineapple2");
        genericFood("sandwich");
        genericFood("soup_beet");
        genericFood("soup_miso");
        genericFood("soup_mushroom");
        genericFood("soup_pea");
        genericFood("spinach");
        genericFood("strawberry2");
        genericFood("strawberry_smoothie");
        genericFood("turnip");
        genericFood("waffle");
        genericFood("waffle2");
        genericFood("watermelon2");
        genericFood("watermelon_slice");
        genericFood("watermelon_slice2");
        genericFood("watermelon_whole");

        ITEMS.register(bus);
    }
    public static RegistryObject<Item> genericFood(String name){
        return ITEMS.register(name,genericFood());
    }
    public static Supplier<Item> genericFood(){
        return () -> new FoodItem(new Item.Properties(),4,0.3f);
    }
}
