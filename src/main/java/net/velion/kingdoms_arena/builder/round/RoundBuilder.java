package net.velion.kingdoms_arena.builder.round;

import net.velion.core.NoNullArrayList;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.round.Round;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.builder.condition.round.end.RoundEndConditionBuilder;
import net.velion.kingdoms_arena.builder.condition.round.win.RoundWinConditionBuilder;
import net.velion.kingdoms_arena.builder.entity.IEntitySelector;
import net.velion.kingdoms_arena.builder.entity.InventoryBuilder;
import net.velion.kingdoms_arena.builder.stage.StageBuilder;
import net.velion.kingdoms_arena.builder.zone.SelectorException;
import net.velion.kingdoms_arena.builder.zone.ZoneSelector;

import java.util.*;

public abstract class RoundBuilder
{
    protected UUID uuid;
    protected int index;
    protected List<RoundEndConditionBuilder> endConditionBuilders = new NoNullArrayList<>();
    protected Map<IEntitySelector, InventoryBuilder> gearParserEntries = new HashMap<>();
    protected Map<IEntitySelector, List<ZoneSelector>> spawnPointEntries = new HashMap<>();
    protected List<StageBuilder> stageBuilders = new NoNullArrayList<>();
    protected List<RoundWinConditionBuilder> winConditionBuilders = new NoNullArrayList<>();

    public RoundBuilder(UUID uuid)
    {
        this.uuid = uuid;
    }

    public abstract Round build(Arena arena, Set<ArenaEntity> entities, Set<Zone> zones)
            throws SelectorException;

    public abstract RoundBuilder setStages(StageBuilder... stageBuilders);

    public abstract RoundBuilder setEndConditions(RoundEndConditionBuilder... conditions);

    public abstract RoundBuilder setGear(IEntitySelector entitySelector, InventoryBuilder gear);

    public abstract RoundBuilder setSpawnPoints(IEntitySelector entitySelector,
                                                ZoneSelector... spawnPointSelectors);

    public abstract RoundBuilder setWinConditions(RoundWinConditionBuilder... conditions);
}
