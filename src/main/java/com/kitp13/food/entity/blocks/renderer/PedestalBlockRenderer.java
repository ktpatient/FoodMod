package com.kitp13.food.entity.blocks.renderer;

import com.kitp13.food.entity.blocks.PedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.joml.Matrix4f;

import java.util.Objects;

public class PedestalBlockRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    private final Minecraft minecraft;
    private final Font font;
    public PedestalBlockRenderer(BlockEntityRendererProvider.Context context){
        this.context = context;
        this.minecraft = Minecraft.getInstance();
        this.font = minecraft.font;
    }
    @Override
    public void render(PedestalBlockEntity pedestalBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        ItemStack requiredItem = pedestalBlockEntity.getRequiredItem();
        // Main.LOGGER.info("RequiredItem : " + requiredItem.getDisplayName().getString());

        if (!requiredItem.isEmpty()){
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.85f, 0.5f);
            poseStack.scale(0.35f, 0.35f, 0.35f);
            long gameTime = Minecraft.getInstance().level.getGameTime();
            float angle = (gameTime + partialTicks) % 360;
            poseStack.mulPose(Axis.YP.rotation(angle/8));
            itemRenderer.renderStatic(requiredItem, ItemDisplayContext.FIXED, getLightLevel(Objects.requireNonNull(pedestalBlockEntity.getLevel()), pedestalBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, pedestalBlockEntity.getLevel(), 1);
            poseStack.popPose();
            if (minecraft.options.keyShift.isDown()) {
                renderLabel(poseStack,multiBufferSource,i,new double[] { 0.05D, 0.65D, 0.05D },requiredItem.getDisplayName(),0xffffff);
            }
        }
    }
    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
    private void renderLabel(PoseStack matrixStack, MultiBufferSource buffer, int lightLevel, double[] corner, Component text, int color) {
        Font fontRenderer = minecraft.font;
        LocalPlayer player = minecraft.player;
        if (player == null)
            return;

        matrixStack.pushPose();
        float scale = 0.01F;
        int opacity = 1711276032;

        matrixStack.translate(corner[0], corner[1] + 0.25D, corner[2]);
        matrixStack.scale(scale, -scale, scale);
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        Matrix4f matrix4f = matrixStack.last().pose();


        float offset = (-fontRenderer.width(text) / 2.0F);
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        fontRenderer.drawInBatch(text, offset, 0.0F, color, false, matrix4f, buffer, Font.DisplayMode.NORMAL, opacity, lightLevel);


        matrixStack.popPose();
    }

}
