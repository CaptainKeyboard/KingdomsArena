package net.velion.kingdoms_arena.arena;

import jakarta.persistence.EntityManager;
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
import net.velion.kingdoms_arena.arena.entity.score.*;
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
import net.velion.kingdoms_arena.jpa.JPAService;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.SQLException;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class HibernateTest
{
    private static MockedStatic<Bukkit> bukkit;
    private static PluginManager pluginManager;
    private static World world;
    private static org.bukkit.entity.Player bukkitPlayer1;
    private static org.bukkit.entity.Player bukkitPlayer2;
    private static PlayerInventory bukkitPlayerInventory;
    private static PlayerInventory bukkitPlayerInventory2;
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
    public static UUID teamScoreEntry1Uuid = UUID.fromString("e226ba27-2dce-4deb-913d-99805cabedfd");
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

    @BeforeAll
    public static void beforeAll() throws SQLException
    {
        JPAService.open();

        bukkit = mockStatic(Bukkit.class);
        world = mock(World.class);
        bukkitPlayer1 = mock(org.bukkit.entity.Player.class);
        bukkitPlayer2 = mock(org.bukkit.entity.Player.class);
        bukkitPlayerInventory = mock(PlayerInventory.class);
        bukkitPlayerInventory2 = mock(PlayerInventory.class);
        pluginManager = mock(PluginManager.class);
        bukkit.when(() -> Bukkit.getWorld("TestWorld")).thenReturn(world);
        bukkit.when(() -> Bukkit.getPluginManager()).thenReturn(pluginManager);
        bukkit.when(() -> Bukkit.getPlayer(player1Uuid)).thenReturn(bukkitPlayer1);
        bukkit.when(() -> Bukkit.getPlayer(player2Uuid)).thenReturn(bukkitPlayer2);
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
        JPAService.tryClose();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testA1()
    {
        EntityManager entityManager = JPAConnector.EntityManager;

        Player player1 = new Player(bukkitPlayer1, null);
        Player player2 = new Player(bukkitPlayer2, null);
        PlayerStorage playerStorage1 = new PlayerStorage(playerStorage1Uuid);
        PlayerStorage playerStorage2 = new PlayerStorage(playerStorage2Uuid);
        Inventory inventory1 = new Inventory();
        Item item1 = new Item(item1Uuid, 1, Material.DIAMOND, 1);
        Item item2 = new Item(item2Uuid, 3, Material.MELON, 12);
        Item item3 = new Item(item3Uuid, 23, Material.ACACIA_LOG, 3);
        Item item4 = new Item(item4Uuid, 7, Material.BIRCH_STAIRS, 5);
        Item item5 = new Item(item5Uuid, 16, Material.FISHING_ROD, 2);
        PlayerScoreEntry playerScoreEntry1 = new PlayerScoreEntry(playerScoreEntry1Uuid);
        PlayerScoreEntry playerScoreEntry2 = new PlayerScoreEntry(playerScoreEntry2Uuid);
        Team team1 = new Team(team1Uuid, "Team1", null);
        TeamScoreEntry teamScoreEntry1 = new TeamScoreEntry(teamScoreEntry1Uuid);
        ArenaColor arenaColor1 = new ArenaColor(arenaColor1Id, ChatColor.WHITE, BarColor.RED, Material.BLUE_WOOL);
        ArenaColor arenaColor2 = new ArenaColor(arenaColor2Id, ChatColor.YELLOW, BarColor.GREEN, Material.CYAN_WOOL);
        KillObjective killObjective1 = new KillObjective(killObjective1Uuid, player1, 42);
        KillObjective killObjective2 = new KillObjective(killObjective2Uuid, team1, 25);
        PointObjective pointObjective1 = new PointObjective(pointObjective1Uuid, team1, 13);
        Order order1 = new Order(order1Uuid);
        Order order2 = new Order(order2Uuid);
        OrderEntry orderEntry1 = new OrderEntry(orderEntry1Uuid, player1, order1);
        OrderEntry orderEntry2 = new OrderEntry(orderEntry2Uuid, team1, order2);
        Stage stage1 = new Stage(stage1Uuid);
        LinearRound linearRound1 = new LinearRound(linearRound1Uuid);
        RoundScoreTable roundScoreTable1 = new RoundScoreTable(roundScoreTable1Uuid);
        TeamArena teamArena1 = new TeamArena(teamArena1Uuid, "TeamArena1", 2);
        ArenaScoreTable arenaScoreTable1 = new ArenaScoreTable(arenaScoreTable1Uuid);
        TriggerSphere triggerSphere1 = new TriggerSphere(triggerSphere1Uuid, "TriggerSphere1", teamArena1, null, 4);
        TriggerSphere triggerSphere2 = new TriggerSphere(triggerSphere2Uuid, "TriggerSphere2", teamArena1, null, 7);
        Inventory inventory2 = new Inventory(inventory2Uuid);
        CaptureFunction captureFunction1 = new CaptureFunction(captureFunction1Uuid, triggerSphere1, 5, 3.2);
        CaptureEnterFunction captureEnterFunction1 =
                new CaptureEnterFunction(captureEnterFunction1Uuid, triggerSphere1, captureFunction1);
        CaptureLeaveFunction captureLeaveFunction1 =
                new CaptureLeaveFunction(captureLeaveFunction1Uuid, triggerSphere1, captureFunction1);
        CaptureObjective captureObjective1 = new CaptureObjective(captureObjective1Uuid, player1, captureFunction1);
        AddSpawnFunction addSpawnFunction1 = new AddSpawnFunction(addSpawnFunction1Uuid, triggerSphere2);
        ArenaMostXGained arenaMostXGained1 = new ArenaMostXGained(arenaMostXGained1Uuid, teamArena1, Action.KILL);
        LastRoundWon lastRoundWon1 = new LastRoundWon(lastRoundWon1Uuid, teamArena1);
        MostRoundsWon mostRoundsWon1 = new MostRoundsWon(mostRoundsWon1Uuid, teamArena1);
        TimeTillStart timeTillStart1 = new TimeTillStart(timeTillStart1Uuid, teamArena1, 12.34);
        MinimalPlayerRequirement minimalPlayerRequirement1 =
                new MinimalPlayerRequirement(minimalPlayerRequirement1Uuid, teamArena1);
        ArenaTimeOver arenaTimeOver1 = new ArenaTimeOver(arenaTimeOver1Uuid, teamArena1, 5.67, false);
        LastRoundOver lastRoundOver1 = new LastRoundOver(lastRoundOver1Uuid, teamArena1);
        MissingPlayerRequirement missingPlayerRequirement1 =
                new MissingPlayerRequirement(missingPlayerRequirement1Uuid, teamArena1);
        LastStageOver lastStageOver1 = new LastStageOver(lastStageOver1Uuid, teamArena1, linearRound1);
        RoundTimeOver roundTimeOver1 = new RoundTimeOver(roundTimeOver1Uuid, teamArena1, linearRound1, 8.9);
        RoundMostXGained roundMostXGained1 =
                new RoundMostXGained(roundMostXGained1Uuid, teamArena1, linearRound1, Action.DEATH);
        LastStageFinished lastStageFinished1 = new LastStageFinished(lastStageFinished1Uuid, teamArena1, linearRound1);
        OrderAchieved orderAchieved1 = new OrderAchieved(orderAchieved1Uuid, teamArena1, stage1);
        ScoreIndex scoreIndex1 = new ScoreIndex(scoreIndex1Uuid);

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
        playerScoreEntry1 = entityManager.merge(playerScoreEntry1);
        playerScoreEntry2 = entityManager.merge(playerScoreEntry2);
        team1 = entityManager.merge(team1);
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
        roundScoreTable1 = entityManager.merge(roundScoreTable1);
        teamArena1 = entityManager.merge(teamArena1);
        arenaScoreTable1 = entityManager.merge(arenaScoreTable1);
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
        player1.setPlayerScoreEntry(playerScoreEntry1);
        player2.setArena(teamArena1);
        player2.setPlayerStorage(playerStorage2);
        player2.setPlayerScoreEntry(playerScoreEntry2);
        inventory1.addItem(item1);
        inventory1.addItem(item2);
        inventory1.addItem(item3);
        inventory2.addItem(item4);
        inventory2.addItem(item5);
        player1.setRespawnInventory(inventory2);
        playerStorage1.setInventory(inventory1);
        team1.addPlayer(player2);
        team1.setOrder(order2);
        team1.setArenaColor(arenaColor2);
        team1.setTeamScoreEntry(teamScoreEntry1);
        teamScoreEntry1.addPlayerScoreEntry(playerScoreEntry2);
        order1.addObjective(killObjective1);
        order2.addObjective(pointObjective1);
        order2.addObjective(killObjective2);
        stage1.putOrder(orderEntry1);
        stage1.putOrder(orderEntry2);
        linearRound1.addStage(stage1);
        roundScoreTable1.addScoreEntry(playerScoreEntry1);
        roundScoreTable1.addScoreEntry(teamScoreEntry1);
        linearRound1.setScoreTable(roundScoreTable1);
        arenaScoreTable1.addRoundScoreTable(roundScoreTable1);
        teamArena1.addRound(linearRound1);
        teamArena1.addPlayer(player1);
        teamArena1.addPlayer(player2);
        teamArena1.addTeam(team1);
        teamArena1.setArenaScoreTable(arenaScoreTable1);
        player1.addSpawnPoint(triggerSphere1);
        team1.addSpawnPoint(triggerSphere2);
        playerScoreEntry1.addScore(Action.KILL, 13);
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
    @org.junit.jupiter.api.Order(2)
    void testA2()
    {
        EntityManager entityManager = JPAConnector.EntityManager;

        Player player1 = entityManager.find(Player.class, player1Uuid);
        Player player2 = entityManager.find(Player.class, player2Uuid);
        PlayerStorage playerStorage1 = entityManager.find(PlayerStorage.class, playerStorage1Uuid);
        PlayerStorage playerStorage2 = entityManager.find(PlayerStorage.class, playerStorage2Uuid);
        Inventory inventory1 = entityManager.find(Inventory.class, inventory1Uuid);
        Inventory inventory2 = entityManager.find(Inventory.class, inventory2Uuid);
        PlayerScoreEntry playerScoreEntry1 = entityManager.find(PlayerScoreEntry.class, playerScoreEntry1Uuid);
        PlayerScoreEntry playerScoreEntry2 = entityManager.find(PlayerScoreEntry.class, playerScoreEntry2Uuid);
        Team team1 = entityManager.find(Team.class, team1Uuid);
        TeamScoreEntry teamScoreEntry1 = entityManager.find(TeamScoreEntry.class, teamScoreEntry1Uuid);
        ArenaColor arenaColor1 = entityManager.find(ArenaColor.class, arenaColor1Id);
        ArenaColor arenaColor2 = entityManager.find(ArenaColor.class, arenaColor2Id);
        KillObjective killObjective1 = entityManager.find(KillObjective.class, killObjective1Uuid);
        KillObjective killObjective2 = entityManager.find(KillObjective.class, killObjective2Uuid);
        PointObjective pointObjective1 = entityManager.find(PointObjective.class, pointObjective1Uuid);
        Order order1 = entityManager.find(Order.class, order1Uuid);
        Order order2 = entityManager.find(Order.class, order2Uuid);
        OrderEntry orderEntry1 = entityManager.find(OrderEntry.class, orderEntry1Uuid);
        OrderEntry orderEntry2 = entityManager.find(OrderEntry.class, orderEntry2Uuid);
        Stage stage1 = entityManager.find(Stage.class, stage1Uuid);
        LinearRound linearRound1 = entityManager.find(LinearRound.class, linearRound1Uuid);
        RoundScoreTable roundScoreTable1 = entityManager.find(RoundScoreTable.class, roundScoreTable1Uuid);
        TeamArena teamArena1 = entityManager.find(TeamArena.class, teamArena1Uuid);
        ArenaScoreTable arenaScoreTable1 = entityManager.find(ArenaScoreTable.class, arenaScoreTable1Uuid);
        TriggerSphere triggerSphere1 = entityManager.find(TriggerSphere.class, triggerSphere1Uuid);
        TriggerSphere triggerSphere2 = entityManager.find(TriggerSphere.class, triggerSphere2Uuid);
        CaptureFunction captureFunction1 = entityManager.find(CaptureFunction.class, captureFunction1Uuid);
        CaptureEnterFunction captureEnterFunction1 =
                entityManager.find(CaptureEnterFunction.class, captureEnterFunction1Uuid);
        CaptureLeaveFunction captureLeaveFunction1 =
                entityManager.find(CaptureLeaveFunction.class, captureLeaveFunction1Uuid);
        CaptureObjective captureObjective1 = entityManager.find(CaptureObjective.class, captureObjective1Uuid);
        AddSpawnFunction addSpawnFunction1 = entityManager.find(AddSpawnFunction.class, addSpawnFunction1Uuid);
        ArenaMostXGained arenaMostXGained1 = entityManager.find(ArenaMostXGained.class, arenaMostXGained1Uuid);
        LastRoundWon lastRoundWon1 = entityManager.find(LastRoundWon.class, lastRoundWon1Uuid);
        MostRoundsWon mostRoundsWon1 = entityManager.find(MostRoundsWon.class, mostRoundsWon1Uuid);
        TimeTillStart timeTillStart1 = entityManager.find(TimeTillStart.class, timeTillStart1Uuid);
        MinimalPlayerRequirement minimalPlayerRequirement1 =
                entityManager.find(MinimalPlayerRequirement.class, minimalPlayerRequirement1Uuid);
        ArenaTimeOver arenaTimeOver1 = entityManager.find(ArenaTimeOver.class, arenaTimeOver1Uuid);
        LastRoundOver lastRoundOver1 = entityManager.find(LastRoundOver.class, lastRoundOver1Uuid);
        MissingPlayerRequirement missingPlayerRequirement1 =
                entityManager.find(MissingPlayerRequirement.class, missingPlayerRequirement1Uuid);
        LastStageOver lastStageOver1 = entityManager.find(LastStageOver.class, lastStageOver1Uuid);
        RoundTimeOver roundTimeOver1 = entityManager.find(RoundTimeOver.class, roundTimeOver1Uuid);
        RoundMostXGained roundMostXGained1 = entityManager.find(RoundMostXGained.class, roundMostXGained1Uuid);
        LastStageFinished lastStageFinished1 = entityManager.find(LastStageFinished.class, lastStageFinished1Uuid);
        OrderAchieved orderAchieved1 = entityManager.find(OrderAchieved.class, orderAchieved1Uuid);
        ScoreIndex scoreIndex1 = entityManager.find(ScoreIndex.class, scoreIndex1Uuid);

        Assertions.assertEquals(playerStorage1, player1.getPlayerStorage());
        Assertions.assertEquals(order1, player1.getOrder());
        Assertions.assertNotNull(triggerSphere1);
        Assertions.assertNotNull(triggerSphere2);
        Assertions.assertEquals(1, triggerSphere2.getFunctions(HookType.ENTER).size());
        Assertions.assertNotNull(addSpawnFunction1);
        Assertions.assertEquals(1, player1.getSpawnPoints().size());
        Assertions.assertEquals(arenaColor1, player1.getArenaColor());
        Assertions.assertEquals(teamArena1, player1.getArena());
        Assertions.assertEquals(playerScoreEntry1, player1.getScoreEntry());
        Assertions.assertEquals(playerScoreEntry2, player2.getScoreEntry());
        Assertions.assertEquals(playerStorage2, player2.getPlayerStorage());
        Assertions.assertEquals(inventory1, playerStorage1.getInventory());
        Assertions.assertEquals(3, inventory1.getItems().size());
        Assertions.assertEquals(inventory2, player1.getRespawnInventory());
        Assertions.assertEquals(2, inventory2.getItems().size());
        Assertions.assertEquals(teamArena1, player2.getArena());
        Assertions.assertEquals(team1, player2.getTeam());
        Assertions.assertEquals(order2, player2.getOrder());
        Assertions.assertEquals(arenaColor2, team1.getArenaColor());
        Assertions.assertEquals(teamScoreEntry1, team1.getScoreEntry());
        Assertions.assertEquals(1, team1.getSpawnPoints().size());
        Assertions.assertNotNull(order2);
        Assertions.assertNotNull(killObjective1);
        Assertions.assertNotNull(killObjective2);
        Assertions.assertNotNull(pointObjective1);
        Assertions.assertNotNull(orderEntry1);
        Assertions.assertNotNull(orderEntry2);
        Assertions.assertNotNull(stage1);
        Assertions.assertNotNull(linearRound1);
        Assertions.assertNotNull(roundScoreTable1);
        Assertions.assertEquals(2, roundScoreTable1.getScoreEntries().size());
        Assertions.assertNotNull(killObjective1.getOperator().equals(player1));
        Assertions.assertNotNull(killObjective2.getOperator().equals(team1));
        Assertions.assertNotNull(pointObjective1.getOperator().equals(team1));
        Assertions.assertEquals(1, order1.getObjectives().size());
        Assertions.assertEquals(2, order2.getObjectives().size());
        Assertions.assertEquals(2, stage1.getOrderEntries().size());
        Assertions.assertEquals(1, linearRound1.getStages().size());
        Assertions.assertEquals(1, teamArena1.getRounds().size());
        Assertions.assertEquals(2, teamArena1.getMinPlayers());
        Assertions.assertEquals(2, teamArena1.getPlayers().size());
        Assertions.assertNotNull(arenaScoreTable1);
        Assertions.assertEquals(1, arenaScoreTable1.getRoundScoreTables().size());
        Assertions.assertNotNull(teamArena1);
        Assertions.assertNotNull(captureFunction1);
        Assertions.assertNotNull(captureEnterFunction1);
        Assertions.assertNotNull(captureLeaveFunction1);
        Assertions.assertNotNull(captureObjective1);
        Assertions.assertEquals(1, captureFunction1.getCaptureObjectives().size());
        Assertions.assertEquals(1, triggerSphere1.getFunctions(HookType.ENTER).size());
        Assertions.assertEquals(1, triggerSphere1.getFunctions(HookType.LEAVE).size());
        Assertions.assertEquals(1, triggerSphere1.getFunctions(HookType.PASSIVE).size());
        Assertions.assertNotNull(arenaMostXGained1);
        Assertions.assertNotNull(lastRoundWon1);
        Assertions.assertNotNull(mostRoundsWon1);
        Assertions.assertEquals(3, teamArena1.getWinConditions().size());
        Assertions.assertNotNull(timeTillStart1);
        Assertions.assertNotNull(minimalPlayerRequirement1);
        Assertions.assertEquals(2, teamArena1.getStartConditions().size());
        Assertions.assertNotNull(arenaTimeOver1);
        Assertions.assertNotNull(lastRoundOver1);
        Assertions.assertNotNull(missingPlayerRequirement1);
        Assertions.assertEquals(3, teamArena1.getEndConditions().size());
        Assertions.assertNotNull(lastStageOver1);
        Assertions.assertNotNull(roundTimeOver1);
        Assertions.assertEquals(2, linearRound1.getRoundEndConditions().size());
        Assertions.assertNotNull(roundMostXGained1);
        Assertions.assertNotNull(lastStageFinished1);
        Assertions.assertEquals(2, linearRound1.getRoundWinConditions().size());
        Assertions.assertNotNull(orderAchieved1);
        Assertions.assertEquals(1, stage1.getEndConditions().size());
        Assertions.assertNotNull(scoreIndex1);
        Assertions.assertEquals(scoreIndex1, teamArena1.getScoreIndex());
        Assertions.assertEquals(15.26, teamArena1.getScoreIndex().getPoints(Action.ASSIST));
        Assertions.assertEquals(player1, playerScoreEntry1.getPlayer());
        Assertions.assertEquals(player2, playerScoreEntry2.getPlayer());
        Assertions.assertEquals(playerScoreEntry1, player1.getScoreEntry());
        Assertions.assertEquals(playerScoreEntry2, player2.getScoreEntry());

    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testA3()
    {
        EntityManager entityManager = JPAConnector.EntityManager;

        Stage stage1 = entityManager.find(Stage.class, stage1Uuid);

        entityManager.remove(stage1);

        JPAConnector.commit();
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void testA4()
    {
        EntityManager entityManager = JPAConnector.EntityManager;

        Player player1 = entityManager.find(Player.class, player1Uuid);
        Player player2 = entityManager.find(Player.class, player2Uuid);
        PlayerStorage playerStorage1 = entityManager.find(PlayerStorage.class, playerStorage1Uuid);
        PlayerStorage playerStorage2 = entityManager.find(PlayerStorage.class, playerStorage2Uuid);
        PlayerScoreEntry playerScoreEntry1 = entityManager.find(PlayerScoreEntry.class, playerScoreEntry1Uuid);
        PlayerScoreEntry playerScoreEntry2 = entityManager.find(PlayerScoreEntry.class, playerScoreEntry2Uuid);
        Inventory inventory2 = entityManager.find(Inventory.class, inventory2Uuid);
        Team team1 = entityManager.find(Team.class, team1Uuid);
        TeamScoreEntry teamScoreEntry1 = entityManager.find(TeamScoreEntry.class, teamScoreEntry1Uuid);
        ArenaColor arenaColor1 = entityManager.find(ArenaColor.class, arenaColor1Id);
        ArenaColor arenaColor2 = entityManager.find(ArenaColor.class, arenaColor2Id);
        KillObjective killObjective1 = entityManager.find(KillObjective.class, killObjective1Uuid);
        KillObjective killObjective2 = entityManager.find(KillObjective.class, killObjective2Uuid);
        PointObjective pointObjective1 = entityManager.find(PointObjective.class, pointObjective1Uuid);
        Order order1 = entityManager.find(Order.class, order1Uuid);
        Order order2 = entityManager.find(Order.class, order2Uuid);
        Stage stage1 = entityManager.find(Stage.class, stage1Uuid);
        LinearRound linearRound1 = entityManager.find(LinearRound.class, linearRound1Uuid);
        RoundScoreTable roundScoreTable1 = entityManager.find(RoundScoreTable.class, roundScoreTable1Uuid);
        TeamArena teamArena1 = entityManager.find(TeamArena.class, teamArena1Uuid);
        ArenaScoreTable arenaScoreTable1 = entityManager.find(ArenaScoreTable.class, arenaScoreTable1Uuid);
        TriggerSphere triggerSphere1 = entityManager.find(TriggerSphere.class, triggerSphere1Uuid);
        CaptureFunction captureFunction1 = entityManager.find(CaptureFunction.class, captureFunction1Uuid);
        CaptureEnterFunction captureEnterFunction1 =
                entityManager.find(CaptureEnterFunction.class, captureEnterFunction1Uuid);
        CaptureLeaveFunction captureLeaveFunction1 =
                entityManager.find(CaptureLeaveFunction.class, captureLeaveFunction1Uuid);
        CaptureObjective captureObjective1 = entityManager.find(CaptureObjective.class, captureObjective1Uuid);
        AddSpawnFunction addSpawnFunction1 = entityManager.find(AddSpawnFunction.class, addSpawnFunction1Uuid);
        ArenaMostXGained arenaMostXGained1 = entityManager.find(ArenaMostXGained.class, arenaMostXGained1Uuid);
        LastRoundWon lastRoundWon1 = entityManager.find(LastRoundWon.class, lastRoundWon1Uuid);
        MostRoundsWon mostRoundsWon1 = entityManager.find(MostRoundsWon.class, mostRoundsWon1Uuid);
        TimeTillStart timeTillStart1 = entityManager.find(TimeTillStart.class, timeTillStart1Uuid);
        MinimalPlayerRequirement minimalPlayerRequirement1 =
                entityManager.find(MinimalPlayerRequirement.class, minimalPlayerRequirement1Uuid);
        ArenaTimeOver arenaTimeOver1 = entityManager.find(ArenaTimeOver.class, arenaTimeOver1Uuid);
        LastRoundOver lastRoundOver1 = entityManager.find(LastRoundOver.class, lastRoundOver1Uuid);
        MissingPlayerRequirement missingPlayerRequirement1 =
                entityManager.find(MissingPlayerRequirement.class, missingPlayerRequirement1Uuid);
        LastStageOver lastStageOver1 = entityManager.find(LastStageOver.class, lastStageOver1Uuid);
        RoundTimeOver roundTimeOver1 = entityManager.find(RoundTimeOver.class, roundTimeOver1Uuid);
        RoundMostXGained roundMostXGained1 = entityManager.find(RoundMostXGained.class, roundMostXGained1Uuid);
        LastStageFinished lastStageFinished1 = entityManager.find(LastStageFinished.class, lastStageFinished1Uuid);
        OrderAchieved orderAchieved1 = entityManager.find(OrderAchieved.class, orderAchieved1Uuid);
        ScoreIndex scoreIndex1 = entityManager.find(ScoreIndex.class, scoreIndex1Uuid);

        Assertions.assertNull(player1);
        Assertions.assertNull(playerStorage1);
        Assertions.assertNotNull(playerScoreEntry1);
        Assertions.assertNull(player2);
        Assertions.assertNull(playerStorage2);
        Assertions.assertNotNull(playerScoreEntry2);
        Assertions.assertNull(team1);
        Assertions.assertNotNull(teamScoreEntry1);
        Assertions.assertNull(arenaColor1);
        Assertions.assertNull(arenaColor2);
        Assertions.assertNull(killObjective1);
        Assertions.assertNull(killObjective2);
        Assertions.assertNull(pointObjective1);
        Assertions.assertNull(order1);
        Assertions.assertNull(order2);
        Assertions.assertNull(stage1);
        Assertions.assertNull(linearRound1);
        Assertions.assertNotNull(roundScoreTable1);
        Assertions.assertNull(teamArena1);
        Assertions.assertNotNull(arenaScoreTable1);
        Assertions.assertNull(triggerSphere1);
        Assertions.assertNull(inventory2);
        Assertions.assertNull(captureFunction1);
        Assertions.assertNull(captureEnterFunction1);
        Assertions.assertNull(captureLeaveFunction1);
        Assertions.assertNull(captureObjective1);
        Assertions.assertNull(addSpawnFunction1);
        Assertions.assertNull(arenaMostXGained1);
        Assertions.assertNull(lastRoundWon1);
        Assertions.assertNull(mostRoundsWon1);
        Assertions.assertNull(timeTillStart1);
        Assertions.assertNull(minimalPlayerRequirement1);
        Assertions.assertNull(arenaTimeOver1);
        Assertions.assertNull(lastRoundOver1);
        Assertions.assertNull(missingPlayerRequirement1);
        Assertions.assertNull(lastStageOver1);
        Assertions.assertNull(roundTimeOver1);
        Assertions.assertNull(roundMostXGained1);
        Assertions.assertNull(lastStageFinished1);
        Assertions.assertNull(orderAchieved1);
        Assertions.assertNull(scoreIndex1);
    }
}
