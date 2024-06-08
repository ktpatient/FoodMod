package com.kitp13.food.blocks;

import com.kitp13.food.Main;
import com.kitp13.food.blocks.bush.BerryBush;
import com.kitp13.food.blocks.crops.PineappleCropBlock;
import com.kitp13.food.blocks.crops.TomatoCropBlock;
import com.kitp13.food.blocks.pizza.PineapplePizzaBlock;
import com.kitp13.food.blocks.pizza.PizzaBlock;
import com.kitp13.food.entity.blocks.BerryBushEntity;
import com.kitp13.food.entity.blocks.ChoppingBoardEntity;
import com.kitp13.food.entity.blocks.PizzaBlockTileEntity;
import com.kitp13.food.entity.blocks.PotBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
    public static final DeferredRegister<Item> ITEM_BLOCKS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);

    public static final RegistryObject<BlockEntityType<PizzaBlockTileEntity>> PIZZA_TILE_ENTITY = BLOCK_ENTITY.register("pizza_be", ()->BlockEntityType.Builder.of(PizzaBlockTileEntity::new, ModBlocks.PIZZA_BLOCK.get()).build(null));
    public static final RegistryObject<Block> PIZZA_BLOCK = BLOCKS.register("pizza_block", PizzaBlock::new);
    public static final RegistryObject<BlockItem> PIZZA_BLOCK_ITEM = ITEM_BLOCKS.register("pizza_block", ()->new BlockItem(PIZZA_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> PINEAPPLE_PIZZA_BLOCK = BLOCKS.register("pineapple_pizza_block", PineapplePizzaBlock::new);
    public static final RegistryObject<BlockItem> PINEAPPLE_PIZZA_BLOCK_ITEM = ITEM_BLOCKS.register("pineapple_pizza_block", ()->new BlockItem(PINEAPPLE_PIZZA_BLOCK.get(), new Item.Properties()));


    public static final RegistryObject<Block> TOMATO_CROP_BLOCK = BLOCKS.register("tomato_crop_block", ()->new TomatoCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> PINEAPPLE_CROP_BLOCK = BLOCKS.register("pineapple_crop_block", ()->new PineappleCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));


    public static final RegistryObject<BlockEntityType<PotBlockEntity>> POT_TILE_ENTITY = BLOCK_ENTITY.register("pot_be", ()->BlockEntityType.Builder.of(PotBlockEntity::new, ModBlocks.POT_BLOCK.get()).build(null));
    public static final RegistryObject<Block> POT_BLOCK = BLOCKS.register("pot_block", PotBlock::new);
    public static final RegistryObject<BlockItem> POT_BLOCK_ITEM = ITEM_BLOCKS.register("pot_block", ()->new BlockItem(POT_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> CHOPPING_BOARD_BLOCK = BLOCKS.register("chopping_board", ()->new ChoppingBoard(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<BlockItem> CHOPPING_BOARD_BLOCK_ITEM = ITEM_BLOCKS.register("chopping_board", ()->new BlockItem(CHOPPING_BOARD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<ChoppingBoardEntity>> CHOPPING_BOARD_ENTITY = BLOCK_ENTITY.register("chopping_board_be", ()->BlockEntityType.Builder.of(ChoppingBoardEntity::new, ModBlocks.CHOPPING_BOARD_BLOCK.get()).build(null));

    public static final RegistryObject<Block> BERRY_BUSH_BLOCK = BLOCKS.register("berry_bush", ()->new BerryBush(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<BlockItem> BERRY_BUSH_BLOCK_ITEM = ITEM_BLOCKS.register("berry_bush", ()->new BlockItem(BERRY_BUSH_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<BerryBushEntity>> BERRY_BUSH_ENTITY = BLOCK_ENTITY.register("berry_bush_be", ()->BlockEntityType.Builder.of(BerryBushEntity::new, ModBlocks.BERRY_BUSH_BLOCK.get()).build(null));

    public static void register(IEventBus bus){
        BLOCKS.register(bus);
        ITEM_BLOCKS.register(bus);
        BLOCK_ENTITY.register(bus);
    }


}
