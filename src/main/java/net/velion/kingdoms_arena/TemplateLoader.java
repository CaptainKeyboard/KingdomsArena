package net.velion.kingdoms_arena;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.velion.core.ArmorType;
import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaColor;
import net.velion.kingdoms_arena.arena.entity.inventory.InventorySlot;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.HookType;
import net.velion.kingdoms_arena.builder.ArenaBuilder;
import net.velion.kingdoms_arena.builder.TeamArenaBuilder;
import net.velion.kingdoms_arena.builder.condition.arena.end.ArenaEndConditionParser;
import net.velion.kingdoms_arena.builder.condition.arena.end.ArenaMissingPlayerRequirementParser;
import net.velion.kingdoms_arena.builder.condition.arena.end.ArenaTimeOverParser;
import net.velion.kingdoms_arena.builder.condition.arena.end.LastRoundOverParser;
import net.velion.kingdoms_arena.builder.condition.arena.start.ArenaStartConditionParser;
import net.velion.kingdoms_arena.builder.condition.arena.start.MinimalPlayerRequirementParser;
import net.velion.kingdoms_arena.builder.condition.arena.start.TimeTillStartBuilder;
import net.velion.kingdoms_arena.builder.condition.arena.win.ArenaWinConditionBuilder;
import net.velion.kingdoms_arena.builder.condition.arena.win.LastRoundWonBuilder;
import net.velion.kingdoms_arena.builder.condition.arena.win.MostRoundsWonBuilder;
import net.velion.kingdoms_arena.builder.condition.arena.win.MostXGainedBuilder;
import net.velion.kingdoms_arena.builder.condition.round.end.LastStageOverBuilder;
import net.velion.kingdoms_arena.builder.condition.round.end.RoundEndConditionBuilder;
import net.velion.kingdoms_arena.builder.condition.round.end.RoundTimeOverBuilder;
import net.velion.kingdoms_arena.builder.condition.round.win.LastStageFinishedBuilder;
import net.velion.kingdoms_arena.builder.condition.round.win.RoundMostXGainedBuilder;
import net.velion.kingdoms_arena.builder.condition.round.win.RoundWinConditionBuilder;
import net.velion.kingdoms_arena.builder.entity.*;
import net.velion.kingdoms_arena.builder.objective.CaptureObjectiveBuilder;
import net.velion.kingdoms_arena.builder.objective.KillObjectiveBuilder;
import net.velion.kingdoms_arena.builder.objective.ObjectiveBuilder;
import net.velion.kingdoms_arena.builder.objective.PointObjectiveBuilder;
import net.velion.kingdoms_arena.builder.order.OrderBuilder;
import net.velion.kingdoms_arena.builder.round.LinearRoundBuilder;
import net.velion.kingdoms_arena.builder.round.RoundBuilder;
import net.velion.kingdoms_arena.builder.score.ArenaScoreIndexBuilder;
import net.velion.kingdoms_arena.builder.stage.StageBuilder;
import net.velion.kingdoms_arena.builder.zone.*;
import net.velion.kingdoms_arena.builder.zone.triggerfunction.*;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TemplateLoader
{
    private static Map<String, Object> variables = null;

    public static Set<Arena> loadAllFinishedTemplates()
            throws IOException, ParseException, TemplateLoaderException, SelectorException
    {
        Set<Arena> arenas = new NoNullHashSet<>();
        File folder = new File("plugins/KingdomsArena/arenas");

        if (!folder.exists())
        {
            folder.mkdirs();
        }

        File[] files = folder.listFiles();

        JSONParser jsonParser = new JSONParser();

        for (File file : files)
        {
            FileReader fileReader = new FileReader(file);
            JSONObject templateJSON = (JSONObject) jsonParser.parse(fileReader);

            if (templateJSON.containsKey("type") && templateJSON.get("type").equals("finalized"))
            {
                arenas.add(loadTemplate(templateJSON).build());
            }

            long value = 0;
            if (templateJSON.containsKey("test123"))
            {
                value = (long) templateJSON.get("test123");
                value++;
            }

            templateJSON.put("test123", value);

            Gson prettyTemplateGSON = new GsonBuilder().setPrettyPrinting().create();

            fileReader.close();

            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(prettyTemplateGSON.toJson(templateJSON));
            fileWriter.flush();
            fileWriter.close();
        }
        return arenas;
    }

    public static ArenaBuilder loadTemplate(File templateFile)
            throws IOException, ParseException, TemplateLoaderException
    {
        FileReader fileReader = new FileReader(templateFile);
        JSONParser jsonParser = new JSONParser();
        JSONObject templateJSON = (JSONObject) jsonParser.parse(fileReader);
        return loadTemplate(templateJSON);
    }

    public static ArenaBuilder loadTemplate(JSONObject templateJSON)
            throws TemplateLoaderException
    {
        ArenaBuilder arenaBuilder;

        if (templateJSON.containsKey("variables"))
        {
            JSONObject variablesJSON = (JSONObject) templateJSON.get("variables");
            variables = loadVariables(variablesJSON);
        }

        if (templateJSON.containsKey("arena"))
        {
            JSONObject arenaJSON = (JSONObject) templateJSON.get("arena");
            arenaBuilder = loadArena(arenaJSON);
        } else
        {
            throw new TemplateLoaderException("[arena] key not found at root");
        }

        return arenaBuilder;
    }

    private static Map<String, Object> loadVariables(JSONObject variablesJSON)
    {
        Set<Map.Entry<String, Object>> entrySet = variablesJSON.entrySet();
        Map<String, Object> variablesMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : entrySet)
        {
            if (entry.getValue() instanceof String value)
            {
                if (value.startsWith("#uuid") || value.startsWith("#UUID"))
                {
                    value = UUID.randomUUID().toString();
                }
                variablesMap.put(entry.getKey(), value);
            } else
            {
                variablesMap.put(entry.getKey(), entry.getValue());
            }
        }
        return variablesMap;
    }

    private static ArenaBuilder loadArena(JSONObject arenaJSON) throws TemplateLoaderException
    {
        ArenaBuilder arenaBuilder;


        if (arenaJSON.containsKey("type"))
        {
            if (arenaJSON.containsKey("name"))
            {
                if (arenaJSON.containsKey("minimal_players"))
                {
                    UUID uuid;
                    if (arenaJSON.containsKey("uuid"))
                    {
                        uuid = getUuid(arenaJSON.get("uuid"));
                    } else
                    {
                        uuid = UUID.randomUUID();
                    }

                    String type = getString(arenaJSON.get("type"));

                    String name = getString(arenaJSON.get("name"));

                    int minPlayers = getInt(arenaJSON.get("minimal_players"));


                    arenaBuilder = switch (type)
                            {
                                //case "SinglePlayerArena" -> new SinglePlayerArenaLoader(uuid, name, minPlayers);
                                case "TeamArena" -> new TeamArenaBuilder(uuid, name,
                                        minPlayers);
                                default -> throw new TemplateLoaderException("Wrong arena type");
                            };

                    JSONArray teamsJSON = (JSONArray) arenaJSON.get("teams");
                    arenaBuilder.setTeams((loadTeams(teamsJSON).toArray(TeamBuilder[]::new)));

                    JSONArray spawnPointsJSON = (JSONArray) arenaJSON.get("spawn_points");
                    arenaBuilder.setSpawnPoints(loadSpawnPoints(spawnPointsJSON).toArray(SpawnPointBuilder[]::new));

                    JSONArray zonesJSON = (JSONArray) arenaJSON.get("zones");
                    arenaBuilder.setZones(
                            loadZones(zonesJSON).toArray(ZoneBuilder[]::new));

                    JSONObject scoreIndexJSON = (JSONObject) arenaJSON.get("score_index");
                    arenaBuilder.setScoreIndexBuilder(loadScoreIndex(scoreIndexJSON));

                    JSONArray arenaStartConditionsJSON = (JSONArray) arenaJSON.get("start_conditions");
                    arenaBuilder.setStartConditions(loadArenaStartConditions(arenaStartConditionsJSON).toArray(
                            ArenaStartConditionParser[]::new));

                    JSONArray arenaWinConditionsJSON = (JSONArray) arenaJSON.get("win_conditions");
                    arenaBuilder.setWinConditions(loadArenaWinConditions(arenaWinConditionsJSON).toArray(
                            ArenaWinConditionBuilder[]::new));

                    JSONArray arenaEndConditionsJSON = (JSONArray) arenaJSON.get("end_conditions");
                    arenaBuilder.setEndConditions(loadArenaEndConditions(arenaEndConditionsJSON).toArray(
                            ArenaEndConditionParser[]::new));

                    JSONArray roundsJSON = (JSONArray) arenaJSON.get("rounds");
                    arenaBuilder.setRounds(loadRounds(roundsJSON).toArray(RoundBuilder[]::new));
                } else
                {
                    throw new TemplateLoaderException("[minimal_players] key not found at [arena]");
                }
            } else
            {
                throw new TemplateLoaderException("[name] key not found at [arena]");
            }
        } else
        {
            throw new TemplateLoaderException("[type] key not found at [arena]");
        }
        return arenaBuilder;
    }

    private static Set<RoundBuilder> loadRounds(JSONArray roundsJSON) throws TemplateLoaderException
    {
        Set<RoundBuilder> roundBuilders = new HashSet<>();

        for (Object roundObject : roundsJSON)
        {
            RoundBuilder roundBuilder = new LinearRoundBuilder(UUID.randomUUID());

            JSONObject roundJSON = (JSONObject) roundObject;

            JSONArray assignSpawnPointsJSON = (JSONArray) roundJSON.get("assign_spawn_points");
            for (Object assignSpawnPointObject : assignSpawnPointsJSON)
            {
                JSONObject assignSpawnPointJSON = (JSONObject) assignSpawnPointObject;

                IEntitySelector entitySelector = getEntitySelector(assignSpawnPointJSON.get("entity"));

                JSONArray spawnPointsJSON = (JSONArray) assignSpawnPointJSON.get("spawn_points");

                roundBuilder.setSpawnPoints(entitySelector,
                        loadSpawnPointMap(spawnPointsJSON).toArray(ZoneSelector[]::new));
            }

            JSONArray roundWinConditionsJSON = (JSONArray) roundJSON.get("win_conditions");
            roundBuilder.setWinConditions(
                    loadRoundWinConditions(roundWinConditionsJSON).toArray(RoundWinConditionBuilder[]::new));

            JSONArray roundEndConditionsJSON = (JSONArray) roundJSON.get("end_conditions");
            roundBuilder.setEndConditions(
                    loadRoundEndConditions(roundEndConditionsJSON).toArray(RoundEndConditionBuilder[]::new));

            JSONArray gearsJSON = (JSONArray) roundJSON.get("assign_gear");
            for (Object gearObject : gearsJSON)
            {
                JSONObject gearJSON = (JSONObject) gearObject;

                IEntitySelector entitySelector = getEntitySelector(((JSONObject) gearObject).get("entity"));

                JSONArray slotsJSON = (JSONArray) gearJSON.get("slots");

                InventoryBuilder inventoryBuilder = loadGearSlots(slotsJSON);

                roundBuilder.setGear(entitySelector, inventoryBuilder);
            }

            JSONArray stagesJSON = (JSONArray) roundJSON.get("stages");
            roundBuilder.setStages(loadStages(stagesJSON).toArray(StageBuilder[]::new));

            roundBuilders.add(roundBuilder);
        }
        return roundBuilders;
    }

    private static Set<StageBuilder> loadStages(JSONArray stagesJSON) throws TemplateLoaderException
    {
        Set<StageBuilder> stageBuilders = new HashSet<>();

        for (Object stageObject : stagesJSON)
        {
            JSONObject stageJSON = (JSONObject) stageObject;
            String type = getString(stageJSON.get("type"));

            StageBuilder stageBuilder = new StageBuilder(UUID.randomUUID());
            JSONArray ordersJSON = (JSONArray) stageJSON.get("orders");
            for (Object orderObject : ordersJSON)
            {
                JSONObject orderJSON = (JSONObject) orderObject;

                IEntitySelector entitySelector = getEntitySelector(orderJSON.get("entity"));
                OrderBuilder orderBuilder = new OrderBuilder(UUID.randomUUID());

                JSONArray objectivesJSON = (JSONArray) orderJSON.get("objectives");
                orderBuilder.setObjectives(loadObjectives(objectivesJSON).toArray(ObjectiveBuilder[]::new));

                stageBuilder.putOrder(entitySelector, orderBuilder);
            }

            stageBuilders.add(stageBuilder);
        }

        return stageBuilders;
    }

    private static Set<ObjectiveBuilder> loadObjectives(JSONArray orderJSON)
            throws TemplateLoaderException
    {
        Set<ObjectiveBuilder> objectiveBuilders = new HashSet<>();
        for (Object objectiveObject : orderJSON)
        {
            JSONObject objectiveJSON = (JSONObject) objectiveObject;

            String type = getString(objectiveJSON.get("type"));
            ObjectiveBuilder objectiveBuilder;
            switch (type)
            {
                case "KillObjective" -> {
                    int kills = getInt(objectiveJSON.get("value"));

                    objectiveBuilder = new KillObjectiveBuilder(UUID.randomUUID(), kills);
                }
                case "PointObjective" -> {
                    int points = getInt(objectiveJSON.get("value"));

                    objectiveBuilder = new PointObjectiveBuilder(UUID.randomUUID(), points);

                }
                case "CaptureObjective" -> {
                    String triggerZone = getString(objectiveJSON.get("trigger_zone"));
                    ZoneSelector zoneSelector = new ZoneSelector(triggerZone);
                    objectiveBuilder = new CaptureObjectiveBuilder(UUID.randomUUID(), zoneSelector);
                }
                default -> throw new TemplateLoaderException("Objective type not found at [objective]");
            }
            objectiveBuilders.add(objectiveBuilder);
        }
        return objectiveBuilders;
    }

    private static InventoryBuilder loadGearSlots(JSONArray slotsJSON)
            throws TemplateLoaderException
    {
        InventoryBuilder inventoryBuilder = new InventoryBuilder();

        for (Object slotObject : slotsJSON)
        {
            JSONObject slotJSON = (JSONObject) slotObject;
            InventorySlot slot = InventorySlot.valueOf(getString(slotJSON.get("slot")));
            Material material = Material.valueOf(getString(slotJSON.get("item")));

            int amount = 1;
            if (slotJSON.containsKey("amount"))
            {
                amount = getInt(slotJSON.get("amount"));
            }

            ItemStack itemStack = new ItemStack(material, amount);

            if (slotJSON.containsKey("leather_armor_meta"))
            {
                JSONObject leatherArmorMetaJSON = (JSONObject) slotJSON.get("leather_armor_meta");
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();

                if (leatherArmorMetaJSON.containsKey("armor"))
                {
                    double armorValue = getDouble(leatherArmorMetaJSON.get("armor"));
                    leatherArmorMeta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                            new AttributeModifier(UUID.randomUUID(), "test-armor", armorValue,
                                    AttributeModifier.Operation.ADD_NUMBER, ArmorType.getSlot(itemStack.getType())));
                }
                if (leatherArmorMetaJSON.containsKey("color"))
                {
                    String colorString = getString(leatherArmorMetaJSON.get("color"));
                    Color color;
                    switch (colorString)
                    {
                        case "aqua", "AQUA":
                            color = Color.AQUA;
                            break;
                        case "black", "BLACK":
                            color = Color.BLACK;
                            break;
                        case "blue", "BLUE":
                            color = ArenaColor.BLUE().getColor();
                            break;
                        case "fuchsia", "FUCHSIA":
                            color = Color.FUCHSIA;
                            break;
                        case "gray", "GRAY":
                            color = Color.GRAY;
                            break;
                        case "green", "GREEN":
                            color = ArenaColor.GREEN().getColor();
                            break;
                        case "lime", "LIME":
                            color = Color.LIME;
                            break;
                        case "maroon", "MAROON":
                            color = Color.MAROON;
                            break;
                        case "navy", "NAVY":
                            color = Color.NAVY;
                            break;
                        case "olive", "OLIVE":
                            color = Color.OLIVE;
                            break;
                        case "orange", "ORANGE":
                            color = Color.ORANGE;
                            break;
                        case "purple", "PURPLE":
                            color = Color.PURPLE;
                            break;
                        case "red", "RED":
                            color = ArenaColor.RED().getColor();
                            break;
                        case "silver", "SILVER":
                            color = Color.SILVER;
                            break;
                        case "teal", "TEAL":
                            color = Color.TEAL;
                            break;
                        case "white", "WHITE":
                            color = Color.WHITE;
                            break;
                        case "yellow", "YELLOW":
                            color = ArenaColor.YELLOW().getColor();
                            break;
                        default:
                            color = null;
                            break;
                    }
                    if (color != null)
                    {
                        leatherArmorMeta.setColor(color);
                    }
                }
                itemStack.setItemMeta(leatherArmorMeta);
            }
            inventoryBuilder.setSlot(slot, itemStack);
        }
        return inventoryBuilder;
    }

    private static Set<RoundEndConditionBuilder> loadRoundEndConditions(JSONArray roundEndConditionsJSON)
            throws TemplateLoaderException
    {
        Set<RoundEndConditionBuilder> roundEndConditionBuilders = new HashSet<>();
        for (Object roundEndConditionObject : roundEndConditionsJSON)
        {
            JSONObject roundEndConditionJSON = (JSONObject) roundEndConditionObject;

            String type = getString(roundEndConditionJSON.get("type"));

            RoundEndConditionBuilder roundEndConditionBuilder = switch (type)
                    {
                        case "LastStageOver" -> new LastStageOverBuilder();
                        case "TimeOver" -> {
                            double time = getDouble(roundEndConditionJSON.get("time"));
                            yield new RoundTimeOverBuilder(time);
                        }
                        default -> throw new TemplateLoaderException(
                                "Round end condition type not found at [round_win_condition]");
                    };
            roundEndConditionBuilders.add(roundEndConditionBuilder);
        }
        return roundEndConditionBuilders;
    }

    private static Set<RoundWinConditionBuilder> loadRoundWinConditions(JSONArray roundWinConditionsJSON)
            throws TemplateLoaderException
    {
        Set<RoundWinConditionBuilder> roundWinConditionBuilders = new HashSet<>();
        for (Object roundWinConditionObject : roundWinConditionsJSON)
        {
            JSONObject roundWinConditionJSON = (JSONObject) roundWinConditionObject;

            String type = getString(roundWinConditionJSON.get("type"));

            RoundWinConditionBuilder roundWinConditionBuilder = switch (type)
                    {
                        case "LastStageFinished" -> new LastStageFinishedBuilder();
                        case "MostXGained" -> {
                            ScoreType scoreType = getAction(roundWinConditionJSON.get("score_type"));
                            yield new RoundMostXGainedBuilder(scoreType);
                        }
                        default -> throw new TemplateLoaderException(
                                "Round win condition type not found at [round_win_condition]");
                    };
            roundWinConditionBuilders.add(roundWinConditionBuilder);
        }
        return roundWinConditionBuilders;
    }

    private static Set<ZoneSelector> loadSpawnPointMap(JSONArray spawnPointsJSON)
            throws TemplateLoaderException
    {
        Set<ZoneSelector> zoneSelectors = new HashSet<>();
        for (Object spawnPointObject : spawnPointsJSON)
        {
            String spawnPoint = getString(spawnPointObject);
            ZoneSelector zoneSelector = new ZoneSelector(spawnPoint);
            zoneSelectors.add(zoneSelector);
        }
        return zoneSelectors;
    }

    private static Set<ArenaEndConditionParser> loadArenaEndConditions(JSONArray arenaEndConditionsJSON)
            throws TemplateLoaderException
    {
        Set<ArenaEndConditionParser> arenaEndConditionParsers = new HashSet<>();

        for (Object endConditionObject : arenaEndConditionsJSON)
        {
            JSONObject endConditionJSON = (JSONObject) endConditionObject;

            if (endConditionJSON.containsKey("type"))
            {
                String type = getString(endConditionJSON.get("type"));

                ArenaEndConditionParser arenaEndConditionParser = switch (type)
                        {
                            case "TimeOver" -> {
                                double time = getDouble(endConditionJSON.get("time"));

                                boolean forceEnd = false;
                                if (endConditionJSON.containsKey("force_end"))
                                {
                                    forceEnd = getBool(endConditionJSON.get("force_end"));
                                }

                                yield new ArenaTimeOverParser(time, forceEnd);
                            }
                            case "LastRoundOver" -> new LastRoundOverParser();
                            case "MissingPlayerRequirement" -> new ArenaMissingPlayerRequirementParser();
                            default -> throw new TemplateLoaderException(
                                    "Start condition type not found at [arena_end_condition]");

                        };
                arenaEndConditionParsers.add(arenaEndConditionParser);
            } else
            {
                throw new TemplateLoaderException("[type] key not found at [arena_end_condition]");
            }
        }
        return arenaEndConditionParsers;
    }

    private static Set<ArenaWinConditionBuilder> loadArenaWinConditions(JSONArray arenaWinConditionsJSON)
            throws TemplateLoaderException
    {
        Set<ArenaWinConditionBuilder> arenaWinConditionBuilders = new HashSet<>();

        for (Object winConditionObject : arenaWinConditionsJSON)
        {
            JSONObject winConditionJSON = (JSONObject) winConditionObject;

            if (winConditionJSON.containsKey("type"))
            {
                String type = (String) winConditionJSON.get("type");

                ArenaWinConditionBuilder arenaWinConditionBuilder = switch (type)
                        {
                            case "MostRoundsWon" -> new MostRoundsWonBuilder();
                            case "LastRoundWon" -> new LastRoundWonBuilder();
                            case "MostXGained" -> {
                                ScoreType scoreType = getAction(winConditionJSON.get("score_type"));
                                yield new MostXGainedBuilder(scoreType);
                            }
                            default -> throw new TemplateLoaderException(
                                    "Win condition type not found at [arena_win_condition]");
                        };
                arenaWinConditionBuilders.add(arenaWinConditionBuilder);
            } else
            {
                throw new TemplateLoaderException("[type] key not found at [arena_win_condition]");
            }
        }
        return arenaWinConditionBuilders;
    }

    private static Set<ArenaStartConditionParser> loadArenaStartConditions(JSONArray arenaStartConditionsJSON)
            throws TemplateLoaderException
    {
        Set<ArenaStartConditionParser> arenaStartConditionParsers = new HashSet<>();

        for (Object startConditionObject : arenaStartConditionsJSON)
        {
            JSONObject startConditionJSON = (JSONObject) startConditionObject;

            if (startConditionJSON.containsKey("type"))
            {
                String type = (String) startConditionJSON.get("type");

                ArenaStartConditionParser arenaStartConditionParser = switch (type)
                        {
                            case "MinimalPlayerRequirement" -> new MinimalPlayerRequirementParser();
                            case "TimeTillStart" -> {
                                double time = getDouble(startConditionJSON.get("time"));
                                yield new TimeTillStartBuilder(time);
                            }
                            default -> throw new TemplateLoaderException(
                                    "Start condition type not found at [arena_start_condition]");
                        };
                arenaStartConditionParsers.add(arenaStartConditionParser);
            } else
            {
                throw new TemplateLoaderException("[type] key not found at [arena_start_condition]");
            }
        }
        return arenaStartConditionParsers;
    }

    private static ArenaScoreIndexBuilder loadScoreIndex(JSONObject scoreIndexJSON) throws TemplateLoaderException
    {
        ArenaScoreIndexBuilder arenaScoreIndexBuilder = new ArenaScoreIndexBuilder();

        for (Object scoreIndexObject : scoreIndexJSON.entrySet())
        {
            Map.Entry<String, Double> entry = (Map.Entry<String, Double>) scoreIndexObject;

            ScoreType scoreType = getAction(entry.getKey());

            double value = getDouble(entry.getValue());

            arenaScoreIndexBuilder.addScorePoints(scoreType, value);
        }

        return arenaScoreIndexBuilder;
    }

    private static Set<ZoneBuilder> loadZones(JSONArray triggerZonesJSON) throws TemplateLoaderException
    {
        Set<ZoneBuilder> zoneBuilders = new HashSet<>();
        for (Object jsonObject : triggerZonesJSON)
        {
            JSONObject triggerZoneJSON = (JSONObject) jsonObject;
            if (triggerZoneJSON.containsKey("name"))
            {
                if (triggerZoneJSON.containsKey("location"))
                {
                    if (triggerZoneJSON.containsKey("radius"))
                    {
                        String name = (String) triggerZoneJSON.get("name");
                        if (name.startsWith("$"))
                        {
                            name = getString(name);
                        }

                        double radius = getDouble(triggerZoneJSON.get("radius"));

                        JSONObject locationJSON = (JSONObject) triggerZoneJSON.get("location");
                        ArenaLocationBuilder arenaLocationBuilder = loadArenaLocation(locationJSON);

                        ZoneBuilder zoneBuilder =
                                new TriggerSphereBuilder(name, arenaLocationBuilder,
                                        radius);

                        if (triggerZoneJSON.containsKey("capture_function"))
                        {
                            JSONObject captureFunctionJSON = (JSONObject) triggerZoneJSON.get("capture_function");
                            zoneBuilder.setCaptureFunction(loadCaptureFunction(captureFunctionJSON));
                        }

                        if (triggerZoneJSON.containsKey("functions"))
                        {
                            JSONArray functionsJSON = (JSONArray) triggerZoneJSON.get("functions");
                            zoneBuilder.setFunctions(loadFunctions(functionsJSON));
                        }

                        zoneBuilders.add(zoneBuilder);
                    } else
                    {
                        throw new TemplateLoaderException("[radius] key not found at [trigger_zone]");
                    }
                } else
                {
                    throw new TemplateLoaderException("[location] key not found at [trigger_zone]");
                }
            } else
            {
                throw new TemplateLoaderException("[name] key not found at [trigger_zone]");
            }
        }

        return zoneBuilders;
    }

    private static Map<HookType, Set<TriggerFunctionBuilder>> loadFunctions(JSONArray functionsJSON)
            throws TemplateLoaderException
    {
        Map<HookType, Set<TriggerFunctionBuilder>> functionMap = new HashMap<>();
        for (HookType hookType : HookType.values())
        {
            functionMap.put(hookType, new NoNullHashSet<>());
        }
        for (Object functionObject : functionsJSON)
        {
            JSONObject functionJSON = (JSONObject) functionObject;

            HookType hookType = HookType.valueOf((String) functionJSON.get("hook"));

            String functionType = (String) functionJSON.get("type");
            TriggerFunctionBuilder triggerFunctionBuilder;
            switch (functionType)
            {
                case "AddScoreFunction":
                    ScoreType scoreType = getAction(functionJSON.get("score_type"));
                    double value = getDouble(functionJSON.get("value"));
                    triggerFunctionBuilder = new AddScoreFunctionBuilder(scoreType, value);
                    break;
                case "AddSpawnFunction":
                    triggerFunctionBuilder = new AddSpawnFunctionBuilder();
                    break;
                case "SetSpawnFunction":
                    triggerFunctionBuilder = new SetSpawnFunctionBuilder();
                    break;
                case "SpawnEntityFunction":
                    EntityType entityType = EntityType.valueOf((String) functionJSON.get("entity_type"));
                    int amount = 1;
                    if (functionJSON.containsKey("amount"))
                    {
                        amount = getInt(functionJSON.get("entity_type"));
                    }

                    JSONObject arenaLocationJSON = (JSONObject) functionJSON.get("location");
                    triggerFunctionBuilder =
                            new SpawnEntityFunctionBuilder(loadArenaLocation(arenaLocationJSON), entityType, amount);
                    break;
                default:
                    throw new TemplateLoaderException("Function type not found");
            }

            functionMap.get(hookType).add(triggerFunctionBuilder);
        }
        return functionMap;
    }

    private static CaptureFunctionBuilder loadCaptureFunction(JSONObject captureFunctionJSON)
            throws TemplateLoaderException
    {
        if (captureFunctionJSON.containsKey("radius"))
        {
            if (captureFunctionJSON.containsKey("ticks"))
            {
                double radius = getDouble(captureFunctionJSON.get("radius"));
                double ticks = getDouble(captureFunctionJSON.get("ticks"));

                return new CaptureFunctionBuilder(radius, ticks);
            } else
            {
                throw new TemplateLoaderException("[ticks] key not found at [capture_function]");
            }
        } else
        {
            throw new TemplateLoaderException("[radius] key not found at [capture_function]");
        }
    }

    private static Set<SpawnPointBuilder> loadSpawnPoints(JSONArray spawnPointsJSON) throws TemplateLoaderException
    {
        Set<SpawnPointBuilder> spawnPoints = new HashSet<>();

        for (Object jsonObject : spawnPointsJSON)
        {
            JSONObject spawnPointJSON = (JSONObject) jsonObject;

            if (spawnPointJSON.containsKey("name"))
            {
                if (spawnPointJSON.containsKey("location"))
                {
                    if (spawnPointJSON.containsKey("radius"))
                    {
                        String name = (String) spawnPointJSON.get("name");
                        if (name.startsWith("$"))
                        {
                            name = getString(name);
                        }

                        Object radiusObject = spawnPointJSON.get("radius");
                        double radius = getDouble(radiusObject);

                        JSONObject locationJSON = (JSONObject) spawnPointJSON.get("location");
                        ArenaLocationBuilder arenaLocationBuilder = loadArenaLocation(locationJSON);

                        SpawnPointBuilder spawnPointBuilder =
                                new SpawnPointBuilder(name, arenaLocationBuilder,
                                        radius);

                        spawnPoints.add(spawnPointBuilder);
                    } else
                    {
                        throw new TemplateLoaderException("[radius] key not found at [spawn_point]");
                    }
                } else
                {
                    throw new TemplateLoaderException("[location] key not found at [spawn_point]");
                }
            } else
            {
                throw new TemplateLoaderException("[name] key not found at [spawn_point]");
            }
        }

        return spawnPoints;
    }

    private static ArenaLocationBuilder loadArenaLocation(JSONObject locationJSON) throws TemplateLoaderException
    {
        if (locationJSON.containsKey("world"))
        {
            if (locationJSON.containsKey("x"))
            {
                if (locationJSON.containsKey("y"))
                {
                    if (locationJSON.containsKey("z"))
                    {
                        String worldName = getString(locationJSON.get("world"));

                        if (worldName.startsWith("$"))
                        {
                            worldName = getString(worldName);
                        }
                        World world = Bukkit.getWorld(worldName);

                        if (world == null)
                        {
                            throw new TemplateLoaderException("World with name [" + worldName + "] not found");
                        }

                        Object xString = locationJSON.get("x");
                        double x = getDouble(xString);

                        Object yString = locationJSON.get("y");
                        double y = getDouble(yString);

                        Object zString = locationJSON.get("z");
                        double z = getDouble(zString);

                        Location location = new Location(world, x, y, z);

                        return new ArenaLocationBuilder(UUID.randomUUID(), location);
                    } else
                    {
                        throw new TemplateLoaderException("[z] key not found at [location] at [spawn_point]");
                    }
                } else
                {
                    throw new TemplateLoaderException("[y] key not found at [location] at [spawn_point]");
                }
            } else
            {
                throw new TemplateLoaderException("[x] key not found at [location] at [spawn_point]");
            }
        } else
        {
            throw new TemplateLoaderException("[world] key not found at [location] at [spawn_point]");
        }
    }

    private static Set<TeamBuilder> loadTeams(JSONArray teamsJSON) throws TemplateLoaderException
    {
        Set<TeamBuilder> teamBuilders = new HashSet<>();

        for (Object teamObject : teamsJSON)
        {
            JSONObject teamJSON = (JSONObject) teamObject;

            if (teamJSON.containsKey("name"))
            {
                if (teamJSON.containsKey("arena_color"))
                {
                    String name = getString(teamJSON.get("name"));

                    UUID uuid;
                    if (teamJSON.containsKey("uuid"))
                    {
                        uuid = getUuid(teamJSON.get("uuid"));
                    } else
                    {
                        uuid = UUID.randomUUID();
                    }

                    String arenaColorString = (String) teamJSON.get("arena_color");
                    ArenaColor arenaColor;
                    if (arenaColorString.startsWith("$"))
                    {
                        arenaColor = ArenaColor.valueOf(getString(arenaColorString));
                    } else
                    {
                        arenaColor = ArenaColor.valueOf(arenaColorString);
                    }

                    teamBuilders.add(new TeamBuilder(uuid, name, arenaColor));
                } else
                {
                    throw new TemplateLoaderException("[arena_color] key not found at [team]");
                }
            } else
            {
                throw new TemplateLoaderException("[name] key not found at [team]");
            }
        }
        return teamBuilders;
    }

    private static UUID getUuid(Object object) throws TemplateLoaderException
    {

        UUID uuid = null;
        if (object instanceof String value)
        {
            if (value.startsWith("$"))
            {
                value = value.substring(1);
                if (variables.containsKey(value))
                {
                    uuid = UUID.fromString((String) variables.get(value));
                } else
                {
                    throw new TemplateLoaderException("Variable not found");
                }
            }
        } else
        {
            throw new TemplateLoaderException("Wrong type");
        }
        return uuid;
    }

    private static String getString(Object object) throws TemplateLoaderException
    {
        if (object instanceof String value)
        {
            if (value.startsWith("$"))
            {
                value = value.substring(1);
                if (variables.containsKey(value))
                {
                    value = (String) variables.get(value);
                } else
                {
                    throw new TemplateLoaderException("Variable not found");
                }
            }
        } else
        {
            throw new TemplateLoaderException("Wrong type");
        }
        return value;
    }

    private static ScoreType getAction(Object object) throws TemplateLoaderException
    {
        return ScoreType.valueOf(getString(object).toUpperCase());
    }

    private static int getInt(Object object) throws TemplateLoaderException
    {
        int value;
        if (object instanceof Long)
        {
            long valueLong = (long) object;
            value = (int) valueLong;
        } else if (object instanceof String valueString)
        {
            if (valueString.startsWith("$"))
            {
                valueString = valueString.substring(1);
                if (variables.containsKey(valueString))
                {
                    value = Integer.parseInt((String) variables.get(valueString));
                } else
                {
                    throw new TemplateLoaderException("Variable not found");
                }
            } else
            {
                value = Integer.parseInt(valueString);
            }
        } else
        {
            throw new TemplateLoaderException("Wrong type");
        }
        return value;
    }

    private static double getDouble(Object object) throws TemplateLoaderException
    {
        double value;
        if (object instanceof Double)
        {
            value = (double) object;
        } else if (object instanceof String valueString)
        {
            if (valueString.startsWith("$"))
            {
                valueString = valueString.substring(1);
                if (variables.containsKey(valueString))
                {
                    value = Double.parseDouble((String) variables.get(valueString));
                } else
                {
                    throw new TemplateLoaderException("Variable not found");
                }
            } else
            {
                value = Double.parseDouble(valueString);
            }
        } else if (object instanceof Long)
        {
            value = (long) object;
        } else
        {
            throw new TemplateLoaderException("Wrong type");
        }
        return value;
    }

    private static boolean getBool(Object object) throws TemplateLoaderException
    {
        boolean value;
        if (object instanceof Boolean)
        {
            value = (boolean) object;
        } else if (object instanceof String valueString)
        {
            if (valueString.startsWith("$"))
            {
                valueString = valueString.substring(1);
                if (variables.containsKey(valueString))
                {
                    value = Boolean.parseBoolean((String) variables.get(valueString));
                } else
                {
                    throw new TemplateLoaderException("Variable not found");
                }
            } else
            {
                value = Boolean.parseBoolean(valueString);
            }
        } else
        {
            throw new TemplateLoaderException("Wrong type");
        }
        return value;
    }

    public static IEntitySelector getEntitySelector(Object object) throws TemplateLoaderException
    {
        IEntitySelector entitySelector;
        if (object instanceof String value)
        {
            if (value.startsWith("$"))
            {
                value = value.substring(1);
                if (variables.containsKey(value))
                {
                    value = (String) variables.get(value);
                } else
                {
                    throw new TemplateLoaderException("Variable not found");
                }
            }

            UUID uuid = null;
            try
            {
                uuid = UUID.fromString(value);
            } catch (IllegalArgumentException exception)
            {

            }

            if (uuid != null)
            {
                return new EntitySelector(uuid);
            } else
            {
                return new EntitySelectorByName(value);
            }
        } else
        {
            throw new TemplateLoaderException("Wrong type");
        }
    }
}
