package com.kitp13.food.event;

import com.kitp13.food.Main;
import com.kitp13.food.items.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void villagerTrades(VillagerTradesEvent event){
        if (event.getType() == VillagerProfession.FARMER){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            // buy, sell, maxUses, xp, bonusPriceAdjustment
            trades.get(1).add((trader, random)-> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 6),
                    new ItemStack(ModItems.TOMATO_SEEDS.get(),4),
                    10,10,0.05f
            ));

            trades.get(2).add((trader, random)-> new MerchantOffer(
               new ItemStack(ModItems.TOMATO_FRUIT.get(), 12),
               new ItemStack(Items.EMERALD,2),
               10,10,0.05f
            ));
        }
    }
}
