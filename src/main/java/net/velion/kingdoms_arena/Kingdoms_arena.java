package net.velion.kingdoms_arena;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.builder.zone.SelectorException;
import net.velion.kingdoms_arena.command.JoinCommand;
import net.velion.kingdoms_arena.command.StartCommand;
import net.velion.kingdoms_arena.command.WatchItem;
import net.velion.kingdoms_arena.event.ArenaListener;
import net.velion.kingdoms_arena.event.BukkitListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Kingdoms_arena extends JavaPlugin
{
    public static Set<Arena> ARENAS = new NoNullHashSet<>();
    public static Map<Player, Arena> ARENA_PLAYERS = new HashMap<>();
    public static Kingdoms_arena KINGDOMS_ARENA;
    public static String arenaUuid;

    public static Arena getArena(String uuid)
    {
        return ARENAS.stream().filter(arena -> arena.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public static Arena getArenaByName(String name)
    {
        return ARENAS.stream().filter(arena -> arena.getName().equals(name)).findFirst().orElse(null);
    }

    public static boolean hasArena(String uuid)
    {
        return ARENAS.stream().anyMatch(arena -> arena.getUuid().equals(uuid));
    }

    @Override
    public void onEnable()
    {
        KINGDOMS_ARENA = this;

        getServer().getPluginManager().registerEvents(new BukkitListener(), this);
        getServer().getPluginManager().registerEvents(new ArenaListener(), this);
        getCommand("join").setExecutor(new JoinCommand());
        getCommand("start").setExecutor(new StartCommand());
        getCommand("watchItem").setExecutor(new WatchItem());

        try
        {
            ARENAS = TemplateLoader.loadAllFinishedTemplates();
            getLogger().info("Arenas loaded: " + ARENAS.size());
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ParseException e)
        {
            e.printStackTrace();
        } catch (TemplateLoaderException e)
        {
            e.printStackTrace();
        } catch (SelectorException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable()
    {
    }
}
