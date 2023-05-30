package ru.permasha.blockdescription.managers;

import com.google.gson.Gson;
import org.bukkit.Location;
import ru.permasha.blockdescription.BlockDescription;
import ru.permasha.blockdescription.objects.LocationModel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        System.out.println(jsonLoc);
        System.out.println(plugin.getDatabase().getEnteredAttributes(jsonLoc));
        return plugin.getDatabase().getEnteredAttributes(jsonLoc) != null;
    }

    public Location fromJsonLocation(String json) {
        return new Gson().fromJson(json, LocationModel.class).toLocation();
    }

    public String toJsonLocation(Location location) {
        return new Gson().toJson(new LocationModel(location));
    }

    public String[] fromJsonAttributes(String json) {
        return new Gson().fromJson(json, String[].class);
    }

    public String toJsonAttributes(String[] arrayAttributes) {
        return new Gson().toJson(arrayAttributes);
    }

    public String[] addAttribute(String[] arrayAttributes, String attribute) {
        List<String> attributes = Arrays.asList(arrayAttributes);
        attributes.add(attribute);
        return attributes.toArray(new String[0]);
    }

    public String[] removeAttribute(String[] arrayAttributes, int index) {
        List<String> attributes = Arrays.asList(arrayAttributes);
        attributes.remove(index);
        return attributes.toArray(new String[0]);
    }

}
