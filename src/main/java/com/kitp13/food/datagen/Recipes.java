package com.kitp13.food.datagen;

import com.kitp13.food.Main;
import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider implements IConditionBuilder {
    public Recipes(PackOutput packOutput){
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModBlocks.PIZZA_BLOCK_ITEM.get())
                .requires(ModItems.PIZZA_SLICE.get()).requires(ModItems.PIZZA_SLICE.get())
                .requires(ModItems.PIZZA_SLICE.get()).requires(ModItems.PIZZA_SLICE.get())
                .requires(ModItems.PIZZA_SLICE.get()).requires(ModItems.PIZZA_SLICE.get())
                .unlockedBy(getHasName(ModItems.PIZZA_SLICE.get()), has(ModItems.PIZZA_SLICE.get()))
                .save(consumer, new ResourceLocation(Main.MODID, "pizza_from_slice"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.DOUGH.get())
                .requires(Items.WHEAT).requires(Items.WHEAT).requires(Items.WHEAT)
                .requires(Items.WHEAT).requires(Items.WHEAT).requires(Items.WHEAT)
                .requires(Items.WHEAT).requires(Items.WHEAT).requires(Items.WATER_BUCKET)
                .unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModBlocks.PIZZA_BLOCK_ITEM.get())
                .requires(ModItems.DOUGH.get()).requires(ModItems.TOMATO_FRUIT.get())
                .requires(ModItems.CHEESE_SLICE.get())
                .unlockedBy(getHasName(ModItems.DOUGH.get()), has(ModItems.DOUGH.get()))
                .save(consumer, new ResourceLocation(Main.MODID, "pizza_from_materials"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.POT_BLOCK_ITEM.get())
                .pattern("IBI").pattern("III")
                .define('I', Items.IRON_INGOT).define('B', Items.BUCKET)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(consumer);
    }
}
