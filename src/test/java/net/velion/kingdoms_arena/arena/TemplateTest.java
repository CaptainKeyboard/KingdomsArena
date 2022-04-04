package net.velion.kingdoms_arena.arena;

import net.velion.kingdoms_arena.TemplateLoader;
import net.velion.kingdoms_arena.TemplateLoaderException;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.builder.zone.SelectorException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class TemplateTest
{
    private UUID arenaUuid;
    private static MockedStatic<Bukkit> bukkit;
    private static World world;
    private static BossBar bossBar;

    @BeforeAll
    public static void beforeAll()
    {
        bossBar = spy(BossBar.class);
        bukkit = mockStatic(Bukkit.class);
        mockStatic(ChatColor.class);
        mockStatic(BarColor.class);
        world = mock(World.class);
        when(world.getName()).thenReturn("TestWorld");
        bukkit.when(() -> Bukkit.getWorld("TestWorld")).thenReturn(world);
        bukkit.when(() -> Bukkit.createBossBar(anyString(), any(), any())).thenReturn(bossBar);
    }

    @BeforeEach
    public void beforeEach() throws SQLException
    {
    }

    @AfterEach
    public void afterEach() throws SQLException
    {
    }

    @Test
    public void shouldLoadAllFinishedArenas()
            throws SQLException, IOException, ParseException, TemplateLoaderException, SelectorException
    {
        TemplateLoader.loadAllFinishedTemplates();
    }

    @Test
    public void shouldLoadAndInsertTestTeamArena()
            throws IOException, ParseException, TemplateLoaderException, SQLException, URISyntaxException,
            SelectorException
    {

        File file;
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("templates/TestTeamArena.arena");
        if (resource == null)
        {
            throw new IllegalArgumentException("file not found! " + "templates/TestTeamArena.arena");
        } else
        {
            file = new File(resource.toURI());
        }

        Arena arena = TemplateLoader.loadTemplate(file).build();

        org.bukkit.entity.Player player1 = spy(org.bukkit.entity.Player.class);
        org.bukkit.entity.Player player2 = spy(org.bukkit.entity.Player.class);

        when(player1.getUniqueId()).thenReturn(UUID.fromString("63d91c82-d0cc-4527-8ac4-00332377ac36"));
        when(player2.getUniqueId()).thenReturn(UUID.fromString("dc11df38-358e-4b98-8292-88757adc9c97"));
        when(player1.getName()).thenReturn("player1Name");
        when(player2.getName()).thenReturn("player2Name");

        Player arenaPlayer1 = new Player(player1, arena, null);
        arenaPlayer1.getName();
        arenaPlayer1.getUuid();
        arenaPlayer1.getClass();
    }
}
