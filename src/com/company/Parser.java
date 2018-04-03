package com.company;
import com.company.commands.Command;
import com.company.commands.GoCommand;
import com.company.commands.QuitCommand;

import java.util.Scanner;

/**
 * This class is part of the Divina Commedia simulator application.
 *
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a four word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 *
 * @author  Michael Kölling, David J. Barnes and Jacopo Madaluni.
 * @version 2016.02.29
 */
public class Parser
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input
    private Game game;

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser(Game game)
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
        this.game = game;
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand()
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;
        String word4 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to four words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();   // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();   // get second word
                if (tokenizer.hasNext()){
                    word3 = tokenizer.next();    // get third word
                    if (tokenizer.hasNext()){
                        word4 = tokenizer.next();   //get fourth word
                        // note: it ignores all the words after the fourth word
                    }
                }
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if (word1 == null){
            word1 = "";   // if the player enters a null command nothing happens.
        }
        if(commands.isCommand(word1)) {
            Command testing = getCorrectCommand(word1,word2,word3, word4);
            if (testing != null){
                return testing;
            }
            return new Command(word1, word2, word3, word4);
        }
        else {
            return new Command(null, word2, word3, word4);
        }
    }

    private Command getCorrectCommand(String keyWord, String word2, String word3, String word4 ){
        switch(keyWord){
            case "help":
            case "see":
            case "look":
            case "go":
                return new GoCommand(keyWord, word2, word3, word4, game);
            case "back":
            case "take":
            case "examine":
            case "inventory":
            case "inv":
            case "drop":
            case "talk":
            case "use":
            case "give":
            case "quit":
                return new QuitCommand(keyWord, word2, word3, word4, game);
            case "textSpeed":
            case "exit":
            case "exits":
            default:
                return null;
        }
    }


}

