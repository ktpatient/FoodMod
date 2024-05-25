package com.kitp13.food.blocks;

import com.kitp13.food.Main;
import com.kitp13.food.blocks.crops.PineappleCropBlock;
import com.kitp13.food.blocks.crops.TomatoCropBlock;
import com.kitp13.food.entity.blocks.PizzaBlockTileEntity;
import com.kitp13.food.entity.blocks.PotBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
    public static final DeferredRegister<Item> ITEM_BLOCKS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);

    public static final RegistryObject<BlockEntityType<PizzaBlockTileEntity>> PIZZA_TILE_ENTITY = BLOCK_ENTITY.register("pizza_be", ()->BlockEntityType.Builder.of(PizzaBlockTileEntity::new, ModBlocks.PIZZA_BLOCK.get()).build(null));
    public static final RegistryObject<Block> PIZZA_BLOCK = BLOCKS.register("pizza_block", ()->new PizzaBlock());
    public static final RegistryObject<BlockItem> PIZZA_BLOCK_ITEM = ITEM_BLOCKS.register("pizza_block", ()->new BlockItem(PIZZA_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> TOMATO_CROP_BLOCK = BLOCKS.register("tomato_crop_block", ()->new TomatoCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> PINEAPPLE_CROP_BLOCK = BLOCKS.register("pineapple_crop_block", ()->new PineappleCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));


    public static final RegistryObject<BlockEntityType<PotBlockEntity>> POT_TILE_ENTITY = BLOCK_ENTITY.register("pot_be", ()->BlockEntityType.Builder.of(PotBlockEntity::new, ModBlocks.POT_BLOCK.get()).build(null));
    public static final RegistryObject<Block> POT_BLOCK = BLOCKS.register("pot_block", ()->new PotBlock());
    public static final RegistryObject<BlockItem> POT_BLOCK_ITEM = ITEM_BLOCKS.register("pot_block", ()->new BlockItem(POT_BLOCK.get(), new Item.Properties()));
}
