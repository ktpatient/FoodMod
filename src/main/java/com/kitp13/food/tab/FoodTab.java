package com.kitp13.food.tab;

import com.kitp13.food.Main;
import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FoodTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

    public static RegistryObject<CreativeModeTab> FOOD_TAB = TABS.register("food_tab", ()->
            CreativeModeTab.builder()
                    .title(Component.translatable("item_group_"+Main.MODID))
                    .icon(()->new ItemStack(ModItems.PIZZA_SLICE.get()))
                    .displayItems((p,out)->{
                        for (RegistryObject<Item> item:ModItems.ITEMS.getEntries()){
                            out.accept(item.get());
                        }
                        for (RegistryObject<Item> item : ModBlocks.ITEM_BLOCKS.getEntries()){
                            out.accept(item.get());
                        }
                    })
                    .build());

    public static void register(IEventBus bus){
        TABS.register(bus);
    }
}
