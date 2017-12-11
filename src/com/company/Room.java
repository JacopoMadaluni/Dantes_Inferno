package com.company;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room or place in the adventure game.
 *
 * This class is part of the Dante's Inferno application.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other location via exits.  For each existing exit, the room
 * stores a reference to the neighboring room.
 *
 */

public class Room
{
    private String name;
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashSet<Creature> creatures;
    private HashSet<Item> items;
    private HashSet<String> exitsPresentations; // user friendly exit message
    private boolean hasCreature;
    private boolean hasItem;
    private boolean locked;
    private String lockedMessage;
    /**
     * Create a room described "description".
     * The exits must be set after the creation of the room.
     * @param description The room's description.
     */
    public Room(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.locked = false;
        exits = new HashMap<>();
        creatures = new HashSet<>();
        items = new HashSet<>();
        exitsPresentations = new HashSet<>();
        hasCreature = false;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * Set the message the user will get when he looks around him.
     * example: "not far away, you see a hill"
     * @param presentation The new presentation.
     */
    public void addExitPresentation(String presentation){
        exitsPresentations.add(presentation);
    }

    /**
     * Set the message to print when the player tries to go to a locked room.
     * @param message The message to print.
     */
    public void setLockedMessage(String message){
        lockedMessage = message;
    }

    /**
     * @return The name keyword of the room.
     * (the one defined in the constructor).
     */
    public String getName(){
        return name;
    }

    /**
     * @return the locked message to be printed.
     */
    public String getLockedMessage(){
        return lockedMessage;
    }

    /**
     * Locks the room.
     * Locked rooms are not accessible.
     */
    public void lock(){
        locked = true;
    }

    /**
     * Unlocks the room.
     */
    public void unlock(){
        locked = false;
    }

    /**
     * @return true if the room is locked.
     */
    public boolean isLocked(){
        return locked;
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You arrive to the hill.
     *     There is a lion in the grass.
     *     You see a bucket on the ground.
     *     ...
     * Gets printed by the game the first time the player enters a room.
     * @return A long description of this room.
     */
    public String getLongDescription()
    {
        String update = "" + description;
        if (hasCreature){
            for (Creature c : creatures){
                update = update + c.getPresentation();
            }
        }
        if (hasItem){
            for (Item i : items){
                update = update + i.getDescription();
            }
        }
        return update + getExitsDescription();
    }

    /**
     * @return the presentation of every neighbor room.
     */
    public String getExitsDescription(){
        String description = "";
        for (String presentation : exitsPresentations){
            description = description + presentation;
        }
        return description;
    }

    /**
     * Return a description in the form:
     *   You see a lion in the grass.
     *   You see a ghost near a tree.
     *   ...
     * Gets printed by the game when a "see" command is called.
     * @return what the player has around.
     *
     */
    public String updateDescription(){
        String update = "You are in the " + getName() + ".\n";
        if (hasCreature){
            for (Creature c : creatures){
                update = update + c.getPresentation();
            }
        }
        if (hasItem){
            for (Item i : items){
                update = update + i.getDescription();
            }
        }
        return update + getExitsDescription();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits keywords: hill, clear, forest.
     * @return The keywords of the room's exits.
     */
    public String getExitString()
    {
        String returnString = "Exits keywords:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit + ",";
        }
        return returnString + "\n";
    }

    /**
     * Removes an item from the room.
     * @param item The name of the item to remove.
     * @throws concurrency exception.
     */
    public void removeItem(String item){
        for (Item i : items){
            if (i.getName().equals(item)){
                items.remove(i);
                return;
            }
        }
    }

    /**
     * Removes a creature from the room.
     * @param creature The creature to remove.
     */
    public void removeCreature(Creature creature){
        creatures.remove(creature);
    }

    /**
     * Puts a creature into the room.
     * @param creature The creature to be put.
     */
    public void putCreature(Creature creature){
        if (!hasCreature){
            creatures.add(creature);
            hasCreature = true;
        }else{
            creatures.add(creature);
        }
    }

    /**
     * Puts an item into the room.
     * @param item The item to be put.
     */
    public void putItem(Item item){
        if (!hasItem){
            items.add(item);
            hasItem = true;
        }else{
            items.add(item);
        }
    }

    /**
     * Given the name of the item, returns the item.
     * If no item matches the name, returns null.
     * @param item The name of the item.
     */
    public Item getItem(String item){
        for (Item i : items){
            if (i.getName().equals(item)){
                return i;
            }
        }
        return null;
    }

    /**
     * Given the name, returns the creature.
     * If no creature matches the name, returns null.
     * @param name The name of the creature.
     */
    public Creature getCreature(String name){
        for (Creature c : creatures){
            if (c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
}


