package com.company;
/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
            "go", "", "save", "quit", "help", "take", "drop", "look", "examine",  "inventory", "inv", "textSpeed", "see", "use", "give", "talk", "back", "exits",
            "exit"
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word.
     * @return true if it is, false if it isn't.
     */
    public static boolean mustBeSaved(String aString)
    {
        if (aString.equals("save")){
            return false;
        }
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    public boolean isUseCommand(String word1, String word3){
        if (word1.equals("use") && word3.equals("on")){
            return true;
        }else{
            return false;
        }
    }

    public static void printUserCommands(){
        System.out.println("go, quit, help, take, drop, inventory, " +
                "see, use, give, talk, back, " +
                "examine, look around, exits");
    }

    /**
     * Print all valid commands to System.out.
     */
    public static void showAll()
    {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}

