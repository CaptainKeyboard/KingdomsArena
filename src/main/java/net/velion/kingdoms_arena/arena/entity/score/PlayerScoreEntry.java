package net.velion.kingdoms_arena.arena.entity.score;

import net.velion.kingdoms_arena.arena.entity.Player;

import java.util.UUID;

public class PlayerScoreEntry extends ScoreEntry
{
    public PlayerScoreEntry(UUID uuid, Player player)
    {
        super(uuid, player);
    }

    @Override
    public UUID getUuid()
    {
        return uuid;
    }

    public double getScore(ScoreType scoreType)
    {
        return scores.containsKey(scoreType) ? scores.get(scoreType) : 0;
    }

    public Player getPlayer()
    {
        return (Player) entity;
    }

    public void setPlayer(Player player)
    {
        this.entity = player;
    }

    public void validate()
    {

    }
}
