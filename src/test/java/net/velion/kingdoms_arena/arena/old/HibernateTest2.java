package net.velion.kingdoms_arena.arena;

import jakarta.persistence.EntityManager;
import net.velion.kingdoms_arena.Kingdoms_arena;
import net.velion.kingdoms_arena.arena.condition.arena.end.ArenaTimeOver;
import net.velion.kingdoms_arena.arena.condition.arena.end.LastRoundOver;
import net.velion.kingdoms_arena.arena.condition.arena.end.MissingPlayerRequirement;
import net.velion.kingdoms_arena.arena.condition.arena.start.MinimalPlayerRequirement;
import net.velion.kingdoms_arena.arena.condition.arena.start.TimeTillStart;
import net.velion.kingdoms_arena.arena.condition.arena.win.ArenaMostXGained;
import net.velion.kingdoms_arena.arena.condition.arena.win.LastRoundWon;
import net.velion.kingdoms_arena.arena.condition.arena.win.MostRoundsWon;
import net.velion.kingdoms_arena.arena.condition.round.end.LastStageOver;
import net.velion.kingdoms_arena.arena.condition.round.end.RoundTimeOver;
import net.velion.kingdoms_arena.arena.condition.round.win.LastStageFinished;
import net.velion.kingdoms_arena.arena.condition.round.win.RoundMostXGained;
import net.velion.kingdoms_arena.arena.condition.stage.end.OrderAchieved;
import net.velion.kingdoms_arena.arena.entity.ArenaColor;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.PlayerStorage;
import net.velion.kingdoms_arena.arena.entity.Team;
import net.velion.kingdoms_arena.arena.entity.inventory.Inventory;
import net.velion.kingdoms_arena.arena.entity.inventory.Item;
import net.velion.kingdoms_arena.arena.entity.score.Action;
import net.velion.kingdoms_arena.arena.entity.score.ScoreIndex;
import net.velion.kingdoms_arena.arena.entity.score.TeamScoreEntry;
import net.velion.kingdoms_arena.arena.objective.CaptureObjective;
import net.velion.kingdoms_arena.arena.objective.KillObjective;
import net.velion.kingdoms_arena.arena.objective.PointObjective;
import net.velion.kingdoms_arena.arena.order.Order;
import net.velion.kingdoms_arena.arena.order.OrderEntry;
import net.velion.kingdoms_arena.arena.round.LinearRound;
import net.velion.kingdoms_arena.arena.stage.Stage;
import net.velion.kingdoms_arena.arena.zone.TriggerSphere;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.*;
import net.velion.kingdoms_arena.jpa.JPAConnector;
import net.velion.kingdoms_arena.jpa.JPALoader;
import net.velion.kingdoms_arena.jpa.JPAService;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.SQLException;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class HibernateTest2
{
    private static MockedStatic<Bukkit> bukkit;
    private static PluginManager pluginManager;
    private static World world;
    private static org.bukkit.entity.Player bukkitPlayer1;
    private static org.bukkit.entity.Player bukkitPlayer2;
    private static PlayerInventory bukkitPlayerInventory;
    private static PlayerInventory bukkitPlayerInventory2;
    private static ScoreboardManager scoreboardManager1;
    private static Scoreboard scoreboard1;
    private static org.bukkit.scoreboard.Team scoreboardTeam1;
    private static org.bukkit.scoreboard.Team scoreboardTeam2;
    private UUID arenaUuid;

    public static UUID player1Uuid = UUID.fromString("ac11b6e0-e31e-41e8-b1e3-24e0652ee13a");
    public static UUID player2Uuid = UUID.fromString("5c389e50-71a7-47e1-995f-29b78cda34c3");
    public static UUID playerStorage1Uuid = UUID.fromString("62cc4a5f-509e-4dc7-b38b-eb133134a369");
    public static UUID playerStorage2Uuid = UUID.fromString("c24dee0b-9c41-4e89-853c-d489c4298a4d");
    public static UUID inventory1Uuid = UUID.fromString("292187ca-f2a1-4786-a2f1-142f766656b4");
    public static UUID inventory2Uuid = UUID.fromString("eb386880-907b-4ffe-83db-c0033c4c8334");
    public static UUID item1Uuid = UUID.fromString("2f3cb28f-4ba5-4f27-a303-73a08d09a5c7");
    public static UUID item2Uuid = UUID.fromString("9acb3009-5847-4cfa-b815-584b2c03dc3c");
    public static UUID item3Uuid = UUID.fromString("e592a71f-2b5b-4008-9424-0d92d2c68529");
    public static UUID item4Uuid = UUID.fromString("e2cd6993-6fe9-400e-8a2e-3d4c7f6de6ab");
    public static UUID item5Uuid = UUID.fromString("39cff253-f1e9-4ef8-a55d-422516592e91");
    public static UUID playerScoreEntry1Uuid = UUID.fromString("c7bb643c-0526-4fdd-bbba-bbc9f14ae8ec");
    public static UUID playerScoreEntry2Uuid = UUID.fromString("0d9c2fdb-fd36-42f1-bdc6-efe780530bf2");
    public static UUID team1Uuid = UUID.fromString("ec83ecf2-3502-43f4-bb0e-6057e5ee2ca1");
    public static UUID team2Uuid = UUID.fromString("0f5223d4-99c4-4736-89e5-c48d55b4d1b9");
    public static UUID teamScoreEntry1Uuid = UUID.fromString("e226ba27-2dce-4deb-913d-99805cabedfd");
    public static UUID teamScoreEntry2Uuid = UUID.fromString("9357f44d-9de2-430d-96b7-6c5058392f67");
    public static UUID pointObjective1Uuid = UUID.fromString("c3723049-3790-4441-8fe8-dd57e079b3cc");
    public static UUID killObjective1Uuid = UUID.fromString("ca160af1-5015-4b6d-b70b-948bbf4ff735");
    public static UUID killObjective2Uuid = UUID.fromString("79dd71de-8b55-4b3f-87e7-fb5a07cee2c8");
    public static UUID captureObjective1Uuid = UUID.fromString("e1fd7518-1866-4059-896d-8676d8d7b991");
    public static UUID captureFunction1Uuid = UUID.fromString("2f7ada92-dca7-49fa-b1b1-97c46119f342");
    public static UUID captureEnterFunction1Uuid = UUID.fromString("c080d95f-7ae3-435e-bda0-56e4cd71c4d5");
    public static UUID captureLeaveFunction1Uuid = UUID.fromString("b7afca4b-82ae-4b30-a26b-60cd24e81d08");
    public static UUID addSpawnFunction1Uuid = UUID.fromString("aa329db5-e6f8-43be-a284-e6766216a9c0");
    public static UUID order1Uuid = UUID.fromString("901276a9-4060-4010-a714-eca378d5c2a3");
    public static UUID order2Uuid = UUID.fromString("b6d44bab-861a-47bf-965f-697cd29dc30b");
    public static UUID orderEntry1Uuid = UUID.fromString("fd42b303-887b-41f0-8bc8-945fc156fcb5");
    public static UUID orderEntry2Uuid = UUID.fromString("ebecb5da-4fc5-4f05-9bb8-3b6af1fb06e3");
    public static UUID stage1Uuid = UUID.fromString("26fb82bd-d5f4-4521-839a-29c0f1247d43");
    public static UUID linearRound1Uuid = UUID.fromString("52a571f6-dcd9-4994-837a-cecdd14ec6ee");
    public static UUID roundScoreTable1Uuid = UUID.fromString("d6838005-0d44-4ab1-8e21-976301a17ec0");
    public static UUID teamArena1Uuid = UUID.fromString("d2b4b199-7e98-42c6-96ec-2e9b9d904a12");
    public static UUID arenaScoreTable1Uuid = UUID.fromString("b4bd7723-1de1-4f7f-ac21-a7593024ddb4");
    public static UUID triggerSphere1Uuid = UUID.fromString("52a5b285-99e7-491d-b9cd-966bbe5dc762");
    public static UUID triggerSphere2Uuid = UUID.fromString("bbcc7b03-d496-425a-bf5a-d4d1a10c14ff");
    public static UUID arenaLocation1Uuid = UUID.fromString("6d25a9a1-aaf9-4666-85ed-b9b2fb17d601");
    public static UUID arenaMostXGained1Uuid = UUID.fromString("897c4994-6723-4386-93d7-735aafabeee0");
    public static UUID lastRoundWon1Uuid = UUID.fromString("7aa97b8e-ef5d-4c39-8e18-859edf5057fa");
    public static UUID mostRoundsWon1Uuid = UUID.fromString("f56e38ba-1d16-459a-bf33-d3df75f0ae61");
    public static UUID timeTillStart1Uuid = UUID.fromString("7977289c-4b97-4143-8f5d-15b925617fd5");
    public static UUID minimalPlayerRequirement1Uuid = UUID.fromString("9a211de9-2e9d-4aea-bcdc-cde16ba9d21c");
    public static UUID arenaTimeOver1Uuid = UUID.fromString("eab08901-c24e-42ac-ad86-5ba7175b5b96");
    public static UUID lastRoundOver1Uuid = UUID.fromString("a708dc73-8922-42b8-ad11-caa518545080");
    public static UUID missingPlayerRequirement1Uuid = UUID.fromString("638df04f-ed71-4793-9ca8-5cfc24e035f6");
    public static UUID lastStageOver1Uuid = UUID.fromString("2beff43c-3c74-4694-bb25-a52b39159756");
    public static UUID roundTimeOver1Uuid = UUID.fromString("66b96ec3-3392-40c0-80e2-2d10d3f3ce35");
    public static UUID roundMostXGained1Uuid = UUID.fromString("b3142b95-ecdb-40eb-8c6a-dae6bcf47a24");
    public static UUID lastStageFinished1Uuid = UUID.fromString("b91a1f59-8351-4168-9a43-7afa787601e3");
    public static UUID orderAchieved1Uuid = UUID.fromString("308864ca-88e2-4934-8a61-f95094d0c141");
    public static UUID scoreIndex1Uuid = UUID.fromString("76ff07d5-e8e1-47a0-9b2e-0ccfffe7ed99");
    public static String arenaColor1Id = "player1Color";
    public static String arenaColor2Id = "team1Color";

    public static Player player1;
    public static Player player2;
    public static PlayerStorage playerStorage1;
    public static PlayerStorage playerStorage2;
    public static Inventory inventory1;
    public static Item item1;
    public static Item item2;
    public static Item item3;
    public static Item item4;
    public static Item item5;
    //public static PlayerScoreEntry playerScoreEntry1;
    //public static PlayerScoreEntry playerScoreEntry2;
    public static Team team1;
    public static Team team2;
    public static TeamScoreEntry teamScoreEntry1;
    public static TeamScoreEntry teamScoreEntry2;
    public static ArenaColor arenaColor1;
    public static ArenaColor arenaColor2;
    public static KillObjective killObjective1;
    public static KillObjective killObjective2;
    public static PointObjective pointObjective1;
    public static Order order1;
    public static Order order2;
    public static OrderEntry orderEntry1;
    public static OrderEntry orderEntry2;
    public static Stage stage1;
    public static LinearRound linearRound1;
    //public static RoundScoreTable roundScoreTable1;
    public static TeamArena teamArena1;
    //public static ArenaScoreTable arenaScoreTable1;
    public static TriggerSphere triggerSphere1;
    public static TriggerSphere triggerSphere2;
    public static Inventory inventory2;
    public static CaptureFunction captureFunction1;
    public static CaptureEnterFunction captureEnterFunction1;
    public static CaptureLeaveFunction captureLeaveFunction1;
    public static CaptureObjective captureObjective1;
    public static AddSpawnFunction addSpawnFunction1;
    public static ArenaMostXGained arenaMostXGained1;
    public static LastRoundWon lastRoundWon1;
    public static MostRoundsWon mostRoundsWon1;
    public static TimeTillStart timeTillStart1;
    public static MinimalPlayerRequirement minimalPlayerRequirement1;
    public static ArenaTimeOver arenaTimeOver1;
    public static LastRoundOver lastRoundOver1;
    public static MissingPlayerRequirement missingPlayerRequirement1;
    public static LastStageOver lastStageOver1;
    public static RoundTimeOver roundTimeOver1;
    public static RoundMostXGained roundMostXGained1;
    public static LastStageFinished lastStageFinished1;
    public static OrderAchieved orderAchieved1;
    public static ScoreIndex scoreIndex1;

    @BeforeAll
    public static void beforeAll() throws SQLException
    {
        JPAService.open();
        JPAConnector.open();

        bukkit = mockStatic(Bukkit.class);
        world = mock(World.class);
        bukkitPlayer1 = mock(org.bukkit.entity.Player.class);
        bukkitPlayer2 = mock(org.bukkit.entity.Player.class);
        bukkitPlayerInventory = mock(PlayerInventory.class);
        bukkitPlayerInventory2 = mock(PlayerInventory.class);
        pluginManager = mock(PluginManager.class);
        scoreboardManager1 = mock(ScoreboardManager.class);
        scoreboard1 = mock(Scoreboard.class);
        scoreboardTeam1 = mock(org.bukkit.scoreboard.Team.class);
        scoreboardTeam2 = mock(org.bukkit.scoreboard.Team.class);

        bukkit.when(() -> Bukkit.getWorld("TestWorld")).thenReturn(world);
        bukkit.when(() -> Bukkit.getPluginManager()).thenReturn(pluginManager);
        bukkit.when(() -> Bukkit.getPlayer(player1Uuid)).thenReturn(bukkitPlayer1);
        bukkit.when(() -> Bukkit.getPlayer(player2Uuid)).thenReturn(bukkitPlayer2);
        bukkit.when(() -> Bukkit.getScoreboardManager()).thenReturn(scoreboardManager1);
        when(world.getName()).thenReturn("TestWorld");
        when(bukkitPlayer1.getName()).thenReturn("Player1");
        when(bukkitPlayer1.getInventory()).thenReturn(bukkitPlayerInventory);
        when(bukkitPlayer1.getGameMode()).thenReturn(GameMode.SPECTATOR);
        when(bukkitPlayer1.getUniqueId()).thenReturn(player1Uuid);
        when(bukkitPlayer2.getName()).thenReturn("Player2");
        when(bukkitPlayer2.getInventory()).thenReturn(bukkitPlayerInventory2);
        when(bukkitPlayer2.getGameMode()).thenReturn(GameMode.ADVENTURE);
        when(bukkitPlayer2.getUniqueId()).thenReturn(player2Uuid);
        when(bukkitPlayerInventory.getContents()).thenReturn(new ItemStack[48]);
        when(scoreboardManager1.getNewScoreboard()).thenReturn(scoreboard1);
        when(scoreboard1.registerNewTeam("Team1")).thenReturn(scoreboardTeam1);
        when(scoreboard1.registerNewTeam("Team2")).thenReturn(scoreboardTeam2);
    }

    @BeforeEach
    public void beforeEach() throws SQLException
    {
        JPAConnector.open();
    }

    @AfterEach
    public void afterEach() throws SQLException
    {

        JPAConnector.tryClose();
    }

    @AfterAll
    public static void afterAll()
    {
        /*
        EntityManager entityManager = JPAConnector.EntityManager;

        Stage stage1 = entityManager.find(Stage.class, stage1Uuid);

        entityManager.remove(stage1);

        JPAConnector.commit();
         */
        JPAService.tryClose();
    }

    @Test
    public void testA1() throws SQLException
    {
        EntityManager entityManager = JPAConnector.EntityManager;

        player1 = new Player(bukkitPlayer1, null);
        player2 = new Player(bukkitPlayer2, null);
        playerStorage1 = new PlayerStorage(playerStorage1Uuid);
        playerStorage2 = new PlayerStorage(playerStorage2Uuid);
        inventory1 = new Inventory(inventory1Uuid);
        item1 = new Item(item1Uuid, 1, Material.DIAMOND, 1);
        item2 = new Item(item2Uuid, 3, Material.MELON, 12);
        item3 = new Item(item3Uuid, 23, Material.ACACIA_LOG, 3);
        item4 = new Item(item4Uuid, 7, Material.BIRCH_STAIRS, 5);
        item5 = new Item(item5Uuid, 16, Material.FISHING_ROD, 2);
        //playerScoreEntry1 = new PlayerScoreEntry(playerScoreEntry1Uuid);
        //playerScoreEntry2 = new PlayerScoreEntry(playerScoreEntry2Uuid);
        team1 = new Team(team1Uuid, "Team1", null);
        team2 = new Team(team2Uuid, "Team2", null);
        teamScoreEntry1 = new TeamScoreEntry(teamScoreEntry1Uuid);
        teamScoreEntry2 = new TeamScoreEntry(teamScoreEntry2Uuid);
        arenaColor1 = new ArenaColor(arenaColor1Id, ChatColor.WHITE, BarColor.RED, Material.BLUE_WOOL);
        arenaColor2 = new ArenaColor(arenaColor2Id, ChatColor.YELLOW, BarColor.GREEN, Material.CYAN_WOOL);
        killObjective1 = new KillObjective(killObjective1Uuid, team1, 42);
        killObjective2 = new KillObjective(killObjective2Uuid, team2, 25);
        pointObjective1 = new PointObjective(pointObjective1Uuid, team2, 13);
        order1 = new Order(order1Uuid);
        order2 = new Order(order2Uuid);
        orderEntry1 = new OrderEntry(orderEntry1Uuid, team1, order1);
        orderEntry2 = new OrderEntry(orderEntry2Uuid, team2, order2);
        stage1 = new Stage(stage1Uuid);
        linearRound1 = new LinearRound(linearRound1Uuid);
        //roundScoreTable1 = new RoundScoreTable(roundScoreTable1Uuid);
        teamArena1 = new TeamArena(teamArena1Uuid, "TeamArena1", 2);
        //arenaScoreTable1 = new ArenaScoreTable(arenaScoreTable1Uuid);
        triggerSphere1 = new TriggerSphere(triggerSphere1Uuid, "TriggerSphere1", teamArena1, null, 4);
        triggerSphere2 = new TriggerSphere(triggerSphere2Uuid, "TriggerSphere2", teamArena1, null, 7);
        inventory2 = new Inventory(inventory2Uuid);
        captureFunction1 = new CaptureFunction(captureFunction1Uuid, triggerSphere1, 5, 3.2);
        captureEnterFunction1 =
                new CaptureEnterFunction(captureEnterFunction1Uuid, triggerSphere1, captureFunction1);
        captureLeaveFunction1 =
                new CaptureLeaveFunction(captureLeaveFunction1Uuid, triggerSphere1, captureFunction1);
        captureObjective1 = new CaptureObjective(captureObjective1Uuid, player1, captureFunction1);
        addSpawnFunction1 = new AddSpawnFunction(addSpawnFunction1Uuid, triggerSphere2);
        arenaMostXGained1 = new ArenaMostXGained(arenaMostXGained1Uuid, teamArena1, Action.KILL);
        lastRoundWon1 = new LastRoundWon(lastRoundWon1Uuid, teamArena1);
        mostRoundsWon1 = new MostRoundsWon(mostRoundsWon1Uuid, teamArena1);
        timeTillStart1 = new TimeTillStart(timeTillStart1Uuid, teamArena1, 12.34);
        minimalPlayerRequirement1 =
                new MinimalPlayerRequirement(minimalPlayerRequirement1Uuid, teamArena1);
        arenaTimeOver1 = new ArenaTimeOver(arenaTimeOver1Uuid, teamArena1, 5.67, false);
        lastRoundOver1 = new LastRoundOver(lastRoundOver1Uuid, teamArena1);
        missingPlayerRequirement1 =
                new MissingPlayerRequirement(missingPlayerRequirement1Uuid, teamArena1);
        lastStageOver1 = new LastStageOver(lastStageOver1Uuid, teamArena1, linearRound1);
        roundTimeOver1 = new RoundTimeOver(roundTimeOver1Uuid, teamArena1, linearRound1, 8.9);
        roundMostXGained1 =
                new RoundMostXGained(roundMostXGained1Uuid, teamArena1, linearRound1, Action.DEATH);
        lastStageFinished1 = new LastStageFinished(lastStageFinished1Uuid, teamArena1, linearRound1);
        orderAchieved1 = new OrderAchieved(orderAchieved1Uuid, teamArena1, stage1);
        scoreIndex1 = new ScoreIndex(scoreIndex1Uuid);

        player1 = entityManager.merge(player1);
        player2 = entityManager.merge(player2);
        playerStorage1 = entityManager.merge(playerStorage1);
        playerStorage2 = entityManager.merge(playerStorage2);
        inventory1 = entityManager.merge(inventory1);
        item1 = entityManager.merge(item1);
        item2 = entityManager.merge(item2);
        item3 = entityManager.merge(item3);
        inventory2 = entityManager.merge(inventory2);
        item4 = entityManager.merge(item4);
        item5 = entityManager.merge(item5);
        //playerScoreEntry1 = entityManager.merge(playerScoreEntry1);
        //playerScoreEntry2 = entityManager.merge(playerScoreEntry2);
        team1 = entityManager.merge(team1);
        team2 = entityManager.merge(team2);
        teamScoreEntry1 = entityManager.merge(teamScoreEntry1);
        arenaColor1 = entityManager.merge(arenaColor1);
        arenaColor2 = entityManager.merge(arenaColor2);
        killObjective1 = entityManager.merge(killObjective1);
        killObjective2 = entityManager.merge(killObjective2);
        pointObjective1 = entityManager.merge(pointObjective1);
        order1 = entityManager.merge(order1);
        order2 = entityManager.merge(order2);
        orderEntry1 = entityManager.merge(orderEntry1);
        orderEntry2 = entityManager.merge(orderEntry2);
        stage1 = entityManager.merge(stage1);
        linearRound1 = entityManager.merge(linearRound1);
        //roundScoreTable1 = entityManager.merge(roundScoreTable1);
        teamArena1 = entityManager.merge(teamArena1);
        //arenaScoreTable1 = entityManager.merge(arenaScoreTable1);
        triggerSphere1 = entityManager.merge(triggerSphere1);
        triggerSphere2 = entityManager.merge(triggerSphere2);
        captureFunction1 = entityManager.merge(captureFunction1);
        captureObjective1 = entityManager.merge(captureObjective1);
        captureEnterFunction1 = entityManager.merge(captureEnterFunction1);
        captureLeaveFunction1 = entityManager.merge(captureLeaveFunction1);
        addSpawnFunction1 = entityManager.merge(addSpawnFunction1);
        arenaMostXGained1 = entityManager.merge(arenaMostXGained1);
        lastRoundWon1 = entityManager.merge(lastRoundWon1);
        mostRoundsWon1 = entityManager.merge(mostRoundsWon1);
        timeTillStart1 = entityManager.merge(timeTillStart1);
        minimalPlayerRequirement1 = entityManager.merge(minimalPlayerRequirement1);
        arenaTimeOver1 = entityManager.merge(arenaTimeOver1);
        lastRoundOver1 = entityManager.merge(lastRoundOver1);
        missingPlayerRequirement1 = entityManager.merge(missingPlayerRequirement1);
        lastStageOver1 = entityManager.merge(lastStageOver1);
        roundTimeOver1 = entityManager.merge(roundTimeOver1);
        roundMostXGained1 = entityManager.merge(roundMostXGained1);
        lastStageFinished1 = entityManager.merge(lastStageFinished1);
        orderAchieved1 = entityManager.merge(orderAchieved1);
        scoreIndex1 = entityManager.merge(scoreIndex1);

        player1.setOrder(order1);
        player1.setArena(teamArena1);
        player1.setArenaColor(arenaColor1);
        player1.setPlayerStorage(playerStorage1);
        //player1.setPlayerScoreEntry(playerScoreEntry1);
        player2.setArena(teamArena1);
        player2.setPlayerStorage(playerStorage2);
        //player2.setPlayerScoreEntry(playerScoreEntry2);
        inventory1.addItem(item1);
        inventory1.addItem(item2);
        inventory1.addItem(item3);
        inventory2.addItem(item4);
        inventory2.addItem(item5);
        player1.setRespawnInventory(inventory2);
        playerStorage1.setInventory(inventory1);
        team1.addPlayer(player1);
        team1.setOrder(order1);
        team1.setArenaColor(arenaColor1);
        team1.setTeamScoreEntry(teamScoreEntry1);
        team2.addPlayer(player2);
        team2.setOrder(order2);
        team2.setArenaColor(arenaColor2);
        team2.setTeamScoreEntry(teamScoreEntry2);
        //teamScoreEntry1.addPlayerScoreEntry(playerScoreEntry2);
        order1.addObjective(killObjective1);
        order2.addObjective(pointObjective1);
        order2.addObjective(killObjective2);
        stage1.putOrder(orderEntry1);
        stage1.putOrder(orderEntry2);
        linearRound1.addStage(stage1);
        linearRound1.setArena(teamArena1);
        //roundScoreTable1.addScoreEntry(playerScoreEntry1);
        //roundScoreTable1.addScoreEntry(teamScoreEntry1);
        //linearRound1.setScoreTable(roundScoreTable1);
        //arenaScoreTable1.addRoundScoreTable(roundScoreTable1);
        teamArena1.addRound(linearRound1);
        teamArena1.addPlayer(player1);
        teamArena1.addPlayer(player2);
        teamArena1.addTeam(team1);
        teamArena1.addTeam(team2);
        //teamArena1.setArenaScoreTable(arenaScoreTable1);
        team1.addSpawnPoint(triggerSphere1);
        team2.addSpawnPoint(triggerSphere2);
        //playerScoreEntry1.addScore(Action.KILL, 13);
        captureFunction1.addCaptureObjective(captureObjective1);
        triggerSphere1.addPassiveFunction(captureFunction1);
        triggerSphere1.putFunction(HookType.ENTER, captureEnterFunction1);
        triggerSphere1.putFunction(HookType.LEAVE, captureLeaveFunction1);
        triggerSphere2.putFunction(HookType.ENTER, addSpawnFunction1);
        teamArena1.addWinCondition(arenaMostXGained1);
        teamArena1.addWinCondition(lastRoundWon1);
        teamArena1.addWinCondition(mostRoundsWon1);
        teamArena1.addStartCondition(timeTillStart1);
        teamArena1.addStartCondition(minimalPlayerRequirement1);
        teamArena1.addEndCondition(arenaTimeOver1);
        teamArena1.addEndCondition(lastRoundOver1);
        teamArena1.addEndCondition(missingPlayerRequirement1);
        teamArena1.setScoreIndex(scoreIndex1);
        linearRound1.addRoundEndCondition(lastStageOver1);
        linearRound1.addRoundEndCondition(roundTimeOver1);
        linearRound1.addRoundWinCondition(roundMostXGained1);
        linearRound1.addRoundWinCondition(lastStageFinished1);
        stage1.addEndCondition(orderAchieved1);
        scoreIndex1.putActionPoints(Action.ASSIST, 15.26);

        JPAConnector.commit();
    }

    @Test
    public void testA2() throws SQLException
    {
        EntityManager entityManager = JPAConnector.EntityManager;

        teamArena1 = entityManager.find(TeamArena.class, teamArena1Uuid);

        teamArena1.initialise();
    }

    @Test
    public void testA3() throws SQLException
    {
        JPALoader.loadAllArenas();

        Kingdoms_arena.ARENAS.getClass();
    }
}
