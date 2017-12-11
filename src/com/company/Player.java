package com.company;
import java.util.HashSet;
/**
 * The player class of the game.
 * This class stores all the informations regarding the player such as:
 * The weight, inventory and some winning scenario informations.
 */
public class Player{
    private int weight = 0;
    private int maxWeight;
    private HashSet<Item> inventory;
    private static boolean hasVirgil;
    private boolean worthy;

    /**
     * Creates a new player.
     */
    public Player(){
        maxWeight = 10;
        inventory = new HashSet<>();
        hasVirgil = false;
        worthy = true;
    }

    /**
     * @return The current weight of the player.
     */
    public int getWeight(){
        return weight;
    }

    /**
     * @return True if the player didn't kill anyone.
     */
    public boolean isWorthy(){
        return worthy;
    }

    /**
     * Sets the new boolean for the winning or losing scenario.
     * @param b The new boolean: false = loosing scenario.
     */
    public void setWorthy(boolean b){
        worthy = b;
    }

    /**
     * @return True if the player has the special character with him.
     */
    public boolean hasVirgil(){
        return hasVirgil;
    }

    /**
     * Sets the presence of the special character.
     * @param b The new boolean: true = the player has the special character with him.
     */
    public void setVirgil(boolean b){
        hasVirgil = b;
    }

    /**
     * @return true if the player can take the item.
     * @param item The item the player wants to take.
     */
    public boolean canTake(Item item){
        if (item.getWeight() + weight <= maxWeight){
            return true;
        }
        return false;
    }

    /**
     * Add an item to the inventory.
     * @param item The item to add.
     */
    public void takeItem(Item item){
        inventory.add(item);
        weight = weight + item.getWeight();
    }

    /**
     * Given the name of the item, returns the item from the inventory.
     * If there is no item matching the name, returns null.
     * @return The Item from the inventory.
     * @param item The name of the item.
     */
    public Item getItem(String item){
        for (Item i : inventory){
            if (i.getName().equals(item)){
                return i;
            }
        }
        return null;
    }

    /**
     * Checks whether the player has an item in his inventory.
     * @return True if the player has the item.
     * @param item The name of the item.
     */
    public boolean has(String item){
        for (Item i : inventory){
            if (i.getName().equals(item)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return A string containing the items in the inventory + their informations ready to be printed by the game.
     */
    public String getInventory(){
        if (this.inventory.isEmpty()){
            return "You have no items.\n";
        }else{
            String inventory = "You have:\n";
            for (Item i : this.inventory){
                inventory = inventory + i.getName() + ": weight = " + i.getWeight() + " (" + i.getType() + ")\n";
            }
            return inventory + "Total weight = " + weight + "/" + maxWeight + "\n";
        }
    }

    /**
     * Removes an item from the inventory.
     * @param item The name of the item to remove.
     */
    public void removeItem(String item){
        for (Item i : inventory){
            if (i.getName().equals(item)){
                inventory.remove(i);
                weight = weight - i.getWeight();
                return;
            }
        }
    }
}

