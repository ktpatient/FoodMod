package com.kitp13.food;

import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.entity.blocks.PotBlockEntity;
import com.kitp13.food.entity.blocks.renderer.PotBlockRenderer;
import com.kitp13.food.fluid.ModFluids;
import com.kitp13.food.items.ModItems;
import net.minecraft.client.multiplayer.ClientRegistryLayer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
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
        ModFluids.FLUIDS.register(bus);
        ModFluids.FLUID_TYPES.register(bus);

        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModBlocks.BLOCK_ENTITY.register(bus);
        ModBlocks.ITEM_BLOCKS.register(bus);
    }
    private void renderEvent(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlocks.POT_TILE_ENTITY.get(), PotBlockRenderer::new);

    }
}
