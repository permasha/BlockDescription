package ru.permasha.blockdescription.managers;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import ru.permasha.blockdescription.BlockDescription;
import ru.permasha.blockdescription.objects.Attribute;
import ru.permasha.blockdescription.objects.LocationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttributesManager {

    BlockDescription plugin;

    public AttributesManager(BlockDescription plugin) {
        this.plugin = plugin;
    }

    /**
     * Check location for attributes
     */
    public boolean isBlockConsistsAttributes(Location location) {
        String jsonLoc = toJsonLocation(location);
        return plugin.getDatabase().getEnteredAttributes(jsonLoc) != null;
    }

    public String getMessageAttribute(Location location) {
        if (isBlockConsistsAttributes(location)) {
            String jsonLoc = toJsonLocation(location);
            String attributesJson = plugin.getDatabase().getEnteredAttributes(jsonLoc);
            String[] attributesArray = fromJsonAttributes(attributesJson);

            List<String> attributesList = new ArrayList<>();
            for (int i = 0; i < attributesArray.length; i++) {
                String attributeStr = attributesArray[i];
                Attribute attribute = fromJsonAttribute(attributeStr);
                attributesList.add(getFormatMessageAttribute(i, attribute));
            }

            return attributesList.stream().map(Object::toString)
                    .collect(Collectors.joining(""));
        }
        return ChatColor.RED + "Данный блок не содержит атрибутов!";
    }

    /**
     * Return formatted list attributes
     * @param location - Block location
     * @return colorize string
     */
    public String getFormatMessageAttributes(Location location) {
        String cfgStr = plugin.getPlugin().getConfig().getString("blockdesc.message")
                .replace("%attributes%", getMessageAttribute(location));
        return colorize(cfgStr);
    }

    public String getFormatMessageAttribute(int index, Attribute attribute) {
        return colorize(plugin.getPlugin().getConfig().getString("blockdesc.attribute"))
                .replace("%index%", ""+index)
                .replace("%player%", attribute.getPlayerName())
                .replace("%description%", attribute.getDescription());
    }

    /**
     * @return Radius for holograms
     */
    public int getRadius() {
        return plugin.getPlugin().getConfig().getInt("blockdesc.radius");
    }

    /**
     * @return Symbol for holograms
     */
    public String getSymbol() {
        return colorize(plugin.getPlugin().getConfig().getString("blockdesc.symbol"));
    }

    public Location fromJsonLocation(String json) {
        return new Gson().fromJson(json, LocationModel.class).toLocation();
    }

    public String toJsonLocation(Location location) {
        return new Gson().toJson(new LocationModel(location));
    }

    public Attribute fromJsonAttribute(String json) {
        return new Gson().fromJson(json, Attribute.class).toAttribute();
    }

    public String toJsonAttribute(Attribute attribute) {
        return new Gson().toJson(attribute);
    }

    public String[] fromJsonAttributes(String json) {
        return new Gson().fromJson(json, String[].class);
    }

    public String toJsonAttributes(String[] arrayAttributes) {
        return new Gson().toJson(arrayAttributes);
    }

    public String[] addAttribute(String[] arrayAttributes, String attribute) {
        List<String> attributes = new ArrayList<>(Arrays.asList(arrayAttributes));
        attributes.add(attribute);
        return attributes.toArray(String[]::new);
    }

    public String[] removeAttribute(String[] arrayAttributes, int index) {
        List<String> attributes = new ArrayList<>(Arrays.asList(arrayAttributes));
        attributes.remove(index);
        return attributes.toArray(String[]::new);
    }

    private String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
