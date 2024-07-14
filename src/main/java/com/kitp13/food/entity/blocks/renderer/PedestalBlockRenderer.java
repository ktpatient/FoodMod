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
import net.minecraft.world.phys.Vec3;
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
                renderLabel(pedestalBlockEntity,poseStack,multiBufferSource,i,new float[] { 0.5f, 1.1f, 0.5f },requiredItem.getDisplayName(),0xffffff,partialTicks);
            }
        }
    }
    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
    private void renderLabel(PedestalBlockEntity e,PoseStack matrixStack, MultiBufferSource buffer, int lightLevel, float[] corner, Component text, int color, float partialTicks) {
        Font fontRenderer = minecraft.font;
        LocalPlayer player = minecraft.player;
        if (player == null)
            return;

        matrixStack.pushPose();

        double angle = Math.toDegrees(angleBetweenXZ(e.getBlockPos().getCenter(), player.position()));

        matrixStack.translate(corner[0], corner[1], corner[2]);
        matrixStack.mulPose(Axis.YP.rotationDegrees((float) angle));
        matrixStack.scale(0.02F, -0.02F, 0.02F);
        float offset = -fontRenderer.width(text) / 2.0F;
        Matrix4f matrix4f = matrixStack.last().pose();
        fontRenderer.drawInBatch(text, offset, 0.0F, color, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 1711276032, lightLevel);

        matrixStack.popPose();
    }

    public static double angleBetweenXZ(Vec3 vec1, Vec3 vec2) {
        double x1 = vec1.x();
        double z1 = vec1.z();
        double x2 = vec2.x();
        double z2 = vec2.z();

        double dotProduct = x1 * x2 + z1 * z2;
        double magnitude1 = Math.sqrt(x1 * x1 + z1 * z1);
        double magnitude2 = Math.sqrt(x2 * x2 + z2 * z2);

        double cosine = dotProduct / (magnitude1 * magnitude2);

        double angleRad = Math.acos(cosine);

        double angleDeg = Math.toDegrees(angleRad);

        return angleDeg;
    }

}
