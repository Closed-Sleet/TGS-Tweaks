package org.thomasphillips.greatsilencehelpermod;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.*;
import java.sql.Time;
import java.util.Random;

public class Events {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void addPlayerToTeam(PlayerEvent.PlayerLoggedInEvent event) {
        MinecraftServer server = event.getEntity().getServer();
        CommandSourceStack commandSource = event.getEntity().getServer().createCommandSourceStack();

        try {
            server.getCommands().getDispatcher().execute("team join invisible " + event.getEntity().getName().getString(), commandSource);
        } catch (CommandSyntaxException exception) {
            LOGGER.error("Command failed to add player to invisible team");
            LOGGER.error(exception.toString());
        }
    }
}
