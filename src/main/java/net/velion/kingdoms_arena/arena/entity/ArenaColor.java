package net.velion.kingdoms_arena.arena.entity;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;

public class ArenaColor
{
    private String name;
    private ChatColor chatColor;
    private BarColor barColor;
    private Material material;
    private Color color;

    public ArenaColor(String name, ChatColor chatColor, BarColor barColor, Material material, Color color)
    {
        this.name = name;
        this.chatColor = chatColor;
        this.barColor = barColor;
        this.material = material;
        this.color = color;
    }

    public static ArenaColor DEFAULT()
    {
        return new ArenaColor("DEFAULT", ChatColor.WHITE, BarColor.WHITE, Material.WHITE_WOOL,
                Color.fromRGB(233, 233, 233));
    }

    public static ArenaColor RED()
    {
        return new ArenaColor("RED", ChatColor.RED, BarColor.RED, Material.RED_WOOL, Color.fromRGB(174, 40, 31));
    }

    public static ArenaColor BLUE()
    {
        return new ArenaColor("BLUE", ChatColor.BLUE, BarColor.BLUE, Material.BLUE_WOOL, Color.fromRGB(47, 49, 142));
    }

    public static ArenaColor YELLOW()
    {
        return new ArenaColor("YELLOW", ChatColor.YELLOW, BarColor.YELLOW, Material.YELLOW_WOOL,
                Color.fromRGB(249, 197, 31));
    }

    public static ArenaColor GREEN()
    {
        return new ArenaColor("GREEN", ChatColor.GREEN, BarColor.GREEN, Material.GREEN_WOOL,
                Color.fromRGB(87, 114, 18));
    }

    public static ArenaColor Test()
    {
        return new ArenaColor("Test", ChatColor.RED, BarColor.RED, Material.RED_WOOL, Color.fromRGB(174, 40, 31));
    }

    public static ArenaColor valueOf(String stringVariable)
    {
        switch (stringVariable.toLowerCase())
        {
            case "default" -> {
                return DEFAULT();
            }
            case "red" -> {
                return RED();
            }
            case "blue" -> {
                return BLUE();
            }
            case "yellow" -> {
                return YELLOW();
            }
            case "green" -> {
                return GREEN();
            }
            case "test" -> {
                return Test();
            }
            default -> {
                return DEFAULT();
            }
        }
    }

    public ChatColor getChatColor()
    {
        return chatColor;
    }

    public BarColor getBarColor()
    {
        return barColor;
    }

    public Material getMaterial()
    {
        return material;
    }

    public String getName()
    {
        return name;
    }

    public Color getColor()
    {
        return color;
    }
}
