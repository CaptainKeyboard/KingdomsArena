package net.velion.kingdoms_arena.arena.zone.triggerfunction;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.primitives.Doubles;
import net.velion.core.NoNullArrayList;
import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.Kingdoms_arena;
import net.velion.kingdoms_arena.arena.entity.ArenaColor;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.Team;
import net.velion.kingdoms_arena.arena.objective.CaptureObjective;
import net.velion.kingdoms_arena.arena.zone.Zone;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;

public class CaptureFunction
{
    protected Zone zone;
    protected double radius;
    protected double ticks;
    protected double progress;
    protected ArenaEntity owner;
    protected ArenaEntity claimer;
    protected BossBar bossBar;
    protected Hologram hologram;
    protected boolean enabled;
    protected ArenaColor defaultArenaColor;
    protected Set<CaptureObjective> captureObjectives;

    public CaptureFunction(Zone zone, double radius, double ticks)
    {
        this.zone = zone;
        this.radius = radius;
        this.ticks = ticks;
        captureObjectives = new NoNullHashSet<>();
        progress = 0;
        enabled = false;
        defaultArenaColor = ArenaColor.DEFAULT();
        bossBar = Bukkit.createBossBar(zone.getName(), defaultArenaColor.getBarColor(), BarStyle.SEGMENTED_10);
        bossBar.setVisible(false);
        hologram = HologramsAPI.createHologram(Kingdoms_arena.KINGDOMS_ARENA,
                zone.getArenaLocation().getBukkitLocation().clone().add(0, 3, 0));
        hologram.appendTextLine("§f§l" + zone.getName());
        hologram.appendItemLine(new ItemStack(defaultArenaColor.getMaterial()));
        hologram.getVisibilityManager().setVisibleByDefault(false);
    }

    public BossBar getBossBar()
    {
        return bossBar;
    }

    public Set<CaptureObjective> getCaptureObjectives()
    {
        return captureObjectives;
    }

    public void setCaptureObjectives(Set<CaptureObjective> captureObjectives)
    {
        this.captureObjectives = captureObjectives;
    }

    public void addCaptureObjective(CaptureObjective captureObjective)
    {
        this.captureObjectives.add(captureObjective);
    }

    public ArenaColor getDefaultArenaColor()
    {
        return defaultArenaColor;
    }

    public ArenaEntity getClaimer()
    {
        return claimer;
    }

    public ArenaEntity getOwner()
    {
        return owner;
    }

    public void setOwner(ArenaEntity owner)
    {
        this.owner = owner;
    }

    public Zone getTriggerZone()
    {
        return zone;
    }

    public void enable()
    {
        if (!enabled)
        {
            reset();
            bossBar.setVisible(true);
        }
        enabled = true;
    }

    public void disable()
    {
        if (enabled)
        {
            reset();
        }
        enabled = false;
    }

    public void reset()
    {
        owner = null;
        claimer = null;
        progress = 0;
        hologram.clearLines();
        hologram.appendTextLine(zone.getName());
        hologram.appendItemLine(new ItemStack(defaultArenaColor.getMaterial()));
        hologram.getVisibilityManager().resetVisibilityAll();
        bossBar.setVisible(false);
        bossBar.setColor(defaultArenaColor.getBarColor());
        bossBar.removeAll();
    }

