package net.velion.kingdoms_arena.builder.round;

import net.velion.core.NoNullArrayList;
import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.round.end.RoundEndCondition;
import net.velion.kingdoms_arena.arena.condition.round.win.RoundWinCondition;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.inventory.Inventory;
import net.velion.kingdoms_arena.arena.round.LinearRound;
import net.velion.kingdoms_arena.arena.stage.Stage;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.builder.condition.round.end.RoundEndConditionBuilder;
import net.velion.kingdoms_arena.builder.condition.round.win.RoundWinConditionBuilder;
import net.velion.kingdoms_arena.builder.entity.IEntitySelector;
import net.velion.kingdoms_arena.builder.entity.InventoryBuilder;
import net.velion.kingdoms_arena.builder.stage.StageBuilder;
import net.velion.kingdoms_arena.builder.zone.SelectorException;
import net.velion.kingdoms_arena.builder.zone.ZoneSelector;

import java.util.*;

public class LinearRoundBuilder extends RoundBuilder
{
    public LinearRoundBuilder(UUID uuid)
    {
        super(uuid);
    }

    @Override
    public LinearRound build(Arena arena, Set<ArenaEntity> entities,
                             Set<Zone> zones) throws SelectorException
    {
        LinearRound linearRound = new LinearRound(arena, uuid);

        List<Stage> stages = new NoNullArrayList<>();
        for (StageBuilder stageBuilder : stageBuilders)
        {
            stages.add(stageBuilder.build(entities, zones));
        }

        Set<RoundWinCondition> roundWinConditions = new NoNullHashSet<>();
        for (RoundWinConditionBuilder conitionParser : winConditionBuilders)
        {
            roundWinConditions.add(conitionParser.build(linearRound));
        }

        Set<RoundEndCondition> roundEndConditions = new NoNullHashSet<>();
        for (RoundEndConditionBuilder conditionBuilder : endConditionBuilders)
        {
            roundEndConditions.add(conditionBuilder.build(linearRound));
        }

        Map<ArenaEntity, Inventory> inventoryMap = new HashMap<>();
        for (Map.Entry<IEntitySelector, InventoryBuilder> gearParserEntry : gearParserEntries.entrySet())
        {
            ArenaEntity entity = gearParserEntry.getKey().select(entities);
            Inventory inventory = gearParserEntry.getValue().build();
            inventoryMap.put(entity, inventory);
        }

        Map<ArenaEntity, Set<Zone>> spawnPointMap = new HashMap<>();
        for (Map.Entry<IEntitySelector, List<ZoneSelector>> spawnPointEntry : spawnPointEntries.entrySet())
        {
            ArenaEntity entity = spawnPointEntry.getKey().select(entities);

            Set<Zone> spawnPoints = new HashSet<>();
            for (ZoneSelector spawnPointSelector : spawnPointEntry.getValue())
            {
                spawnPoints.add(spawnPointSelector.select(zones));
            }
            spawnPointMap.put(entity, spawnPoints);
        }

        linearRound.setStages(stages);
        linearRound.setSpawnPoints(spawnPointMap);
        linearRound.setInvetoryMap(inventoryMap);
        linearRound.setEndConditions(roundEndConditions);
        linearRound.setWinConditions(roundWinConditions);

        return linearRound;
    }

    public RoundBuilder setStages(StageBuilder... stageBuilders)
    {
        this.stageBuilders = List.of(stageBuilders);
        return this;
    }

    public RoundBuilder setEndConditions(RoundEndConditionBuilder... conditions)
    {
        this.endConditionBuilders = List.of(conditions);
        return this;
    }

    @Override
    public RoundBuilder setGear(IEntitySelector entitySelector, InventoryBuilder gear)
    {
        this.gearParserEntries.put(entitySelector, gear);
        return this;
    }

    @Override
    public RoundBuilder setSpawnPoints(IEntitySelector entitySelector,
                                       ZoneSelector... spawnPointSelectors)
    {
        this.spawnPointEntries.put(entitySelector, List.of(spawnPointSelectors));
        return this;
    }

    public RoundBuilder setWinConditions(RoundWinConditionBuilder... conditions)
    {
        this.winConditionBuilders = List.of(conditions);
        return this;
    }
}
