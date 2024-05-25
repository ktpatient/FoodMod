package com.kitp13.food.datagen;

import com.kitp13.food.Main;
import com.kitp13.food.datagen.lang.EN_US;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(event.includeServer(), new ItemModelGenerator(packOutput, Main.MODID,existingFileHelper));
        generator.addProvider(event.includeServer(), new BlockStateGenerator(packOutput, Main.MODID,existingFileHelper));
        generator.addProvider(event.includeServer(), new Recipes(packOutput));
        generator.addProvider(event.includeServer(), LootTableProvider.create(packOutput));
        generator.addProvider(event.includeClient(), new EN_US(packOutput, Main.MODID, "en_us"));
    }
}
