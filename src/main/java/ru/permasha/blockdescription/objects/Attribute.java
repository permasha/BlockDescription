package ru.permasha.blockdescription.objects;

public class Attribute {

    String playerName;
    String description;

    public Attribute(String playerName, String description) {
        this.playerName = playerName;
        this.description = description;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDescription() {
        return description;
    }

    public Attribute toAttribute() {
        return new Attribute(playerName, description);
    }
}
