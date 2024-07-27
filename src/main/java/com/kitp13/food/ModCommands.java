package com.kitp13.food;

import com.kitp13.food.entity.blocks.PedestalBlockEntity;
import com.kitp13.food.library.chat.Components;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(Commands.literal("setBlockEntityItems")
                .then(Commands.argument("pos", BlockPosArgument.blockPos())
                        .then(Commands.argument("requiredItem", ItemArgument.item(context))
                                .then(Commands.argument("requiredCount", IntegerArgumentType.integer(1))
                                        .then(Commands.argument("rewardItem", ItemArgument.item(context))
                                                .then(Commands.argument("rewardCount", IntegerArgumentType.integer(1))
                                                        .executes(ctx -> {
                                                            BlockPos pos = BlockPosArgument.getLoadedBlockPos(ctx, "pos");
                                                            ItemStack requiredItem = ItemArgument.getItem(ctx, "requiredItem").createItemStack(IntegerArgumentType.getInteger(ctx, "requiredCount"), false);
                                                            ItemStack rewardItem = ItemArgument.getItem(ctx, "rewardItem").createItemStack(IntegerArgumentType.getInteger(ctx, "rewardCount"), false);

                                                            BlockEntity blockEntity = ctx.getSource().getLevel().getBlockEntity(pos);
                                                            if (blockEntity instanceof PedestalBlockEntity customBlockEntity) {
                                                                customBlockEntity.setRequiredItem(requiredItem);
                                                                customBlockEntity.setRewardItem(rewardItem);
                                                                customBlockEntity.setItemsSubmitted(false);
                                                                ctx.getSource().sendSuccess(()->Components.ColoredText("Set required and reward items successfully.",ChatFormatting.AQUA), true);
                                                                return 1;
                                                            }

                                                            return 0;
                                                        })))))));
    }
}
