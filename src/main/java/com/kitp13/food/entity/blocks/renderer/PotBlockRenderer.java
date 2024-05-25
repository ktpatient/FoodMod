package com.kitp13.food.entity.blocks.renderer;


import com.kitp13.food.Main;
import com.kitp13.food.blocks.PotBlock;
import com.kitp13.food.entity.blocks.PotBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import static com.kitp13.food.entity.blocks.PotBlockEntity.getResourceLocation;


public class PotBlockRenderer implements BlockEntityRenderer<PotBlockEntity>  {
    private static final float SIDE_MARGIN = (float) PotBlock.SHAPE.min(Direction.Axis.X) + 0.1f,
            MIN_Y = 2.1f / 16f,
            MAX_Y = 0.4f - MIN_Y;
    public PotBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PotBlockEntity blockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        //Main.LOGGER.info("RENDER!!!!");
        // Main.LOGGER.info(blockEntity.getTank().getFluidAmount());
        if (blockEntity.getFluidAmount() == 0){
            return;
        }
        FluidStack fluidStack = new FluidStack(ForgeRegistries.FLUIDS.getValue(getResourceLocation("food:milk_fluid")),1000);
        //
        renderQuad(poseStack, multiBufferSource, fluidStack, i, i1);
    }


    private static void renderQuad(PoseStack matrixStack, MultiBufferSource buffer, FluidStack fluidStack, int combinedLight, int combinedOverlay) {
        // Define vertices for a simple quad
        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(fluidTypeExtensions.getStillTexture(fluidStack));        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.solid());

        if (sprite == null) {
            return; // Fluid sprite not found, return early
        }

        float minX = -0.5f;
        float maxX = 0.5f;
        float minY = 0.25f; // Top face
        float maxY = 0.25f; // Top face
        float minZ = -0.5f;
        float maxZ = 0.5f;

        float minU = sprite.getU0();
        float maxU = sprite.getU1();
        float minV = sprite.getV0();
        float maxV = sprite.getV1();

        // Get a VertexConsumer for the quad

        int color = fluidTypeExtensions.getTintColor();
        float alpha = 1;
        alpha *= (color >> 24 & 255) / 255f;
        float red = (color >> 16 & 255) / 255f;
        float green = (color >> 8 & 255) / 255f;
        float blue = (color & 255) / 255f;

        // Render the quad
        matrixStack.pushPose();
        // Translate to the center of the block
        matrixStack.translate(0.5, 0, 0.5);
        // Draw the quad
        vertexConsumer.vertex(matrixStack.last().pose(), minX, minY, minZ)
                .color(red, green, blue, alpha)
                .uv(minU, minV)
                .uv2(combinedLight)
                .normal(0, 1, 0) // Facing upwards
                .endVertex();
        vertexConsumer.vertex(matrixStack.last().pose(), minX, maxY, maxZ)
                .color(red, green, blue, alpha)
                .uv(minU, maxV)
                .uv2(combinedLight)
                .normal(0, 1, 0) // Facing upwards
                .endVertex();
        vertexConsumer.vertex(matrixStack.last().pose(), maxX, maxY, maxZ)
                .color(red, green, blue, alpha)
                .uv(maxU, maxV)
                .uv2(combinedLight)
                .normal(0, 1, 0) // Facing upwardse
                .endVertex();
        vertexConsumer.vertex(matrixStack.last().pose(), maxX, minY, minZ)
                .color(red, green, blue, alpha)
                .uv(maxU, minV)
                .uv2(combinedLight)
                .normal(0, 1, 0) // Facing upwards
                .endVertex();
        matrixStack.popPose();


    }

}
