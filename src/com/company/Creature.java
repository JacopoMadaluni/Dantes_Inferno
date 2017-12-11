package com.company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
/**
 * This is the creature class of the Divina Commedia simulator application.
 * In this class are stored all the informations regarding a creature (or a character)
 * in the game.
 *
 * The same class is used both for dynamic creature and static creatures.
 * Dynamic creatures changes their behaviour basing on the actions of the player.
 * Static creatures don't change behaviour what so ever.
 */
public class Creature{
    protected String name;
    protected String presentation;
    private HashMap< Integer, ArrayList<String> > dialogues;  // the integer is the status of the creature.
    private Integer status;                                   // the status of the creature is managed by the StatusManager.
    protected int dialogueIndex;
    protected boolean hasDialogue;
    private boolean hasItem;
    private HashSet<Item> creatureInventory;
    private boolean isLocked;
    private String lockedMessage;


    /**
     * Creates a new creature.
     * @param name The name keyword of the creature.
     * @param presentation The presentation of the creature.
     */
    public Creature(String name, String presentation){
        this.name = name;
        this.status = 1;
        this.presentation = presentation;
        this.hasDialogue = false;
        this.dialogues = new HashMap<>();
        this.creatureInventory = new HashSet<>();
        this.isLocked = false;
        initializeDialogues();
    }

    /**
     * Initializes the statuses and the relative dialogue array.
     */
    private void initializeDialogues(){
        this.dialogues.put(1, new ArrayList<String>());     // Static creatures status.
        this.dialogues.put(2, new ArrayList<String>());
        this.dialogues.put(3, new ArrayList<String>());
        this.dialogues.put(4, new ArrayList<String>());
        this.dialogues.put(5, new ArrayList<String>());     // event status.
        this.dialogues.put(6, new ArrayList<String>());
        this.dialogues.put(7, new ArrayList<String>());
        this.dialogues.put(8, new ArrayList<String>());
        this.dialogues.put(9, new ArrayList<String>());
        this.dialogues.put(10, new ArrayList<String>());    // final status.
    }

    /**
     * @return The presentation of the creature.
     */
    public String getPresentation(){
        return presentation;
    }

    /**
     * @return True if the creature has at least one item.
     */
    public boolean hasItems(){
        if (creatureInventory.size() != 0){
            return true;
        }
        return false;
    }

    /**
     * (The player cannot talk to a locked creature)
     * @return True if the creature is locked.
     *
     */
    public boolean isLocked(){
        return isLocked;
    }

    /**
     * Locks the creature.
     */
    public void lock(){
        isLocked = true;
    }

    /**
     * Unlocks the creature.
     */
    public void unlock(){
        isLocked = false;
    }

    /**
     * @return The message to print when the player tries to talk to a locked creature.
     */
    public String getLockedMessage(){
        return lockedMessage;
    }

    /**
     * Sets the locked message.
     * @param message The locked message.
     */
    public void setLockedMessage(String message){
        lockedMessage = message;
    }

    /**
     * Adds a dialogue to the creature.
     * @param status The status in which the dialogue will be printed.
     * @param dialogue The dialogue string.
     */
    public void addDialogue(int status, String dialogue){
        if (!hasDialogue){
            hasDialogue = true;
            dialogueIndex = 0;
        }
        dialogues.get(status).add(dialogue);
    }

    /**
     * Puts an item into the inventory of the creature.
     * @param The item to take.
     */
    public void takeItem(Item item){
        creatureInventory.add(item);
    }

    /**
     * Removes an item from the inventory of the creature.
     * @param item The item to remove.
     */
    public void removeItem(Item item){
        creatureInventory.remove(item);
    }

    /**
     * Removes an item from the inventory of the creature.
     * @param item The name keyword of the item to remove.
     */
    public void removeItem(String item){
        for (Item i : creatureInventory){
            if (i.getName().equals(item)){
                creatureInventory.remove(i);
                return;
            }
        }
    }

    /**
     * Given the name of the item, returns the item.
     * If the creature has no such item, returns null.
     * @param item The name keyword of the item.
     * @return The item or null (if the creature doesn't have it).
     */
    public Item getItem(String item){
        for (Item i : creatureInventory){
            if (i.getName().equals(item)){
                return i;
            }
        }
        return null;
    }

    /**
     * @return True if the creature has at least one dialogue.
     */
    public boolean hasDialogue(){
        return hasDialogue;
    }

    /**
     * @return the current status of the creature.
     */
    public int getStatus(){
        return status;
    }

    /**
     * Changes the name of the creature.
     * @param name The new name keyword.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Changes the presentation of the creature.
     * @param presentation The new presentation.
     */
    public void setPresentation(String presentation){
        this.presentation = presentation;
    }

    /**
     * This method returns the right dialogue to print.
     * @return The dialogue to print.
     */
    public String getDialogue(){
        return dialogues.get(status).get(dialogueIndex);
    }

    /**
     * This method increases the dialogue index so that the next time
     * the player talks to the same creature the next dialogue will be returned by the getDialogue() method.
     * This method also assures that the index never goes out of range.
     */
    public void increaseDialogueIndex(){
        int maxDialogueIndex = getMaxDialogueIndex();
        if (!((dialogueIndex + 1) == maxDialogueIndex)){
            dialogueIndex += 1;

        }else{
            dialogueIndex = 0;
        }
    }

    /**
     * This method returns the current max dialogue index possible.
     * (Since different statuses may have different numbers of dialogues)
     * @return The current max dialogue index.
     */
    protected int getMaxDialogueIndex(){
        return dialogues.get(status).size();
    }

    /**
     * Advances the status of the creature.
     * The dialogue index is reset to 0 (avoid out-of-range errors, the dialogue array is changed).
     */
    public void advanceStatus(){
        status++;
        dialogueIndex = 0;
    }

    /**
     * Decreases the status of the creature.
     * The dialogue index is reset to 0 (avoid out-of-range errors, the dialogue array is changed).
     */
    public void decreaseStatus(){
        status--;
        dialogueIndex = 0;
    }

    /**
     * Changes the status of the creature.
     * @param newStatus The new status.
     */
    public void setStatus(int newStatus){
        status = newStatus;
        dialogueIndex = 0;
    }

    /**
     * @return The name keyword of the creature.
     */
    public String getName(){
        return name;
    }

    /**
     * @return The inventory of the creature.
     */
    public HashSet getInventory(){
        return creatureInventory;
    }
}
