package com.company;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
/**
 * The Event Manager is a class of the Divina Commedia simulator application.
 * The Event Manager checks the implications of any action the player performs.
 * Tells the game how to change consequently.
 *
 * @author (Jacopo Madaluni)
 * @version (6.12.2017)
 */
public class EventManager
{
    private StatusManager statusManager;
    private HashMap<Creature, Integer> eventDatabase;
    private Game game;
    private boolean mineIronEventHappened = false;
    private boolean cutMeatEventHappened = false;

    /**
     * Creates a new Event Manager.
     * @param game The current Game.
     */
    public EventManager(Game game){
        eventDatabase = new HashMap<>();
        statusManager = new StatusManager(game);
        this.game = game;
        //initializeEvents();
    }

    /**
     * Asks the status manager the status of a creature.
     */
    private int getStatus(Creature creature){
        return statusManager.getStatus(creature);
    }

    /**
     * Processes the use event of the type: use <item> on <creature>.
     * @param item The item used.
     * @param creature The creature on which the item has been used.
     */
    public void processUseEvent(Item item, Creature creature){
        if (item.isWeapon()){
            if (creature.getName().equals("lucifer")){
                game.luciferKillsYouEvent();
            }else if (creature.getName().equals("virgil")){
                game.print("The " + item.getName() + " passes through virgil, producing no effect.\n");
            }else {
                killEvent(creature);
            }
        }else if (item.getName().equals("bucket") && creature.getName().equals("butcher") && getStatus(creature) == 6){
            Item bucketItem = game.getItem("bucket");
            Bucket bucket = (Bucket) bucketItem;
            if (bucket.isFilled()){
                statusManager.setStatus(creature, 5);
                butcherGivesKnifeEvent(creature);
            }
        }else{
            game.print("No effect produced.\n");
        }
    }

    /**
     * Processes the use event of type: use <item> on <item>.
     * @param item1 The item used.
     * @param item2 The item on which the first item has been used.
     */
    public void processUseEvent(Item item1, Item item2){
        if (item1.getName().equals("bucket") && item2.getName().equals("river")){
            Item bucketItem = game.getItem("bucket");
            Bucket bucket = (Bucket) bucketItem;
            bucket.fill();
            game.print("You filled the bucket with water.\n");
        }else if (item1.getName().equals("knife") && item2.getName().equals("corpse")){
            cutMeatEvent(item1);
        }else if (item1.getName().equals("pickaxe") && item2.getName().equals("rocks")){
            mineIronEvent();
        }else{
            game.print("The action produced no effect.\n");
        }
    }

    /**
     * Processes the use event of type: use <item>
     * @param item The item used.
     */
    public void processUseEvent(Item item){
        if (item.getName().equals("boat")){
            useBoatEvent();
        }else{
            game.print("On what?\n");
        }
    }

    /**
     * Processes the give event.
     * @param item The item given.
     * @param creature The creature that receives the item.
     */
    public void processGiveEvent(Item item, Creature creature){
        if (item.getName().equals("meat") && creature.getName().equals("cerberus")){
            cerberusFallsAsleepEvent();
            return;
        }else if(item.getType().equals("material") && creature.getName().equals("alchemist")){
            if (getStatus(creature) > 3){
                alchemistGivesGoldenCoinEvent(item, creature);
                statusManager.setStatus(creature, 5);
                return;
            }
        }else if (item.getName().equals("goldencoin") && creature.getName().equals("charon")){
            goToFinalPlatformEvent();
            return;
        }
        game.print("The " + creature.getName() + " doesn't want the " + item.getName() + ".\n");
    }

    /**
     * Processes the talk event.
     * Gets called every time the player talks to a creature.
     * @param creature The creature the player talked with.
     */
    public void processDialogueEvent(Creature creature){
        if (creature.getName().equals("lucifer")){
            game.endingEvent();
            return;
        }else if (creature.getName().equals("ghost")){
            game.revealVirgiliusEvent(creature);
            return;
        }
        // general case:
        statusManager.dialogueUpdate(creature);
        if (5 == getStatus(creature)){
            String name = creature.getName();
            switch (name){
                case "stranger":
                    strangerGivespickaxeEvent(creature);
                    break;
                case "guardian":
                    guardianEvent(creature);
                    break;
                default:
                    break;

            }
        }
    }

    /**  All the events (the game actually performs the events).
     See game for better comments on these events.             */

    /**
     */
    private void strangerGivespickaxeEvent(Creature stranger){
        game.strangerGivesAxeEvent(stranger);
    }

    private void cutMeatEvent(Item knife){
        if (!cutMeatEventHappened){
            game.cutMeatEvent(knife);
            cutMeatEventHappened = true;
        }else{
            game.print("Your knife is broken.\n");
        }
    }

    private void butcherGivesKnifeEvent(Creature creature){
        game.butcherGivesKnifeEvent(creature);
    }

    /**
     * General event of a creature being killed.
     */
    private void killEvent(Creature creature){
        if (creature.getName().equals("guardian")){
            game.unlockRoom("limbo");
        }else if (creature.getName().equals("cerberus")){
            game.setCreaturePresentation("alchemist", "Behind the dead cerberus, there is the Alchemist.\n");
            game.unlockCreature("alchemist");
        }else if (creature.getName().equals("charon")){
            game.unlockItem("boat");
        }
        game.killCreature(creature);
    }

    private void goToFinalPlatformEvent(){
        game.goToFinalPlatformEvent();
    }

    private void mineIronEvent(){
        if (!mineIronEventHappened){
            game.mineIronEvent();
            mineIronEventHappened = true;
        }else{
            game.print("You already mined a big piece of iron.\n");
        }
    }

    private void guardianEvent(Creature guardian){
        game.guardianEvent(guardian);
    }

    private void cerberusFallsAsleepEvent(){
        game.cerberusFallsAsleep();
    }

    private void useBoatEvent(){
        game.useBoatEvent();
    }

    private void alchemistGivesGoldenCoinEvent(Item materialGiven, Creature alchemist){
        game.alchemistGivesGoldenCoinEvent(materialGiven, alchemist);
    }
}
