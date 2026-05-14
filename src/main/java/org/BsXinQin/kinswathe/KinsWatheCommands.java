package org.BsXinQin.kinswathe;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class KinsWatheCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("kinswathe")
                    .then(literal("setStartingCooldown")
                            .requires(source -> source.hasPermissionLevel(2))
                            .then(argument("seconds", IntegerArgumentType.integer(1))
                                    .executes(context -> {
                                        int value = IntegerArgumentType.getInteger(context, "seconds");
                                        KinsWatheConfig.HANDLER.instance().StartingCooldown = value;
                                        KinsWatheConfig.HANDLER.save();
                                        context.getSource().sendFeedback(() -> Text.literal("[Kin's Wathe] StartingCooldown: " + value + " seconds"), true);
                                        return Command.SINGLE_SUCCESS;
                                    })))
                    .then(literal("setEnableJumpNotInGame")
                            .requires(source -> source.hasPermissionLevel(2))
                            .then(argument("enable", BoolArgumentType.bool())
                                    .executes(context -> {
                                        boolean value = BoolArgumentType.getBool(context, "enable");
                                        KinsWatheConfig.HANDLER.instance().EnableJumpNotInGame = value;
                                        KinsWatheConfig.HANDLER.save();
                                        context.getSource().sendFeedback(() -> Text.literal("[Kin's Wathe] EnableJumpNotInGame: " + (value ? "true" : "false")), true);
                                        return Command.SINGLE_SUCCESS;
                                    })))
                    .then(literal("setEnableStartSafeTime")
                            .requires(source -> source.hasPermissionLevel(2))
                            .then(argument("enable", BoolArgumentType.bool())
                                    .executes(context -> {
                                        boolean value = BoolArgumentType.getBool(context, "enable");
                                        KinsWatheConfig.HANDLER.instance().EnableStartSafeTime = value;
                                        KinsWatheConfig.HANDLER.save();
                                        context.getSource().sendFeedback(() -> Text.literal("[Kin's Wathe] EnableStartSafeTime: " + (value ? "true" : "false")), true);
                                        return Command.SINGLE_SUCCESS;
                                    })))
                    .then(literal("setEnableAutoJoinVoiceChat")
                            .requires(source -> source.hasPermissionLevel(2))
                            .then(argument("enable", BoolArgumentType.bool())
                                    .executes(context -> {
                                        boolean value = BoolArgumentType.getBool(context, "enable");
                                        KinsWatheConfig.HANDLER.instance().EnableAutoJoinVoiceChat = value;
                                        KinsWatheConfig.HANDLER.save();
                                        context.getSource().sendFeedback(() -> Text.literal("[Kin's Wathe] EnableAutoJoinVoiceChat: " + (value ? "true" : "false")), true);
                                        return Command.SINGLE_SUCCESS;
                                    })))
                    .then(literal("setEnableBetterBlackout")
                            .requires(source -> source.hasPermissionLevel(2))
                            .then(argument("enable", BoolArgumentType.bool())
                                    .executes(context -> {
                                        boolean value = BoolArgumentType.getBool(context, "enable");
                                        KinsWatheConfig.HANDLER.instance().EnableBetterBlackout = value;
                                        KinsWatheConfig.HANDLER.save();
                                        context.getSource().sendFeedback(() -> Text.literal("[Kin's Wathe] EnableBetterBlackout: " + (value ? "true" : "false")), true);
                                        return Command.SINGLE_SUCCESS;
                                    })))
                    .then(literal("setEnableAutoPsychoInstinct")
                            .requires(source -> source.hasPermissionLevel(2))
                            .then(argument("enable", BoolArgumentType.bool())
                                    .executes(context -> {
                                        boolean value = BoolArgumentType.getBool(context, "enable");
                                        KinsWatheConfig.HANDLER.instance().EnableAutoPsychoInstinct = value;
                                        KinsWatheConfig.HANDLER.save();
                                        context.getSource().sendFeedback(() -> Text.literal("[Kin's Wathe] EnableAutoPsychoInstinct: " + (value ? "true" : "false")), true);
                                        return Command.SINGLE_SUCCESS;
                                    }))));
        });
    }
}