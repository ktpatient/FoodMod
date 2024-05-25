package com.kitp13.food.datagen;

import com.kitp13.food.Main;
import com.kitp13.food.blocks.ModBlocks;
import com.kitp13.food.blocks.crops.BaseCropBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeCrop((BaseCropBlock) ModBlocks.TOMATO_CROP_BLOCK.get(),"tomato_crop","tomato_stage");
        makeCrossCrop((BaseCropBlock) ModBlocks.PINEAPPLE_CROP_BLOCK.get(), "pineapple_crop", "pineapple_stage");
    }

    private void makeCrop(BaseCropBlock block, String modelName, String textureName){
        Function<BlockState, ConfiguredModel[]> function = state -> cropStates(block, state,modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }

    private void makeCrossCrop(BaseCropBlock block, String modelName, String textureName){
        Function<BlockState, ConfiguredModel[]> function = state -> crossCropStates(block, state,modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] crossCropStates(BaseCropBlock block, BlockState state, String modelName, String textureName){
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().withExistingParent(modelName+state.getValue(block.getAgeProperty()), mcLoc("block/block"))
                .texture("cross", "block/"+textureName+state.getValue(block.getAgeProperty()))
                .texture("particle", "block/"+textureName+state.getValue(block.getAgeProperty()))
                .element()
                .from(7.5f, -1.0f, 0.0f)
                .to(8.5f, 15.0f, 16.0f)
                .face(Direction.WEST).uvs(0, 0, 16, 16).texture("#cross").end()
                .face(Direction.EAST).uvs(0, 0, 16, 16).texture("#cross").end()
                .end()
                .element()
                .from(0.0f, -1.0f, 7.5f)
                .to(16.0f, 15.0f, 8.5f)
                .face(Direction.NORTH).uvs(0, 0, 16, 16).texture("#cross").end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#cross").end()
                .end().renderType("cutout"));
        return models;
    }

    private ConfiguredModel[] cropStates(BaseCropBlock block, BlockState state, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName+state.getValue(block.getAgeProperty()),
                new ResourceLocation(Main.MODID, "block/"+textureName+state.getValue(block.getAgeProperty()))).renderType("cutout"));
        return models;
    }
}
