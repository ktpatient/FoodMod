package com.kitp13.food.items.tools;

import com.kitp13.food.Main;
import com.kitp13.food.items.tools.modifiers.*;
import com.kitp13.food.library.chat.Components;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class Paxel extends DiggerItem {

    private static final String SOCKET_KEY = "socket";
    private static final String TOOL_CAPABILITIES_KEY = "ToolCapabilities";
    private static final String MINING_MODIFIER_KEY = "MiningModifier";
    private static final String DURABILITY_MODIFIER_KEY = "DurabilityModifier";
    private static final String MODIFIERS_KEY = "Modifiers";
    private final Random random;

    public Paxel(float damage, float attackSpeed, Tier tier, TagKey<Block> p_204111_, Properties properties) {
        super(damage, attackSpeed, tier, p_204111_, properties);
        random = new Random();
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        float speed = 1.0f;
        if (hasCapability(stack, ToolCapabilities.PICKAXE) && state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            speed = this.speed;
        }
        if (hasCapability(stack, ToolCapabilities.AXE) && state.is(BlockTags.MINEABLE_WITH_AXE)) {
            speed = this.speed;
        }
        if (hasCapability(stack, ToolCapabilities.SHOVEL) && state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            speed = this.speed;
        }
        return speed + getMiningSpeedModifier(stack);
    }

    public static int getSockets(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getInt(SOCKET_KEY);
    }

    public static void setSockets(ItemStack stack, int sockets) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt(SOCKET_KEY, sockets);
    }

    public static void setMiningSpeedModifier(ItemStack stack, float modifier) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putFloat(MINING_MODIFIER_KEY, modifier);
    }

    public static float getMiningSpeedModifier(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(MINING_MODIFIER_KEY)) {
            return tag.getFloat(MINING_MODIFIER_KEY);
        }
        return 0.0F;
    }

    public static void setDurabilityModifier(ItemStack stack, int modifier) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(DURABILITY_MODIFIER_KEY, modifier);
    }

    public static int getDurabilityModifier(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(DURABILITY_MODIFIER_KEY)) {
            return tag.getInt(DURABILITY_MODIFIER_KEY);
        }
        return 0;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        int original =  super.getMaxDamage(stack);
        int modifier = getDurabilityModifier(stack);
        return original + modifier;
    }

    public static void setToolCapabilities(ItemStack stack, int capabilities) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(TOOL_CAPABILITIES_KEY, capabilities);
    }

    public static int getToolCapabilities(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TOOL_CAPABILITIES_KEY)) {
            return tag.getInt(TOOL_CAPABILITIES_KEY);
        }
        return 0;
    }

    public static boolean hasCapability(ItemStack stack, ToolCapabilities capability) {
        int capabilities = getToolCapabilities(stack);
        return ToolCapabilities.hasCapability(capabilities, capability);
    }

    public static boolean hasModifier(ItemStack stack,String name){
        List<Modifiers> modifiersList = getModifiers(stack);
        for (Modifiers modifier : modifiersList){
            if (modifier.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public static void addModifier(ItemStack stack, Modifiers modifier) {
        CompoundTag tag = stack.getOrCreateTag();
        ListTag modifiersList = tag.getList(MODIFIERS_KEY, 10); // 10 for compound tag type
        CompoundTag modifierTag = new CompoundTag();
        if (modifier instanceof LeveledModifier leveledModifier) {
            modifierTag.putString("Type", leveledModifier.getName());
            modifierTag.putInt("Level", leveledModifier.getLevel());
        }
        else if (modifier instanceof BooleanModifier){
            modifierTag.putString("Type", modifier.getName());
        }
        else {
            Main.LOGGER.error("Error passing in modifier {}", modifier.getName());
        }
        modifiersList.add(modifierTag);
        tag.put(MODIFIERS_KEY, modifiersList);
    }

    public static void removeModifier(ItemStack stack, Modifiers modifier) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(MODIFIERS_KEY)) {
            ListTag modifiersList = tag.getList(MODIFIERS_KEY, 10);
            ListTag newModifiersList = new ListTag();
            for (int i = 0; i < modifiersList.size(); i++) {
                CompoundTag modifierTag = modifiersList.getCompound(i);
                String type = modifierTag.getString("Type");
                if (!type.equals(modifier.getName())) {
                    newModifiersList.add(modifierTag);
                }
            }
            tag.put(MODIFIERS_KEY, newModifiersList);
        }
    }

    public static List<Modifiers> getModifiers(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        List<Modifiers> modifiers = new ArrayList<>();
        if (tag != null && tag.contains(MODIFIERS_KEY)) {
            ListTag modifiersList = tag.getList(MODIFIERS_KEY, 10);
            for (int i = 0; i < modifiersList.size(); i++) {
                CompoundTag modifierTag = modifiersList.getCompound(i);
                String type = modifierTag.getString("Type");

                if (ModifiersRegistry.MODIFIERS_MAP.get(type) instanceof BooleanModifier){
                    modifiers.add(ModifiersRegistry.MODIFIERS_MAP.get(type));
                } else if (ModifiersRegistry.MODIFIERS_MAP.get(type) instanceof LeveledModifier modifier){
                    modifier.setLevel(modifierTag.getInt("Level"));
                    modifiers.add(modifier);
                }
            }
        }
        return modifiers;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level p_41422_, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, p_41422_, tooltip, flag);
        tooltip.add(Components.ColoredPart("Sockets: ", getSockets(stack),ChatFormatting.BLUE));
        if (hasCapability(stack, ToolCapabilities.PICKAXE)){
            tooltip.add(Components.ColoredPart("Works as Tool: ", "PICKAXE", ChatFormatting.BLUE));
        }
        if (hasCapability(stack, ToolCapabilities.AXE)){
            tooltip.add(Components.ColoredPart("Works as Tool: ", "AXE", ChatFormatting.BLUE));
        }
        if (hasCapability(stack, ToolCapabilities.SHOVEL)){
            tooltip.add(Components.ColoredPart("Works as Tool: ", "SHOVEL", ChatFormatting.BLUE));
        }
        tooltip.add(Components.ColoredPart("Mining Speed Modifier: ", getMiningSpeedModifier(stack), ChatFormatting.BLUE));
        tooltip.add(Components.ColoredPart("Durability Modifier: ", getDurabilityModifier(stack), ChatFormatting.BLUE));

        List<Modifiers> modifiersList = getModifiers(stack);
        if (!modifiersList.isEmpty()){
            tooltip.add(Component.empty().append(Component.literal("Modifiers: ")));
        }
        for (Modifiers modifiers : modifiersList){
            tooltip.add(Component.empty().append(Component.literal("    ")).append(modifiers.tooltip(stack)));
            if (Screen.hasShiftDown()){
                tooltip.add(modifiers.shiftTooltip(stack));
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(),InputConstants.KEY_LSHIFT)){
            setSockets(player.getItemInHand(hand),(getSockets(player.getItemInHand(hand))+1));
        }
        return super.use(level, player, hand);
    }

    @Override
    public boolean isCorrectToolForDrops(@NotNull ItemStack stack, @NotNull BlockState state) {
        if (hasCapability(stack, ToolCapabilities.PICKAXE) && state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            return true;
        }
        if (hasCapability(stack, ToolCapabilities.AXE) && state.is(BlockTags.MINEABLE_WITH_AXE)) {
            return true;
        }
        if (hasCapability(stack, ToolCapabilities.SHOVEL) && state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        for (Modifiers modifiers : getModifiers(stack)){
            modifiers.onAttack(stack, target, attacker,random);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entity) {
        for (Modifiers modifiers : getModifiers(stack)){
            modifiers.onMine(stack, level, state, pos, entity,random);
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }
    public static final PaxelRenderer RENDERER = new PaxelRenderer();

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return RENDERER;
            }
        });
    }
}
