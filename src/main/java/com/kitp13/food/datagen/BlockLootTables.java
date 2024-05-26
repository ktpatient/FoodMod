package com.kitp13.food.datagen;

import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.blocks.crops.PineappleCropBlock;
import com.kitp13.food.blocks.crops.TomatoCropBlock;
import com.kitp13.food.items.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    public BlockLootTables(){
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());

    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.CHOPPING_BOARD_BLOCK.get());
        this.dropSelf(ModBlocks.POT_BLOCK.get());
        createPlantLootTable(ModBlocks.TOMATO_CROP_BLOCK.get(), ModItems.TOMATO_SEEDS.get(),
                ModItems.TOMATO_FRUIT.get(), TomatoCropBlock.AGE,TomatoCropBlock.MAX_AGE);
        createPlantLootTable(ModBlocks.PINEAPPLE_CROP_BLOCK.get(), ModItems.PINEAPPLE_SEEDS.get(),
                ModItems.PINEAPPLE.get(), PineappleCropBlock.AGE, PineappleCropBlock.MAX_AGE);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        blocks.add(ModBlocks.TOMATO_CROP_BLOCK.get());
        blocks.add(ModBlocks.PINEAPPLE_CROP_BLOCK.get());
        blocks.add(ModBlocks.POT_BLOCK.get());
        blocks.add(ModBlocks.CHOPPING_BOARD_BLOCK.get());
        return blocks;
    }
    private void createPlantLootTable(Block cropBlock, Item seed, Item fruit, IntegerProperty integerProperty, int maxAge){
        LootItemCondition.Builder lootItemConditionBuilder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(cropBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(integerProperty, maxAge));
        this.add(cropBlock, createCropDrops(cropBlock, fruit
                ,seed, lootItemConditionBuilder));
    }

    protected LootTable.@NotNull Builder createCropDrops(@NotNull Block block, @NotNull Item fruitItem, @NotNull Item seedItem, LootItemCondition.@NotNull Builder conditionBuilder) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(2))
                        .when(conditionBuilder)
                        .add(LootItem.lootTableItem(fruitItem))
                        .add(LootItem.lootTableItem(seedItem)
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))

                );
    }
}
