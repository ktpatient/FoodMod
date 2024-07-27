package com.kitp13.food.event;

import com.kitp13.food.Main;
import com.kitp13.food.items.ModItems;
import com.kitp13.food.items.ToolItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT )
public class ClientEvents {
    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event){
        event.getItemColors().register(new ToolItemColor(), ModItems.FORK.get());
    }
}
