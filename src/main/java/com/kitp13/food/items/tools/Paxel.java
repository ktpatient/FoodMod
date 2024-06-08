package com.kitp13.food.items.tools;

import com.kitp13.food.Main;
import com.kitp13.food.items.tools.modifiers.Modifiers;
import com.kitp13.food.items.tools.modifiers.TestModifier;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Paxel extends DiggerItem {

    private static final String SOCKET_KEY = "socket";
    private static final String TOOL_CAPABILITIES_KEY = "ToolCapabilities";
    private static final String MINING_MODIFIER_KEY = "MiningModifier";
    private static final String DURABILITY_MODIFIER_KEY = "DurabilityModifier";
    private static final String MODIFIERS_KEY = "Modifiers";

    public Paxel(float damage, float attackSpeed, Tier tier, TagKey<Block> p_204111_, Properties properties) {
        super(damage, attackSpeed, tier, p_204111_, properties);
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

    public static void addModifier(ItemStack stack, Modifiers modifier) {
        CompoundTag tag = stack.getOrCreateTag();
        ListTag modifiersList = tag.getList(MODIFIERS_KEY, 10); // 10 for compound tag type
        CompoundTag modifierTag = new CompoundTag();
        if (modifier instanceof TestModifier) {
            modifierTag.putString("Type", TestModifier.NAME);
            modifierTag.putInt("Level", ((TestModifier) modifier).getLevel());
        } else {
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
                if (type.equals("Test")) {
                    modifiers.add(new TestModifier(modifierTag.getInt("Level")));
                }
            }
        }
        return modifiers;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level p_41422_, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, p_41422_, tooltip, flag);
        tooltip.add(Component.literal("Sockets: "+getSockets(stack)).withStyle(Style.EMPTY.withColor(TextColor.parseColor("#4085F5"))));
        tooltip.add(Component.literal("Capabilities: "+getToolCapabilities(stack)).withStyle(Style.EMPTY.withColor(TextColor.parseColor("#F58540"))));
        if (hasCapability(stack, ToolCapabilities.PICKAXE)){
            tooltip.add(Component.literal("PICKAXE"));
        }
        if (hasCapability(stack, ToolCapabilities.AXE)){
            tooltip.add(Component.literal("AXE"));
        }
        if (hasCapability(stack, ToolCapabilities.SHOVEL)){
            tooltip.add(Component.literal("SHOVEL"));
        }
        tooltip.add(Component.literal("Mining Speed Modifier: " + getMiningSpeedModifier(stack)));
        tooltip.add(Component.literal("Durability Modifier: " + getDurabilityModifier(stack)));
        for (Modifiers modifiers : getModifiers(stack)){
            tooltip.add(modifiers.tooltip(stack));
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
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entity) {
        for (Modifiers modifiers : getModifiers(stack)){
            modifiers.onMine(stack, level, state, pos, entity);
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }
}
