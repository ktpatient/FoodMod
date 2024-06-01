package com.kitp13.food.fluid;

import com.kitp13.food.Main;
import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.items.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Main.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.Keys.FLUIDS, Main.MODID);

    public static final ResourceLocation FLUID_STILL   = new ResourceLocation("minecraft:block/water_still"  );
    public static final ResourceLocation FLUID_FLOWING = new ResourceLocation("minecraft:block/water_flow"   );
    public static final ResourceLocation FLUID_OVERLAY = new ResourceLocation("minecraft:block/water_overlay");

    private static ForgeFlowingFluid.Properties makeProperties() {
        return new ForgeFlowingFluid.Properties(MILK_FLUID_TYPE, MILK_FLUID_STILL, MILK_FLUID_FLOWING)
                .bucket(MILK_FLUID_BUCKET).block(MILK_FLUID_BLOCK);
    }

    public static RegistryObject<FluidType> MILK_FLUID_TYPE = FLUID_TYPES.register("milk_fluid", () -> new BaseFluidType(FLUID_STILL, FLUID_FLOWING, FLUID_OVERLAY,
            0xFFF5F5F5, new Vector3f(0, 145f / 255f, 207f / 255f), FluidType.Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH).lightLevel(2).temperature(150)
            .supportsBoating(true)));
    public static RegistryObject<FlowingFluid> MILK_FLUID_STILL = FLUIDS.register("milk_fluid", ()->
            new ForgeFlowingFluid.Source(makeProperties()));
    public static RegistryObject<FlowingFluid> MILK_FLUID_FLOWING = FLUIDS.register("milk_fluid_flowing", ()->
            new ForgeFlowingFluid.Flowing(makeProperties()));
    public static RegistryObject<LiquidBlock> MILK_FLUID_BLOCK = ModBlocks.BLOCKS.register("mana_fluid_block", () ->
            new LiquidBlock(MILK_FLUID_STILL, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable()));
    public static RegistryObject<Item> MILK_FLUID_BUCKET = ModItems.ITEMS.register("milk_fluid_bucket", ()->
            new BucketItem(MILK_FLUID_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));


    public static void register(IEventBus bus){
        FLUIDS.register(bus);
        FLUID_TYPES.register(bus);

    }}
