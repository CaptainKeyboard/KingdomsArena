package net.velion.kingdoms_arena.builder;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.builder.zone.SelectorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

public class FactoryTest
{
    private UUID arenaUuid;

    @BeforeEach
    public void beforeEach() throws SQLException
    {
    }

    @AfterEach
    public void afterEach() throws SQLException
    {
    }

    @Test
    public void shouldCreateTeamArena() throws SQLException, SelectorException
    {
        arenaUuid = UUID.randomUUID();

        Arena arena = Factory.NEW.TeamArena(arenaUuid, arenaUuid.toString(), 2).build();

        Assertions.assertNotNull(arena);
    }
}
