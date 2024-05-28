package com.kitp13.food.tab;

import com.kitp13.food.Main;
import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FoodTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

    public static RegistryObject<CreativeModeTab> FOOD_TAB = TABS.register("food_tab", ()->
            CreativeModeTab.builder()
                    .title(Component.translatable("item_group_"+Main.MODID))
                    .icon(()->new ItemStack(ModItems.PIZZA_SLICE.get()))
                    .displayItems((p,out)->{
                        out.accept(ModItems.TOMATO_FRUIT.get());
                        out.accept(ModItems.TOMATO_SEEDS.get());
                        out.accept(ModItems.PINEAPPLE.get());
                        out.accept(ModItems.PINEAPPLE_SEEDS.get());
                        out.accept(ModItems.DOUGH.get());
                        out.accept(ModItems.CHEESE_SLICE.get());
                        out.accept(ModItems.PIZZA_SLICE.get());
                        out.accept(ModBlocks.PIZZA_BLOCK_ITEM.get());
                        out.accept(ModItems.PINEAPPLE_PIZZA_SLICE.get());
                        out.accept(ModBlocks.PINEAPPLE_PIZZA_BLOCK_ITEM.get());
                        out.accept(ModItems.CAKE_BITE.get());
                        out.accept(ModBlocks.CHOPPING_BOARD_BLOCK_ITEM.get());
                        out.accept(ModBlocks.POT_BLOCK_ITEM.get());
                    })
                    .build());
}
