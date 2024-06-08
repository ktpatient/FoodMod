package com.kitp13.food.items.tools;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PaxelRenderer extends BlockEntityWithoutLevelRenderer {
    public PaxelRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }
    private static final ResourceLocation STICK_TEXTURE = new ResourceLocation("minecraft", "textures/item/stick.png");
    private static final ResourceLocation PICKAXE_HEAD_TEXTURE = new ResourceLocation("food", "textures/item/pickaxe_head.png");
    private static final ResourceLocation AXE_HEAD_TEXTURE = new ResourceLocation("food", "textures/item/axe_head.png");
    private static final ResourceLocation SHOVEL_HEAD_TEXTURE = new ResourceLocation("food", "textures/item/shovel_head.png");

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        //poseStack.pushPose();
        //ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        //BakedModel model = itemRenderer.getModel(stack, null, null, 0);
        //itemRenderer.render(stack, ctx, false, poseStack, buffer, combinedLight, combinedOverlay, model);
        //poseStack.popPose();
        renderOverlay(STICK_TEXTURE,poseStack, buffer, combinedOverlay,combinedLight);
        if(Paxel.hasCapability(stack,ToolCapabilities.PICKAXE)){
            renderOverlay(PICKAXE_HEAD_TEXTURE,poseStack, buffer, combinedOverlay,combinedLight);
        }
        if(Paxel.hasCapability(stack, ToolCapabilities.AXE)){
            renderOverlay(AXE_HEAD_TEXTURE,poseStack,buffer,combinedOverlay,combinedLight);
        }
        if(Paxel.hasCapability(stack,ToolCapabilities.SHOVEL)){
            renderOverlay(SHOVEL_HEAD_TEXTURE,poseStack,buffer,combinedOverlay,combinedLight);
        }
        //renderOverlay(DIAMOND_TEXTURE,poseStack, buffer, combinedOverlay,combinedLight);
    }
    private void renderOverlay(ResourceLocation location, PoseStack pose, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableCull();
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(location));

        pose.pushPose();

        pose.translate(0.5f, 0.5f, 0.5f);

        // ERROR SOMEWHERE IN HERE, A SIDE SOMETIMES GETS CULLED OR SOMETHING?
        float uScale = 1.0f / 16;
        float vScale = 1.0f / 16;
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {

                float minX = x * uScale - 0.5f;
                float maxX = (x + 1) * uScale - 0.5f;
                float minY = y * vScale - 0.5f;
                float maxY = (y + 1) * vScale - 0.5f;
                float minZ = 0.0f;
                float maxZ = uScale;

                float uMin = x * uScale;
                float uMax = (x + 1) * uScale;
                float vMin = y * vScale;
                float vMax = (y + 1) * vScale;

                float uMinFlipped = 1.0f - uMin;
                float uMaxFlipped = 1.0f - uMax;
                float vMinFlipped = 1.0f - vMin;
                float vMaxFlipped = 1.0f - vMax;

                // Front face
                vertexConsumer.vertex(pose.last().pose(), minX, minY, maxZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 0, 1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, maxZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 0, 1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, maxZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 0, 1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, maxZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 0, 1).endVertex();

                // Back face
                vertexConsumer.vertex(pose.last().pose(), minX, minY, minZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 0, -1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, minZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 0, -1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, minZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 0, -1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, minZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 0, -1).endVertex();

                // Left face
                vertexConsumer.vertex(pose.last().pose(), minX, minY, minZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(-1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, minY, maxZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(-1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, maxZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(-1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, minZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(-1, 0, 0).endVertex();

                // Right face
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, minZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, maxZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, maxZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, minZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(1, 0, 0).endVertex();

                // Top face
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, minZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, maxZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, maxZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, minZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, 1, 0).endVertex();

                // Bottom face
                vertexConsumer.vertex(pose.last().pose(), minX, minY, minZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, -1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, minY, maxZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, -1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, maxZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, -1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, minZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(combinedLight).uv2(combinedOverlay).normal(0, -1, 0).endVertex();
            }
        }

        pose.popPose();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        RenderSystem.enableCull();

    }



}
