package me.xorgon.simpletrails;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.supaham.commons.bukkit.SimpleCommonPlugin;
import de.slikey.effectlib.EffectManager;
import me.xorgon.simpletrails.commands.TrailsCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.*;

public class SimpleTrails extends SimpleCommonPlugin<SimpleTrails> {

    private static SimpleTrails instance;
    private STManager manager;
    private EffectManager effectManager;
    private CommandsManager<CommandSender> commands;


    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        manager = new STManager();
        registerEvents(new STListener());
        setupCommands();
        effectManager = new EffectManager(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        manager.stopAllTrails();
        effectManager.dispose();
    }

    public static SimpleTrails getInstance() {
        return instance;
    }

    public STManager getManager() {
        return manager;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    public void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender commandSender, String s) {
                return commandSender.hasPermission(s);
            }
        };

        CommandsManagerRegistration reg = new CommandsManagerRegistration(this, commands);

        reg.register(TrailsCommands.TrailCommand.class);
        reg.register(TrailsCommands.TrailsCommand.class);

    }


}
