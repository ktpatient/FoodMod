package com.kitp13.food.items.tools;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

public class PaxelRenderer extends BlockEntityWithoutLevelRenderer {
    public PaxelRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }
    private static final ResourceLocation STICK_TEXTURE = new ResourceLocation("minecraft", "textures/item/stick.png");
    private static final ResourceLocation PICKAXE_HEAD_TEXTURE = new ResourceLocation("food", "textures/item/pickaxe_head.png");
    private static final ResourceLocation AXE_HEAD_TEXTURE = new ResourceLocation("food", "textures/item/axe_head.png");
    private static final ResourceLocation SHOVEL_HEAD_TEXTURE = new ResourceLocation("food", "textures/item/shovel_head.png");
    private static final ResourceLocation[] BASE_TOOLS_TEXTURES = new ResourceLocation[]{
            new ResourceLocation("food", "textures/item/paxel.png"),
            new ResourceLocation("food", "textures/item/paxel01.png"),
            new ResourceLocation("food", "textures/item/paxel02.png"),
            new ResourceLocation("food", "textures/item/paxel03.png"),
            new ResourceLocation("food", "textures/item/paxel04.png"),
            new ResourceLocation("food", "textures/item/paxel05.png"),
            new ResourceLocation("food", "textures/item/paxel06.png"),
            new ResourceLocation("food", "textures/item/paxel07.png"),
    };
    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        renderOverlay(BASE_TOOLS_TEXTURES[Paxel.getToolCapabilities(stack)],poseStack,buffer,combinedOverlay,combinedLight);
    }
    private void renderOverlay(ResourceLocation location, PoseStack pose, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        RenderSystem.setShaderTexture(0, location);
        //RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableCull();
        RenderSystem.setShaderLights(
                new Vector3f(0.2f, 1.0f, -0.7f).normalize(), // Light direction
                new Vector3f(-0.2f, 1.0f, 0.7f).normalize()  // Secondary light direction
        );

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(location));
        int brightLight = 15728880;
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

                // Front face (counter-clockwise order)
                vertexConsumer.vertex(pose.last().pose(), minX, minY, maxZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 0, 1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, maxZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 0, 1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, maxZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 0, 1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, maxZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 0, 1).endVertex();

                // Back face (counter-clockwise order)
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, minZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 0, -1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, minY, minZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 0, -1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, minZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 0, -1).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, minZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 0, -1).endVertex();

                // Left face (counter-clockwise order)
                vertexConsumer.vertex(pose.last().pose(), minX, minY, minZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(-1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, minY, maxZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(-1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, maxZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(-1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, minZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(-1, 0, 0).endVertex();

                // Right face (counter-clockwise order)
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, maxZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, minZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, minZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(1, 0, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, maxZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(1, 0, 0).endVertex();

                // Bottom face (counter-clockwise order)
                vertexConsumer.vertex(pose.last().pose(), minX, minY, minZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, -1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, minZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, -1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, minY, maxZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, -1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, minY, maxZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, -1, 0).endVertex();

                // Top face (counter-clockwise order)
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, maxZ).color(255, 255, 255, 255).uv(uMin, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, maxZ).color(255, 255, 255, 255).uv(uMax, vMaxFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), maxX, maxY, minZ).color(255, 255, 255, 255).uv(uMax, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 1, 0).endVertex();
                vertexConsumer.vertex(pose.last().pose(), minX, maxY, minZ).color(255, 255, 255, 255).uv(uMin, vMinFlipped).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightLight).normal(0, 1, 0).endVertex();

            }

        }

        pose.popPose();
        //RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        RenderSystem.enableCull();

    }



}
