package com.company;

/**
 * This is the special item class.
 * The Bucket is a child of the class Item.
 * The Bucket can be filled or empty.
 */
public class Bucket extends Item
{
    private boolean filled;
    /**
     * Creates a new bucket.
     * The bucket is set to be grabable by default.
     * The bucket is empty by default.
     * @param name The name of the bucket.
     * @param weight The weight of the bucket.
     * @param presentation The presentation of the bucket.
     */
    public Bucket(String name, int weight, String presentation){
        super(name, weight, presentation, true);
        this.filled = false;

    }

    /**
     * Sets the filled field to true.
     * Changes the examine message consequently.
     */
    public void fill(){
        filled = true;
        setExamineMessage("A huge bucket filled with water.\n");
    }

    /**
     * Sets the filled field to false.
     * Changes the examine message consequently.
     */
    public void empty(){
        filled = false;
        setExamineMessage("A huge bucket made of wood.\n");
    }

    /**
     * @return True if the bucket is filled with water.
     */
    public boolean isFilled(){
        return filled;
    }

    /**
     * Sets a description for the bucket.
     * (The bucket is invisible at the beginning, the player know there is a
     * bucket to take by examining the hut in the "hill" room.)
     */
    public void reveal(){
        super.description = "The bucket is on the ground.\n";
    }
}
