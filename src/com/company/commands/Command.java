package com.company.commands;

import com.company.Game;

/**
 * This class is part of the Divina Commedia simulator application.
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of four strings: a command word other four
 * words,(for example, if the command was "use bucket on river", then the four strings
 * obviously are "use", "bucket", "on" and"river").
 *
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 *
 * If the command had only one word, then the other words are <null>.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Command
{
    protected String commandWord;
    protected String secondWord;
    protected String thirdWord;
    protected String fourthWord;
    protected Game game;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     * @param thirdWord The third word of the command.
     * @param fourthWord The fourth word of the command.
     */
    public Command(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
        this.fourthWord = fourthWord;
        this.game = game;
    }

    public Command(String firstWord, String secondWord, String thirdWord, Game game){
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
        this.game = game;
    }

    public Command(String firstWord, String secondWord, Game game){
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.game = game;
    }

    public Command(Game game){
        this.game = game;
    }

    /**
     * TODO OOOLD
     * @param firstWord
     * @param secondWord
     * @param thirdWord
     * @param fourthWord
     */
    public Command(String firstWord, String secondWord, String thirdWord, String fourthWord){
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
        this.fourthWord = fourthWord;
    }



    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord()
    {
        if (commandWord == null){
            return "";
        }
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return The third word of this command. Returns null if there was no
     * third word.
     */
    public String getThirdWord(){
        return thirdWord;
    }

    /**
     * @return The fourth word of this command. Returns null if there was no
     * fourth word.
     */
    public String getFourthWord(){
        return fourthWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    public boolean execute(){
        return false;
    }

    @Override
    public String toString(){
        return commandWord + " " + secondWord;
    }
}

