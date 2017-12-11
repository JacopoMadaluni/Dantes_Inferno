package com.company;

/**
 * The item class of the game.
 * This class stores all the informations regarding an Item.
 */
public class Item{
    protected boolean grabable;
    protected int weight;
    protected String item;
    protected String description;
    protected boolean isWeapon;
    protected String type;
    protected boolean locked;
    protected String lockedMessage;
    protected String examineMessage;

    /**
     * Creates a new item.
     * @param name The name keyword of the item.
     * @param weight The weight of the item.
     * @param description The description of the item (what gets printed when the player looks around him).
     * @param grabable True if the item can be taken by the player.
     */
    public Item(String name, int weight, String description, boolean grabable){
        this.item = name;
        this.examineMessage = "It seems there's nothing to see.\n";
        this.description = description;
        this.grabable = grabable;
        this.isWeapon = false;
        this.locked = false;
        this.weight = weight;
        this.type = "MISC";
    }

    /**
     * Changes the name of the item.
     * @param newName The new name keyword.
     */
    public void setName(String newName){
        this.item = newName;
    }

    /**
     * Sets the message that gets printed when the player tries to use a locked item.
     * @param message The message that tells the player the item is locked.
     */
    public void setLockedMessage(String message){
        lockedMessage = message;
    }

    /**
     * Set the message that gets printed when the player examines the item.
     * @param message The message that gets printed.
     */
    public void setExamineMessage(String message){
        examineMessage = message;
    }

    /**
     * Sets the type of the item (additional informations).
     * The default type is "MISC".
     * @param newType The type of the item: weapon, material, food, ecc..
     */
    public void setType(String newType){
        type = newType;
    }

    /**
     * Sets the item to be able to kill characters.
     */
    public void setWeapon(){
        isWeapon = true;
    }

    /**
     * Unlocks the item.
     */
    public void unlock(){
        locked = false;
    }

    /**
     * Locks the item.
     */
    public void lock(){
        locked = true;
    }

    /**
     * @return the description of the item.
     * (the one specified in the constructor).
     */
    public String getDescription(){
        return description;
    }

    /**
     * @return True if the player can take the item.
     */
    public boolean isGrabable(){
        return grabable;
    }

    /**
     * @return True if the item is locked.
     */
    public boolean isLocked(){
        return locked;
    }

    /**
     * @return The examine message that gets printed when the player examines an item.
     */
    public String getExamineMessage(){
        return examineMessage;
    }

    /**
     * @return The message that tells the player the item is locked.
     */
    public String getLockedMessage(){
        return lockedMessage;
    }

    /**
     * @return The weight of the item.
     */
    public int getWeight(){
        return weight;
    }

    /**
     * @return True if the item is a weapon.
     */
    public boolean isWeapon(){
        return isWeapon;
    }

    /**
     * @return A string that tells the type of the item.
     */
    public String getType(){
        return type;
    }

    /**
     * @return The name keyword of the item.
     */
    public String getName(){
        return item;
    }
}

