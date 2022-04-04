package net.velion.kingdoms_arena.arena.zone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

@Entity
public class ArenaLocation
{
    @Id
    protected UUID uuid;
    protected String world = "";
    protected double x = 0d;
    protected double y = 0d;
    protected double z = 0d;
    protected float yaw = 0f;
    protected float pitch = 0f;

    public ArenaLocation(UUID uuid, String world, double x, double y, double z, float yaw, float pitch)
    {
        this.uuid = uuid;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    private ArenaLocation()
    {
        this.uuid = UUID.randomUUID();
    }

    public ArenaLocation(UUID uuid, org.bukkit.Location location)
    {
        this(uuid, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(),
                location.getPitch());
    }

    public ArenaLocation(UUID uuid, String world, double x, double y, double z)
    {
        this(uuid, world, x, y, z, 0f, 0f);
    }

    /**
     * Use {@link #ArenaLocation(UUID, Location)} instead.
     */
    @Deprecated
    public ArenaLocation(org.bukkit.Location location)
    {
        this.uuid = UUID.randomUUID();
        world = location.getWorld().getName();
        x = location.getX();
        y = location.getY();
        z = location.getZ();
        yaw = location.getYaw();
        pitch = location.getPitch();
    }

    public org.bukkit.Location getBukkitLocation()
    {
        World bukkitWorld = Bukkit.getWorld(world);
        return new Location(bukkitWorld, x, y, z, yaw, pitch);
    }

    public UUID getUuid()
    {
        return uuid;
    }
}
