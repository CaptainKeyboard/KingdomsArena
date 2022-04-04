package net.velion.core;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class ArmorType
{
    public static EquipmentSlot getSlot(Material material)
    {
        if (material.name().endsWith("_HELMET"))
        {
            return EquipmentSlot.HEAD;
        } else if (material.name().endsWith("_CHESTPLATE"))
        {
            return EquipmentSlot.CHEST;
        } else if (material.name().endsWith("_LEGGINGS"))
        {
            return EquipmentSlot.LEGS;
        } else if (material.name().endsWith("_BOOTS"))
        {
            return EquipmentSlot.FEET;
        }
        return null;
    }
}
