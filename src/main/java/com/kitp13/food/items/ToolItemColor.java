package com.kitp13.food.items;

import com.kitp13.food.items.tools.Fork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class ToolItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack itemStack, int i) {
        int baseColor = Fork.getCol(itemStack);
        // Main.LOGGER.info(Fork.getCol(itemStack));
        float[] hsbVals = new float[3];
        Color.RGBtoHSB((baseColor >> 16) & 0xFF, (baseColor >> 8) & 0xFF, baseColor & 0xFF, hsbVals);

        long worldTime = Minecraft.getInstance().level.getGameTime(); // Get the world time
        int cycleDuration = 200; // Number of ticks for a full color cycle (10 seconds)
        float timeFactor = (worldTime % cycleDuration) / (float) cycleDuration;

        // Adjust the hue based on the time factor
        float newHue = (hsbVals[0] + timeFactor) % 1.0f;

        // Convert the new HSB value back to RGB
        int newColor = Color.HSBtoRGB(newHue, hsbVals[1], hsbVals[2]);

        return newColor;
    }

}
