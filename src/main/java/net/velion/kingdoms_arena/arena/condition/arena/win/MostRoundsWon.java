package net.velion.kingdoms_arena.arena.condition.arena.win;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MostRoundsWon extends ArenaWinCondition
{
    public MostRoundsWon(Arena arena)
    {
        super(arena);
    }


    @Override
    public void setup()
    {

    }

    @Override
    public Set<ArenaEntity> check()
    {
       /* Set<Map.Entry<Entity, Long>> entityOccurencies =
                arena.getArenaScoreTable().getAllScoreTables().stream()
                        .flatMap(roundScoreTable -> roundScoreTable.getWinners().stream())
                        .collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet();*/

        Stream<ArenaEntity> test = arena.getArenaScoreTable().getRoundScoreTables().stream()
                .flatMap(roundScoreTable -> roundScoreTable.getWinners().stream());

        Set<Map.Entry<ArenaEntity, Long>> entityOccurencies =
                test.collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet();

        Long maxAmount =
                entityOccurencies.stream().map(Map.Entry::getValue).max(Comparator.comparing(aLong -> aLong)).get();

        Set<ArenaEntity> winners =
                entityOccurencies.stream().filter(stringLongEntry -> stringLongEntry.getValue().equals(maxAmount))
                        .map(Map.Entry::getKey).collect(Collectors.toSet());

        return winners;
    }

    @Override
    public void reset()
    {

    }
}
