package com.kitp13.food.event;

import com.kitp13.food.Main;
import com.kitp13.food.ModCommands;
import com.kitp13.food.items.ModItems;
import com.kitp13.food.items.ToolItemColor;
import com.kitp13.food.items.tools.Fork;
import com.kitp13.food.items.tools.Paxel;
import com.kitp13.food.items.tools.ToolCapabilities;
import com.kitp13.food.items.tools.modifiers.BooleanModifier;
import com.kitp13.food.items.tools.modifiers.LeveledModifier;
import com.kitp13.food.items.tools.modifiers.Modifiers;
import com.kitp13.food.items.tools.modifiers.ModifiersRegistry;
import com.kitp13.food.library.ItemUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModEvents {
    public static int convertStringToInt(String input) {
        if (input == null || input.isEmpty()) {
            return 1;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e1) {
            try {
                if (input.startsWith("0x") || input.startsWith("0X")) {
                    return Integer.parseInt(input.substring(2), 16);
                }
                if (input.startsWith("-0x") || input.startsWith("-0X")) {
                    return Integer.parseInt(input.substring(3), 16) * -1;
                }
            } catch (NumberFormatException e2) {
                return 1;
            }
        }
        return 1;
    }
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandBuildContext context = event.getBuildContext();
        ModCommands.register(event.getDispatcher(), context);
    }
    @SubscribeEvent
    public static void villagerTrades(VillagerTradesEvent event){
        if (event.getType() == VillagerProfession.FARMER){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            // buy, sell, maxUses, xp, bonusPriceAdjustment
            trades.get(1).add((trader, random)-> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 6),
                    new ItemStack(ModItems.TOMATO_SEEDS.get(),4),
                    10,10,0.05f
            ));

            trades.get(2).add((trader, random)-> new MerchantOffer(
               new ItemStack(ModItems.TOMATO_FRUIT.get(), 12),
               new ItemStack(Items.EMERALD,2),
               10,10,0.05f
            ));
        }
    }
    @SubscribeEvent
    public static void breakEvent(BlockEvent.BreakEvent event){
        if (event.getState().is(Blocks.CAKE)){
            int slicesRemaining = 7 - event.getState().getValue(BlockStateProperties.BITES);
            for (int i = 0; i < slicesRemaining; i++) {
                ItemUtils.spawnItemAtBlock(event.getPlayer().level(), event.getPos(),new ItemStack(ModItems.CAKE_BITE.get()));
            }
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack result = event.getCrafting();
        Player player = event.getEntity();

        if (result.getItem() instanceof Paxel) {
            Paxel.setSockets(result, 5);
        }
    }

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack leftStack = event.getLeft();
        ItemStack rightStack = event.getRight();

        if (leftStack.getItem() instanceof Fork fork){
            ItemStack copy = leftStack.copy();
            Fork.setCol(copy, convertStringToInt(event.getName()));
            event.setOutput(copy);
            event.setCost(1);
            event.setMaterialCost(1);
        }

        if (leftStack.getItem().equals(ModItems.FORK_HANDLE.get()) && rightStack.getItem().equals(ModItems.FORK_HEAD.get())) {
            event.setOutput(new ItemStack(ModItems.FORK.get()));
            event.setCost(1);
            event.setMaterialCost(1);
        }


        if (!(leftStack.getItem() instanceof Paxel)) {
            return;
        }
        if (Paxel.getSockets(leftStack) <= 0){
            return;
        }

        for (Modifiers modifiers : ModifiersRegistry.MODIFIERS_MAP.values()) {
            if (rightStack.getItem() == modifiers.applyMaterial()) {
                if (modifiers instanceof BooleanModifier && !Paxel.hasModifier(leftStack, modifiers.getName())) {
                    ItemStack output = leftStack.copy();
                    Paxel.setSockets(output, Paxel.getSockets(output) - 1);
                    Paxel.addModifier(output, modifiers);
                    event.setOutput(output);
                    event.setCost(1);
                    event.setMaterialCost(1);
                } else if (modifiers instanceof LeveledModifier leveledModifier) {
                    ItemStack output = leftStack.copy();
                    Paxel.setSockets(output, Paxel.getSockets(output) - 1);
                    int currentLevel = 0;
                    for (Modifiers modifier : Paxel.getModifiers(output)) {
                        if (Objects.equals(modifier.getName(), leveledModifier.getName())) {
                            currentLevel += leveledModifier.getLevel();
                            Paxel.removeModifier(output, modifier);
                        }
                    }
                    leveledModifier.setLevel(currentLevel + 1);
                    Paxel.addModifier(output, leveledModifier.setLevel(currentLevel + 1));
                    event.setOutput(output);
                    event.setCost(1);
                    event.setMaterialCost(1);
                } else {
                    // Main.LOGGER.error("Error Parsing Modifier");
                }
            }
        }

        if (rightStack.getItem() instanceof AxeItem && !Paxel.hasCapability(leftStack, ToolCapabilities.AXE)) {
            int combinedCapabilities = Paxel.getToolCapabilities(leftStack) | ToolCapabilities.AXE.getBit();
            ItemStack output = leftStack.copy();
            Paxel.setToolCapabilities(output, combinedCapabilities);
            Paxel.setSockets(output, Paxel.getSockets(output) - 1);
            event.setOutput(output);
            event.setCost(1);

        } else if (rightStack.getItem() instanceof PickaxeItem && !Paxel.hasCapability(leftStack, ToolCapabilities.PICKAXE)) {
            int combinedCapabilities = Paxel.getToolCapabilities(leftStack) | ToolCapabilities.PICKAXE.getBit();
            ItemStack output = leftStack.copy();
            Paxel.setToolCapabilities(output, combinedCapabilities);
            Paxel.setSockets(output, Paxel.getSockets(output) - 1);
            event.setOutput(output);
            event.setCost(1);

        } else if (rightStack.getItem() instanceof ShovelItem && !Paxel.hasCapability(leftStack, ToolCapabilities.SHOVEL)) {
            int combinedCapabilities = Paxel.getToolCapabilities(leftStack) | ToolCapabilities.SHOVEL.getBit();
            ItemStack output = leftStack.copy();
            Paxel.setToolCapabilities(output, combinedCapabilities);
            Paxel.setSockets(output, Paxel.getSockets(output) - 1);
            event.setOutput(output);
            event.setCost(1);

        } else if (rightStack.getItem() == Items.REDSTONE_BLOCK) {
            float currentSpeed = Paxel.getMiningSpeedModifier(leftStack);
            ItemStack output = leftStack.copy();
            Paxel.setMiningSpeedModifier(output, currentSpeed + 2.5f);
            Paxel.setSockets(output, Paxel.getSockets(output) - 1);
            event.setOutput(output);
            event.setCost(1);
            event.setMaterialCost(1);

        } else if (rightStack.getItem() == Items.OBSIDIAN) {
            int currentModifier = Paxel.getDurabilityModifier(leftStack);
            ItemStack output = leftStack.copy();
            Paxel.setDurabilityModifier(output, currentModifier + 256);
            Paxel.setSockets(output, Paxel.getSockets(output) - 1);
            event.setOutput(output);
            event.setCost(1);
            event.setMaterialCost(1);

        } else if (rightStack.getItem() == ModItems.PAXEL_REPAIR.get()) {
            ItemStack output = leftStack.copy();
            Paxel.setSockets(output, Paxel.getSockets(output) - 1);
            output.setDamageValue(0);
            event.setOutput(output);
            event.setCost(1);
            event.setMaterialCost(1);
        }
    }

}
