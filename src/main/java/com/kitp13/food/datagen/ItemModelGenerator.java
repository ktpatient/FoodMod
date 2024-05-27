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
        generateItemModel("dough");
        generateItemModel("pineapple");
        generateItemModel("pineapple_seeds");
        generateItemModel("tomato_seeds");
        generateItemModel("tomato");
        generateItemModel("pizza_slice");
        generateItemModel("pineapple_pizza_slice");
        generateItemModel("pizza_block","pizza");
        generateItemModel("pineapple_pizza_block", "pineapple_pizza");
        generateBlockParentModel("pot_block", "pot");
        generateBlockParentModel("chopping_board", "chopping_board");
        generateItemParentModel("milk_fluid_bucket", "milk_bucket", "minecraft");
    }
    private void generateItemModel(String name){
        generateItemModel(name,name);
    }
    private void generateItemModel(String itemName, String texturePath) {
        getBuilder(itemName)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
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
