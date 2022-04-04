package net.velion.kingdoms_arena.arena;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.velion.core.NoNullArrayList;
import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.condition.arena.end.ArenaEndCondition;
import net.velion.kingdoms_arena.arena.condition.arena.start.ArenaStartCondition;
import net.velion.kingdoms_arena.arena.condition.arena.win.ArenaWinCondition;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.Team;
import net.velion.kingdoms_arena.arena.entity.score.ArenaScoreTable;
import net.velion.kingdoms_arena.arena.entity.score.ScoreIndex;
import net.velion.kingdoms_arena.arena.round.Round;
import net.velion.kingdoms_arena.arena.zone.TriggerSphere;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.event.RunArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static net.velion.kingdoms_arena.Kingdoms_arena.ARENA_PLAYERS;
import static net.velion.kingdoms_arena.Kingdoms_arena.KINGDOMS_ARENA;

public abstract class Arena
{
    protected UUID uuid;
    protected String name;
    protected ArenaScoreTable arenaScoreTable;
    protected Set<Zone> zones = new NoNullHashSet<>();
    protected List<Round> rounds = new NoNullArrayList<>();
    protected Integer currentRound;
    protected Integer minPlayers;
    protected Set<Player> players = new NoNullHashSet<>();
    protected Set<Team> teams = new NoNullHashSet<>();
    protected ScoreIndex scoreIndex;
    protected Status status;
    protected Set<ArenaWinCondition> winConditions = new NoNullHashSet<>();
    protected Set<ArenaStartCondition> startConditions = new NoNullHashSet<>();
    protected Set<ArenaEndCondition> endConditions = new NoNullHashSet<>();
    private boolean restartAtEnd;
    private BukkitRunnable arenaStarter;

    public Arena(UUID uuid, String name, int minPlayers)
    {
        this.uuid = uuid;
        this.name = name;
        this.minPlayers = minPlayers;
        currentRound = -1;
        status = Status.NOT_STARTED;
        restartAtEnd = false;
        arenaScoreTable = new ArenaScoreTable(UUID.randomUUID());
    }

    public void addWinCondition(ArenaWinCondition arenaWinCondition)
    {
        this.winConditions.add(arenaWinCondition);
    }

    public Set<ArenaWinCondition> getWinConditions()
    {
        return winConditions;
    }

    public void addStartCondition(ArenaStartCondition arenaStartCondition)
    {
        this.startConditions.add(arenaStartCondition);
    }

    public Set<ArenaStartCondition> getStartConditions()
    {
        return startConditions;
    }

    public void addEndCondition(ArenaEndCondition arenaEndCondition)
    {
        this.endConditions.add(arenaEndCondition);
    }

    public Set<ArenaEndCondition> getEndConditions()
    {
        return endConditions;
    }

