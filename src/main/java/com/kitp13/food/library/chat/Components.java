package com.kitp13.food.library.chat;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class Components {
    public static MutableComponent ColoredText(String text, ChatFormatting color){
        return Component.literal(text).withStyle(color);
    }
    private static Component ColoredText(int num, ChatFormatting color){
        return Component.literal(""+num).withStyle(color);
    }
    private static Component ColoredText(float num, ChatFormatting color){
        return Component.literal(""+num).withStyle(color);
    }
    private static Component PlainText(String text){
        return Component.literal(text);
    }
    private static MutableComponent fromList(Component... components){
        MutableComponent message = Component.empty();
        for (Component com : components){
            message.append(com);
        }
        return message;
    }
    public static MutableComponent ColoredPart(String plainText, String ColouredText, ChatFormatting color){
        return fromList(PlainText(plainText),ColoredText(ColouredText,color));
    }
    public static MutableComponent ColoredPart(String plainText, float ColouredText, ChatFormatting color){
        return fromList(PlainText(plainText),ColoredText(ColouredText,color));
    }
    public static MutableComponent ColoredPart(String plainText, int ColouredText, ChatFormatting color){
        return fromList(PlainText(plainText),ColoredText(ColouredText,color));
    }
    public static MutableComponent ColoredPart(MutableComponent component, String ColouredText, ChatFormatting color){
        return fromList(component,ColoredText(ColouredText,color));
    }
}
