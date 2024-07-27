package com.kitp13.food.entity.blocks.renderer;

import com.kitp13.food.entity.blocks.PedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class PedestalBlockRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private final Font font;
    private static final float[] floatingRenderOffset = new float[]{0.5f, 1.10f, 0.5f};
    public PedestalBlockRenderer(BlockEntityRendererProvider.Context context){
        this.font = minecraft.font;
    }
    @Override
    public void render(PedestalBlockEntity pedestalBlockEntity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        ItemStack requiredItem = pedestalBlockEntity.getRequiredItem();

        if (!requiredItem.isEmpty() && !pedestalBlockEntity.areItemsSubmitted()){
            renderItemStackRotating(poseStack,multiBufferSource,pedestalBlockEntity.getBlockPos(),partialTicks,pedestalBlockEntity.getRewardItem(), 0.35f);

            renderPrice(poseStack, multiBufferSource, requiredItem, combinedLight, combinedOverlay);
            //renderComponentFacingPlayer(poseStack,multiBufferSource,i,new float[] { 0.5f, 1.45f, 0.5f },component,pedestalBlockEntity.getBlockPos(),0xFFFFFF);

        }
    }
    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
    private void renderItemStackRotating(PoseStack poseStack, MultiBufferSource multiBufferSource, BlockPos position, float partialTicks, ItemStack toRender, float scale){
        poseStack.pushPose();
        poseStack.translate(floatingRenderOffset[0], floatingRenderOffset[1], floatingRenderOffset[2] );
        poseStack.scale(scale, scale, scale);
        assert minecraft.level != null;
        long gameTime = minecraft.level.getGameTime();
        float angle = (gameTime + partialTicks) % 360;
        poseStack.mulPose(Axis.YP.rotation(angle/8));
        minecraft.getItemRenderer().renderStatic(toRender, ItemDisplayContext.FIXED, getLightLevel(minecraft.level, position),
                OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, minecraft.level, 1);
        poseStack.popPose();
    }
    private void renderComponentFacingPlayer(PoseStack matrixStack, MultiBufferSource buffer, int lightLevel, float[] offset, Component text, BlockPos blockPos,int bkgColour) {
        Font fontRenderer = minecraft.font;

        matrixStack.pushPose();

        Vec3 cameraPos = minecraft.gameRenderer.getMainCamera().getPosition();
        double dx = cameraPos.x - (blockPos.getX() + 0.5);
        double dz = cameraPos.z - (blockPos.getZ() + 0.5);
        double yaw = Math.atan2(dx, dz);
        float yawDegrees = (float) Math.toDegrees(yaw);
        matrixStack.translate(offset[0], offset[1], offset[2]);
        matrixStack.mulPose(Axis.YP.rotationDegrees(yawDegrees));
        matrixStack.scale(0.02F, -0.02F, 0.02F);
        float font_offset = -fontRenderer.width(text) / 2.0F;
        Matrix4f matrix4f = matrixStack.last().pose();
        fontRenderer.drawInBatch(text, font_offset, 0.0F, bkgColour, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 1711276032, lightLevel);

        matrixStack.popPose();
    }
    private void renderComponentStatic(PoseStack matrixStack, MultiBufferSource buffer, int lightLevel, Component text, Vec3 pos, float rotation,int bkgColour) {
        Font fontRenderer = minecraft.font;

        matrixStack.pushPose();
        matrixStack.translate(pos.x,pos.y,pos.z);
        matrixStack.mulPose(Axis.YP.rotationDegrees(rotation));
        matrixStack.scale(0.02F, -0.02F, 0.02F);
        float font_offset = -fontRenderer.width(text) / 2.0F;
        Matrix4f matrix4f = matrixStack.last().pose();
        fontRenderer.drawInBatch(text, font_offset, 0.0F, bkgColour, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 1711276032, lightLevel);

        matrixStack.popPose();
    }
    private void renderPrice(PoseStack matrixStack, MultiBufferSource buffer,ItemStack priceStack, int combinedLight, int combinedOverlay) {
        matrixStack.translate(0.5,0.5,0.5);
         FormattedCharSequence text = Component.literal(String.valueOf(priceStack.getCount())).getVisualOrderText();
        int textWidth = this.font.width(text);
        float itemScale = 0.6125f;
        float textScale = 0.015F;

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            matrixStack.pushPose();

            matrixStack.mulPose(Axis.YP.rotationDegrees(dir.toYRot()));
            matrixStack.translate(0.0D, -0.1D, 0.125D);

            matrixStack.pushPose();

            matrixStack.translate(0.0D, -0.1D, 0.2D);
            matrixStack.scale(itemScale,itemScale,itemScale);

            minecraft.getItemRenderer().renderStatic(priceStack, ItemDisplayContext.GROUND, combinedLight, combinedOverlay, matrixStack, buffer, null, 0);

            matrixStack.popPose();

            matrixStack.translate(0.0D, 0.25D, 0.2D);
            matrixStack.scale(textScale, -textScale, textScale);

            // Draw the text twice for shadow effect
            this.font.drawInBatch(text, 1.0F - textWidth / 2.0F, 1.0F - 9.0F / 2.0F, 0xFF000000, false, matrixStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, combinedLight);
            matrixStack.translate(0.0D, 0.0D, 0.001D);
            this.font.drawInBatch(text, -textWidth / 2.0F, -9 / 2.0F, 0xFFFFFFFF, false, matrixStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, combinedLight);

            matrixStack.popPose();
        }
    }

}
