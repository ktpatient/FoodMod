package com.kitp13.food.items.tools;

import com.kitp13.food.Main;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PaxelRenderer extends BlockEntityWithoutLevelRenderer {
    public PaxelRenderer() {
        super(null, null);
    }

    @Override
    public void renderByItem(ItemStack p_108830_, ItemDisplayContext p_270899_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_) {
        Main.LOGGER.info("renderByItem");
        super.renderByItem(p_108830_, p_270899_, p_108832_, p_108833_, p_108834_, p_108835_);
    }
}