    public void _execute(Set<Player> players)
    {
        bossBar.setProgress(Doubles.constrainToRange(progress / (ticks / 4), 0, 1));

        List<ArenaEntity> entities = getConqueringEntities(players);
        ArenaEntity currentConqueror = getCurrentConqueror(entities);

        if (currentConqueror != null)
        {
            if (claimer == null)
            {
                claimer = currentConqueror;
                bossBar.setColor(currentConqueror.getArenaColor().getBarColor());
            }

            if (!currentConqueror.equals(owner))
            {
                if (currentConqueror.equals(claimer))
                {
                    progress++;
                } else
                {
                    progress--;
                }
            }
        }

        if (claimer != null && currentConqueror == null && entities.isEmpty())
        {
            if (claimer.equals(owner) && progress < (ticks / 4))
            {
                progress++;
            }
            if (!claimer.equals(owner) && progress > 0)
            {
                progress--;
            }
        }


        if (claimer != null)
        {
            String name = zone.getName();
            if (progress <= 0)
            {
                claimer = null;
                owner = null;
                bossBar.setTitle(name);
                hologram.removeLine(1);
                hologram.appendItemLine(new ItemStack(defaultArenaColor.getMaterial()));
                checkArena();
            } else if (!claimer.equals(owner) && progress >= (ticks / 4))
            {
                owner = claimer;
                hologram.removeLine(1);
                hologram.appendItemLine(new ItemStack(owner.getArenaColor().getMaterial()));
                bossBar.setTitle(name + " [§" + owner.getArenaColor().getChatColor().getChar() +
                        claimer.getName() + "§r]");
                bossBar.setColor(owner.getArenaColor().getBarColor());
                checkArena();
            }
        }
    }

    private void checkArena()
    {
        for (CaptureObjective captureObjective : captureObjectives)
        {
            captureObjective.updateScoreboard();
        }
        zone.getArena().check();
    }

    public void registerObjective(CaptureObjective objective)
    {
        captureObjectives.add(objective);

        if (!enabled)
        {
            enable();
        }

        showTo(objective.getOperator());
    }

    public void unregisterObjective(CaptureObjective objective)
    {
        captureObjectives.remove(objective);


        if (captureObjectives.isEmpty())
        {
            if (enabled)
            {
                disable();
            }
        }
    }

    public void showTo(ArenaEntity arenaEntity)
    {
        if (arenaEntity instanceof Team)
        {
            Team team = (Team) arenaEntity;
            for (Player player : team.getPlayers())
            {
                hologram.getVisibilityManager().showTo(player.getBukkitPlayer());
            }
        } else
        {
            Player player = (Player) arenaEntity;
            hologram.getVisibilityManager().showTo((player).getBukkitPlayer());
        }
    }

    public void hideFrom(ArenaEntity arenaEntity)
    {
        if (arenaEntity instanceof Team)
        {
            Team team = (Team) arenaEntity;
            for (Player player : team.getPlayers())
            {
                hologram.getVisibilityManager().hideTo(player.getBukkitPlayer());
            }
        } else
        {
            Player player = (Player) arenaEntity;
            hologram.getVisibilityManager().hideTo((player).getBukkitPlayer());
        }
    }

    private ArenaEntity getCurrentConqueror(List<ArenaEntity> entities)
    {
        Set<Map.Entry<ArenaEntity, Long>> occurences =
                entities.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet();

        OptionalLong max = occurences.stream().mapToLong(value -> value.getValue()).max();


        if (max.isPresent())
        {
            List<ArenaEntity> conquerors =
                    occurences.stream().filter(entityLongEntry -> entityLongEntry.getValue().equals(max.getAsLong()))
                            .map(entityLongEntry -> entityLongEntry.getKey()).collect(
                                    Collectors.toList());

            return conquerors.size() == 1 ? conquerors.get(0) : null;
        } else
        {
            return null;
        }
    }

    public List<ArenaEntity> getConqueringEntities(Set<Player> players)
    {
        List<ArenaEntity> entities = new NoNullArrayList<>();

        if (!players.isEmpty())
        {
            for (Player player : players)
            {
                if (captureObjectives.stream()
                        .anyMatch(captureObjective -> player.getOrder().hasObjective(captureObjective)))
                {
                    if (player.getTeam() != null)
                    {
                        Team team = player.getTeam();
                        entities.add(team);
                    } else
                    {
                        entities.add(player);
                    }
                }
            }
        }
        return entities;
    }
}


