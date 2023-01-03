package it.ohalee.minecraftgpt.command;

import it.ohalee.minecraftgpt.Main;
import it.ohalee.minecraftgpt.Type;
import it.ohalee.minecraftgpt.conversation.TypeManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class ChatCommand implements TabExecutor {

    private final Main plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
            return true;
        }

        Type type = Type.SINGLE;
        if (args.length >= 1) {
            type = Type.getType(args[0]);
            if (type == null) {
                player.sendMessage(plugin.getConfig().getString("command.invalid-type")
                        .replace("&", "§")
                        .replace("{types}", String.join(", ", Arrays.stream(Type.values()).map(Enum::name).toArray(String[]::new))));
                return true;
            }
        }

        if (!player.hasPermission("minecraftgpt.command." + type)) {
            player.sendMessage(plugin.getConfig().getString("command.no-permission").replace("&", "§"));
            return true;
        }

        TypeManager.startConversation(plugin, player, type);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Arrays.stream(Type.values()).map(type -> type.name().toLowerCase()).toList();
    }


}