    public Set<ArenaEntity> getEntities()
    {
        Set<ArenaEntity> entities = new NoNullHashSet<>();
        entities.addAll(players);
        entities.addAll(teams);
        return entities;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public ArenaScoreTable getArenaScoreTable()
    {
        return arenaScoreTable;
    }

    public Round getCurrentRound()
    {
        return rounds.get(currentRound);
    }

    public Set<Zone> getTriggerZones()
    {
        return zones;
    }

    public Set<Team> getArenaTeams()
    {
        return teams;
    }

    public int getMinPlayers()
    {
        return minPlayers;
    }

    public String getName()
    {
        return name;
    }

    public ScoreIndex getScoreIndex()
    {
        return scoreIndex;
    }

    public void setScoreIndex(ScoreIndex scoreIndex)
    {
        this.scoreIndex = scoreIndex;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setTeams(Set<Team> teams)
    {
        this.teams.clear();
        this.teams.addAll(teams);
    }

    public void addTeam(Team team)
    {
        this.teams.add(team);
    }

    public Set<Player> getPlayers()
    {
        return players;
    }

    public void setPlayers(Set<Player> players)
    {
        this.players.clear();
        this.players.addAll(players);
    }

    public void addPlayer(Player player)
    {
        this.players.add(player);
    }

    public void removePlayer(Player player)
    {
        players.remove(player);
    }

    public List<Round> getRounds()
    {
        return rounds;
    }

    public void setRounds(List<Round> rounds)
    {
        this.rounds.clear();
        this.rounds.addAll(rounds);
    }

    public void addRound(Round round)
    {
        this.rounds.add(round);
    }

    public void setWinConditions(Set<ArenaWinCondition> winConditions)
    {
        this.winConditions.clear();
        this.winConditions.addAll(winConditions);
    }

    public void setStartConditions(Set<ArenaStartCondition> startConditions)
    {
        this.startConditions.clear();
        this.startConditions.addAll(startConditions);
    }

    public void setEndConditions(Set<ArenaEndCondition> endConditions)
    {
        this.endConditions.clear();
        this.endConditions.addAll(endConditions);
    }

    public void setTriggerZones(Set<Zone> zones)
    {
        this.zones.clear();
        this.zones.addAll(zones);
    }

    public void addTriggerZone(TriggerSphere triggerSphere1)
    {
        zones.add(triggerSphere1);
    }

    public void setArenaScoreTable(ArenaScoreTable arenaScoreTable)
    {
        this.arenaScoreTable = arenaScoreTable;
    }

    public void start() throws Exception
    {
        if (status.equals(Status.NOT_STARTED))
        {
            status = Status.INITIALISING;
            setup();

            status = Status.VALIDATING;
            validate();

            status = Status.WAITING_FOR_START;
            arenaStarter = new ArenaStartTask();
            arenaStarter.runTaskTimer(KINGDOMS_ARENA, 20, 10);
        } else
        {
            throw new Exception("Arena already started");
        }
    }

    public void setup()
    {
        for (ArenaStartCondition condition : startConditions)
        {
            condition.setup();
        }
    }

    public boolean validate() throws ArenaInvalidException
    {
        if (scoreIndex == null)
        {
            throw new ArenaInvalidException("ScoreIndex is null");
        } else
        {
            scoreIndex.validate();
        }
        if (rounds == null || rounds.isEmpty())
        {
            throw new ArenaInvalidException("Rounds are either null or empty");
        } else
        {
            for (Round round : rounds)
            {
                round.validate();
            }
        }
        if (winConditions == null || winConditions.isEmpty())
        {
            throw new ArenaInvalidException("WinConditions either null or empty");
        }
        if (startConditions == null || startConditions.isEmpty())
        {
            throw new ArenaInvalidException("StartConditions either null or empty");
        }
        if (endConditions == null || endConditions.isEmpty())
        {
            throw new ArenaInvalidException("EndConditions either null or empty");
        }
        if (status == null)
        {
            throw new ArenaInvalidException("Status is null");
        }
        return true;
    }

    public void startFirstRound()
    {
        List<Player> players = new ArrayList<>(this.players);
        Collections.shuffle(players);
        for (Player player : players)
        {
            player.store();
            getSmallestTeam().addPlayer(player);
        }
        nextRound();
    }

    protected Team getSmallestTeam()
    {
        int min = teams.stream().mapToInt(value -> value.getPlayers().size()).min().getAsInt();

        return teams.stream().filter(team -> team.getPlayers().size() == min).findFirst().get();
    }

    public void check()
    {
        if (getCurrentRound().check())
        {
            nextRound();
        } else
        {
        }
    }

    public void nextRound()
    {
        if (hasNextRound())
        {
            currentRound++;
            status = Status.ROUND_TRANSITION;

            Round round = rounds.get(currentRound);

            round.enable();

            for (Zone zone : zones)
            {
                zone.enable();
            }

            status = Status.RUNNING;
        } else
        {
            status = Status.OVER;
            end();
        }
    }

    public void end()
    {
        List<ArenaEntity> winners = null;
        if (winConditions != null && !winConditions.isEmpty())
        {
            Set<Map.Entry<ArenaEntity, Long>> entityOccurencies =
                    winConditions.stream().flatMap(arenaWinCondition -> arenaWinCondition.check().stream())
                            .collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet();

            Long maxAmount =
                    entityOccurencies.stream().map(Map.Entry::getValue).max(Comparator.comparing(aLong -> aLong))
                            .get();

            winners =
                    entityOccurencies.stream()
                            .filter(stringLongEntry -> stringLongEntry.getValue().equals(maxAmount))
                            .map(Map.Entry::getKey).collect(Collectors.toList());

        }

        Title title;
        if (winners != null)
        {
            title = Title.title(Component.text("Arena over"),
                    Component.text(
                            "Winner/s: " + winners.stream().map(ArenaEntity::getName).collect(Collectors.toList())));
        } else
        {
            title = Title.title(Component.text("Arena over"), Component.text(""));
        }

        for (Player player : players)
        {
            player.getBukkitPlayer().showTitle(title);
            player.restore();
        }
        reset();
    }

    public void reset()
    {
        for (ArenaStartCondition condition : startConditions)
        {
            condition.reset();
        }

        for (ArenaWinCondition condition : winConditions)
        {
            condition.reset();
        }

        for (ArenaEndCondition condition : endConditions)
        {
            condition.reset();
        }

        for (Player player : new ArrayList<>(players))
        {
            leave(player.getBukkitPlayer());
        }

        for (Round round : rounds)
        {
            round.reset();
        }

        for (Zone zone : zones)
        {
            zone.reset();
        }

        status = Status.NOT_STARTED;

        currentRound = -1;
        players = new HashSet<>();

        for (Team team : teams)
        {
            team.reset();
        }

        if (arenaStarter != null && !arenaStarter.isCancelled())
        {
            arenaStarter.cancel();
            arenaStarter = null;
        }
    }

    public Player getArenaPlayer(org.bukkit.entity.Player player)
    {
        return players.stream().filter(player1 -> player1.getBukkitPlayer().equals(player)).findFirst().orElse(null);
    }

    public boolean hasNextRound()
    {
        return currentRound + 1 < rounds.size();
    }

    public boolean isArenaPlayer(org.bukkit.entity.Player player)
    {
        return players.stream().anyMatch(player1 -> player1.getBukkitPlayer().equals(player));
    }

    public abstract void join(org.bukkit.entity.Player player) throws SQLException;

    public void leave(org.bukkit.entity.Player player)
    {
        Player arenaPlayer = getArenaPlayer(player);
        players.remove(arenaPlayer);
        arenaPlayer.setTeam(null);
        ARENA_PLAYERS.remove(player);
    }

    class ArenaStartTask extends BukkitRunnable
    {
        @Override
        public void run()
        {
            if (startConditions.stream().allMatch(arenaStartCondition -> arenaStartCondition.check()))
            {
                RunArenaEvent runArenaEvent = new RunArenaEvent(Arena.this);
                Bukkit.getPluginManager().callEvent(runArenaEvent);
                cancel();
            }
        }
    }
}
