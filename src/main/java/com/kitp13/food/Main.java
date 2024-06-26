package com.kitp13.food;

import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.entity.blocks.renderer.ChoppingBoardRenderer;
import com.kitp13.food.entity.blocks.renderer.PotBlockRenderer;
import com.kitp13.food.fluid.ModFluids;
import com.kitp13.food.items.ModItems;
import com.kitp13.food.tab.FoodTab;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "food";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public Main(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::renderEvent);
        ModFluids.register(bus);
        ModItems.register(bus);
        ModBlocks.register(bus);
        FoodTab.register(bus);
    }
    private void setupClient(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }
    private void renderEvent(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlocks.POT_TILE_ENTITY.get(), PotBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.CHOPPING_BOARD_ENTITY.get(), ChoppingBoardRenderer::new);
    }
}
