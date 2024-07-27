package com.kitp13.food.datagen;

import com.kitp13.food.Main;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        generateItemModel("cheese" );
        generateItemModel("strawberry");
        generateItemModel("dough");
        generateItemModel("pineapple");
        generateItemModel("pineapple_seeds");
        generateItemModel("tomato_seeds");
        generateItemModel("tomato");
        generateItemModel("cake_bite");
        generateItemModel("pizza_slice");
        generateItemModel("pineapple_pizza_slice");
        generateItemModel("pizza_block","pizza");
        generateItemModel("pineapple_pizza_block", "pineapple_pizza");
        generateBlockParentModel("pot_block", "pot");
        generateBlockParentModel("chopping_board", "chopping_board");
        generateItemParentModel("milk_fluid_bucket", "milk_bucket", "minecraft");

        generateItemModel("bag_of_chips");
        generateItemModel("bannana");
        generateItemModel("blueberries");
        generateItemModel("bread_slice");
        generateItemModel("butter");
        generateItemModel("chili_pepper_green");
        generateItemModel("chili_pepper_red");
        generateItemModel("donut");
        generateItemModel("doughnut_chocolate");
        generateItemModel("doughnut_pink");
        generateItemModel("doughnut_pink_sprinkles");
        generateItemModel("egg");
        generateItemModel("french_fries");
        generateItemModel("garlic");
        generateItemModel("green_apple");
        generateItemModel("hotdog");
        generateItemModel("ice_cream");
        generateItemModel("lasagna");
        generateItemModel("lemon");
        generateItemModel("mango");
        generateItemModel("meatball");
        generateItemModel("onion");
        generateItemModel("orange");
        generateItemModel("pear");
        generateItemModel("pineapple2");
        generateItemModel("sandwich");
        generateItemModel("soup_beet");
        generateItemModel("soup_miso");
        generateItemModel("soup_mushroom");
        generateItemModel("soup_pea");
        generateItemModel("spinach");
        generateItemModel("strawberry2");
        generateItemModel("strawberry_smoothie");
        generateItemModel("turnip");
        generateItemModel("waffle");
        generateItemModel("waffle2");
        generateItemModel("watermelon2");
        generateItemModel("watermelon_slice");
        generateItemModel("watermelon_slice2");
        generateItemModel("watermelon_whole");

        // generateEntityItemModel("paxel");
        generateItemModel("modifier_brittle");
        generateItemModel("modifier_vampiric");
        generateItemModel("modifier_exp");
        generateItemModel("paxel_repair", "repair");
        generateItemModel("cactus_tea");
        generateItemModel("empty_tea");
        generateItemModel("rose_tea");
        generateItemModel("sugar_cane_tea");
        generateItemModel("tea");
        generateItemModel("water_tea");
        generateItemModel("fork_head");
        generateItemModel("handle");
        generateItemModel("fork");
    }
    private void generateItemModel(String name){
        generateItemModel(name,name);
    }
    private void generateItemModel(String itemName, String texturePath) {
        getBuilder(itemName)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(Main.MODID, "item/"+texturePath));
    }
    private void generateEntityItemModel(String name){
        generateEntityItemModel(name,name);
    }
    private void generateEntityItemModel(String itemName, String texturePath) {
        getBuilder(itemName)
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("layer0", new ResourceLocation(Main.MODID, "item/"+texturePath));
    }
    private void generateParentModel(String itemName, String modelName, String modelType, String modid){
        getBuilder(itemName)
                .parent(new ModelFile.UncheckedModelFile(new ResourceLocation(modid,modelType+"/"+modelName)));
    }
    private void generateBlockParentModel(String itemName,String ModelName){
        generateParentModel(itemName,ModelName,"block", Main.MODID);
    }
    private void generateItemParentModel(String itemName,String ModelName,String modid){
        generateParentModel(itemName,ModelName,"item",modid);
    }
    private void generateItemParentModel(String itemName,String ModelName){
        generateParentModel(itemName,ModelName,"item",Main.MODID);
    }
}
