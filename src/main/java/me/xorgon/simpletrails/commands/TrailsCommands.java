package me.xorgon.simpletrails.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import me.xorgon.simpletrails.STManager;
import me.xorgon.simpletrails.SimpleTrails;
import me.xorgon.simpletrails.objects.Trail;
import me.xorgon.simpletrails.objects.TrailMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrailsCommands {

    public static class TrailsCommand {

        @Command(aliases = {"trails"}, desc = "The root SimpleTrails command.")
        public static void trails(CommandContext args, CommandSender sender) {
            if (sender instanceof Player) {
                TrailMenu menu = new TrailMenu((Player) sender);
                menu.showMenu();
            }
        }
    }

    public static class TrailCommand {

        @Command(aliases = {"trail"}, desc = "Turn on a trail.")
        public static void trail(CommandContext args, CommandSender sender) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                STManager manager = SimpleTrails.getInstance().getManager();
                for (Trail trail : manager.getAvailableTrails().values()) {
                    if (args.argsLength() > 0) {
                        if (args.getString(0).equals(trail.getName())) {
                            boolean started = manager.startTrail(player, trail);
                            if (!started){
                                player.sendMessage(ChatColor.RED + "You do not have permission to use that trail.");
                            } else {
                                player.sendMessage(ChatColor.YELLOW + "You are using the " + trail.getDisplayName() + ChatColor.YELLOW + " trail.");
                            }
                        }
                    } else {
                        manager.stopTrail(player);
                    }
                }
            }
        }

    }


}
