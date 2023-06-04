package ru.permasha.blockdescription.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.permasha.blockdescription.BlockDescription;
import ru.permasha.blockdescription.objects.Attribute;

import java.util.Arrays;
import java.util.regex.Pattern;

public class BlockDescCommand implements CommandExecutor {

    BlockDescription plugin;

    public BlockDescCommand(BlockDescription plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be player");
            return true;
        }

        Block block = player.getTargetBlockExact(5);
        if (block == null) {
            player.sendMessage(colorize("&cМаксимальная дистанция взаимодействия 5 блоков!"));
            return true;
        }

        Location location = block.getLocation();
        String jsonLoc = plugin.getAttributesManager().toJsonLocation(location);

        if (args.length == 0) {
            String msg = plugin.getAttributesManager().getFormatMessageAttributes(location);
            player.sendMessage(msg);
            return true;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (args.length < 2) {
                player.sendMessage(colorize("&cСинтаксис: &7/blockdesc add <описание>"));
                return true;
            }

            // Getting args after first arg
            String[] desc = Arrays.copyOfRange(args, 1, args.length);
            // Create attribute
            String description = String.join(" ", desc);
            Attribute attribute = new Attribute(player.getName(), description);
            String jsonAttribute = plugin.getAttributesManager().toJsonAttribute(attribute);

            if (!plugin.getAttributesManager().isBlockConsistsAttributes(location)) {
                // Create new array and convert it to string, and put string to database
                String[] attributesArray = plugin.getAttributesManager().addAttribute(new String[0], jsonAttribute);
                String attributes = plugin.getAttributesManager().toJsonAttributes(attributesArray);
                plugin.getDatabase().setAttributes(jsonLoc, attributes);

                plugin.getHologramManager().createHologram(location);
            } else {
                // Get string Json format from database, convert to Array, create new array and put new description
                String[] attributesArray = getAttributesArrayFromJson(jsonLoc);
                String[] attributes = plugin.getAttributesManager().addAttribute(attributesArray, jsonAttribute);

                // Convert new array to str and put it in database
                String finalStr = plugin.getAttributesManager().toJsonAttributes(attributes);
                plugin.getDatabase().setAttributes(jsonLoc, finalStr);
            }
            player.sendMessage(colorize("&aВ блок успешно добавлен атрибут - &f" + description));
            return true;
        }

        if(args[0].equalsIgnoreCase("remove")) {
            if (args.length != 2) {
                player.sendMessage(colorize("&cСинтаксис: &7/blockdesc remove <описание>"));
                return true;
            }

            if (!plugin.getAttributesManager().isBlockConsistsAttributes(location)) {
                player.sendMessage(colorize("&cДанный блок не содержит атрибутов!"));
                return true;
            }

            if (!isNumeric(args[1])) {
                player.sendMessage(colorize("&cДолжно быть число!"));
                return true;
            }

            int index = Integer.parseInt(args[1]);
            String[] attributesArray = getAttributesArrayFromJson(jsonLoc);

            if (attributesArray.length-1 < index) {
                player.sendMessage(colorize("&cАтрибута с таким индексом нет!"));
                return true;
            }

            String[] attributes = plugin.getAttributesManager().removeAttribute(attributesArray, index);
            if (attributes.length == 0) {
                plugin.getDatabase().removeLocation(jsonLoc);
                plugin.getHologramManager().removeHologram(location);
            } else {
                // Convert new array to str and put it in database
                String finalStr = plugin.getAttributesManager().toJsonAttributes(attributes);
                plugin.getDatabase().setAttributes(jsonLoc, finalStr);
            }

            player.sendMessage(colorize("&aИз блока успешно удалён атрибут - &f" + attributesArray[index]));
            return true;
        }

        return true;
    }

    /**
     * Get array from Database of string loc
     * @param jsonLoc - Json format location
     * @return String array
     */
    private String[] getAttributesArrayFromJson(String jsonLoc) {
        String strAttributes = plugin.getDatabase().getEnteredAttributes(jsonLoc);
        return plugin.getAttributesManager().fromJsonAttributes(strAttributes);
    }

    private String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

}
